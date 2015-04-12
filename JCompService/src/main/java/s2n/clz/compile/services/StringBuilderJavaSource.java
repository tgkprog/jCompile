package s2n.clz.compile.services;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * A Java source that holds the code in a string builder. Uses SimpleJavaFileObject which is not std API but allows us to use in memory
 * String to compile. For this assignment not sure if we will have a general place to save and compile files. This is faster and more sure.
 * Also more secure as not writing to local file system.
 */
public class StringBuilderJavaSource extends SimpleJavaFileObject {
	private StringBuilder code;

	/**
	 * Constructs a new StringBuilderJavaSource.
	 * 
	 * @param name
	 *            identifier, coulld be the source file represented by this file object or in our case an arbitary name
	 */
	public StringBuilderJavaSource(String name) {
		super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		code = new StringBuilder();
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}

	public void append(String str) {
		code.append(str);
		code.append('\n');
	}
}
