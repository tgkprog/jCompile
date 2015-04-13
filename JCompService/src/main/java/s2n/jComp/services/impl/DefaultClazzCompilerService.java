package s2n.jComp.services.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.ToolProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import s2n.jComp.dao.CodeQuestionDao;
import s2n.jComp.dto.Result;
import s2n.jComp.entities.ByteArrayJavaClass;
import s2n.jComp.entities.CodeQuestion;
import s2n.jComp.entities.StringBuilderJavaSource;
import s2n.jComp.services.ClazzCompilerService;
import s2n.jComp.services.RamClassLoader;
import s2n.jComp.services.utl.MiscUtl;

@Service
public class DefaultClazzCompilerService implements ClazzCompilerService {
	private static final Logger logger = LogManager.getLogger(DefaultClazzCompilerService.class);

	// if not thread safe can move to a ThreadLocal or put in spring Thread scope
	private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	//@Autowired
	//@Qualifier(value = "questionsDao")
	private CodeQuestionDao codeQuestionDao = null;

	@Override
	@Cacheable(value = "codeQCache")
	public CodeQuestion getQuestionByCode(String code, Result result) {
		CodeQuestion cq = null;
		try {
			cq = codeQuestionDao.getByCode(code);
		} catch (Exception e) {
			MiscUtl.fillError(logger, result, e, "Question get code :" + code, 1);

		}
		return cq;
	}

	@Override
	public Class<?> getClass(String data, final String fullName, Result result) {
		try {
			
			final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

			JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
			clazProcess(data, fullName, result, classFileObjects, diagnostics, fileManager);

			byte[] clzdat = null;
			if (classFileObjects.size() == 0) {
				// error
				result.setCompileStatus(false);
				result.appendCompileErrors("Class not compiled\n");
				return null;
			}else if (classFileObjects.size() > 1) {
				result.appendCompileMsgs("More than 1 class found, tryig with all\n");
			}
			RamClassLoader loader = new RamClassLoader(null);
			for (ByteArrayJavaClass cl : classFileObjects) {
				// byteCodeMap.put(TST_CLZ_NAME, cl.getBytes());
				try {
					clzdat = cl.getBytes();
					Class<?> clz = loader.defineClass(fullName, clzdat);
					result.setCompileStatus(true);
					return clz;
				} catch (Throwable e) {
					logger.info("Define class Error will try next " + e);;
				}
			}
			result.appendCompileErrors("Class not defined \n");
			return null;
		} catch (Exception ex) {
			logger.warn("get Class :" + ex, ex);
			result.appendCompileErrors("Err :" + ex + " \n");
		}
		return null;
	}

	private void clazProcess(String data, final String fullName, Result result, final List<ByteArrayJavaClass> classFileObjects,
			DiagnosticCollector<JavaFileObject> diagnostics, JavaFileManager fileManager) throws IOException {
		fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
			public JavaFileObject getJavaFileForOutput(Location location, final String className, Kind kind, FileObject sibling)
					throws IOException {
				if (className.startsWith(fullName)) {
					ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
					classFileObjects.add(fileObject);
					return fileObject;
				} else
					return super.getJavaFileForOutput(location, className, kind, sibling);
			}
		};

		JavaFileObject source = buildSource(data, fullName);
		StringWriter sw = new StringWriter();
		JavaCompiler.CompilationTask task = compiler.getTask(sw, fileManager, diagnostics, null, null, Arrays.asList(source));
		Boolean compileResult = task.call();
		final String sws = sw.toString();
		if (sws != null && sws.length() > 0) {
			result.appendCompileMsgs("Compile Messages\n");
			result.appendCompileMsgs(sws + "\n");
		}
		List<Diagnostic<? extends JavaFileObject>> dia = diagnostics.getDiagnostics();
		if (dia.size() > 0) {
			result.appendCompileMsgs("Compile Diagnostics:\n");

			for (Diagnostic<? extends JavaFileObject> d : dia) {
				final String ss = d.getKind() + ": " + d.getMessage(null);
				logger.trace(ss);
				result.appendCompileMsgs(ss + "\n");
			}
		}
		fileManager.close();
		result.setCompileStatus(compileResult);
		if (!compileResult) {
			result.appendCompileErrors("Compilation failed.");

		}
		System.out.println("Build 4");
	}

	private JavaFileObject buildSource(String data, String fullName) {
		StringBuilderJavaSource source = new StringBuilderJavaSource(fullName);
		source.append(data);
		return source;
	}

	public CodeQuestionDao getCodeQuestionDao() {
		return codeQuestionDao;
	}

	public void setCodeQuestionDao(CodeQuestionDao defaultCodeQuestionDao) {
		this.codeQuestionDao = defaultCodeQuestionDao;
	}

}
