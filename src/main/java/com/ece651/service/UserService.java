package com.ece651.service;

import java.io.Serializable;

import com.ece651.entity.User;

public interface UserService {
	
    /**
     * 保存用户
     * @param user
     * @return
     */
    Serializable save(User user); 
    
    /**
     * 更新用户
     * @param user
     * @return
     */
    void update(User user); 

}
