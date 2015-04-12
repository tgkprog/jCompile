package s2n.clz.compile.services;

import s2n.clz.compile.dto.Result;

public interface Tester {
	void test(Class<?> clz, Result results);
}
