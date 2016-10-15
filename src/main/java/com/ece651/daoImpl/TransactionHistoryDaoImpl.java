/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;

import com.ece651.dao.TransactionHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.toolsUnits.PackObjTools;
import com.ece651.toolsUnits.PackSQLTools;
import com.ece651.toolsUnits.PackValuesTools;

/**
 * @ClassName:     TransactionHistoryDaoImpl.java
 * @Description:   Transaction_History对象的持久层实现类，实现其自身接口，继承泛型持久层实现类  
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:46:56 
 */
public class TransactionHistoryDaoImpl extends BaseDaoImpl<Transaction_history, Integer> implements TransactionHistoryDao {

	/**
	 * 添加一条新交易记录
	 * @return thid
	 */
	@Override
	public Serializable insertNewTranHistory(
			Transaction_history transactionHistory) {
		save(transactionHistory);
		return null;
	}

	/**
	 * 修改交易记录
	 * @param User
	 * @return uid
	 */
	@Override
	public int updateTranHistory(Transaction_history transactionHistory) {
		update(transactionHistory);
		return 1;
	}

	/**
	 * 删除交易记录
	 * @param User
	 * @return uid
	 */
	@Override
	public int deleteTranHistory(Transaction_history transactionHistory) {
		delete(transactionHistory);
		return 1;
	}

	/**
	 * 单一对象条件查询
	 * @param Transaction_history
	 * @return Transaction_history
	 */
	@Override
	public Transaction_history findbyConditions(
			Transaction_history transactionHistory) {
		return getBySQL(PackSQLTools.packSQL(transactionHistory), PackValuesTools.packValues(transactionHistory));		 
	}

	/**
	 * 分页条件查询
	 * @param Transaction_history
	 * @param PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> findMoreByCondition(Transaction_history transactionHistory, PageResults pageInfo) {
		String sql = PackSQLTools.packSQL(transactionHistory);
		Object[] values = PackValuesTools.packValues(transactionHistory);
		return findPageByFetchedHql(sql, sql, pageInfo.getPageNo(), pageInfo.getPageSize(), values);	
	}

}
