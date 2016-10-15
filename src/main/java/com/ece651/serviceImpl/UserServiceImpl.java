package com.ece651.serviceImpl;

import java.io.Serializable;

import com.ece651.dao.UserDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User;
import com.ece651.service.UserService;

/**
 * @ClassName:     UserServiceImpl.java
 * @Description:   User对象的业务逻辑类 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:38:12 
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
     * @return userid(Serializable)
     */
	@Override
	public Serializable save(User user) {
		//TODO 添加逻辑
		
		return userDao.saveUser(user);
	}

	 /**
     * 更新用户
     * @param user
     * @return int
     */
	@Override
	public int update(User user) {
		//TODO 添加逻辑
		
		return userDao.updateUser(user);
	}

	/**
     * 删除用户
     * @param user
     * @return int
     */
	@Override
	public int delete(User user) {
		//TODO 添加逻辑
		
		return userDao.deleteUser(user);
	}

	/**
     * 根据id查找用户
     * @param user
     * @return user
     */
	@Override
	public User findByID(User user) {
		//TODO 添加逻辑
		
		return userDao.get(user.getUid());
	}

	/**
     * 根据username查找用户
     * 根据用户名查找用户(注册时用户名唯一)
     * @param user
     * @return user
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
     * 根据username & password查找用户
     * 根据用户名和密码查找用户(登录)
     * @param user
     * @return user
     */
	@Override
	public User findbyNameAndPassword(User user) {
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

}
