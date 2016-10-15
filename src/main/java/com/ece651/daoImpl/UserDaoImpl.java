/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;

import com.ece651.dao.UserDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.User;
import com.ece651.toolsUnits.PackObjTools;
import com.ece651.toolsUnits.PackSQLTools;
import com.ece651.toolsUnits.PackValuesTools;

/**
 * @ClassName:     UserDaoImpl.java
 * @Description:   User对象的持久层实现类，实现其自身接口，继承泛型持久层实现类 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月14日 下午5:28:19 
 */
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao{
	
	/**
	 * 修改用户
	 * @param User
	 * @return int
	 */
	@Override
	public int updateUser(User user){
		update(user);
		return 1;//true
	}
	
	/**
	 * 删除用户
	 * @param User
	 * @return int
	 */
	@Override
	public int deleteUser(User user){
		delete(user);
		return 1;//true
	}
	
	/**
	 * 新增用户
	 * @param User
	 * @return uid
	 */
	@Override
	public Serializable saveUser(User user){
		return save(user);
	}
	
	/**
	 * 单一对象id查询
	 * @param User
	 * @return User
	 */
	@Override
	public User findById(User user) {
		return get(user.getUid());
	}

	/**
	 * 单一对象条件查询
	 * @param User
	 * @return User
	 */
	@Override
	public User findbyConditions(User user) {
		return  getBySQL(PackSQLTools.packSQL(user), PackValuesTools.packValues(user));
	}

	/**
	 * 分页条件查询
	 * @param User
	 * @param PageResults
	 * @return PageResults<User>
	 */
	@Override
	public PageResults<User> findMoreByCondition(User user, PageResults pageInfo) {
		String sql = PackSQLTools.packSQL(user);
		Object[] values = PackValuesTools.packValues(user);
		return findPageByFetchedHql(sql, sql, pageInfo.getPageNo(), pageInfo.getPageSize(), values);	
	}
	

}
