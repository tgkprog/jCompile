package s2n.clz.compile.services;

import s2n.clz.compile.dto.Result;

public interface ClazzRunTestsService {
	Result runTests(Class target, Class<Tester> tester);
}
