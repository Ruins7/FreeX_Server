/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.util.List;

import com.ece651.dao.TransactionHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.toolsUnits.PackSQLTools;
import com.ece651.toolsUnits.PackValuesTools;

/**
 * @ClassName: TransactionHistoryDaoImpl.java
 * @Description: Transaction_History dao class, implements TransactionHistoryDao interface, extends BaseDaoImpl
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.15 2:46:56 PM
 */
public class TransactionHistoryDaoImpl extends
		BaseDaoImpl<Transaction_history, Integer> implements
		TransactionHistoryDao {

	/**
	 * add new trade history
	 * @param Transaction_history(no need thid)
	 * @return thid
	 */
	@Override
	public Serializable insertNewTranHistory(
			Transaction_history transactionHistory) {
		save(transactionHistory);
		return null;
	}

	/**
	 * modify(usually it can not be modified)
	 * @param Transaction_history
	 * @return int
	 */
	@Override
	public int updateTranHistory(Transaction_history transactionHistory) {
		//update(transactionHistory);
		return 0;
	}

	/**
	 * delete trade history
	 * @param Transaction_history
	 * @return int
	 */
	@Override
	public int deleteTranHistory(Transaction_history transactionHistory) {
		delete(transactionHistory);
		return 1;
	}

	/**
	 * search one obj
	 * @param Transaction_history
	 * @return Transaction_history
	 */
	@Override
	public Transaction_history findbyConditions(
			Transaction_history transactionHistory) {
		return getBySQL(PackSQLTools.packSQL(transactionHistory),
				PackValuesTools.packValues(transactionHistory));
	}

	/**
	 * search by page
	 * @param Transaction_history
	 * @param PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> findMoreByCondition(
			Transaction_history transactionHistory, PageResults pageInfo) {
		return findPageByFetchedHql(PackSQLTools.packSQL(transactionHistory), PackSQLTools.packSQL(transactionHistory), pageInfo.getPageNo(),
				pageInfo.getPageSize(), PackValuesTools.packValues(transactionHistory));
	}

	/**
	 * search by conditions
	 * @param Transaction_history
	 * @return List<Transaction_history>
	 */
	@Override
	public List<Transaction_history> findMoreByConditions(
			Transaction_history transactionHistory) {
		return getListBySQL(PackSQLTools.packSQL(transactionHistory), PackValuesTools.packValues(transactionHistory));
	}

}
