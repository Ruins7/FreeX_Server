/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.ece651.dao.UserDao;
import com.ece651.entity.User;

/**
 * @ClassName:     UserDaoImpl.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年9月30日 下午9:28:30 
 */

public class UserDaoImpl implements UserDao{
	
	private SessionFactory sessionFactory;

	//set注入
    public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserDaoImpl() {
		super();
	}

	@Override
    public Serializable save(User user) {
    	return sessionFactory.getCurrentSession().save(user);
    	
    }

	@Override
	public void update(User user) {
    	 sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public Serializable findByCondition(User condition) {
		return null; 
	}
    
//    @Transactional
//	@Override
//    public User findById(Integer id) { 
//		User user = super.getHibernateTemplate().get(User.class, id);	 
//		return user;
//	}
//
//    @Transactional
//	@Override
//	public User findByEmail(String email) {
//		 User user = super.getHibernateTemplate().get(User.class, email);
//		return user;
//	}

    
	

}
