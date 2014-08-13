package com.depobrp.web.zk.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Vbox;

import com.depobrp.commons.util.CollectionUtils;
import com.depobrp.commons.util.FormatUtils;
import com.depobrp.model.user.User;
import com.depobrp.model.user.UserActivity;
import com.depobrp.service.common.ObjectService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserActivityComposer extends SelectorComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4675760219815011149L;
	
	@WireVariable
	private ObjectService objectService;
	
	@Wire
    private Timer userActivityLookupTimer;
	
	@Wire
	private Listbox activityList;

	@Listen("onTimer = #userActivityLookupTimer")
    public void refreshUserActivity() {
		
		System.out.println("onTimer");
		
		User root = SessionInitializer.getUserWithSubordinate();
		
//		List<UserActivity> datas = objectService.getAll(UserActivity.class);
		List<UserActivity> datas = getLastActivityByNameList(getSubordinateUsernames(root));
		
		System.out.println("total data size:" + datas.size());

		
		//clear data on grid
		activityList.getItems().clear();
		
		for (UserActivity activity : datas) {
			Listitem item = new Listitem();

			String label = 
					FormatUtils.format(activity.getCreatedDate(), "MMM dd, HH:mm") + 
					" - " + activity.getCreatedBy();
			
			Label timeAndCreatedBy = new Label(label);
			Label description = new Label(activity.getDescription());
			
			Vbox vbox = new Vbox(new Label[]{timeAndCreatedBy, description});
			
			Listcell itemCell = new Listcell();
			
			vbox.setParent(itemCell);
			
			item.appendChild(itemCell);
			item.setParent(activityList);
		}

	}
	
	private List<String> getSubordinateUsernames(User root){
	
		if(root == null) return null;
		
		List<String> result = new ArrayList<String>();
		result.add(root.getUsername());
		
		if(CollectionUtils.isNotEmpty(root.getAllSubOrdinates())){
			
			System.out.println("subordinate size:" + root.getAllSubOrdinates());
			
			for (User user : root.getAllSubOrdinates()) {
				result.add(user.getUsername());
				
				System.out.println(user.getUsername());
			}
		}
		
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<UserActivity> getLastActivityByNameList(List<String> allSubordinates){
		String hql = "from UserActivity a where a.createdBy in (:allSubordinates) order by a.id desc ";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("allSubordinates", allSubordinates);
		return objectService.getFromHql(hql, param, 10);
	}
	
	

}
