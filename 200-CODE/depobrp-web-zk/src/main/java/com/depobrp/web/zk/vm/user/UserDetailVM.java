package com.depobrp.web.zk.vm.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.depobrp.commons.security.util.SecurityUtil;
import com.depobrp.model.user.Role;
import com.depobrp.model.user.User;
import com.depobrp.service.user.UserService;
import com.depobrp.web.zk.common.BaseController;

@Component("UserDetailVM")
@Scope("prototype")
public class UserDetailVM extends BaseController{

	private User currentUser = new User();
	
	@Wire("#userDetailDialog")
	private Window window;
	
	@Autowired
	@Qualifier("userService")
	private UserService service;
	
	private List<Role> allRoles;
	
	private Role selectedRole;
	
	private String supervisorUsername;

	@Init
    public void init(
    		@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view,
            @ExecutionArgParam ("selectedUser") User userFromParentWindow) {
        
		Selectors.wireComponents(view, this, false);
		
		if(userFromParentWindow != null && userFromParentWindow.getId() != null){
			this.currentUser = service.getInitializedUser(userFromParentWindow.getId());
			String cipherPass = currentUser.getPassword();
			currentUser.setPassword(SecurityUtil.decrypt(cipherPass));
			this.selectedRole = currentUser.getRole();
			if(currentUser.getSupervisor()!=null){
				this.supervisorUsername = currentUser.getSupervisor().getUsername();
			}
        }
		
        this.allRoles = service.getAll(Role.class);
    }
	
	@Command
	public void autocompleteSupervisor(@BindingParam("event") Event event){
		InputEvent inputEvent = (InputEvent) event;
        String inputText = inputEvent.getValue();
        
        if(inputText != null && inputText.length() < 3) return;
        
        List<User> users = service.searchUserByUsername(inputText);
        
        Combobox autocompleteBox = (Combobox) event.getTarget();
        autocompleteBox.getChildren().clear();
        
        for (User user : users) {
        	Comboitem item = new Comboitem();
            item.setLabel(user.getUsername());
            item.setValue(user.getUsername());
            autocompleteBox.appendChild(item);
		}
	}

	@Command
	public void saveUser(){
		
		Messagebox.show(
				"Do you want to save user?", "Confirm Dialog",
				Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							saveUserAfterConfirmation();
						} 
					}
				});
	}
	
	private void saveUserAfterConfirmation(){
		
		try {
			
			boolean isUpdate = true;
			
			if(supervisorUsername != null && supervisorUsername.trim().length() > 1){
				User supervisor = service.getUserByUsername(this.supervisorUsername);
				if(supervisor == null)
					throw new RuntimeException("Please insert correct supervisor name");
				
				currentUser.setSupervisor(supervisor);
			}
			
			if(currentUser.getId() == null)
				isUpdate = false;
			
			String cipherPass = SecurityUtil.encrypt(currentUser.getPassword());
			currentUser.setPassword(cipherPass);
			currentUser.setRole(selectedRole);
			currentUser.setCreatedBy(getUsername());
			currentUser.setCreatedDate(new Date());
			currentUser.setUpdatedBy(getUsername());
			currentUser.setUpdatedDate(new Date());
			
			service.save(currentUser);
			
			info("Successfully saved");
			
			if(isUpdate){
				audit("Save User:" + currentUser.getUsername());
			}else{
				audit("Update User:" + currentUser.getUsername());
			}
			
			
			BindUtils.postGlobalCommand(null, null, "refreshUserList", null);
		
		} catch (Exception e) {
			info("Please try again.");
		} finally {
			this.window.detach();
		}	
	}	
	
	@Command
	public void closeDialog(){
		this.window.detach();
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}
	
	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public String getSupervisorUsername() {
		return supervisorUsername;
	}

	public void setSupervisorUsername(String supervisorUsername) {
		this.supervisorUsername = supervisorUsername;
	}
	
}
