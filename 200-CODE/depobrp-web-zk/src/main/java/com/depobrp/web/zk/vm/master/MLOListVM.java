package com.depobrp.web.zk.vm.master;

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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.depobrp.model.master.MLO;
import com.depobrp.service.master.MLOService;
import com.depobrp.web.zk.common.BaseController;

@Component("MLOListVM")
@Scope("prototype")
public class MLOListVM extends BaseController {

	@Autowired
	private MLOService service;

	private List<MLO> mloList;

	private MLO selectedMlo = new MLO();

	private MLO filterMlo = new MLO();

	@PostConstruct
	public void init() {
		refreshMloList();
	}

	@GlobalCommand("refreshMloList")
	@NotifyChange({ "mloList", "mlo" })
	public void refreshMloList() {
		
		mloList = service.getAll(MLO.class);
		selectedMlo = new MLO();
	}

	@Command
	public void deleteMlo(@BindingParam("selectedMlo") MLO selectedMlo) {

		this.selectedMlo = selectedMlo;
		confirm("Are you sure to DELETE " + selectedMlo.getName() + "?", deleteMloEvent());
	}
	
	private EventListener<Event> deleteMloEvent(){
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					service.remove(selectedMlo);
					info("Successfully deleted");
					BindUtils.postGlobalCommand(null, null, "refreshMloList", null);
				}
			}
		};
	}

	@Command
	public void editMlo(@BindingParam("selectedMlo") MLO selectedMlo) {

		this.selectedMlo = selectedMlo;
		confirm("Are you sure to EDIT selected Row?", editMloEvent());

	}
	private EventListener<Event> editMloEvent(){
		
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					service.save(selectedMlo);
					info("Successfully saved");
					BindUtils.postGlobalCommand(null, null,"refreshMloList", null);
				}
			}
		};
	}

	@Command
	@NotifyChange("mloList")
	public void changeFilter() {
		
		mloList = service.getAllByFilter(filterMlo);
	}

	@Command
	public void openMessagePopup(@BindingParam("open") boolean open,
			@BindingParam("referenceValue") Row referenceValue) {

		if (open) {
			this.selectedMlo = referenceValue.getValue();
		}
	}

	@Command
	public void sendMessage() {
		System.out.println("send message: " + this.selectedMlo.getName());
	}
	
	@Command
	public void editMloDetail(){
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(selectedMlo != null && selectedMlo.getId() != null){
			param.put("selectedMlo", this.selectedMlo);
		}else{
			param.put("selectedMlo", new MLO());
		}
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/mlo-detail.zul", null, param);
		
		popupWin.doModal();
	} 

	public MLO getFilterMlo() {
		return filterMlo;
	}

	public void setFilterMlo(MLO filterMlo) {
		this.filterMlo = filterMlo;
	}

	public void setMloList(List<MLO> mloList) {
		this.mloList = mloList;
	}

	public List<MLO> getMloList() {
		return mloList;
	}

}
