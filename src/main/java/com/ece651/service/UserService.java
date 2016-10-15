package com.ece651.service;

import java.io.Serializable;

import com.ece651.entity.User;

public interface UserService {
	
    /**
     * 新增用户
     * @param user
     * @return userid(Serializable)
     */
    Serializable save(User user); 
    
    /**
     * 更新用户
     * @param user
     * @return int
     */
    int update(User user); 
    
    /**
     * 删除用户
     * @param user
     * @return int
     */
    int delete(User user);
    
    /**
     * 根据id查找用户
     * @param user
     * @return user
     */
    User findByID(User user);
    
    /**
     * 根据username查找用户
     * @param user
     * @return user
     */
    User findbyName(User user);
    
    /**
     * 根据username & password查找用户
     * @param user
     * @return user
     */
    User findbyNameAndPassword(User user);
    
    /**
     * 根据email查找用户
     * @param user
     * @return user
     */
    public User findbyEmail(User user);

}
