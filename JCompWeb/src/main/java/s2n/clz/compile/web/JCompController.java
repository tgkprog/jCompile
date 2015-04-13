package s2n.clz.compile.web;

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
	
	@Autowired
	@Qualifier(value = "jCompApi")
	private ClzApiFacade facade = null;
	

	public JCompController() {

	}
	
	@RequestMapping(value = "/q/{code}", method = RequestMethod.GET)
	public @ResponseBody QuestionDetails getQuestion( @PathVariable String code) {
		return facade.getQuestion(code);
	}
	
	@RequestMapping(value = "/ans", method = RequestMethod.POST)
	public @ResponseBody Result answerCheck( String code, String name, String src) {
		return facade.compileAndTest(code, name, src);
	}


}
