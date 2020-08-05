package com.bazl.dna.test.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.test.entity.User;

public interface IUserService {

	List<User> findAllUsers();

	int insertUser(User user);

	public User getUserById(Long id);

	public ResponseData login(String json);

	public ResponseData getTypeById(String id);
	
	public ResponseData findTypeList(@RequestBody JSONObject paramJson);
}
