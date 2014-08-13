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

import com.depobrp.model.master.Consignee;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;

@Component("ConsigneeListVM")
@Scope("prototype")
public class ConsigneeListVM extends BaseController{

	private List<Consignee> consigneeList;
	
	private Consignee selectedConsignee;
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	@GlobalCommand("refreshConsigneeList")
	@NotifyChange({"consigneeList", "selectedConsignee"})
	@PostConstruct
	public void refreshConsigneeList(){
		this.selectedConsignee = new Consignee();
		this.consigneeList = service.getAll(Consignee.class);
	}

	@Command
	public void showConsigneeDetail(@BindingParam("selectedConsignee") Consignee selectedConsignee){
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(selectedConsignee != null && selectedConsignee.getId() != null){
			this.selectedConsignee = selectedConsignee;
			param.put("selectedConsignee", this.selectedConsignee);
		}else{
			param.put("selectedConsignee", new Consignee());
		}
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/consignee-detail.zul", null, param);
		
		popupWin.doModal();
		
	}
	
	@Command
	public void deleteConsignee(@BindingParam("selectedConsignee") Consignee selectedConsignee){
		this.selectedConsignee = selectedConsignee;
		confirm("Do you want to delete Consignee?", deleteEventCallback());
	}
	
	private EventListener<Event> deleteEventCallback() {
		
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equals("onOK")) {
					service.remove(selectedConsignee);
					BindUtils.postGlobalCommand(null, null,"refreshConsigneeList", null);
					info("Consignee has been successfully saved");
				}
			}
		};
	}
	
	public List<Consignee> getConsigneeList() {
		return consigneeList;
	}

	public void setConsigneeList(List<Consignee> consigneeList) {
		this.consigneeList = consigneeList;
	}

	public Consignee getSelectedConsignee() {
		return selectedConsignee;
	}

	public void setSelectedConsignee(Consignee selectedConsignee) {
		this.selectedConsignee = selectedConsignee;
	}

}
