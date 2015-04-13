package s2n.jComp.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import s2n.jComp.dao.CodeQuestionDao;
import s2n.jComp.entities.CodeQuestion;

@Repository
public class DefaultCodeQuestionDao implements CodeQuestionDao {

	private static final Logger logger = LogManager.getLogger(DefaultCodeQuestionDao.class);

	protected HibernateTemplate template = null;

	private int debug;

	public void setSessionFactory(SessionFactory sessionFactory) {
		template = new HibernateTemplate(sessionFactory);
		System.out.println("set template :" + template);
	}
	
	public void setDebug(int d) {
		debug = d;
		System.out.println("set debug :" + debug);
	}

	@Override
	public CodeQuestion getByCode(String code) {
		System.out.println("getByCode2 : " + code + ", w/ template :" + template);
		List<CodeQuestion> q = (List<CodeQuestion>) template.find("from CodeQuestion d where d.questionCode = ?", code);
		if (q != null && q.isEmpty() == false) {
			return q.get(0);
		}
		if(debug > 0){
			return mockedByCode(code);
		}
		return null;
	}

	private CodeQuestion mockedByCode(String code) {
		if(code == null)return null;
		CodeQuestion cq = new CodeQuestion();
		if(code.equals("q1")){
			cq =  new CodeQuestion(-1, code, "Add two ints", "Add two ints. Signature : add(int,int)", 
					"1", "external.tester.AddTwoTester",
					"");
		}else if(code.equals("q2")){
			cq =  new CodeQuestion(-2, code, "Multiply two ints", "Multiply two ints. Signature : mul(int,int)", 
					"1", "external.tester.MulTwoTester",
					"");
		}else if(code.equals("s1")){
			cq =  new CodeQuestion(-3, code, "Sample s1", "Test sample s1. Signature : get1(int, String)", 
					"1", "external.tester.S1Tester",
					"");
		}
		return cq;
	}

}
