package com.ece651.serviceImpl;

import java.io.Serializable;

import com.ece651.dao.BaseDao;
import com.ece651.entity.User;
import com.ece651.service.UserService;

public class UserServiceImpl implements UserService {
	
	//所有service实现类都注入通用baseDao,但是对应的实体不同
    private BaseDao<User, Serializable> baseDao;
    
    //set注入
    public void setBaseDao(BaseDao<User, Serializable> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Serializable save(User user) {
		return baseDao.save(user);
	}

	@Override
	public void update(User user) {
		baseDao.update(user);
	}

}
