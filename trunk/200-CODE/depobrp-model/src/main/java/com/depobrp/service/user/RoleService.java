package com.depobrp.service.user;

import com.depobrp.model.user.Role;
import com.depobrp.service.common.ObjectService;

public interface RoleService extends ObjectService {

	Role getRoleWithModules(Long roleId);
	
}
