/**
 * 
 */
package com.ece651.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

import com.ece651.toolsUnits.RowMapper;
import com.ece651.dao.BaseDao;
import com.ece651.entity.PageResults;

/**
 * @ClassName: BaseDaoImpl.java
 * @Description: Hibernate4 所有常用的数据持久化方法实现， 使用泛型
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月9日 下午9:05:14
 */
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	protected Class<T> entityClass;
	protected SessionFactory sessionFactory;

	// set注入
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public BaseDaoImpl() {
		super();
	}

	// 使用反射机制获取编译期的准确类型
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@Override
	public Serializable save(T t) {
		return sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void SaveOrUpdate(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public boolean contains(T t) {
		return sessionFactory.getCurrentSession().contains(t);
	}

	@Override
	public void delete(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}

	@Override
	public boolean deleteById(ID Id) {
		T t = get(Id);
		if (t == null) {
			return false;
		}
		delete(t);
		return true;
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		for (Object entity : entities) {
			sessionFactory.getCurrentSession().delete(entity);
		}
	}

	@Override
	public int queryHql(String hqlString, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int querySql(String sqlString, Object... values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public Object[] getByHQL(String hqlString, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (Object[]) query.uniqueResult();
	}

	@Override
	public Object[] getBySQL(String sqlString, Object[] values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (Object[]) query.uniqueResult();
	}

	@Override
	public List<T> getListByHQL(String hqlString, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@Override
	public List<T> getListBySQL(String sqlString, Object... values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	/**
	 * 由sql语句得到List
	 * 
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

	@Override
	public void refresh(T t) {
		sessionFactory.getCurrentSession().refresh(t);
	}

	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public Long countByHql(String hql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (Long) query.uniqueResult();
	}

	/**
	 * <HQL分页查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param countHql
	 *            查询记录条数的HQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param values
	 *            不定Object数组参数
	 * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
	 */
	@Override
	public PageResults<T> findPageByFetchedHql(String hql, String countHql,
			int pageNo, int pageSize, Object... values) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		if (countHql == null) {
			ScrollableResults results = query.scroll();
			results.last();
			retValue.setTotalCount(results.getRowNumber() + 1);// 设置总记录数
		} else {
			Long count = countByHql(countHql, values);
			retValue.setTotalCount(count.intValue());
		}
		retValue.resetPageNo();
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		if (itemList == null) {
			itemList = new ArrayList<T>();
		}
		retValue.setResults(itemList);
		return retValue;
	}

	/**
	 * 设置每行批处理参数
	 * 
	 * @param ps
	 * @param pos
	 *            ?占位符索引，从0开始
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

	@Override
	public T load(ID id) {
		return (T) sessionFactory.getCurrentSession().load(getEntityClass(), id);
	}

	@Override
	public T get(ID id) {
		return (T) sessionFactory.getCurrentSession().get(getEntityClass(), id);
	}

}
