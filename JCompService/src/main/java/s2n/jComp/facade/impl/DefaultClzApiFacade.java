package s2n.jComp.facade.impl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import s2n.jComp.dto.QuestionDetails;
import s2n.jComp.dto.Result;
import s2n.jComp.entities.CodeQuestion;
import s2n.jComp.facade.ClzApiFacade;
import s2n.jComp.services.ClazzCompilerService;
import s2n.jComp.services.ClazzRunTestsService;
import s2n.jComp.services.utl.SrcCodeValidator;
import s2n.jComp.services.utl.SrcCodeValidators;


public class DefaultClzApiFacade implements ClzApiFacade {
	private static final Logger logger = LogManager.getLogger(DefaultClzApiFacade.class);

	private ClazzCompilerService compilerService;
	private ClazzRunTestsService testService;
	
	@Override
	public Result compileAndTest(String code, String name, String src) {
		Result result = new Result();
		String loc = "1";
		System.out.println("compileAndTest At " + new java.util.Date());
		System.out.println("Got code \n" + src + "\n");
		System.out.println("code " + code);
		System.out.println("name " + name);
		try {
			result.setCompileStatus(true);
			CodeQuestion cq = compilerService.getQuestionByCode(code, result);
			loc = "2";
			if(result.isCompileStatus() == false || cq == null){
				result.setCompileStatus(false);//small change that cq not found but status not set to false
				return result;
			}
			loc = "3";
			String clz = cq.getValidatorClzName();
			SrcCodeValidator vali = SrcCodeValidators.getInstance().get(clz, result);
			loc = "4";
			if(result.isCompileStatus() == false || vali == null){
				result.setCompileStatus(false);
				return result;
			}
			loc = "5";
			vali.validate(src, result);
			if(result.isCompileStatus() == false){
				return result;
			}
			//validate
			//get class
			loc = "6";
			Class clzz = this.compilerService.getClass(src, name, result);
			if(result.isCompileStatus() == false || clzz == null){
				result.setCompileStatus(false);
				return result;
			}
			loc = "7";
			//test
			result.setTestStatus(true);
			this.testService.runTests(clzz, result, cq);
			loc = "8";
			if(result.isTestStatus() == false){
				return result;
			}
			result.setStatus(true);
		} catch (Throwable e) {
			logger.warn("Errors in facade " + e, e);
			result.appendCompileErrors("Errors " + e + ", Location :" + loc + "\n");
			result.setStatus(false);
			result.setCompileStatus(false);
		}
		return result;
	}

	public ClazzCompilerService getCompilerService() {
		return compilerService;
	}

	public void setCompilerService(ClazzCompilerService compilerService) {
		this.compilerService = compilerService;
	}

	public ClazzRunTestsService getTestService() {
		return testService;
	}

	public void setTestService(ClazzRunTestsService testService) {
		this.testService = testService;
	}

	@Override
	@Cacheable(value = "codeQDtoCache")
	public QuestionDetails getQuestion(String code) {
		QuestionDetails qd = new QuestionDetails();
		try {
			CodeQuestion cq = compilerService.getQuestionByCode(code, null);
			if(cq != null){
				qd.setCode(code);
				qd.setDetails(cq.getQuestion());
				qd.setSummary(cq.getQuestionSummary());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qd;
	}
	
	public static String getVersion(){
		return "1";
	}
}
