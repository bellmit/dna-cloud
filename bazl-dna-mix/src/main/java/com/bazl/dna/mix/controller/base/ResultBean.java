package com.bazl.dna.mix.controller.base;

import java.io.Serializable;

public class ResultBean implements Serializable {

	private static final long serialVersionUID = -8363923199593511398L;

	public static final long CODE_ERROR_LOGIN = 2; //未登录标识
	public static final long CODE_SUCCESS = 1;  //接口访问成功
	public static final long CODE_ERROR = 0;  //接口访问失败

	public static final String NAME_SUCCESS = "成功";

	private long code;
	private long errorCode;
	private Object data;
	private String errorMessage;

	public ResultBean() {
	}

	public ResultBean(long code, long errorCode, Object data, String errorMessage) {
		super();
		this.code = code;
		this.errorCode = errorCode;
		this.data = data;
		this.errorMessage = errorMessage;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ResultBean [code=" + code + ", errorCode=" + errorCode + ", data=" + data + ", errorMessage=" + errorMessage + "]";
	}

}
