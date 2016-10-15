/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;

import com.ece651.entity.PageResults;
import com.ece651.entity.User;

/**
 * @ClassName:     UserDao.java
 * @Description:   User对象的持久层接口，继承泛型持久层接口
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月14日 下午5:22:30 
 */
public interface UserDao extends BaseDao<User, Integer>{
	
	public Serializable saveUser(User user);
	public int updateUser(User user);
	public int deleteUser(User user);
	public User findById(User user);
	public User findbyConditions(User user);
	public PageResults<User> findMoreByCondition(User user, PageResults pageInfo);

}
