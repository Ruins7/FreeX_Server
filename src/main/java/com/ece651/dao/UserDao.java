/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.PageResults;
import com.ece651.entity.User;

/**
 * @ClassName: UserDao.java
 * @Description: User dao interface，extends generic dao interface
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.14 5:22:30 PM
 */
public interface UserDao extends BaseDao<User, Integer> {

	public Serializable saveUser(User user);

	public int updateUser(User user);

	public int deleteUser(User user);

	public User findById(User user);

	public User findbyConditions(User user);

	public List<User> findMoreByConditions(User user);

	public PageResults<User> findMoreByCondition(User user, PageResults pageInfo);

}
