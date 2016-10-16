/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import com.ece651.dao.BaseDao;
import com.ece651.entity.PageResults;
import com.ece651.toolsUnits.RowMapper;

/**
 * @ClassName: BaseDaoImpl.java
 * @Description: Hibernate4 all generic functions for data persistence
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.9 9:05:14 PM
 */
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	protected Class<T> entityClass;
	protected SessionFactory sessionFactory;

	/**
	 *  set injection
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public BaseDaoImpl() {
		super();
	}

	/**
	 * using java reflection to get the exact type
	 */
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	/**
	 * insert obj
	 * @param t
	 * @return Serializable(id)
	 */
	@Override
	public Serializable save(T t) {
		return sessionFactory.getCurrentSession().save(t);
	}

	/**
	 * insert or update obj
	 * @param t
	 * @return void
	 */
	@Override
	public void SaveOrUpdate(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	/**
	 * if contains
	 * @param t
	 * @return boolean
	 */
	@Override
	public boolean contains(T t) {
		return sessionFactory.getCurrentSession().contains(t);
	}

	/**
	 * delete
	 * @param t
	 * @return void
	 */
	@Override
	public void delete(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}

	/**
	 * delete by id
	 * @param Id
	 * @return boolean
	 */
	@Override
	public boolean deleteById(ID Id) {
		T t = get(Id);
		if (t == null) {
			return false;
		}
		delete(t);
		return true;
	}

	/**
	 * delete all obj
	 * @param entities
	 * @return void
	 */
	@Override
	public void deleteAll(Collection<T> entities) {
		for (Object entity : entities) {
			sessionFactory.getCurrentSession().delete(entity);
		}
	}

	/**
	 * query by HQL
	 * @param String  
	 * @param Object[] 
	 * @return int
	 */
	@Override
	public int queryHql(String hqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * query by SQL
	 * @param sqlString  values
	 * @return int
	 */
	@Override
	public int querySql(String sqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sqlString).addEntity(getEntityClass());
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * search only one obj by HQL
	 * @param hqlString 
	 * @param values
	 * @return T
	 */
	@Override
	public T getByHQL(String hqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	/**
	 * search only one obj by SQL
	 * @param sqlString 
	 * @param values 
	 * @return T
	 */
	@Override
	public T getBySQL(String sqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sqlString).addEntity(getEntityClass());
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	/**
	 * query by HQL to get list
	 * @param hqlString
	 * @param values
	 * @return List<T>
	 */
	@Override
	public List<T> getListByHQL(String hqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	/**
	 * query by SQL to get list
	 * @param sqlString
	 * @param values
	 * @return List<T>
	 */
	@Override
	public List<T> getListBySQL(String sqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sqlString).addEntity(getEntityClass());
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	/**
	 * SQL to get list
	 * @param sql
	 * @param map
	 * @param values
	 * @return List
	 */
	@Override
	public List findListBySql(final String sql, final RowMapper map,
			final Object... values) {
		final List list = new ArrayList();
		// 执行JDBC的数据批量保存
		Work jdbcWork = new Work() {
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = connection.prepareStatement(sql);
					for (int i = 0; i < values.length; i++) {
						setParameter(ps, i, values[i]);
					}
					rs = ps.executeQuery();
					int index = 0;
					while (rs.next()) {
						Object obj = map.mapRow(rs, index++);
						list.add(obj);
					}
				} finally {
					if (rs != null) {
						rs.close();
					}
					if (ps != null) {
						ps.close();
					}
				}
			}
		};
		sessionFactory.getCurrentSession().doWork(jdbcWork);
		return list;
	}

	/**
	 * refresh
	 * @param t
	 * @return void
	 */
	@Override
	public void refresh(T t) {
		sessionFactory.getCurrentSession().refresh(t);
	}

	/**
	 * update
	 * @param t
	 * @return void
	 */
	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	/**
	 * query sql to get total count for all records
	 * @param Sql 
	 * @param values 
	 * @return total number of all records
	 */
	@Override
	public Long countBySql(String sql, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(getEntityClass());
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (long) query.list().size();
	}

	/**
	 * SQL query by page
	 * @param sql
	 * @param countSql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return PageResults，it contains all info includes info of pages and objs which from query
	 */
	@Override
	public PageResults<T> findPageByFetchedHql(String sql, String countSql,
			int pageNo, int pageSize, Object[] values) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addEntity(getEntityClass());// addEntity()
				// 将查处的Object[]转换为对应的实体类
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		if (countSql == null) {
			ScrollableResults results = query.scroll();
			results.last();
			retValue.setTotalCount(results.getRowNumber() + 1);// 设置总记录数
		} else {
			Long count = countBySql(countSql, values);
			retValue.setTotalCount(count.intValue());
		}
		retValue.resetPageNo();
		@SuppressWarnings("unchecked")
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
		if (itemList == null) {
			itemList = new ArrayList<T>();
		}
		retValue.setResults(itemList);
		return retValue;
	}

	/**
	 * 设置每行批处理参数
	 * @param ps
	 * @param pos  ?占位符索引，从0开始
	 * @param data
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	private void setParameter(PreparedStatement ps, int pos, Object data)
			throws SQLException {
		if (data == null) {
			ps.setNull(pos + 1, Types.VARCHAR);
			return;
		}
		Class dataCls = data.getClass();
		if (String.class.equals(dataCls)) {
			ps.setString(pos + 1, (String) data);
		} else if (boolean.class.equals(dataCls)) {
			ps.setBoolean(pos + 1, ((Boolean) data));
		} else if (int.class.equals(dataCls)) {
			ps.setInt(pos + 1, (Integer) data);
		} else if (double.class.equals(dataCls)) {
			ps.setDouble(pos + 1, (Double) data);
		} else if (Date.class.equals(dataCls)) {
			Date val = (Date) data;
			ps.setTimestamp(pos + 1, new Timestamp(val.getTime()));
		} else if (BigDecimal.class.equals(dataCls)) {
			ps.setBigDecimal(pos + 1, (BigDecimal) data);
		} else {
			// 未知类型
			ps.setObject(pos + 1, data);
		}
	}

	/**
	 * load
	 * @param id
	 * @return T
	 */
	@Override
	public T load(ID id) {
		return (T) sessionFactory.getCurrentSession().load(getEntityClass(), id);
	}

	/**
	 * get
	 * @param id
	 * @return T
	 */
	@Override
	public T get(ID id) {
		return (T) sessionFactory.getCurrentSession().get(getEntityClass(), id);
	}

}
