package com.depobrp.web.zk.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Window;

import com.depobrp.model.user.User;
import com.depobrp.service.user.UserService;
import com.depobrp.web.zk.security.CustomUserDetail;

@Component("WestSidebarAjaxbasedController")
@Scope("prototype")
public class WestSidebarAjaxbasedController extends BaseController {

	private TreeModel<User> model;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	private void init(){
		model = new UserTree(getRootUser());
	}
	
	@Command
	public void showUserDetail(@BindingParam("selectedUser") User user){
		Map<String, Object> param = new HashMap<String, Object>();
		if(user != null && user.getId() != null){
			param.put("selectedUser", user);
		}else{
			param.put("selectedUser", new User());
		}
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/user-detail.zul", null, param);
		
		popupWin.doModal();
	}
	
	private User getRootUser(){
		User root = new User();
		List<User> children = new ArrayList<User>();
		User firstChild = SessionInitializer.getUserWithSubordinate();
		
		traverse(firstChild);
		
		children.add(firstChild);
		root.setSubOrdinates(children);
				
		return root;
	}

	public TreeModel<User> getModel() {
		return model;
	}

	public void setModel(TreeModel<User> model) {
		this.model = model;
	}
	
	private void traverse(User parentNode) {
		
		// print node information
		if(getLoginUsers().contains(parentNode.getUsername())){
			parentNode.setLogin(true);
		}else
			parentNode.setLogin(false);
	    // traverse all nodes that belong to the parent
		if(parentNode.getSubOrdinates().size() < 1) return;
		
		for(User subOrdinate : parentNode.getSubOrdinates()) {
	      
	      // traverse children
	      traverse(subOrdinate);
	    }
	  }
	
	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	/**
	 * Get current login id user from session registry
	 * @return
	 */
	private List<String> getLoginUsers(){
		
		List<Object> principals = sessionRegistry.getAllPrincipals();
		List<String> loginUsers = new ArrayList<String>();
		
		for (Object object : principals) {
			
			if(object instanceof CustomUserDetail){
				CustomUserDetail userDetail = (CustomUserDetail)object;
				loginUsers.add(userDetail.getUsername());
			}
		}
		return loginUsers;
	}
}
