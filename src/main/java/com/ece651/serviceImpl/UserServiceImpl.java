package com.ece651.serviceImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.UserDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User;
import com.ece651.service.UserService;

/**
 * @ClassName:     UserServiceImpl.java
 * @Description:   User对象的业务逻辑类 
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.15   2:38:12 PM
 */
public class UserServiceImpl implements UserService {
	
    private UserDao userDao;
    
    /**
     * set注入方法
     */
    public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    /**
     * 新增用户
     * @param user(no need uid)
     * @return Serializable userid
     */
	@Override
	public Serializable signup(User user) {
		//TODO 添加逻辑
		
		return userDao.saveUser(user);
	}

	 /**
     * 更新用户
     * @param user
     * @return int,1=true,0=false
     */
	@Override
	public int modify(User user) {
		//TODO 添加逻辑
		
		return userDao.updateUser(user);
	}

	/**
     * 删除用户
     * @param user
     * @return int, 1=true,0=false
     */
	@Override
	public int deleteUser(User user) {
		//TODO 添加逻辑
		
		return userDao.deleteUser(user);
	}

	/**
     * 根据id查找用户
     * @param user
     * @return user
     */
	@Override
	public User searchUserByID(User user) {
		//TODO 添加逻辑
		
		return userDao.findById(user);
	}

	/**
     * 根据username查找用户
     * 根据用户名查找用户(注册时用户名唯一)
     * @param user
     * @return user
     */
	@Override
	public User checkIfUnameAvail(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}
	
	/**
     * 根据email查找用户(忘记密码)
     * @param user
     * @return user
     */
	@Override
	public User findbyEmail(User user) {
		//TODO 添加逻辑
		
		return userDao.findbyConditions(user);
	}

	/**
     * 根据username & password查找用户
     * 根据用户名和密码查找用户(登录)
     * @param user
     * @return user
     */
	@Override
	public User login(User user) {
		//TODO 添加逻辑
		
		return user = userDao.findbyConditions(user);
	}

	/**
     * 根据条件查找用户，分页
     * @param user, PageResults
     * @return PageResults
     */
	@Override
	public PageResults<User> findUsersByConditionAndPage(User user, PageResults pageInfo) {
		//TODO 添加逻辑
		
		return userDao.findMoreByCondition(user, pageInfo);
	}

	/**
     * 条件查询所有user
     * @param user
     * @return List<User>
     */
	@Override
	public List<User> searchAllUsersByConditions(User user) {
		
		return userDao.findMoreByConditions(user);
	}

}
