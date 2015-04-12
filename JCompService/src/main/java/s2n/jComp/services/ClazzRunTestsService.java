package s2n.jComp.services;

import s2n.jComp.dto.Result;

public interface ClazzRunTestsService {
	Result runTests(Class target, Class<Tester> tester);
}
