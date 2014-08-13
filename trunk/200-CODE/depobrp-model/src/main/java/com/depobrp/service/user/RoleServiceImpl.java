package com.depobrp.service.user;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.model.user.Role;
import com.depobrp.service.common.ObjectServiceImpl;

@Service("roleService")
@Transactional
public class RoleServiceImpl 
	extends ObjectServiceImpl 
	implements RoleService{

	public Role getRoleWithModules(Long roleId) {
		
		Role role = super.find(Role.class, roleId);
		Hibernate.initialize(role.getModules());
		return role;
		
	}

}
