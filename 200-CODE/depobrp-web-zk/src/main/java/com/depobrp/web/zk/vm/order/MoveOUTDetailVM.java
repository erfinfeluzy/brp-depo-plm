package com.depobrp.web.zk.vm.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.Condition;
import com.depobrp.model.order.FreightContainer.EmptyFull;
import com.depobrp.model.order.FreightContainer.OrderStatus;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;

@Component("MoveOUTDetailVM")
@Scope("prototype")
public class MoveOUTDetailVM extends BaseController {

	@Init
	public void init(
			@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view,
            @ExecutionArgParam ("selectedContainer") FreightContainer selectedContainer){
		
		Selectors.wireComponents(view, this, false);
		this.currentContainer = selectedContainer;
		allConditions = new ListModelList<Condition>(Condition.values());
		emptyOrFull = new ListModelList<EmptyFull>(EmptyFull.values());
	}
	
	private FreightContainer currentContainer;
	
	private ListModelList<Condition> allConditions;

	private ListModelList<EmptyFull> emptyOrFull;
	
	@Wire("#moveOUTWindow")
	private Window window;
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	
	@Command
	public void moveOUT(){
		confirm("Do you want to Move OUT this container?", 
			new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals("onOK")) {
						moveOutAfterConfirm();
					} 
				}
			});
	}
	
	private void moveOutAfterConfirm() {
		
		try {
			Date trxDate = new Date();
			System.out.println("save:" + currentContainer.getContainerNum());
			currentContainer.setOrderStatus(OrderStatus.MOVE_OUT);
			currentContainer.setMoveOUTDate(trxDate);
			currentContainer.setUpdatedBy(getUsername());
			currentContainer.setUpdatedDate(trxDate);
			service.save(currentContainer);
			
			audit("Move OUT:" + currentContainer.getContainerNum());
			
			info("Successfuly move IN container:" + currentContainer.getContainerNum());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			this.window.detach();
		}
	}

	public FreightContainer getCurrentContainer() {
		return currentContainer;
	}

	public void setCurrentContainer(FreightContainer currentContainer) {
		this.currentContainer = currentContainer;
	}

	public ListModelList<Condition> getAllConditions() {
		return allConditions;
	}

	public void setAllConditions(ListModelList<Condition> allConditions) {
		this.allConditions = allConditions;
	}

	public ListModelList<EmptyFull> getEmptyOrFull() {
		return emptyOrFull;
	}

	public void setEmptyOrFull(ListModelList<EmptyFull> emptyOrFull) {
		this.emptyOrFull = emptyOrFull;
	}
}
