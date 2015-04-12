package s2n.jComp.services;

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
import javax.tools.JavaFileObject.Kind;
import javax.tools.ToolProvider;

import s2n.jComp.entities.ByteArrayJavaClass;
import s2n.jComp.entities.StringBuilderJavaSource;


/**
*
*/
public class RamClassTest {
	private static final String TST_CLZ_NAME = "s2n.dynamic.s1.Tst";

	public static void main(final String[] args) throws IOException, ClassNotFoundException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

		JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
			public JavaFileObject getJavaFileForOutput(Location location, final String className, Kind kind, FileObject sibling)
					throws IOException {
				if (className.startsWith(className)) {
					ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
					classFileObjects.add(fileObject);
					return fileObject;
				} else
					return super.getJavaFileForOutput(location, className, kind, sibling);
			}
		};

		String className = TST_CLZ_NAME;
		JavaFileObject source = buildSource("Object");
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
		try {
			byte[] clzdat = null;
			for (ByteArrayJavaClass cl : classFileObjects){
				//byteCodeMap.put(TST_CLZ_NAME, cl.getBytes());
				clzdat = cl.getBytes();
			}
			RamClassLoader loader = new RamClassLoader(null);
			Class clz = loader.defineClass(TST_CLZ_NAME, clzdat);
			Object obj = clz.newInstance();
			System.out.println(obj);
			Method methodA = clz.getMethod("getThat", int.class);
			int i = 2;
			int 	rtn = (int)methodA.invoke(obj, i);
			System.out.println("Sent :" + i + ", got :" + rtn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*
	 * Builds the source for the subclass that implements the addEventHandlers method.
	 * 
	 * @return a file object containing the source in a string builder
	 */
	static JavaFileObject buildSource(String superclassName) throws IOException, ClassNotFoundException {
		StringBuilderJavaSource source = new StringBuilderJavaSource(TST_CLZ_NAME);
		source.append("package s2n.dynamic.s1;\n");
		source.append("public class Tst extends " + superclassName + " {");
		source.append("public int getThat(int v){ \nreturn v+ 3;\n}");

		source.append("} ");
		return source;
	}
}
