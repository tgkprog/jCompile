package s2n.jComp.services;


import s2n.jComp.dto.Result;
import s2n.jComp.entities.CodeQuestion;

public interface ClazzCompilerService {
	Class<?> getClass(String data, String fullName, Result result);

	CodeQuestion getQuestionByCode(String code, Result result);
}
