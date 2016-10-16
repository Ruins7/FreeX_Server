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
 * @Description:   Balance对象的持久层实现类，实现其自身接口，继承泛型持久层实现类 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:11:19 
 */
public class BalanceDaoImpl extends BaseDaoImpl<Balance, Integer> implements BalanceDao {

	/**
	 * 添加一条新的币种的余额记录
	 * @param Balance
	 * @return bid
	 */
	@Override
	public Serializable insertNewBalance(Balance balance) {
		return save(balance);
	}

	/**
	 * 更新账户余额
	 * @param Balance
	 * @return int
	 */
	@Override
	public int updateBalance(Balance balance) {
		update(balance);
		return 1;//true
	}

	/**
	 * 删除某一个币种的余额记录，只有当该币种的余额为0 才可以调用（在service层检查余额是否为0）
	 * @param Balance
	 * @return int
	 */
	@Override
	public int deleteBalance(Balance balance) {
		delete(balance);
		return 1;//true
	}

	/**
	 * 单一对象条件查询
	 * @param Balance
	 * @return Balance
	 */
	@Override
	public Balance findbyConditions(Balance balance) {
		return getBySQL(PackSQLTools.packSQL(balance), PackValuesTools.packValues(balance));
	}

	/**
	 * 分页条件查询
	 * @param Balance
	 * @param PageResults
	 * @return PageResults<Balance>
	 */
	@Override
	public PageResults<Balance> findMoreByCondition(Balance balance, PageResults pageInfo) {
		String sql = PackSQLTools.packSQL(balance);
		Object[] values = PackValuesTools.packValues(balance);
		return findPageByFetchedHql(sql, sql, pageInfo.getPageNo(), pageInfo.getPageSize(), values);	
	}

	/**
	 * 条件查询
	 * @param Balance
	 * @return List<Balance>
	 */
	@Override
	public List<Balance> findMoreByConditions(Balance balance) {
		return getListBySQL(PackSQLTools.packSQL(balance), PackValuesTools.packValues(balance));
	}

}
