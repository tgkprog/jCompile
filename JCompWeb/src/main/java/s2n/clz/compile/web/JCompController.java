package s2n.clz.compile.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2n.jComp.dto.QuestionDetails;
import s2n.jComp.dto.Result;
import s2n.jComp.facade.ClzApiFacade;



@Controller
@RequestMapping(value = "/api")
public class JCompController {
	private static final Logger logger = LogManager.getLogger(JCompController.class);
	static{
		
		System.out.println("JCompController static out");
		logger.info("JCompController static");
	}
	@Autowired
	@Qualifier(value = "jCompApi")
	private ClzApiFacade facade = null;
	

	public JCompController() {

	}
	
	@RequestMapping(value = "q/{code}", method = RequestMethod.GET)
	public @ResponseBody QuestionDetails getQuestion( @PathVariable String code) {
		return facade.getQuestion(code);
	}
	
	@RequestMapping(value = "aq2", method = RequestMethod.GET)
	public @ResponseBody QuestionDetails getQuestion2(String code) {
		return facade.getQuestion(code);
	}
	
	@RequestMapping(value = "aq3", method = RequestMethod.GET)
	public @ResponseBody QuestionDetails getQuestion3(HttpServletRequest request) {
		String code = request.getParameter("code");
		return facade.getQuestion(code);
	}
	
	@RequestMapping(value = "ans", method = RequestMethod.POST)
	public @ResponseBody Result answerCheck( String code, String name, String src) {
		return facade.compileAndTest(code, name, src);
	}


}

