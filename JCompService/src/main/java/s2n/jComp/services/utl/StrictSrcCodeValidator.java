package s2n.jComp.services.utl;

import s2n.jComp.dto.Result;

/**
 * Simple crude but strict non-exhaustive source code validator. Attempts to deny code that uses io or external API
 */
public class StrictSrcCodeValidator implements SrcCodeValidator {

	public void validate(String src, Result result) {
		if (src == null || src.length() < 10) {
			result.setCompileStatus(false);
			result.appendCompileErrors("Code missing " + ((src == null) ? "null code" : "short code " + src.length()));
		}
		// need a code parser and then inspect which packages it uses
		// right now its very crude
		// src = src.replace("  ", " ");
		vali(src, "java.io", result);
		vali(src, "java.net", result);
		vali(src, "apache.", result);
		vali(src, "spring.", result);
		vali(src, "javax.", result);
	}

	private boolean vali(String src, String find, Result result) {
		int i = src.indexOf(find);
		if (i > -1) {
			result.setCompileStatus(false);
			result.appendCompileErrors("Validation failed found :").appendCompileErrors(find).appendCompileErrors("\n");
			return false;
		}
		return true;
	}

	@Override
	public boolean isThreadSafe() {
		return true;
	}

}
