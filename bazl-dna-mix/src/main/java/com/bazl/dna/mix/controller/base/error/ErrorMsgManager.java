package com.bazl.dna.mix.controller.base.error;

import com.bazl.dna.mix.controller.base.ResultBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author
 * @date
 * @version 1.0
 */
//@ControllerAdvice
@RestControllerAdvice
public class ErrorMsgManager {


    @ExceptionHandler // 所有的异常都是Exception子类
    public Object defaultErrorHandler(Exception e) { // 出现异常之后会跳转到此方法
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,getExceptionAllinformation(e));//处理异常并装订返回2
    }

	@ExceptionHandler // 所有的异常都是Exception子类
	public Object defaultErrorHandler(IException e) { // 出现异常之后会跳转到此方法
		return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,e.getPerrormessage());//处理异常并装订返回2
	}

	public static String getExceptionAllinformation(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try {
			out.close();
		} catch (Exception e) {
		}
		return ret;
	}

	public static String GetErrorMsg(long errorCode) {
		String _errorMsg = "";
		if (errorCode == ErrorCode.IBOAN_REQJSON_IO_EXCEPTION) {
			_errorMsg = "输入输出异常！";
		} else if (errorCode == ErrorCode.IBOAN_REQJSON_CHARACTERCODING_EXCEPTION) {
			_errorMsg = "输入输出异常！";
		} else if (errorCode == ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION) {
			_errorMsg = "输入输出异常,协议体结构错误！";
		} else if (errorCode == ErrorCode.IBOAN_REQJSON_C2SBASEMSG_ISNULL) {
			_errorMsg = "输入输出异常！";
		} else if (errorCode == ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE) {
			_errorMsg = "参数错误，不合理的请求协议类型！";
		} else if (errorCode == ErrorCode.IBOAN_SERVICE_EXCEPTION) {
			_errorMsg = "自定义服务相关异常！";
		} else if (errorCode == ErrorCode.ERR_DATA_EMPTY) {
			_errorMsg = "查询数据为空！";
		}
		return _errorMsg;
	}

}
