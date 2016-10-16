/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.PageResults;
import com.ece651.entity.User_history;

/**
 * @ClassName:     UserHistoryDao.java
 * @Description:   user histotry dao interface 
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.16 4:12:56 PM 
 */
public interface UserHistoryDao extends BaseDao<User_history, Integer>{

	public Serializable insertNewUserHis(User_history userHistory);
	
	public int updateUserHis(User_history userHistory);
	
	public int deleteUserHis(User_history userHistory);
	
	public User_history findbyConditions(User_history userHistory);
	
	public List<User_history> findMoreByConditions(User_history userHistory);

	public PageResults<User_history> findMoreByCondition(
			User_history userHistory, PageResults pageInfo);
	
}
