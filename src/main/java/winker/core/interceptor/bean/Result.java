package winker.core.interceptor.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 结果类<br>
 *
 * 
 */
public class Result<T> extends PageParameter {

	private List<String> errorList;

	/**
	 * 请求是否成功
	 */
	@JsonProperty("success")
	private boolean success = Boolean.FALSE;

	/**
	 * 错误码
	 */
	@JsonProperty("errorCode")
	private String errorCode = "";

	/**
	 * 消息
	 */
	@JsonProperty("errorMsg")
	private String errorMsg = "";

	/**
	 * 数据
	 */
	@JsonProperty("data")
	private T data;

	public Result<T> generateCodeAndMsgInfo(String code, String msg) {
		this.setErrorCode(code);
		this.setErrorMsg(msg);
		return this;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		this.addErrorMsgToList(getErrorMsg());
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public void addErrorMsgToList(String errorMsg) {
		if (errorList == null) {
			errorList = new ArrayList<>();
		}
		errorList.add(errorMsg);
	}
}
