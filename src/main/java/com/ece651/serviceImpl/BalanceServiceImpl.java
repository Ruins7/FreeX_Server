/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.BalanceDao;
import com.ece651.entity.Balance;
import com.ece651.service.BalanceService;

/**
 * @ClassName:     BalanceServiceImpl.java
 * @Description:   Balance对象的业务逻辑类 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午6:37:33 
 */
public class BalanceServiceImpl implements BalanceService{

	private BalanceDao balanceDao;
	
	/**
     * 注入
     */
	public void setBalanceDao(BalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}

	/**
     * 新增币种余额
     * @param Balance(no need bid)
     * @return bid(Serializable)
     */
	@Override
	public Serializable addNewCurrencyBalance(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.insertNewBalance(balance);
	}

	/**
     * 存钱
     * @param Balance
     * @return int,1=true,0=false
     */
	@Override
	public int deposit(Balance balance) {
		//TODO 添加逻辑
		
		balanceDao.updateBalance(balance);
		return 1;
	}
	
	/**
	 * 取钱
	 * @param Balance
	 * @return int,1=true,0=false
	 */
	@Override
	public int withdrawal(Balance balance) {
		//TODO 添加逻辑
		
		balanceDao.updateBalance(balance);
		return 1;
	}

	/**
	 * 删除某一个币种的余额记录，只有当该币种的余额为0 才可以调用（在service层检查余额是否为0）
	 * @param Balance
	 * @return int,1=true,0=false
	 */
	@Override
	public int deleteCurrencyBalance(Balance balance) {
		//TODO 添加逻辑
		
		balanceDao.deleteBalance(balance);
		return 1;
	}

	/**
	 * 查询某个用户所有的币种余额
	 * @param Balance
	 * @return List<Balance>
	 */
	@Override
	public List<Balance> searchAllBalOfUser(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.findMoreByConditions(balance);	
	}

	/**
	 * 查询某个用户某一币种余额(交易，取钱之前的检查)
	 * @param Balance
	 * @return Balance
	 */
	@Override
	public Balance searchOneCurrOfUser(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.findbyConditions(balance);
	}

}
