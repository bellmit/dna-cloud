package com.bazl.dna.sys.constants;

/***
 * @Description:定义本模块API的错误信息
 * @author lt
 * @date 2018-10-09
 */
public class ErrorInfo {
	
	/**
	 * 参数错误
	 */
	public static final String ERR_PARAM = "参数错误";
	
	/**
	 * 数据已存在
	 */
	public static final String ERR_IS_EXISTS = "数据已存在";
	
	/**
	 * 用户名不能为空
	 */
	public static final String ERR_USER_NAME_NOT_NULL = "用户名不能为空";
	
	/**
	 * 密码不能为空
	 */
	public static final String ERR_PASS_NOT_NULL = "密码不能为空";
	
	/**
	 * 用户未找到
	 */
	public static final String ERR_USER_NOT_FOUND = "用户未找到";
	
	/**
	 * 您的IP不在范围之内
	 */
	public static final String ERR_USER_IP_RANGE = "您的IP不在范围之内";
	
	/**
	 * 请先删除实验室用户
	 */
	public static final String ERR_USER_LAB_USER = "请先删除实验室用户";

	private ErrorInfo() {
		
	}
}
