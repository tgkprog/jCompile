package s2n.jComp.services.utl;

import org.apache.logging.log4j.Logger;

import s2n.jComp.dto.Result;

public class MiscUtl {

	public static void fillError(Logger logger, Result result, Exception e, String msg, int typ) {
		logger.warn(msg + e, e);
		result.appendCompileErrors(msg + e + "\n");
		if(typ == 1){
			result.setCompileStatus(false);
		}else{
			result.setTestStatus(false);
		}
	}
}
