package com.depobrp.service.master;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.model.master.MLO;
import com.depobrp.service.common.ObjectServiceImpl;

@Transactional(
		isolation = Isolation.DEFAULT, 
		propagation = Propagation.REQUIRED,
		readOnly = false)
@Service("mloService")
@SuppressWarnings("unchecked")
public class MLOServiceImpl 
			extends ObjectServiceImpl 
				implements MLOService{

	
	public List<MLO> getAllByFilter(MLO mlo) {
		
		String hql = " from MLO m where 1=1 ";
		
		if(mlo == null){
			return super.getFromHql(hql);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		
		if(mlo.getName() != null && mlo.getName().length() > 0){
			hql = hql + " and m.name like :name ";
			paramMap.put("name", mlo.getName() + "%");
		}
		
		if(mlo.getEmail() != null && mlo.getEmail().length() > 0){
			hql = hql + " and m.email like :email ";
			paramMap.put("email", mlo.getEmail() + "%");
		}
		
		return (List<MLO>) super.getFromHql(hql, paramMap);
	}

	
}
