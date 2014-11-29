package com.depobrp.web.zk.vm.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;

import com.depobrp.commons.util.CollectionUtils;
import com.depobrp.commons.util.ISO6346Utils;
import com.depobrp.model.master.Consignee;
import com.depobrp.model.master.MLO;
import com.depobrp.model.master.Vessel;
import com.depobrp.model.order.DeliveryOrderOUT;
import com.depobrp.model.order.FreightContainer;
import com.depobrp.service.order.DeliveryOrderOUTService;
import com.depobrp.service.order.FreightContainerService;
import com.depobrp.web.zk.common.BaseController;

@Component("DeliveryOrderOutVM")
@Scope("prototype")
public class DeliveryOrderOutVM extends BaseController {

	private List<MLO> mloList;
	
	private List<Consignee> consigneeList;
	
	private List<Vessel> vesselList;

	private List<FreightContainer> containerList;
	
	private FreightContainer currentContainer;
	
	private DeliveryOrderOUT doOUT = new DeliveryOrderOUT();
	
	private String addContainerErrorMessage;
	
	private ListModelList<FreightContainer.Size> containerSizeList;
	
	private ListModelList<FreightContainer.Type> containerTypeList;
	
	private List<FreightContainer> autocompleteContainerList;
	
	@Autowired
	@Qualifier("deliveryOrderOUTService")
	private DeliveryOrderOUTService service;

	@Autowired
	@Qualifier("freightContainerService")
	private FreightContainerService freightContainerService;
	
	@PostConstruct
	public void init(){
		mloList = service.getAll(MLO.class);
		consigneeList = service.getAll(Consignee.class);
		vesselList = service.getAll(Vessel.class);
		containerList = new ArrayList<FreightContainer>();
		addContainerErrorMessage=  new String();
		currentContainer = new FreightContainer();
		containerSizeList = new ListModelList<FreightContainer.Size>(FreightContainer.Size.values());
		containerTypeList = new ListModelList<FreightContainer.Type>(FreightContainer.Type.values());
	}
	
	@GlobalCommand("resetDeliveryOrderOUTPage")
	@NotifyChange({"doOUT", "containerList", "currentContainer"})
	public void resetDeliveryOrderINPage(){
		this.doOUT = new DeliveryOrderOUT();
		containerList = new ArrayList<FreightContainer>();
		currentContainer = new FreightContainer();
	}
	
	@Command
	public void saveDeliveryOrderOUT(){
		doOUT.setCreatedBy(super.getUsername());
		doOUT.setContainers(new HashSet<FreightContainer>(containerList));
		if(CollectionUtils.isEmpty(this.containerList)){
			alert("Please Insert Container Number");
			return;
		}
			
		confirm("Are you sure?", saveAfterConfirmation());
	}
	
	private EventListener<Event> saveAfterConfirmation(){
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				service.saveDoOutWithContainer(doOUT);
				BindUtils.postGlobalCommand(null, null,"resetDeliveryOrderOUTPage", null);
				info("Successfully saved");
			}
		};
	}
	
	@Command
	@NotifyChange({"containerList", "addContainerErrorMessage", "currentContainer"})
	public void addContainer(){
		
		if(currentContainer == null || 
				currentContainer.getContainerNum() == null ||
				currentContainer.getContainerNum().length() < 1 ||
				existInContainerList() ){
			
			addContainerErrorMessage = "Unable to add container number";
			return;
		}
		
		if(! ISO6346Utils.isValidContainerNumber(currentContainer.getContainerNum())){
			alert("Invalid Container Number");
			return;
		}
		
		int cd = ISO6346Utils.calculateCheckDigit(currentContainer.getContainerNum());
		if(currentContainer.getCheckDigit()== null || cd != currentContainer.getCheckDigit()){
			
			addContainerErrorMessage = "Correct CD is '"+ cd +"'";
			currentContainer.setCheckDigit(cd);
			return;
		}
		
		currentContainer.setContainerNum(currentContainer.getContainerNum().toUpperCase());
		currentContainer.setCreatedBy(super.getUsername());
		
		containerList.add(currentContainer);
		addContainerErrorMessage = "";
		currentContainer = new FreightContainer();
	}
	
	@Command
	@NotifyChange({"containerList", "addContainerErrorMessage"})
	public void removeContainer(@BindingParam("selected") FreightContainer container){
		
		containerList.remove(container);
		addContainerErrorMessage = "";
	}
	
	private boolean existInContainerList() {
		for (FreightContainer containerInList : this.containerList) {
			if (containerInList.getContainerNum()
					.equalsIgnoreCase(
							this.currentContainer.getContainerNum()))
				return true;
		}
		return false;
	}
	
	@Command
	@NotifyChange("autocompleteContainerList")
	public void searchContainerNumber(@BindingParam("autocomplete") String autocomplete){
		autocompleteContainerList = freightContainerService.getFreightContainerByName(autocomplete);
	}

	@Command
	@NotifyChange("containerList")
	public void lookupContainer(@BindingParam("autocomplete") String autocomplete){
		System.out.println(autocomplete);
	}

	public List<MLO> getMloList() {
		return mloList;
	}

	public void setMloList(List<MLO> mloList) {
		this.mloList = mloList;
	}

	public List<Consignee> getConsigneeList() {
		return consigneeList;
	}

	public void setConsigneeList(List<Consignee> consigneeList) {
		this.consigneeList = consigneeList;
	}

	public List<Vessel> getVesselList() {
		return vesselList;
	}

	public void setVesselList(List<Vessel> vesselList) {
		this.vesselList = vesselList;
	}

	public DeliveryOrderOUT getDoOUT() {
		return doOUT;
	}

	public void setDoOUT(DeliveryOrderOUT doOUT) {
		this.doOUT = doOUT;
	}

	public String getAddContainerErrorMessage() {
		return addContainerErrorMessage;
	}

	public void setAddContainerErrorMessage(String addContainerErrorMessage) {
		this.addContainerErrorMessage = addContainerErrorMessage;
	}

	public List<FreightContainer> getContainerList() {
		return containerList;
	}

	public void setContainerList(List<FreightContainer> containerList) {
		this.containerList = containerList;
	}

	public FreightContainer getCurrentContainer() {
		return currentContainer;
	}

	public void setCurrentContainer(FreightContainer currentContainer) {
		this.currentContainer = currentContainer;
	}

	public ListModelList<FreightContainer.Size> getContainerSizeList() {
		return containerSizeList;
	}

	public void setContainerSizeList(
			ListModelList<FreightContainer.Size> containerSizeList) {
		this.containerSizeList = containerSizeList;
	}

	public ListModelList<FreightContainer.Type> getContainerTypeList() {
		return containerTypeList;
	}

	public void setContainerTypeList(
			ListModelList<FreightContainer.Type> containerTypeList) {
		this.containerTypeList = containerTypeList;
	}

	public List<FreightContainer> getAutocompleteContainerList() {
		return autocompleteContainerList;
	}

	public void setAutocompleteContainerList(
			List<FreightContainer> autocompleteContainerList) {
		this.autocompleteContainerList = autocompleteContainerList;
	}

}
