/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.Balance;
import com.ece651.entity.PageResults;

/**
 * @ClassName: TransactionDao.java
 * @Description: personal account deposit and withdraw
 * 				 trade bwtween users dao interface
 * 				 extends generic dao interface
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.15 1:51:37 PM
 */
public interface BalanceDao extends BaseDao<Balance, Integer> {

	public Serializable insertNewBalance(Balance balance);

	public int updateBalance(Balance balance);

	public int deleteBalance(Balance balance);

	public Balance findbyConditions(Balance balance);
	
	public List<Balance> findMoreByConditions(Balance balance);

	public PageResults<Balance> findMoreByCondition(Balance balance,
			PageResults pageInfo);

}
