package com.bazl.dna.deploy.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class Server implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ip;
	private Integer port;
	private String username;
	private String password;

	/**
	 * 构建参数 {
	 * 		IP, 端口, 帐号, 密码
	 * }
	 */
	public Server() {
		
	}
	
	public Server(String ip, Integer port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
