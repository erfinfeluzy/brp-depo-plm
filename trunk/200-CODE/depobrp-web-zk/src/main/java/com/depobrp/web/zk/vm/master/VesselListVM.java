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
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.depobrp.model.master.Vessel;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;


@Component("VesselListVM")
@Scope("prototype")
public class VesselListVM extends BaseController{

	private List<Vessel> vesselList;
	
	private Vessel selectedVessel = new Vessel();
	
	private Vessel filterVessel = new Vessel();
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	
	@GlobalCommand("refreshVesselList")
	@NotifyChange({"vesselList", "selectedVessel"})
	@PostConstruct
	public void refreshVesselList(){
		this.selectedVessel = new Vessel();
		vesselList = service.getAll(Vessel.class);
	}
	
	@Command
	@NotifyChange("vesselList")
	public void changeFilter(){
		vesselList = service.getAll(Vessel.class);// note: change by vessel filter
	}
	
	@Command
	public void showVesselDetail(@BindingParam("selectedVessel") Vessel selectedVessel){
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(selectedVessel != null && selectedVessel.getId() != null){
			this.selectedVessel = selectedVessel;
			param.put("selectedVessel", this.selectedVessel);
		}else{
			param.put("selectedVessel", new Vessel());
		}
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/vessel-detail.zul", null, param);
		
		popupWin.doModal();
		
	}
	
	@Command
	public void deleteVessel(@BindingParam("selectedVessel") Vessel selectedVessel){
		this.selectedVessel = selectedVessel;
		confirm("Do you want to delete Vessel?", deleteEventCallback());
	}
	
	private EventListener<Event> deleteEventCallback() {
		
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equals("onOK")) {
					service.remove(selectedVessel);
					BindUtils.postGlobalCommand(null, null,"refreshVesselList", null);
					info("Vessel has been successfully saved");
				}
			}
		};
	}

	@Command
	public void openRightClickPopup(
			@BindingParam("open") boolean open,
			@BindingParam("referenceValue") Row referenceValue){
		
		if (open) {
			this.selectedVessel = referenceValue.getValue();
		}
	}

	public List<Vessel> getVesselList() {
		return vesselList;
	}

	public void setVesselList(List<Vessel> vesselList) {
		this.vesselList = vesselList;
	}

	public Vessel getSelectedVessel() {
		return selectedVessel;
	}

	public void setSelectedVessel(Vessel selectedVessel) {
		this.selectedVessel = selectedVessel;
	}

	public Vessel getFilterVessel() {
		return filterVessel;
	}

	public void setFilterVessel(Vessel filterVessel) {
		this.filterVessel = filterVessel;
	}
}
