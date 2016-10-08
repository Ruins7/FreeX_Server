package com.ece651.serviceImpl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ece651.dao.UserDao;
import com.ece651.entity.User;
import com.ece651.service.UserService;

//使用Spring提供的@Service注解将UserServiceImpl标注为一个Service

@Service("userService")
public class UserServiceImpl implements UserService {
	
	/**
     * 注入userDao
     */
    @Autowired
    private UserDao userDao;

    @Override
	public void test() {
		System.out.println("Hello World!");

	}
    
    @Override
	public Serializable save(User user) {
		return userDao.save(user);
	}

}
