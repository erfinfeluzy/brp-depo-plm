package com.depobrp.web.zk.vm.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.depobrp.model.user.User;
import com.depobrp.service.user.UserService;
import com.depobrp.web.zk.common.BaseController;

@Component("UserListVM")
@Scope("prototype")
public class UserListVM  extends BaseController{

	@Autowired
	private UserService service;
	
	private List<User> userList;
	
	private User selectedUser;
	
	@PostConstruct
	@GlobalCommand("refreshUserList")
	@NotifyChange({"userList", "selectedUser"})
	public void refreshUserList(){
		userList = service.getAllUsersWithRole();
		selectedUser = new User();
	}
	
	@Command
	public void editUserDetail(){
		showUserDetail(this.selectedUser);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void deleteUser(@BindingParam("selectedUser") User user){
		
		this.selectedUser = user;
		
		Messagebox.show(
				"Are you sure to DELETE "+ selectedUser.getUsername() +"?", "Confirm Dialog",
				Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							service.remove(selectedUser);
							info("Successfully deleted");
							audit("Remove User:" + selectedUser.getUsername());
							BindUtils.postGlobalCommand(null, null, "refreshUserList", null);
						} 
					}
				}
			);
	}
	
	 
	
	@GlobalCommand("showUserDetail")
	public void showUserDetail(@BindingParam("selectedUser") User user){
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(user != null && user.getId() != null){
			param.put("selectedUser", user);
		}
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/user-detail.zul", null, param);
		
		popupWin.doModal();
	} 
	
	@Command
    public void openMenuPopup(
			@BindingParam("open") boolean open,
			@BindingParam("referenceValue") Row referenceValue) {
    	if(open){
    		this.selectedUser = referenceValue.getValue();
		}
		
	}
	
	
	//======== SETTER GETTER ===========//

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}


	public User getSelectedUser() {
		return selectedUser;
	}


	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	
	
}
