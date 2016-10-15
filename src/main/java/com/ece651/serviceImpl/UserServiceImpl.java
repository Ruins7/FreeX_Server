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

	/**
     * 新增用户, 不需要uid
     * @return userid
     */
	@Override
	public Serializable save(User user) {
		//TODO 添加逻辑
		
		return userDao.saveUser(user);
	}

	/**
     * 修改用户
     * @return int 1 == ture
     */
	@Override
	public int update(User user) {
		//TODO 添加逻辑
		
		return userDao.updateUser(user);
	}

	/**
     * 删除用户
     * @return int 1 == ture
     */
	@Override
	public int delete(User user) {
		//TODO 添加逻辑
		
		return userDao.deleteUser(user);
	}

	/**
     * 根据id查找用户
     * @return user
     */
	@Override
	public User findByID(User user) {
		//TODO 添加逻辑
		
		return userDao.get(user.getUid());
	}

	/**
     * 根据用户名查找用户(注册时用户名唯一)
     */
	@Override
	public User findbyName(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}
	
	/**
     * 根据email查找用户(忘记密码)
     */
	@Override
	public User findbyEmail(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}

	/**
     * 根据用户名和密码查找用户(登录)
     */
	@Override
	public User findbyNameAndPassword(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}

}
