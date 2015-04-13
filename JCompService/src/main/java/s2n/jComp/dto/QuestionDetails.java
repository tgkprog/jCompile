package s2n.jComp.dto;

import java.io.Serializable;

public class QuestionDetails  implements Serializable {

	/**
	 * Only change if you want saved/ existing to be in-compatible. Example: if u have changed or removed a property.
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String summary;
	private String details;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	

}
