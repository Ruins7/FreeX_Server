/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

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
	 * 保存用户
	 * @param user
	 * @return
	*/
	Serializable save(User user); 

}
