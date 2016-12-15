/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;

import com.ece651.dao.UserHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User_history;
import com.ece651.service.UserHistoryService;

/**
 * @ClassName:     UserHistoryServiceImpl.java
 * @Description:   User_history object service layer
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.16  5:14:54 PM 
 */
public class UserHistoryServiceImpl implements UserHistoryService{

	private UserHistoryDao userHistoryDao;
	
	/**
     * Injection
     */
	public void setUserHistoryDao(UserHistoryDao userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
	}

	/**
     * add new user history
     * @param User_history(no need uhid)
     * @return uhid(Serializable)
     */
	@Override
	public Serializable addNewUserHis(User_history userHistory) {
		// TODO add logic here
		
		return userHistoryDao.insertNewUserHis(userHistory);
	}

	/**
     * delete user histroy
     * @param User_history(no need uhid)
     * @return int 1=true, 0=false;
     */
	@Override
	public int delUserHis(User_history userHistory) {
		// TODO add logic here
		
		return userHistoryDao.deleteUserHis(userHistory);
	}

	/**
     * search all users histroy
     * @param PageResults
     * @return PageResults<User_history>
     */
	@Override
	public PageResults<User_history> searchAllUserHis(PageResults pageInfo) {
		// TODO add logic here
		
		return userHistoryDao.findMoreByCondition(null, pageInfo);
	}

	/**
     * search a user history of a user
     * @param PageResults, User_history
     * @return PageResults<User_history>
     */
	@Override
	public PageResults<User_history> searchAllUserHisOfAUser(
			User_history userHistory, PageResults pageInfo) {
		// TODO add logic here
		
		return userHistoryDao.findMoreByCondition(userHistory, pageInfo);
	}

}
