package external.tester;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import s2n.jComp.dto.Result;
import s2n.jComp.services.Tester;
import s2n.jComp.services.utl.MiscUtl;


public class AddTwoTester implements Tester{
	private static final Logger logger = LogManager.getLogger(AddTwoTester.class);
	@Override
	public void test(Class<?> clz, Result result) {
		String msg = null;
		try {
			msg = "Testing add 2 : create";
			Object o = clz.newInstance();
			msg = "Looking for method add(int,int) ";
			Method m = clz.getMethod("add", int.class, int.class);
			msg = "Invoke methd ";
			String r = m.invoke(o, 1, 8) + "";
			int rtn = Integer.parseInt(r);
			if(rtn != 9){
				msg = "Add (1 + 8) gave " + rtn;
				MiscUtl.fillError(logger, result, null, msg, 2);
			}
		} catch (Throwable e) {
			MiscUtl.fillError(logger, result, e, msg, 2);
		}
		
	}

}

