package com.ece651.service;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.PageResults;
import com.ece651.entity.User;

/**
 * @ClassName: UserService.java
 * @Description: User serivce interface
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.15 2:38:12 PM
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
