package com.jlu.cloudnote.dao;

import com.jlu.cloudnote.entity.User;

public interface UserDao {
	public User findByName(String name);

	public void save(User user);
}
