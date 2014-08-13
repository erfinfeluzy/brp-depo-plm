package com.depobrp.web.zk.vm.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.depobrp.model.user.Module;
import com.depobrp.model.user.Role;
import com.depobrp.service.user.RoleService;
import com.depobrp.web.zk.common.BaseController;

@Component("RoleConfigurationVM")
@Scope("prototype")
public class RoleConfigurationVM extends BaseController {
	
	@Autowired
	@Qualifier("roleService")
	private RoleService service;

	@Wire("#roleConfigurationDialog")
	private Window window;
	
	private List<Module> availableModule;
	
	private List<Module> selectedModule;
	
	private List<Role> allRole;
	
	private Role selectedRole;
	
	private boolean createNewRole = false;
	
	private String newRoleName;
	
	@Init
    public void init(@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view) {
        Selectors.wireComponents(view, this, false);
        
        availableModule = service.getAll(Module.class);
        selectedModule = new ArrayList<Module>();
        allRole = service.getAll(Role.class);
        createNewRole = false;
	}
	
	@Command
	public void closeDialog(){
		this.window.detach();
	}
	
	@Command
	public void saveRoleModule(){
		if(createNewRole){
			System.out.println("create new role");
			Role newRole = new Role(newRoleName);
			newRole.setModules(new HashSet<Module>(selectedModule));
			service.save(newRole);
			Clients.showNotification("Successfully saved role: " + newRoleName);
		}else if(selectedRole != null){
			System.out.println("update role");
			selectedRole.setModules(new HashSet<Module>(selectedModule));
			service.save(selectedRole);
			Clients.showNotification("Successfully saved role: " + selectedRole.getName());
		}
		
	}
	
	@Command
	@NotifyChange({"availableModule","selectedModule"})
	public void selectRole(){
		
		if(selectedRole != null){
			Role roleDetail = service.getRoleWithModules(selectedRole.getId());
			availableModule = service.getAll(Module.class);
			selectedModule = new ArrayList<Module>();
			for (Module module : roleDetail.getModules()) {
				selectedModule.add(module);
				availableModule.remove(module);
			}
		}
	}
	
	@Command
	@NotifyChange({"availableModule","selectedModule"})
	public void selectModule(@BindingParam("module") Module module){
		
		if(module != null && 
				availableModule.contains(module) && 
				!selectedModule.contains(module)){
			
			availableModule.remove(module);
			selectedModule.add(module);
		}
	}
	
	@Command
	@NotifyChange({"availableModule","selectedModule"})
	public void removeModule(@BindingParam("module") Module module){
		
		if(module != null &&
				!availableModule.contains(module) && 
				selectedModule.contains(module)){
			
			availableModule.add(module);
			selectedModule.remove(module);
		}
	}

	
	public List<Module> getAvailableModule() {
		return availableModule;
	}

	public void setAvailableModule(List<Module> availableModule) {
		this.availableModule = availableModule;
	}

	public List<Module> getSelectedModule() {
		return selectedModule;
	}

	public void setSelectedModule(List<Module> selectedModule) {
		this.selectedModule = selectedModule;
	}

	public List<Role> getAllRole() {
		return allRole;
	}

	public void setAllRole(List<Role> allRole) {
		this.allRole = allRole;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public boolean isCreateNewRole() {
		return createNewRole;
	}

	public void setCreateNewRole(boolean createNewRole) {
		this.createNewRole = createNewRole;
	}

	public String getNewRoleName() {
		return newRoleName;
	}

	public void setNewRoleName(String newRoleName) {
		this.newRoleName = newRoleName;
	}
	
}
