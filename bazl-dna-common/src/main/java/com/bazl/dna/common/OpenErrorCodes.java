package com.bazl.dna.common;

/***
 * @Description:定义开放API的错误编码
 * @author lrm
 * @date 2018-09-25
 */
public class OpenErrorCodes {
	
	/***********************************************************************
	 * 【1、服务类异常】
	 **********************************************************************/
	/**
	 * 服务无法响应
	 */
	public static final int ERR_NO_RESPOND = 100;
	/**
	 * 服务请求超时
	 */
	public static final int ERR_RESPONSE_TIMEOUT = 101;
	/**
	 * 请求内容格式不正确
	 */
	public static final int ERR_FORMAT_INCORRECT = 102;
	/**
	 * 请求方法不存在
	 */
	public static final int ERR_UNKOWN_METHOD = 103;
	/**
	 * 请求服务不存在
	 */
	public static final int ERR_SERVICE_NOT_FOUND = 104;
	/**
	 * 服务已经过期
	 */
	public static final int ERR_SERVICE_DEPRECATED = 105;
	/**
	 * 服务必须通过SSL方式请求
	 */
	public static final int ERR_SSL_REQUIRED = 106;
	/**
	 * 服务必须通过指定的加密算法
	 */
	public static final int ERR_CIPHER_REQUIRED = 107;
	/**
	 * 不能识别的客户端应用
	 */
	public static final int ERR_APPKEY_UNKNOWN = 108;
	/**
	 * 客户端未被激活(无效的请求客户端)
	 */
	public static final int ERR_APPKEY_VERIFY = 109;
	/**
	 * 无效的报文签名
	 */
	public static final int ERR_INVALID_SIGN = 110;
	/**
	 * 不支持的签名算法
	 */
	public static final int ERR_UNKOWN_SIGNER = 111;
	/**
	 * 方法内部异常
	 */
	public static final int ERR_SERVICE_INTERNAL_EXCEPTION = 112;
	/**
	 * 错误的请求参数
	 */
	public static final int ERR_INVALID_PARAMETER = 113;
	/**
	 * 请求被拒绝,注册器中仅有empty协议数据(原启动的服务已经全部停掉)
	 */
	public static final int ERR_REQUEST_ABORTED = 115;
	/**
	 * 请求内容不合法
	 */
	public static final int ERR_REQUEST_CONTENT = 116;
	/**
	 * 解压缩过程失败
	 */
	public static final int ERR_DECOMPRESSION_FAILED = 120;
	/**
	 * 不被支持的压缩算法
	 */
	public static final int ERR_UNKONW_COMPRESSION_ALGORITHM = 121;
	/**
	 * 压缩过程失败
	 */
	public static final int ERR_COMPRESSION_FAILED = 122;
	/**
	 * 非法的客户端
	 */
	public static final int ERR_ILLEGAL_CLIENT = 123;
	/**
	 * 响应报文不合法
	 */
	public static final int ERR_ILLEGAL_RESPONSE = 124;
	/**
	 * 响应报文签名异常
	 */
	public static final int ERR_RESPONSE_SIGN = 125;
	/**
	 * 应用未激活
	 */
	public static final int ERR_APPKEY_DISABLED = 126;
	/**
	 * 未知封装异常
	 */
	public static final int ERR_UNKNOWN_BIZ = 127;
	/**
	 * 请求时间与服务器时间差距超过5分钟
	 */
	public static final int ERR_REQUEST_TIMESTAMP = 128;
	/**
	 * 未知的协议封装异常
	 */
	public static final int ERR_UNKONW_BIZ_EXCPT = 129;
	/**
	 * 响应格式化异常
	 */
	public static final int ERR_RESPONSE_FORMAT = 130;
	/**
	 * 无效的客户端
	 */
	public static final int ERR_UNKONW_CLIENT = 131;
	/***********************************************************************
	 * 【2、服务器端异常】
	 **********************************************************************/
	/**
	 * 请求异常
	 */
	public static final int ERR_REQUEST = 200;
	/**
	 * 服务驱动异常
	 */
	public static final int ERR_SERVICE_DRIVEN = 201;
	/**
	 * 内部网络异常
	 */
	public static final int ERR_INTERNAL_NETWORK = 203;
	/**
	 * 内部序列化异常
	 */
	public static final int ERR_INTERNAL_SERIALIZATION = 204;
	/**
	 * 内部响应异常
	 */
	public static final int ERR_INTERNAL_RESPONSE = 205;
	/**
	 * 未知的RPC异常
	 */
	public static final int ERR_UNKNOWN_RPC = 206;
	/***********************************************************************
	 * 【3、服务限制类异常】
	 **********************************************************************/
	/**
	 * 权限不足
	 */
	public static final int ERR_PERMISSION_NOT_ENOUGH = 300;
	/**
	 * 请求大小超限
	 */
	public static final int ERR_REQUEST_OVERRUN = 301;
	/**
	 * 同一个AppKey请求超限
	 */
	public static final int ERR_APPTOKEN_REQUEST_OVERRUN = 301;
	/**
	 * 请求会话过程超限
	 */
	public static final int ERR_SESSION_REQUEST_OVERRUN = 302;
	/**
	 * 服务请求次数超限
	 */
	public static final int ERR_SERVICE_INVOKE_OVERRUN = 303;
	/**
	 * 服务调用频率超限
	 */
	public static final int ERR_SERVICE_INVOKE_QTS_OVERRUN = 304;
	/**
	 * 需要访问的接口需要认证
	 */
	public static final int ERR_UNAUTHORIZED_USER = 305;
	/***********************************************************************
	 * 【4、用户使用级异常】
	 **********************************************************************/
	/**
	 * 客户端使用用户尚未登录
	 */
	public static final int ERR_USER_AUTHENTICTED_REQUIRED = 400;
	/**
	 * 客户端使用用户提供的access_token不合法
	 */
	public static final int ERR_USER_ACCESS_TOKEN_INVALID = 401;
	/**
	 * 客户端使用用户提供的access_token已经过期
	 */
	public static final int ERR_USER_ACCESS_TOKEN_EXPIRED = 402;
	
	/**
	 * 参数错误
	 */
	public static final int ERR_PARAM = 2001;

	/**
	 * Constructor
	 */
	public OpenErrorCodes() {
		super();
	}
}
