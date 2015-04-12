package s2n.clz.compile.dto;

public class Result {

	private StringBuilder compileErrors;
	private StringBuilder compileMsgs;
	private boolean status;
	private boolean compileStatus;
	private boolean testStatus;
	private StringBuilder testErrors;
	private StringBuilder testMsgs;

	public Result() {

	}

	public StringBuilder getCompileErrors() {
		return compileErrors;
	}

	public void setCompileErrors(StringBuilder compileErrors) {
		this.compileErrors = compileErrors;
	}

	public void appedCompileErrors(String mErrors) {
		this.compileErrors.append(mErrors);
	}

	public StringBuilder getCompileMsgs() {
		return compileMsgs;
	}

	public void setCompileMsgs(StringBuilder compileMsgs) {
		this.compileMsgs = compileMsgs;
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

	public void setTestErrors(StringBuilder testErrors) {
		this.testErrors = testErrors;
	}

	public void appedTestErrors(String error) {
		this.testErrors.append(error);
	}

	public StringBuilder getTestMsgs() {
		return testMsgs;
	}

	public void setTestMsgs(StringBuilder testMsgs) {
		this.testMsgs = testMsgs;
	}

}
