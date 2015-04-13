package s2n.jComp.services.utl;

import s2n.jComp.dto.Result;

public interface SrcCodeValidator {
	void validate(String src, Result result);
	boolean isThreadSafe();
}
