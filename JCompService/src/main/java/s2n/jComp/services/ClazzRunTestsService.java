package s2n.jComp.services;

import s2n.jComp.dto.Result;
import s2n.jComp.entities.CodeQuestion;

public interface ClazzRunTestsService {
	void runTests(Class<?> target, Result result, CodeQuestion cq);
}
