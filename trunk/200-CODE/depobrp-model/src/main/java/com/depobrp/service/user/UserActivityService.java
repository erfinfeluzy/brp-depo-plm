package com.depobrp.service.user;

import com.depobrp.service.common.ObjectService;

public interface UserActivityService extends ObjectService{

	void saveActivity(String description, String username);
	
}
