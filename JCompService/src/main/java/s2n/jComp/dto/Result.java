package s2n.jComp.dto;

import java.io.Serializable;

public class Result implements Serializable {

	/**
	 * Only change if you want saved/ existing to be in-compatible. Example: if u have changed or removed a property.
	 */
	private static final long serialVersionUID = 1L;
	private StringBuilder compileErrors = new StringBuilder();
	private StringBuilder compileMsgs = new StringBuilder();
	private boolean status;
	private boolean compileStatus;
	private boolean testStatus;
	private StringBuilder testErrors = new StringBuilder();
	private StringBuilder testMsgs = new StringBuilder();

	public Result() {

	}

	public String getCompileErrors() {
		return compileErrors.toString();
	}

	public void setCompileErrors(String s) {
		compileErrors.delete(0, compileErrors.length());
		compileErrors.append(s);
	}

	public Result appendCompileErrors(String mErrors) {
		this.compileErrors.append(mErrors);
		return this;
	}

	public StringBuilder getCompileMsgs() {
		return compileMsgs;
	}

	public void setCompileMsgs(String s) {
		compileMsgs.delete(0, compileMsgs.length());
		compileMsgs.append(s);
	}
	
	public Result appendCompileMsgs(String s) {
		this.compileMsgs.append(s);
		return this;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isCompileStatus() {
		return compileStatus;
	}

	public void setCompileStatus(boolean compileStatus) {
		this.compileStatus = compileStatus;
	}

	public boolean isTestStatus() {
		return testStatus;
	}

	public void setTestStatus(boolean testStatus) {
		this.testStatus = testStatus;
	}

	public StringBuilder getTestErrors() {
		return testErrors;
	}

	public void setTestErrors(String s) {
		testErrors.delete(0, testErrors.length());
		testErrors.append(s);
	}

	public Result appendTestErrors(String error) {
		this.testErrors.append(error);
		return this;
	}

	public StringBuilder getTestMsgs() {
		return testMsgs;
	}

	public void setTestMsgs(String s) {
		testMsgs.delete(0, testMsgs.length());
		testMsgs.append(s);
	}
	
	public Result appendTestMsgs(String s) {
		this.testMsgs.append(s);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Result [compileErrors=").append(compileErrors).append(", compileMsgs=").append(compileMsgs).append(", status=")
				.append(status).append(", compileStatus=").append(compileStatus).append(", testStatus=").append(testStatus)
				.append(", testErrors=").append(testErrors).append(", testMsgs=").append(testMsgs).append("]");
		return builder.toString();
	}
	
	

}
