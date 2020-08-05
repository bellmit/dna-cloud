package com.bazl.dna.common;

/**
 * <p>Description: 常量定义</p>
 */
public class PublicConstants {

	/**
	 * 状态码
	 */
	public static final String STATUS_YES = "1";
	public static final String STATUS_NO = "0";
	
	/**
	 * 创建线程数
	 */
	public static final int THREAD_NUMBER = 10;
	
	/**
	 * 过期时间
	 */
	public static final Long EXPIRE_TIME = 60 * 12L;
	
	/**
	 * 参数 count
	 */
	public static final String PARAM_COUNT = "count";
	
	/**
	 * 参数 list
	 */
	public static final String PARAM_LIST = "list";
	
	/**
	 * 分页对象
	 */
	public static final String PARAM_PAGE = "pageInfo";
	
	public static final String ERROR_PARAM_NOTNULL = "Param is error, %s is not allow null.";
	
	public static final int SUCCESS_CODE = 1;
	
	public static final String FAILURE = "failure";

	public static final String SUCCESS = "success";
	
	public static final int DEFAULT_VALUE = 1;
		
	/**
	 * Constructor
	 */
	public PublicConstants() {
		super();
	}
}
