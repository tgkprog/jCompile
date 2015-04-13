package s2n.jComp.web.facadeTests;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import s2n.jComp.dto.Result;
import s2n.jComp.facade.ClzApiFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-conf.xml")
public class JCompFacadeTest {

	static {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
	}

	@Autowired
	@Qualifier(value = "jCompApi")
	private ClzApiFacade facade = null;

	@Test
	public void serviceInjected() {
		assertNotNull("DefaultCodeQuestionDao is null.", facade);
	}

	@Test
	public void compileSimple() throws SQLException, DataAccessException, Exception {
		assertNotNull("facade is null.", facade);
		String src1 = getSrc1();
		String name = "s2n.dynamic.s1.AddTwoB";
		Result result = facade.compileAndTest(src1, name, src1);
		assertNotNull("Get CQ By Code q1 :", result);
		System.out.println("result :" + result);
		
//		Class<?> adder = service.getClass(src1, "s2n.dynamic.s1.AddTwo" , result);
//		assertNotNull("Adder :", adder);
//		Object obj = adder.newInstance();
//		System.out.println("Object : " + obj);
//		Method m = adder.getMethod("add", int.class, int.class);
//		Object r = m.invoke(obj, 1, 2);
//		int rtn = Integer.parseInt(r + "");
//		assertEquals("Adding 1 and 2 ", 3, r);
	}

	String getSrc1() {
		StringBuilder src = new StringBuilder();
		src.append("package s2n.dynamic.s1;\n");
		src.append("public class AddTwoB  {");
		src.append("public int add(int c, int b){ \nreturn c + b;\n}");
		src.append("} ");
		return src.toString();

	}

}
