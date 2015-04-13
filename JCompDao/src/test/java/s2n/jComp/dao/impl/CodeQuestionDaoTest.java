package s2n.jComp.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import s2n.jComp.entities.CodeQuestion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao-context.xml")
public class CodeQuestionDaoTest {

	static {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
	}

	@Autowired
	@Qualifier (value="questionsDao")
	private DefaultCodeQuestionDao defaultCodeQuestionDao = null;

	@Test
	public void daoInjected() {
		assertNotNull("DefaultCodeQuestionDao is null.", defaultCodeQuestionDao);
	}

	 @Test
	public void codeQuestion2() throws SQLException, DataAccessException, Exception {
		assertNotNull("DefaultCodeQuestionDao is null.", defaultCodeQuestionDao);
		CodeQuestion cq = defaultCodeQuestionDao.getByCode("q1");
		assertNotNull("Get CQ By Code q1 :", cq);
		System.out.println("Cq :" + cq);
		assertEquals("Question summary q1.", "Program to add two numbers", cq.getQuestionSummary());
		
		cq = defaultCodeQuestionDao.getByCode("s1");
		assertNotNull("Get CQ By Code s1 :", cq);
		System.out.println("Cq s1 :" + cq);
		assertEquals("Question summary s1", "Sample s1", cq.getQuestionSummary());
	}
}
