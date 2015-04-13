package s2n.jComp.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CodeQuestion")
public class CodeQuestion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int Id;

	@Column(name = "question_code")
	private String questionCode;
	
	@Column(name = "question_Summary")
	private String questionSummary;
	

	@Column(name = "question")
	private String question;

	@Column(name = "rating")
	private String rating;

	@Column(name = "tester_class_name")
	private String testClzName;
	
	@Column(name = "validator_class_name")
	private String validatorClzName;

	@Column(name = "test_properties")
	private String tstProps;


	//@ManyToOne
	//@JoinColumn(name = "user_id", nullable = false)
	//private User user;

	public CodeQuestion() {
	}

	
	
	public CodeQuestion(int id, String questionCode, String questionSummary, String question, String rating, String testClzName,
			String tstProps) {
		super();
		Id = id;
		this.questionCode = questionCode;
		this.questionSummary = questionSummary;
		this.question = question;
		this.rating = rating;
		this.testClzName = testClzName;
		this.tstProps = tstProps;
	}



	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getQuestionSummary() {
		return questionSummary;
	}

	public void setQuestionSummary(String questionSummary) {
		this.questionSummary = questionSummary;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTestClzName() {
		return testClzName;
	}

	public void setTestClzName(String testClzName) {
		this.testClzName = testClzName;
	}

	public String getTstProps() {
		return tstProps;
	}

	public void setTstProps(String tstProps) {
		this.tstProps = tstProps;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeQuestion [Id=").append(Id).append(", questionSummary=").append(questionSummary).append(", rating=")
				.append(rating).append(", testClzName=").append(testClzName).append("]");
		return builder.toString();
	}



}
