package com.bazl.dna.test.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.test.client.IUserServiceClient;
import com.bazl.dna.test.dao.IUserDao;
import com.bazl.dna.test.entity.User;
import com.bazl.dna.test.mapper.UserMapper;
import com.bazl.dna.test.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserDao userDao;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private IUserServiceClient userServiceClient;

	@Override
	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	@Override
	@Transactional
	public int insertUser(User user) {
		try {
			return userDao.insertUser(user);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
		
	}

	@Override
	public User getUserById(Long id) {
		return userMapper.getUserById(id);
	}

	@Override
	public ResponseData login(String json) {
		return userServiceClient.login(json);
	}

	@Override
	public ResponseData getTypeById(String id) {
		return userServiceClient.getTypeById(id);
	}

	@Override
	public ResponseData findTypeList(JSONObject paramJson) {
		return userServiceClient.findTypeList(paramJson);
	}
}
