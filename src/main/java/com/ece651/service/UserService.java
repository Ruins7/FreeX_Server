package com.ece651.service;

import java.io.Serializable;

import com.ece651.entity.User;


public interface UserService {
	
	/**
     * 测试方法
     */
    void test();
    
    /**
     * 保存用户
     * @param user
     * @return
     */
    Serializable save(User user); 

}
