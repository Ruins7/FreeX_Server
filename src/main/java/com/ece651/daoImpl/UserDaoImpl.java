/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
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

@Repository("userDao")
public class UserDaoImpl implements UserDao{

	/**
     * 使用@Autowired注解将sessionFactory注入到UserDaoImpl中
     */
	
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional 
    @Override
    public Serializable save(User user) {
        return sessionFactory.getCurrentSession().save(user);
    }
	

}
