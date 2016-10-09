package com.ece651.serviceImpl;

import java.io.Serializable;

import com.ece651.dao.UserDao;
import com.ece651.entity.User;
import com.ece651.service.UserService;

public class UserServiceImpl implements UserService {
	
    private UserDao userDao;
    
    //set注入
    public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Serializable save(User user) {
		return userDao.save(user);
	}

	@Override
	public void update(User user) {
		 
		 userDao.update(user);;
	}

}
