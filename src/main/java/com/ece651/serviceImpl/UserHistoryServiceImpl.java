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
 * @Description:   User_history对象的业务逻辑类 
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.16  5:14:54 PM 
 */
public class UserHistoryServiceImpl implements UserHistoryService{

	private UserHistoryDao userHistoryDao;
	
	/**
     * 注入
     */
	public void setUserHistoryDao(UserHistoryDao userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
	}

	/**
     * 新增user记录
     * @param User_history(no need uhid)
     * @return uhid(Serializable)
     */
	@Override
	public Serializable addNewUserHis(User_history userHistory) {
		// TODO add logic here
		
		return userHistoryDao.insertNewUserHis(userHistory);
	}

	/**
     * 删除user记录
     * @param User_history(no need uhid)
     * @return int 1=true, 0=false;
     */
	@Override
	public int delUserHis(User_history userHistory) {
		// TODO add logic here
		
		return userHistoryDao.deleteUserHis(userHistory);
	}

	/**
     * 查询所有user的user记录
     * @param PageResults
     * @return PageResults<User_history>
     */
	@Override
	public PageResults<User_history> searchAllUserHis(PageResults pageInfo) {
		// TODO add logic here
		
		return userHistoryDao.findMoreByCondition(null, pageInfo);
	}

	/**
     * 查询某个user的user记录
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
