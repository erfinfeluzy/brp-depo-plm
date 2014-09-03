package com.depobrp.web.zk.vm.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import com.depobrp.model.master.MLO;
import com.depobrp.model.master.Vessel;
import com.depobrp.model.order.DeliveryOrderIN;
import com.depobrp.model.order.FreightContainer;
import com.depobrp.service.common.TablePager;
import com.depobrp.service.order.FreightContainerService;

@Component("MoveINListVM")
@Scope("prototype")
public class MoveINListVM {
	
	private Date fromDate;

	private Date toDate;
	
	private FreightContainer filter;
	
	private FreightContainer selectedContainer;
	
	private TablePager<FreightContainer> containerList;
	
	private List<FreightContainer> autocompleteContainerList;
	
	private List<MLO> mloList;

	private List<Vessel> vesselList;
	
	private int activePage = 0;
	private int maxRowPerPage = 10;
	
	@Autowired
	@Qualifier("freightContainerService")
	private FreightContainerService service;
	
	@PostConstruct
	public void init(){
		reset();
		mloList = service.getAll(MLO.class);
		vesselList = service.getAll(Vessel.class);
	}
	
	@Command
	@NotifyChange({"filter", "fromDate", "toDate"})
	public void reset(){
		filter = new FreightContainer();
		DeliveryOrderIN doIN = new DeliveryOrderIN();
		filter.setDoIN(doIN);
		fromDate = null;
		toDate = null;
	}
	
	@Command
	@NotifyChange("autocompleteContainerList")
	public void searchContainerNumber(@BindingParam("autocomplete") String autocomplete){
		autocompleteContainerList = service.getFreightContainerByName(autocomplete);
	}
	
	@Command
	@NotifyChange("containerList")
	public void lookupContainer(){
		containerList = service.getReadyToMoveINList(filter, fromDate, toDate, activePage, maxRowPerPage);
	}
	
	@Command
	public void prepareMoveIN(@BindingParam("selectedContainer") FreightContainer container){
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(container != null && container.getId() != null){
			this.selectedContainer = container;
			param.put("selectedContainer", this.selectedContainer);
			
			Window popupWin = (Window) Executions.createComponents(
					"/widgets/modal/move-in.zul", null, param);
			
			popupWin.doModal();
			
		}
		
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public FreightContainer getFilter() {
		return filter;
	}

	public void setFilter(FreightContainer filter) {
		this.filter = filter;
	}

	public FreightContainer getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(FreightContainer selectedContainer) {
		this.selectedContainer = selectedContainer;
	}

	public List<FreightContainer> getAutocompleteContainerList() {
		return autocompleteContainerList;
	}

	public void setAutocompleteContainerList(
			List<FreightContainer> autocompleteContainerList) {
		this.autocompleteContainerList = autocompleteContainerList;
	}

	public List<MLO> getMloList() {
		return mloList;
	}

	public void setMloList(List<MLO> mloList) {
		this.mloList = mloList;
	}

	public List<Vessel> getVesselList() {
		return vesselList;
	}

	public void setVesselList(List<Vessel> vesselList) {
		this.vesselList = vesselList;
	}

	public TablePager<FreightContainer> getContainerList() {
		return containerList;
	}

	public void setContainerList(TablePager<FreightContainer> containerList) {
		this.containerList = containerList;
	}

	public int getActivePage() {
		return activePage;
	}

	@NotifyChange("containerList")
	public void setActivePage(int activePage) {
		this.activePage = activePage;
		lookupContainer();
	}

	public int getMaxRowPerPage() {
		return maxRowPerPage;
	}

	public void setMaxRowPerPage(int maxRowPerPage) {
		this.maxRowPerPage = maxRowPerPage;
	}
}
