/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;

import com.ece651.dao.UserDao;
import com.ece651.entity.User;
import com.ece651.toolsUnits.Tools;

/**
 * @ClassName:     UserDaoImpl.java
 * @Description:   User对象的持久层实现类，实现其自身接口，继承泛型持久层实现类 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月14日 下午5:28:19 
 */
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao{
	
	@Override
	public int updateUser(User user){
		update(user);
		return 1;//true
	}
	
	@Override
	public int deleteUser(User user){
		delete(user);
		return 1;//true
	}
	
	@Override
	public Serializable saveUser(User user){
		return save(user);
	}
	
	@Override
	public User findById(User user) {
		return get(user.getUid());
	}

	//user 不能为null
	@Override
	public User findbyConditions(User user) {
		
		ArrayList list = new ArrayList();
		if (user.getUid() != 0) {
			list.add(user.getUid());
		}
		if (user.getUsername() != null) {
			list.add(user.getUsername());
		}
		if (user.getPassword() != null) {
			list.add(user.getPassword());
		}
		if (user.getEmail() != null) {
			list.add(user.getEmail());
		}
		
		Object[] values = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			values[i] = list.get(i);
		}
		
		Object[] obj = getBySQL(Tools.packSQL(user),  values);
		if(obj == null){
			return null;
		}
		return Tools.packAUser(obj);
	}
	

}
