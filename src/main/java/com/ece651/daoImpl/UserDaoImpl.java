/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

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

public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
	
    private SessionFactory sessionFactory;
    
    @Transactional 
    @Override
    public Serializable save(User user) {
    	return super.getHibernateTemplate().save(user);
    }

    @Transactional
	@Override
	public void update(User user) {
    	super.getHibernateTemplate().update(user);
	}

    @Transactional
	@Override
	public Serializable findByCondition(User condition) {
    	List list=super.getHibernateTemplate().findByExample(condition);
		return (Serializable) list;
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
