package com.depobrp.model.report;

import java.util.List;

import com.depobrp.model.order.FreightContainer;

public class MLODailyReport {
	
	public MLODailyReport(){}

	public MLODailyReport(
			List<FreightContainer> moveInList,
			List<FreightContainer> moveOutList,
			List<FreightContainer> storageList) {
		super();
		this.moveInList = moveInList;
		this.moveOutList = moveOutList;
		this.storageList = storageList;
	}
	
	private List<FreightContainer> moveInList;
	private List<FreightContainer> moveOutList;
	private List<FreightContainer> storageList;
	
	public List<FreightContainer> getMoveInList() {
		return moveInList;
	}
	public void setMoveInList(List<FreightContainer> moveInList) {
		this.moveInList = moveInList;
	}
	public List<FreightContainer> getMoveOutList() {
		return moveOutList;
	}
	public void setMoveOutList(List<FreightContainer> moveOutList) {
		this.moveOutList = moveOutList;
	}
	public List<FreightContainer> getStorageList() {
		return storageList;
	}
	public void setStorageList(List<FreightContainer> storageList) {
		this.storageList = storageList;
	}

}
