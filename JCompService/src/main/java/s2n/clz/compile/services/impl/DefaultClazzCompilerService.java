package s2n.clz.compile.services.impl;

import java.io.IOException;
import java.lang.reflect.Method;
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
import javax.tools.ToolProvider;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject.Kind;

import s2n.clz.compile.services.ByteArrayJavaClass;
import s2n.clz.compile.services.ClazzCompilerService;
import s2n.clz.compile.services.RamClassLoader;
import s2n.clz.compile.services.StringBuilderJavaSource;

public class DefaultClazzCompilerService implements ClazzCompilerService {
	// if not thread safe can move to a ThreadLocal
	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	@Override
	public Class<?> getClass(String data, String fullName) {
		try {
			final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

			JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
			fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
				public JavaFileObject getJavaFileForOutput(Location location, final String className, Kind kind, FileObject sibling)
						throws IOException {
					if (className.startsWith("s2n.dynamic.")) {
						ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
						classFileObjects.add(fileObject);
						return fileObject;
					} else
						return super.getJavaFileForOutput(location, className, kind, sibling);
				}
			};

			JavaFileObject source = buildSource(data, fullName);
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, Arrays.asList(source));
			Boolean result = task.call();

			for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics())
				System.out.println(d.getKind() + ": " + d.getMessage(null));
			fileManager.close();
			if (!result) {
				System.out.println("Compilation failed.");
				System.exit(1);
			}
			System.out.println("Build 4");

			byte[] clzdat = null;
			for (ByteArrayJavaClass cl : classFileObjects) {
				// byteCodeMap.put(TST_CLZ_NAME, cl.getBytes());
				clzdat = cl.getBytes();
			}
			RamClassLoader loader = new RamClassLoader(null);
			Class clz = loader.defineClass(fullName, clzdat);
			Object obj = clz.newInstance();
			System.out.println(obj);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private JavaFileObject buildSource(String data, String fullName) {
		StringBuilderJavaSource source = new StringBuilderJavaSource(fullName);
		source.append(data);
		return source;
	}

}
