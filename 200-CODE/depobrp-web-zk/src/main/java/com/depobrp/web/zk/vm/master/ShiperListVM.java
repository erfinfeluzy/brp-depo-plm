package com.depobrp.web.zk.vm.master;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Window;

import com.depobrp.model.master.Shiper;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;

@Component("ShiperListVM")
@Scope("prototype")
public class ShiperListVM extends BaseController{

	private List<Shiper> shiperList;
	
	private Shiper selectedShiper;
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	@GlobalCommand("refreshShiperList")
	@NotifyChange({"shiperList", "selectedShiper"})
	@PostConstruct
	public void refreshShiperList(){
		this.selectedShiper = new Shiper();
		this.shiperList = service.getAll(Shiper.class);
	}
	
	@Command
	public void showShiperDetail(@BindingParam("selectedShiper") Shiper selectedShiper){
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(selectedShiper != null && selectedShiper.getId() != null){
			this.selectedShiper = selectedShiper;
			param.put("selectedShiper", this.selectedShiper);
		}else{
			param.put("selectedShiper", new Shiper());
		}
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/shiper-detail.zul", null, param);
		
		popupWin.doModal();
		
	}
	
	@Command
	public void deleteShiper(@BindingParam("selectedShiper") Shiper selectedShiper){
		this.selectedShiper = selectedShiper;
		confirm("Do you want to delete Shiper?", deleteEventCallback());
	}
	
	private EventListener<Event> deleteEventCallback() {
		
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equals("onOK")) {
					service.remove(selectedShiper);
					BindUtils.postGlobalCommand(null, null,"refreshShiperList", null);
					info("Shiper has been successfully saved");
				}
			}
		};
	}

	public List<Shiper> getShiperList() {
		return shiperList;
	}

	public void setShiperList(List<Shiper> shiperList) {
		this.shiperList = shiperList;
	}

	public Shiper getSelectedShiper() {
		return selectedShiper;
	}

	public void setSelectedShiper(Shiper selectedShiper) {
		this.selectedShiper = selectedShiper;
	}
}
