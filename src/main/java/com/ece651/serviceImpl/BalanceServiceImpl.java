/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.BalanceDao;
import com.ece651.entity.Balance;
import com.ece651.entity.Currency;
import com.ece651.entity.User;
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
     * @return int
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
	 * @return int
	 */
	@Override
	public int withdrawal(Balance balance) {
		//TODO 添加逻辑
		
		balanceDao.updateBalance(balance);
		return 1;
	}

	@Override
	public int deleteCurrencyBalance(Balance balance) {
		//TODO 添加逻辑
		
		balanceDao.deleteBalance(balance);
		return 1;
	}

	@Override
	public List<Balance> searchAllBalOfUser(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.findMoreByConditions(balance);	
	}

	@Override
	public Balance searchOneCurrOfUser(User user, Currency currency) {
		//TODO 添加逻辑
		Balance balance = new Balance();
		balance.setBuid(user.getUid());
		balance.setBcid(currency.getCid());
		
		return balanceDao.findbyConditions(balance);
	}

}
