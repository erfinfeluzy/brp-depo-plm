package com.depobrp.web.zk.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.depobrp.model.user.User;
import com.depobrp.service.user.UserActivityService;
import com.depobrp.web.zk.security.CustomUserDetail;

public abstract class BaseController {
	
	protected final static Logger logger = Logger.getLogger(BaseController.class);
	
	@Autowired
	private UserActivityService userActivityService;
	
	private static final String NOTIF_INFORMATION = "Information";
	private static final String NOTIF_CONFIRMATION = "Confirmation";

	
	
	protected User getUserContext(){
		
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
	
	protected String getUsername(){
		
		return getUserContext().getUsername();
	}
	
	protected void audit(Object object){
		String result = object.toString();
		result = result.replaceAll("[\\t\\n\\r]"," ");
		logger.info(result);
		
		userActivityService.saveActivity(object.toString(), getUsername());
	}
	
	protected void alert(String message){
    	Messagebox.show(message, 
    			NOTIF_INFORMATION, 
				Messagebox.OK, 
				Messagebox.ERROR);
    	
    	Clients.evalJavaScript("playSound();");
    	
    }
	
	protected void info(String message){
    	Messagebox.show(message, 
    			NOTIF_INFORMATION, 
				Messagebox.OK, 
				Messagebox.INFORMATION);
    	
    	Clients.evalJavaScript("playSound();");
    	
    }
	
	protected void confirm(String confirmationMessage, EventListener<Event> callbackEvent){
		
		Messagebox.show(
				confirmationMessage, NOTIF_CONFIRMATION,
				Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				callbackEvent
			);
	}
	
}
