package com.depobrp.service.user;

import java.util.List;

import com.depobrp.model.user.User;
import com.depobrp.service.common.ObjectService;

public interface UserService extends ObjectService {

	User getInitializedUser(Long userId);
	
	List<User> getAllUsersWithRole();
	
	List<User> searchUserByUsername(final String keyword);
	
	User getUserByUsername(final String username);
	
	User getUserWithAllSubOrdinates(Long id);
	
}
