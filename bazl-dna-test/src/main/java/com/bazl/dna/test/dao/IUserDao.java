package com.bazl.dna.test.dao;

import java.util.List;

import com.bazl.dna.test.entity.User;

public interface IUserDao {
	User findUserById(Long id);

	List<User> findAllUsers();

	int insertUser(User user);

}
