package s2n.jComp.services;

import java.util.Map;

/** 
 * Class loader to load classes from RAM - byte class definition
 * */
public final class RamClassLoader extends ClassLoader {

	private Map<String, byte[]> clzData;

	public RamClassLoader() {

	}

	public RamClassLoader(Map<String, byte[]> pclzData) {
		this.clzData = pclzData;
	}

	public Class findClass(String name) throws ClassNotFoundException {

		try {
			return defineClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Err " + e);
			return super.findClass(name);
		}
	}

	/** 
	 * Define an arbitrary class from its compiled bytes */
	
	public final Class<?> defineClass(String name, byte[] classBytes) throws ClassNotFoundException {
		if (classBytes == null)
			throw new ClassNotFoundException(name);
		Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
		if (cl == null)
			throw new ClassNotFoundException(name);
		return cl;
	}

	public Class<?> defineClass(String name) throws ClassNotFoundException {
		if (clzData == null) {
			throw new ClassNotFoundException(name + ", Also my class data map not initialized");
		}
		byte[] classBytes = clzData.get(name);
		if (classBytes == null)
			throw new ClassNotFoundException(name);
		Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
		if (cl == null)
			throw new ClassNotFoundException(name);
		return cl;
	}
}
