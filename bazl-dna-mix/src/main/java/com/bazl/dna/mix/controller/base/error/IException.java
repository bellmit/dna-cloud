package com.bazl.dna.mix.controller.base.error;


/**
 * 协议参数接收异常
 */
public class IException extends Exception{

	private static final long serialVersionUID = 1470429134859431873L;
	public long perrorcode;
	public String perrormessage;
	
	public IException(int perrorcode){
		this.perrorcode = perrorcode;
		this.perrormessage = ErrorMsgManager.GetErrorMsg(perrorcode);
	}
	
	public IException(int perrorcode,String perrormessage){
		this.perrorcode = perrorcode;
		this.perrormessage = perrormessage;
	}

	public long getPerrorcode() {
		return perrorcode;
	}

	public void setPerrorcode(long perrorcode) {
		this.perrorcode = perrorcode;
	}

	public String getPerrormessage() {
		return perrormessage;
	}

	public void setPerrormessage(String perrormessage) {
		this.perrormessage = perrormessage;
	}
}
