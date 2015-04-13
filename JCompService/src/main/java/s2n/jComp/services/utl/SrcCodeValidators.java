package s2n.jComp.services.utl;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import s2n.jComp.dto.Result;
import s2n.jComp.services.impl.DefaultClazzCompilerServiceTest;

public class SrcCodeValidators {
	private static final Logger logger = LogManager.getLogger(SrcCodeValidators.class);
	
	private static SrcCodeValidators me = new SrcCodeValidators();
	private Map<String, SrcCodeValidator> valis = new HashMap<>();

	
	public SrcCodeValidators getInstance(){
		return me;
	}
	
	public SrcCodeValidator get(String clz, Result result){
		SrcCodeValidator ins = valis.get(clz);
		try {
			if(ins == null){
				Class clzz = Class.forName(clz);
				ins = (SrcCodeValidator) clzz.newInstance();
				valis.put(clz, ins);
			}
		} catch (ClassNotFoundException | InstantiationException |  IllegalAccessException ex) {
			result.setTestErrors("Test class not found ");
			result.setTestStatus(false);
			logger.warn("Err "+ ex ,ex);
		} 
		return ins;
	}
}
