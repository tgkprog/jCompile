package external.tester;

import java.lang.reflect.Method;

import s2n.clz.compile.dto.Result;

public class TestUtl {
	public void invokeInt(Class clz, String mName, int paramIn, int expected, Result results){
		Method m = findMethod(clz, mName, int.class);
		if(m == null){
			//results.
			return;
		}

	}
	
	public Method findMethod(Class clz, String mName, Object ...params){
		Method m = null;
		return m;
	}

}
