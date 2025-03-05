package com.tap.dao;

import java.util.List;

import com.tap.model.User;

public interface UserDAO {
	
	boolean addUser(User user);
	User getUser(int userId);
	boolean updateUser(User user);
	boolean deleteUser(int userId);
	User getUserByUsername(String username);
	User getUserByEmail(String email);
	List<User>getAllUsers();

}
