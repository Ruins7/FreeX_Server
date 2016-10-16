/**
 * 
 */
package com.ece651.service;

import java.io.Serializable;

import com.ece651.entity.PageResults;
import com.ece651.entity.User_history;

/**
 * @ClassName:     UserHistoryService.java
 * @Description:   User_history serivce interface
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.16 5:08:34 PM 
 */
public interface UserHistoryService {

	public Serializable addNewUserHis(User_history userHistory);
	
	public int delUserHis(User_history userHistory);
	
	public PageResults<User_history> searchAllUserHis(PageResults pageInfo);

	public PageResults<User_history> searchAllUserHisOfAUser(User_history userHistory, PageResults pageInfo);
	
}
