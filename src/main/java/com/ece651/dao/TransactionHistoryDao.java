/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;

/**
 * @ClassName: TransactionHistoryDao.java
 * @Description: Transaction history dao interface，extends generic dao interface
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.15 2:43:30 PM
 */
public interface TransactionHistoryDao extends
		BaseDao<Transaction_history, Integer> {

	public Serializable insertNewTranHistory(
			Transaction_history transactionHistory);

	public int updateTranHistory(Transaction_history transactionHistory);

	public int deleteTranHistory(Transaction_history transactionHistory);
	
	public Transaction_history findbyConditions(
			Transaction_history transactionHistory);
	
	public List<Transaction_history> findMoreByConditions(Transaction_history transactionHistory);

	public PageResults<Transaction_history> findMoreByCondition(
			Transaction_history transactionHistory, PageResults pageInfo);

}
