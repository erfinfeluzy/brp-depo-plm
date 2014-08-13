package com.depobrp.service.common;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface DaoHibernate {
	
	<T> List<T> getAll(Class <T> entityClass);
	
	List getFromHql(String hql);
	List getFromHql(String hql, int limit);
	
	List getFromHql(String hql, List<Object> param);
	List getFromHql(String hql, List<Object> param, int limit);
	
	List getFromHql(String hql, Object[] param);
	List getFromHql(String hql, Object[] param, int limit);
	
	List getFromHql(String hql, Map<String, Object> paramMap);
	List getFromHql(String hql, Map<String, Object> paramMap, int limit);
	
	<T> T find(Class<T> entityClass, Long id);
	
	Object find(String hql, Map<String, Object> paramMap);
	
	void save(Object object);
	
	void remove(Object object);
	
	void remove(Class clazz, Long id);
	
	List getPaginationList(String hql, Map<String, Object> paramMap, int rowPerPage, int pageNum);
	
	Integer getPaginationCounter(String hql, Map<String, Object> paramMap);
	
	Long count(String hql, Map<String, Object> paramMap);

	void update(String hql, Map<String, Object> paramMap);
	
	List<Object[]> getCustomObjectFromHql(String hql, Map<String, Object> paramMap);
	
	List<Object[]> getCustomObjectFromHql(String hql, Map<String, Object> paramMap, int limit);
}
