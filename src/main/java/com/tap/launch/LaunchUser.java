package com.tap.launch;

import com.tap.dao.UserDAO;
import com.tap.daoimplementation.UserDAOImpl;
import com.tap.model.User;
import com.tap.utility.*;

public class LaunchUser {

	public static void main(String[] args) {
		
		UserDAO udao=new UserDAOImpl();
		
		User u=new User(0,"ram","ramu","ram133", "Ram@gmail.com","939779978", "K.A puram", null, null, null);
		udao.addUser(u);
		
//		udao.deleteUser(3);
//		
//		udao.getUser(4);
		
		
		
		

	}

}
