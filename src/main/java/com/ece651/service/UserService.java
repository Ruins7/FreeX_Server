package com.ece651.service;

import java.io.Serializable;

import com.ece651.entity.PageResults;
import com.ece651.entity.User;

/**
 * @ClassName:     UserService.java
 * @Description:   User对象的业务逻辑类接口
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:38:12 
 */
public interface UserService{

    public Serializable save(User user);  
    public int update(User user); 
    public int delete(User user); 
    public User findByID(User user);   
    public User findbyName(User user);
    public User findbyNameAndPassword(User user);
    public User findbyEmail(User user);
    public PageResults<User> findUsersByConditionAndPage(User user, PageResults pageInfo);

}
