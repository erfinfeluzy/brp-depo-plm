package com.depobrp.web.zk.common;

import java.util.List;

import com.depobrp.model.user.Page;
import com.depobrp.model.user.User;

public interface SidebarPageConfig {
	
	/** get pages of this application **/
	List<Page> getPages();
	
	/** get page **/
	Page getPage(String name);
	
	User getUser();
}