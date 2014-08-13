package com.depobrp.service.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository("daoHibernate")
public class DaoHibernateImpl implements DaoHibernate{

	private HibernateTemplate hibernate;
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public DaoHibernateImpl(SessionFactory sessionFactory) {
		this.hibernate = new HibernateTemplate(sessionFactory);
		this.sessionFactory = sessionFactory;
	}
	
	private void reset(){
		this.hibernate.setMaxResults(0);
	}
	
	protected final Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public <T> T find(Class<T> clazz, Long id) {
		return hibernate.get(clazz, id);
	}

	public <T> List<T> getAll(Class <T> entityClass){
		return hibernate.loadAll(entityClass);
	}

	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, List<Object> param) {
		return hibernate.find(hql, param.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, List<Object> param, int limit) {
		try{
			hibernate.setMaxResults(limit);
			return hibernate.find(hql, param.toArray());
		}finally{
			reset();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, Object[] param) {
		return hibernate.find(hql, param);
	}
	
	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, Object[] param, int limit) {
		try {
			hibernate.setMaxResults(limit);
			return hibernate.find(hql, param);
		} finally{
			reset();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql) {
		return hibernate.find(hql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void remove(Class clazz, Long id) {
		hibernate.delete(hibernate.load(clazz, id));
	}

	public void remove(Object object) {
		hibernate.delete(object);
	}
	
	public void remove(String hql, List<Object> param){
		hibernate.bulkUpdate(hql, param.toArray());
	}

	public void save(Object object) {
		hibernate.saveOrUpdate(object);
	}
	
	
	/**
	 * Get result list for pagination (for hibernate query with parameter
	 * value).
	 * <p>
	 * paramMap only cater <br/>
	 * java.lang.String as key (key name must be the same as hibernate query
	 * parameter). <br/>
	 * and parameter value as <br>
	 * java.lang.String, java.lang.Long, java.lang.Double, java.lang.Double, and
	 * java.util.Collection.<br/>
	 * <p>
	 * Leave paramMap as null for hibernate query without parameter.
	 * <p>
	 * example: <br/>
	 * <code>
	 * String hql = " from Customer c where c.id = :idLong and c.name = :nameString " ;
	 * <br/>java.util.Map paramMap = new java.util.HashMap();
	 * <br/>paramMap.put("idLong", new Long(1));
	 * <br/>paramMap.put("nameString", "Erfin");
	 * <br/>int rowPerPage = 25;
	 * <br/>int pageNum = 3;
	 * <br/>List result = getPaginationResult(hql, paramMap, rowPerPage, pageNum);
	 * </code>
	 * 
	 * @param hql
	 * @param paramMap
	 * @param rowPerPage
	 * @param pageNum
	 * @return pagination result list
	 * @author Erfin Feluzy
	 */
	@SuppressWarnings("rawtypes")
	public List getPaginationList(String hql, Map<String, Object> paramMap, int rowPerPage, int pageNum) {
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		int firstResultIndex = PaginationUtils.getFirstIndex(pageNum, rowPerPage);
		return query.setFirstResult(firstResultIndex).setMaxResults(rowPerPage).list();
	}
	
	/**
	 * Get total row counter for pagination.
	 * 
	 * @param hql
	 * @param paramMap
	 * @return row counter
	 */
	public Integer getPaginationCounter(String hql, Map<String, Object> paramMap) {		
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		Number num = (Number) query.uniqueResult();
		if (num==null)
			return null;
		else
			return num.intValue();
	}
	
	/**
	 * Get total row
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public Long count(String hql, Map<String, Object> paramMap){
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		Number num = (Number) query.uniqueResult();
		if (num==null)
			return null;
		else
			return num.longValue();
	}
	
	/**
	 * @param query
	 * @param paramMap
	 * @return
	 */
	private Query setParameter(Query query, Map<String, Object> paramMap) {
		if (!CollectionUtils.isEmpty(paramMap)) {
			Iterator<Map.Entry<String, Object>> it = paramMap.entrySet().iterator();
			
			while (it.hasNext()) {
				Map.Entry<String, Object> param = (Map.Entry<String, Object>) it.next();
				String key = param.getKey();
				Object obj = param.getValue();
				
				if (obj instanceof java.lang.String)
					query.setString(key, obj.toString());
				else if (obj instanceof java.lang.Long)
					query.setLong(key, ((java.lang.Long) obj).longValue());
				else if (obj instanceof java.lang.Integer)
					query.setInteger(key, ((java.lang.Integer) obj).intValue());
				else if (obj instanceof java.lang.Double)
					query.setDouble(key, ((java.lang.Double) obj).doubleValue());
				else if (obj instanceof java.util.Date)
					query.setTimestamp(key, (java.util.Date) obj);
				else if (obj instanceof java.util.Collection)
					query.setParameterList(key, (Collection<?>) obj);
				else if (obj instanceof java.lang.Boolean)
					query.setBoolean(key, (Boolean) obj);
				else if (obj instanceof Enum)
					query.setParameter(key, obj);
			}
		}	
		return query;
	}

	public Object find(String hql, Map<String, Object> paramMap) {
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		return query.uniqueResult();
	}
	
	public void update(String hql, Map<String, Object> paramMap){
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getCustomObjectFromHql(String hql, Map<String, Object> paramMap){
		
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getCustomObjectFromHql(String hql, Map<String, Object> paramMap, int limit){
		
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		query.setMaxResults(limit);
		
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, int limit) {
		try{
			hibernate.setMaxResults(limit);
			return hibernate.find(hql);
		}
		finally{
			hibernate.setMaxResults(0);
		}
	}

	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, Map<String, Object> paramMap, int limit) {
		
		return getPaginationList(hql, paramMap, limit, 0);
	}

	@SuppressWarnings("rawtypes")
	public List getFromHql(String hql, Map<String, Object> paramMap) {
		Query query = this.getSession().createQuery(hql);
		query = setParameter(query, paramMap);
		return query.list();
	}

	

}
