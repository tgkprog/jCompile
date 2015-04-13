package s2n.jComp.services.impl;

import org.springframework.stereotype.Service;

import s2n.jComp.dto.Result;
import s2n.jComp.entities.CodeQuestion;
import s2n.jComp.services.ClazzRunTestsService;
import s2n.jComp.services.Tester;

@Service
public class DefaultClazzRunTestsService implements ClazzRunTestsService {
	
	public void runTests(Class<?> targetClz, Result result, CodeQuestion cq){
		String msg = null;
		try{
			msg = "Create tester";
			Tester tester = getTester(result, cq);
			msg = "Run tests";
			tester.test(targetClz, result);
		}catch(Throwable e){
			result.appendTestErrors("Error ").appendTestErrors(e.toString()).appendTestErrors(" at ")
			.appendTestErrors(msg);
		}
	}

	private Tester getTester(Result result, CodeQuestion cq) throws Throwable {
		String cls = cq.getTestClzName();
		Class<Tester> clz = (Class<Tester>)Class.forName(cls);
		Tester tst = (Tester)clz.newInstance();
		return tst;
	}
}
