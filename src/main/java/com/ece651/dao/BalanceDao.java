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
 * @Description: 用户个人账户存取，用户之间交易的持久层接口,继承泛型持久层接口
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月15日 下午1:51:37
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
