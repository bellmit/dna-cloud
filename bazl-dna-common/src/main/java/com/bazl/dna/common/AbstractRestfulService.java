package com.bazl.dna.common;

import java.util.Map;

import com.google.common.collect.Maps;

/***
 * @Description:对外服务基础类
 * @author lrm
 * @date 2018年9月25日
 */
public interface AbstractRestfulService {
	
	/***
	 * 返回正确消息
	 * @param code
	 * @param data
	 * @return
	 */
	public default Map<String, Object> okResult(int code, Object data) {
		Map<String, Object> res = Maps.newHashMap();
		res.put("code", code);
		res.put("data", data);
		return res;
	}

	/***
	 * 返回错误消息
	 * 
	 * @param data
	 * @return
	 */
	public default Map<String, Object> errorResult(Object data) {
		Map<String, Object> res = Maps.newHashMap();
		res.put("message", data);
		return res;
	}

	/***
	 * 错误返回
	 * 
	 * @param code
	 *            错误码
	 * @param message
	 *            错误描述
	 * @return
	 */
	public default Map<String, Object> errorResult(int code, String message) {
		Map<String, Object> res = Maps.newHashMap();
		res.put("code", code);
		res.put("message", message);
		return res;
	}
}
