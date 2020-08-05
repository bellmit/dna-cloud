package com.bazl.dna.common;

public class PublicErrorCodes {

	/***
	 * 重复操作
	 */
	public static final int ERR_REPEAT_ACTION = 80001;

	/***
	 * 无权操作(不是当前处理人)
	 */
	public static final int ERR_APPROVER_NOT_AUTH = 80002;
	
	/***
	 * 不合法用户-只允许登录用户操作
	 */
	public static final int ERR_ONLY_ALLOWED_LOGON = 80003;
	
	/***
	 * 没有找到数据
	 */
	public static final int ERR_NOT_FOUND_DATA = 80004;
	
	/***
	 * 时间重叠
	 */
	public static final int ERR_TIME_OVERLAP = 80005;
	
	/***
	 * 时间范围与要求不符
	 */
	public static final int ERR_TIME_RANGE = 80006;
	
	/***
	 * 超过限制范围
	 */
	public static final int ERR_OVER_LIMIT_RANGE = 80007;
	
	/**
	 * Constructor
	 */
	private PublicErrorCodes() {
		super();
	}
}
