/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.BalanceDao;
import com.ece651.entity.Balance;
import com.ece651.entity.PageResults;
import com.ece651.toolsUnits.PackSQLTools;
import com.ece651.toolsUnits.PackValuesTools;

/**
 * @ClassName:     BalanceDaoImpl.java
 * @Description:   Balance dao class, implements BalanceDao interface, extends BaseDaoImpl
 * @author         Freddy Lee
 * @version        V1.0  
 * @Date           2016.10.15 2:11:19 PM 
 */
public class BalanceDaoImpl extends BaseDaoImpl<Balance, Integer> implements BalanceDao {

	/**
	 * add new type of currency balance record
	 * @param Balance(no need bid)
	 * @return bid
	 */
	@Override
	public Serializable insertNewBalance(Balance balance) {
		return save(balance);
	}

	/**
	 * update balance
	 * @param Balance
	 * @return int
	 */
	@Override
	public int updateBalance(Balance balance) {
		update(balance);
		return 1;//true
	}

	/**
	 * delete ceratin balance record, invoke only when the balance is 0(check on service layer)
	 * @param Balance
	 * @return int
	 */
	@Override
	public int deleteBalance(Balance balance) {
		delete(balance);
		return 1;//true
	}

	/**
	 * search one obj by conditions
	 * @param Balance
	 * @return Balance
	 */
	@Override
	public Balance findbyConditions(Balance balance) {
		return getBySQL(PackSQLTools.packSQL(balance), PackValuesTools.packValues(balance));
	}

	/**
	 * search by conditions by pages 
	 * @param Balance
	 * @param PageResults
	 * @return PageResults<Balance>
	 */
	@Override
	public PageResults<Balance> findMoreByCondition(Balance balance, PageResults pageInfo) {
		return findPageByFetchedHql(PackSQLTools.packSQL(balance), PackSQLTools.packSQL(balance), pageInfo.getPageNo(), pageInfo.getPageSize(), PackValuesTools.packValues(balance));	
	}

	/**
	 * search by conditions by list
	 * @param Balance
	 * @return List<Balance>
	 */
	@Override
	public List<Balance> findMoreByConditions(Balance balance) {
		return getListBySQL(PackSQLTools.packSQL(balance), PackValuesTools.packValues(balance));
	}

	/**
	 * search if a user already has a type of currency
	 * @param Balance
	 * @return boolean
	 */
	@Override
	public Boolean ifExistCurrency(Balance balance) {
		return contains(balance);
	}

}
