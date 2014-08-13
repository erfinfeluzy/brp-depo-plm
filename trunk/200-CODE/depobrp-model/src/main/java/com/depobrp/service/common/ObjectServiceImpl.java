package com.depobrp.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Transactional(
		isolation = Isolation.DEFAULT, 
		propagation = Propagation.REQUIRED,
		readOnly = false)
@Service("objectService")
@SuppressWarnings("rawtypes")
public class ObjectServiceImpl
		implements ObjectService
{
	@Autowired
	protected DaoHibernate daoHibernate;
	
	@Transactional(readOnly = true)
	public <T> T find(Class<T> clazz, Long id) {
		return daoHibernate.find(clazz, id);
	}

	@Transactional(readOnly = true)
	public <T>List<T> getAll(Class<T> clazz){
		return daoHibernate.getAll(clazz);
	}

	@Transactional(readOnly = true)
	public List getFromHql(String hql, List<Object> param) {
		return daoHibernate.getFromHql(hql, param);
	}
	
	@Transactional(readOnly = true)
	public List getFromHql(String hql, Object [] param) {
		return daoHibernate.getFromHql(hql, param);
	}
	
	@Transactional(readOnly = true)
	public List getFromHql(String hql, Map<String, Object> param, int limit) {
		return daoHibernate.getFromHql(hql, param, limit);
	}

	@Transactional
	public void remove(Object object) {
		daoHibernate.remove(object);
	}

	@Transactional
	public void remove(Class clazz, Long id) {
		daoHibernate.remove(clazz, id);
	}

	@Transactional
	public void save(Object object) {
		daoHibernate.save(object);
	}

	@Transactional
	public void saveAll(List list) {
		for (Object object : list) {
			daoHibernate.save(object);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public <T>TablePager<T> getPageTable(String hqlResult, String hqlCount, 
			Map<String, Object> paramMap, int rowPerPage, int pageNum, Class<T> clazz){
		
		List content  = daoHibernate.getPaginationList(hqlResult, paramMap, rowPerPage, pageNum);
		Integer totalRow = daoHibernate.getPaginationCounter(hqlCount, paramMap);
		
		return new TablePager<T>(pageNum, totalRow, rowPerPage, content);
	}
	
	@Transactional(readOnly=true)
	public Long count(String hql, Map<String, Object> paramMap){
		return daoHibernate.count(hql, paramMap);
	}
	
	@Transactional(readOnly = true)
	public Object find(String hql, Map<String, Object> paramMap){
		return daoHibernate.find(hql, paramMap);
	}
	
	@Transactional(readOnly = true)
	public List getFromHql(String hql){
		return daoHibernate.getFromHql(hql);
	}
	
	@Transactional(readOnly = true)
	public List getFromHql(String hql, int limit){
		return daoHibernate.getFromHql(hql, limit);
	}
	
	@Transactional(readOnly = true)
	public List getFromHql(String hql, Map<String, Object> paramMap){
		return daoHibernate.getFromHql(hql, paramMap);
	}

	@Transactional
	public void update(String hql, Map<String, Object> paramMap){
		daoHibernate.update(hql, paramMap);
	}

	@Transactional(readOnly = true)
	public List getFromHql(String hql, List<Object> param, int limit) {
		return daoHibernate.getFromHql(hql, param, limit);
	}

	@Transactional(readOnly = true)
	public List getFromHql(String hql, Object[] param, int limit) {
		return daoHibernate.getFromHql(hql, param, limit);
	}
}
