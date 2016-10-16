/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.UserHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User_history;
import com.ece651.toolsUnits.PackSQLTools;
import com.ece651.toolsUnits.PackValuesTools;

/**
 * @ClassName:     UserHistoryDaoImpl.java
 * @Description:   User Histroy dao implement class
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.16 PM 4:32:34 
 */
public class UserHistoryDaoImpl extends BaseDaoImpl<User_history, Integer> implements UserHistoryDao{

	/**
	 * add new user histroy
	 * @param User_history
	 * @return Serializable(uhid)
	 */
	@Override
	public Serializable insertNewUserHis(User_history userHistory) {
		return save(userHistory);
	}

	/**
	 * update user histroy
	 * @param User_history
	 * @return int
	 */
	@Override
	public int updateUserHis(User_history userHistory) {
		//update(userHistory);
		return 0;
	}

	/**
	 * delete a user histroy
	 * @param User_history
	 * @return int
	 */
	@Override
	public int deleteUserHis(User_history userHistory) {
		delete(userHistory);
		return 1;
	}

	/**
	 * find a user histroy by conditions
	 * @param User_history
	 * @return User_history
	 */
	@Override
	public User_history findbyConditions(User_history userHistory) {
		return getBySQL(PackSQLTools.packSQL(userHistory), PackValuesTools.packValues(userHistory));
	}

	/**
	 * find a user histroy list by conditions
	 * @param User_history
	 * @return List<User_history>
	 */
	@Override
	public List<User_history> findMoreByConditions(User_history userHistory) {
		
		return getListBySQL(PackSQLTools.packSQL(userHistory), PackValuesTools.packValues(userHistory));
	}

	/**
	 * find all user histroy of a user by conditions in pages
	 * @param User_history, PageResults
	 * @return PageResults<User_history>
	 */
	@Override
	public PageResults<User_history> findMoreByCondition(
			User_history userHistory, PageResults pageInfo) {
		return findPageByFetchedHql(PackSQLTools.packSQL(userHistory), PackSQLTools.packSQL(userHistory), pageInfo.getPageNo(), pageInfo.getPageSize(), PackValuesTools.packValues(userHistory));
	}

}
