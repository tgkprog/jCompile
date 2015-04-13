package s2n.jComp.facade;

import s2n.jComp.dto.Result;

public interface ClzApiFacade {
	Result compileAndTest(String code, String name, String src);
}
