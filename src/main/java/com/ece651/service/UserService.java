package com.ece651.service;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.PageResults;
import com.ece651.entity.User;

/**
 * @ClassName: UserService.java
 * @Description: User对象的业务逻辑类接口
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月15日 下午2:38:12
 */
public interface UserService {

	public Serializable signup(User user);

	public int modify(User user);

	public int deleteUser(User user);

	public User searchUserByID(User user);

	public User checkIfUnameAvail(User user);

	public User login(User user);

	public User findbyEmail(User user);
	
	public List<User> searchAllUsersByConditions(User user);

	public PageResults<User> findUsersByConditionAndPage(User user,
			PageResults pageInfo);

}
