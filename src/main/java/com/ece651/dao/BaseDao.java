/**
 * 
 */
package com.ece651.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.ece651.toolsUnits.RowMapper;
import com.ece651.entity.PageResults;

/**
 * @ClassName: BaseDao.java
 * @Description: Hibernate4 all generic functions for data persistence
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.9 8:34:11 PM
 */
public interface BaseDao<T, ID extends Serializable> {

	public abstract Serializable save(T t);

	public abstract void SaveOrUpdate(T t);

	public abstract T load(ID id);

	public abstract T get(ID id);

	public abstract boolean contains(T t);

	public abstract void delete(T t);

	public abstract boolean deleteById(ID Id);
	
	public abstract void deleteAll(Collection<T> entities);

	public abstract int queryHql(String hqlString, Object[] values);

	public abstract int querySql(String sqlString, Object[] values);
	
	public abstract T getByHQL(String hqlString, Object[] values);
	
	public abstract T getBySQL(String sqlString, Object[] values);
	
	public abstract List<T> getListByHQL(String hqlString, Object[] values);
	
	public abstract List<T> getListBySQL(String sqlString, Object[] values);

	public List findListBySql(final String sql, final RowMapper map,
			final Object[] values);
	
	public abstract void refresh(T t);
	
	public abstract void update(T t);
	
	public abstract Long countBySql(String sql, Object[] values);

	public abstract PageResults<T> findPageByFetchedHql(String sql,
			String countSql, int pageNo, int pageSize, Object[] values);

}
