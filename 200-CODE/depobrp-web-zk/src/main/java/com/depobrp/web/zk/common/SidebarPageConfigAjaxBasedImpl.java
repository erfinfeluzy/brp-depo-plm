package com.depobrp.web.zk.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.depobrp.model.user.Module;
import com.depobrp.model.user.Page;
import com.depobrp.model.user.User;


public class SidebarPageConfigAjaxBasedImpl implements SidebarPageConfig{
	
	private HashMap<String,Page> pageMap = new LinkedHashMap<String,Page>();
	
	private User user = new User();

	private void init(){
		
		this.user = SessionInitializer.getUserContext();
		
		for (Module module : user.getRole().getModules()) {
			for (Page page : module.getPages()) {
				pageMap.put(page.getName(), page);
			}
		}
	}


	public SidebarPageConfigAjaxBasedImpl(){	
		init();
	}
	
	public List<Page> getPages(){
		return new ArrayList<Page>(pageMap.values());
	}
	
	public Page getPage(String name){
		return pageMap.get(name);
	}
	
	public User getUser(){
		return user;
	}
	
}