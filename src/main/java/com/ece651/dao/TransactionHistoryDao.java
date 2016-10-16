/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;

import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.entity.User;

/**
 * @ClassName: TransactionHistoryDao.java
 * @Description: Transaction histroy对象的持久层接口，继承泛型持久层接口
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月15日 下午2:43:30
 */
public interface TransactionHistoryDao extends
		BaseDao<Transaction_history, Integer> {

	public Serializable insertNewTranHistory(
			Transaction_history transactionHistory);

	public int updateTranHistory(Transaction_history transactionHistory);

	public int deleteTranHistory(Transaction_history transactionHistory);

	public Transaction_history findbyConditions(
			Transaction_history transactionHistory);

	public PageResults<Transaction_history> findMoreByCondition(
			Transaction_history transactionHistory, PageResults pageInfo);

}
