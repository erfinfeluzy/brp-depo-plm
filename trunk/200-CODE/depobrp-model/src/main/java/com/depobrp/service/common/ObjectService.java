package com.depobrp.service.common;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ObjectService
{
	<T> List <T> getAll(Class <T> clazz);
	
	List getFromHql(String hql);
	List getFromHql(String hql, int limit);
	
	List getFromHql(String hql, List<Object> param);
	List getFromHql(String hql, List<Object> param, int limit);
	
	List getFromHql(String hql, Object[] param);
	List getFromHql(String hql, Object[] param, int limit);
	
	List getFromHql(String hql, Map<String, Object> paramMap);
	List getFromHql(String hql, Map<String, Object> param, int limit);
	
	<T> T find(Class<T> clazz, Long id);
	
	Object find(String hql, Map<String, Object> paramMap );
	
	void save(Object object);
	
	void remove(Object object);
	
	void remove(Class clazz, Long id);
	
	void saveAll(List list);
	
	Long count(String hql, Map<String, Object> paramMap);

	void update(String hql, Map<String, Object> paramMap);
	
	<T> TablePager<T> getPageTable(
			String hqlResult, 
			String hqlCount, 
			Map<String, Object> paramMap, 
			int rowPerPage, 
			int pageNum, 
			Class<T> clazz);
}
