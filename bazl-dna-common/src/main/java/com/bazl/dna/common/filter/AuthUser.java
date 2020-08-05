/*
 *    Copyright 2016 10gen Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.bazl.dna.common.filter;

import java.io.Serializable;

/**
 * @Description:Jwt验证用户
 * @author zhaoyong
 * @date 2017年8月7日
 */
public class AuthUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String id;
	private final String username;
	private final String password;
	private final String isAdmin;
	private final String tokenType;
	private final String roleList;
	private final String jobList;
	
	private final String orgId;
	private final String userType;

	public AuthUser(String id, String username, String password,String tokenType, 
			String roleList,String jobList,String isAdmin, String orgId, String userType) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.tokenType = tokenType;
		this.roleList = roleList;
		this.jobList = jobList;
		this.isAdmin = isAdmin;
		this.orgId = orgId;
		this.userType = userType;
	}

	public String getId() {
		return id;
	}

	public String getTokenType() {
		return tokenType;
	}


	public String getRoleList() {
		return roleList;
	}

	public String getJobList() {
		return jobList;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getOrgId() {
		return orgId;
	}
	
	public String getUserType() {
		return userType;
	}

}
