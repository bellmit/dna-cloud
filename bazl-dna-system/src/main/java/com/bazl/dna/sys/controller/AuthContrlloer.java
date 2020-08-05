package com.bazl.dna.sys.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysLab;
import com.bazl.dna.sys.service.ISysLabService;
import com.bazl.dna.sys.service.ISysRoleService;
import com.bazl.dna.sys.service.ISysUserService;
import com.bazl.dna.util.DataUtils;
import com.google.common.base.Strings;

/**
 * 认证登录接口
 */
@RestController
@RequestMapping("/auth")
public class AuthContrlloer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthContrlloer.class);
	
	public static final String HEADER_UNKNOWN = "unknown";
	
	@Autowired
	public ISysUserService sysUserService;
	
	@Autowired
	public ISysRoleService sysRoleService;
	
	@Autowired
	public ISysLabService sysLabService;
	
	/**
	 * 系统登录
	 * @param json
	 * @return
	 */
	@PostMapping("/login")
    public ResponseData login(HttpServletRequest request, @RequestBody JSONObject json){
		String userName = json.getString(SysConstants.USER_NAME);
		if(Strings.isNullOrEmpty(userName)){
			return new ResponseData(ErrorCodes.ERR_USER_NAME_NOT_NULL, ErrorInfo.ERR_USER_NAME_NOT_NULL);
		}
		String password = json.getString(SysConstants.PASS);
		if(Strings.isNullOrEmpty(password)){
			return new ResponseData(ErrorCodes.ERR_PASSWORD_NOT_NULL,ErrorInfo.ERR_PASS_NOT_NULL);
		}
		SysLab sysLab = sysLabService.getByUser(userName);
		if (sysLab != null) {
			String ip = getIpAddress(request);
			LOGGER.info("ip: {}", ip);
			boolean isRange = DataUtils.ipExistsInRange(ip, sysLab.getServerIpAddr());
			if (!isRange) {
				return new ResponseData(ErrorCodes.ERR_USER_IP_RANGE, ErrorInfo.ERR_USER_IP_RANGE);
			}
		}
		
		Map<String, Object> result = sysUserService.login(userName, password);
		if (result != null) {
			return new ResponseData(result);
		}
		return new ResponseData(ErrorCodes.ERR_USER_NOT_FOUND, ErrorInfo.ERR_USER_NOT_FOUND);
    }
	
	/**
	 * 获取左侧菜单
	 * @param menuType
	 * @return
	 */
	@GetMapping("/menu/{menuType}")
    public ResponseData findMenuByRoles(@CurrentUser AuthUser user, @PathVariable String menuType) {
		if(Strings.isNullOrEmpty(menuType)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
		try {
			if (user != null) {
				JSONArray array = JSON.parseArray(user.getRoleList());
				if (array.isEmpty())
					return new ResponseData();
				List<String> roles = JSON.parseArray(array.toJSONString(), String.class);
				JSONArray result = sysRoleService.findMenuByRoles(roles, menuType);
				return new ResponseData(result);
			}
		} catch (Exception e) {
			LOGGER.error("Error findMenuByRoles: ", e);
		}
		return new ResponseData(OpenErrorCodes.ERR_USER_ACCESS_TOKEN_INVALID, ErrorInfo.ERR_USER_NAME_NOT_NULL);
    }
	
	/**
	 * 获取菜单对应权限
	 * @param menuId
	 * @return
	 */
	@GetMapping("/menu/operation/{menuId}")
    public ResponseData findMenuOperByRoles(@CurrentUser AuthUser user, @PathVariable String menuId){
		if(Strings.isNullOrEmpty(menuId)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
		try {
			if (user != null) {
				JSONArray array = JSON.parseArray(user.getRoleList());
				if (array.isEmpty())
					return new ResponseData();
				List<String> roles = JSON.parseArray(array.toJSONString(), String.class);
				List<Map<String, String>> result = sysRoleService.findMenuOperByRoles(roles, menuId);
				return new ResponseData(result);
			}
		} catch (Exception e) {
			LOGGER.error("Error findMenuOperByRoles: ", e);
		}
		return new ResponseData(OpenErrorCodes.ERR_USER_ACCESS_TOKEN_INVALID, ErrorInfo.ERR_USER_NAME_NOT_NULL);
    }
	
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("X-Real-IP");
	    }
	    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = getLocalhost();
		}
	    if (ip != null && ip.length() > 15 && ip.contains(",")) {
	    		ip = StringUtils.substringAfterLast(ip, ",");
		}
	    return ip;
	} 
	
	/**
	 * 获取本机ip
	 * @return String
	 */
	public static String getLocalhost() {
		try {
			InetAddress inet = InetAddress.getLocalHost();  
			return inet.getHostAddress();
		} catch (UnknownHostException e) {
			LOGGER.error("Error getLocalhost:", e);
		}
		return null;
	}
	
}
