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
 * @Description:   Balance 
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.15  6:37:33 PM
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
     * add new currency balance
     * @param Balance(no need bid)
     * @return bid(Serializable)
     */
	@Override
	public Serializable addNewCurrencyBalance(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.insertNewBalance(balance);
	}

	/**
     * deposit(this currency has already existed, update)
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
	 * withdraw
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
	 * delete a certain currency balance (only available when the balance amount is 0, check if amount is 0 at service layer)
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
	 * search all currency balance amount of a user
	 * @param Balance
	 * @return List<Balance>
	 */
	@Override
	public List<Balance> searchAllBalOfUser(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.findMoreByConditions(balance);	
	}

	/**
	 * search a currency balance amount of a user(check before transaction and withdraw)
	 * @param Balance
	 * @return Balance
	 */
	@Override
	public Balance searchOneCurrOfUser(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.findbyConditions(balance);
	}

	/**
	 * check if a user already has a certain currency balance(check before deposit)
	 * @param Balance(buid,bcid)
	 * @return Balance
	 */
	@Override
	public Boolean searchOneCurrExist(Balance balance) {
		//TODO 添加逻辑
		
		return balanceDao.ifExistCurrency(balance);
	}

}
