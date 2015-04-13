package s2n.jComp.dao;

import s2n.jComp.entities.CodeQuestion;

public interface CodeQuestionDao{
	
	//CodeQuestion getById(int id);
	CodeQuestion getByCode(String code);
}
