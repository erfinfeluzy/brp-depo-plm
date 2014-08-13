package com.depobrp.service.user;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.model.user.UserActivity;
import com.depobrp.service.common.ObjectServiceImpl;


@Service("userActivityService")
@Transactional
public class UserActivityServiceImpl 
		extends ObjectServiceImpl 
		implements UserActivityService
		{

	@Async
	public void saveActivity(String description, String createdBy){
		UserActivity activity = new UserActivity(description, createdBy);
		save(activity);
	}
	
}
