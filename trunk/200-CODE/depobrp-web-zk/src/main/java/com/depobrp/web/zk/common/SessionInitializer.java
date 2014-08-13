package com.depobrp.web.zk.common;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;

import com.depobrp.model.user.User;
import com.depobrp.service.user.UserService;
import com.depobrp.web.zk.security.CustomUserDetail;

public class SessionInitializer implements Initiator, InitiatorExt {
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		getUserContext();
		getUserWithSubordinate();
	}
	
	public static User getUserWithSubordinate() {
		
		User user = null;
		
		if(Sessions.getCurrent().getAttribute("USER_SUBORDINATE") == null){
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
			UserService service = SpringContext.getApplicationContext().getBean(UserService.class);
			user = service.getUserWithAllSubOrdinates(userDetail.getUser().getId());
			
			Sessions.getCurrent().setAttribute("USER_SUBORDINATE", user);
		
		}else{
			user = (User) Sessions.getCurrent().getAttribute("USER_SUBORDINATE");
		}
		
		return user;
	}

	public static User getUserContext(){
		
		User user = null;
		
		if(Sessions.getCurrent().getAttribute("USER_CONTEXT") == null){
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
			user = userDetail.getUser();
			Sessions.getCurrent().setAttribute("USER_CONTEXT", user);
		
		}else{
			user = (User) Sessions.getCurrent().getAttribute("USER_CONTEXT");
		}
		
		return user;
	}

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {}

	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}

	@Override
	public void doFinally() throws Exception {}

	

}
