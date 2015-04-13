package s2n.jComp.services.utl;

import org.apache.logging.log4j.Logger;

import s2n.jComp.dto.Result;

public class MiscUtl {

	public static void fillError(Logger logger, Result result, Throwable e, String msg, int typ) {
		logger.warn(msg + e, e);
		if (result != null) {
			if (typ == 1) {
				result.appendCompileErrors(msg);
				if (e != null) {
					result.appendCompileErrors(" " + e.toString());
				}
				result.appendCompileErrors("\n");
				result.setCompileStatus(false);
			} else {
				result.appendTestErrors(msg);
				if (e != null) {
					result.appendTestErrors(" " + e.toString());
				}
				result.appendTestErrors("\n");
				result.setTestStatus(false);
			}
		}
	}
}
