package s2n.jComp.entities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Class with the bytecode of class.
 */
public class ByteArrayJavaClass extends SimpleJavaFileObject {
	private ByteArrayOutputStream stream;

	public ByteArrayJavaClass(String name) {
		super(URI.create("bytes:///" + name), Kind.CLASS);
		stream = new ByteArrayOutputStream();
	}

	public OutputStream openOutputStream() throws IOException {
		return stream;
	}

	public byte[] getBytes() {
		return stream.toByteArray();
	}
}
