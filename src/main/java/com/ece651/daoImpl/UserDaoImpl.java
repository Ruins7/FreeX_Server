/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.UserDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User;
import com.ece651.toolsUnits.PackSQLTools;
import com.ece651.toolsUnits.PackValuesTools;

/**
 * @ClassName:     UserDaoImpl.java
 * @Description:   User dao class, implements UserDao interface, extends BaseDaoImpl
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.14 5:28:19 PM 
 */
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao{
	
	/**
	 * modefy
	 * @param User
	 * @return int
	 */
	@Override
	public int updateUser(User user){
		update(user);
		return 1;//true
	}
	
	/**
	 * delete
	 * @param User
	 * @return int
	 */
	@Override
	public int deleteUser(User user){
		delete(user);
		return 1;//true
	}
	
	/**
	 * add new user
	 * @param User(no need uid)
	 * @return uid
	 */
	@Override
	public Serializable saveUser(User user){
		return save(user);
	}
	
	/**
	 * search one user by id
	 * @param User
	 * @return User
	 */
	@Override
	public User findById(User user) {
		return get(user.getUid());
	}

	/**
	 * search on user by conditions
	 * @param User
	 * @return User
	 */
	@Override
	public User findbyConditions(User user) {
		return getBySQL(PackSQLTools.packSQL(user), PackValuesTools.packValues(user));
	}

	/**
	 * saerch users by conditions and page
	 * @param User
	 * @param PageResults
	 * @return PageResults<User>
	 */
	@Override
	public PageResults<User> findMoreByCondition(User user, PageResults pageInfo) {
		return findPageByFetchedHql(PackSQLTools.packSQL(user), PackSQLTools.packSQL(user), pageInfo.getPageNo(), pageInfo.getPageSize(), PackValuesTools.packValues(user));	
	}

	/**
	 * search users by conditions by list
	 * @param User
	 * @return List<User>
	 */
	@Override
	public List<User> findMoreByConditions(User user) {
		return getListBySQL(PackSQLTools.packSQL(user), PackValuesTools.packValues(user));
	}
	

}
