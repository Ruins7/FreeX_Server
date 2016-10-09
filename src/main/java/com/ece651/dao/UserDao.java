/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;

import com.ece651.entity.User;

/**
 * @ClassName:     UserDao.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年9月30日 下午9:27:35 
 */

public interface UserDao {
	
	/**
	 * insert user
	 * @param user
	 * @return
	*/
	Serializable save(User user); 
	
	/**
	 * update user
	 * @param user
	 * @return
	*/
	void update(User user); 
	 
	 /**
		 * find user by condition
		 * @param user
		 * @return
		*/
	 Serializable findByCondition(User condition); 
	 
//	 /**
//		 * find user by id
//		 * @param user
//		 * @return
//		*/
//	 public User findById(Integer id);
//	 
//	 /**
//		 * find user by email
//		 * @param user
//		 * @return
//		*/
//	 public User findByEmail(String email);

}
