package com.ece651.serviceImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.UserDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User;
import com.ece651.service.UserService;

/**
 * @ClassName:     UserServiceImpl.java
 * @Description:   User object service layer
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.15   2:38:12 PM
 */
public class UserServiceImpl implements UserService {
	
    private UserDao userDao;
    
    /**
     * Injection
     */
    public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    /**
     * add new user
     * @param user(no need uid)
     * @return Serializable userid
     */
	@Override
	public Serializable signup(User user) {
		//TODO 添加逻辑
		
		return userDao.saveUser(user);
	}

	 /**
     * update user
     * @param user
     * @return int,1=true,0=false
     */
	@Override
	public int modify(User user) {
		//TODO 添加逻辑
		
		return userDao.updateUser(user);
	}

	/**
     * delete user
     * @param user
     * @return int, 1=true,0=false
     */
	@Override
	public int deleteUser(User user) {
		//TODO 添加逻辑
		
		return userDao.deleteUser(user);
	}

	/**
     * find user by id
     * @param user
     * @return user
     */
	@Override
	public User searchUserByID(User user) {
		//TODO 添加逻辑
		
		return userDao.findById(user);
	}

	/**
     * find user by username(username is unique when sign up)
     * @param user
     * @return user
     */
	@Override
	public User checkIfUnameAvail(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}
	
	/**
     * find user by email
     * @param user
     * @return user
     */
	@Override
	public User findbyEmail(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}

	/**
     * find user by username and password
     * @param user
     * @return user
     */
	@Override
	public User login(User user) {
		//TODO 添加逻辑
		
		return user = userDao.findbyConditions(user);
	}

	/**
     * find user by conditions by page
     * @param user, PageResults
     * @return PageResults
     */
	@Override
	public PageResults<User> findUsersByConditionAndPage(User user, PageResults pageInfo) {
		//TODO 添加逻辑
		
		return userDao.findMoreByCondition(user, pageInfo);
	}

	/**
     * find users by conditions
     * @param user
     * @return List<User>
     */
	@Override
	public List<User> searchAllUsersByConditions(User user) {
		
		return userDao.findMoreByConditions(user);
	}

}
