package com.depobrp.web.zk.vm.order;

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
import org.zkoss.zul.ListModelList;

import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.Condition;
import com.depobrp.model.order.FreightContainer.EmptyFull;
import com.depobrp.web.zk.common.BaseController;

@Component("MoveINDetailVM")
@Scope("prototype")
public class MoveINDetailVM extends BaseController {

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
	
	
	@Command
	public void moveIN(){
		confirm("Do you want to Move IN this container?", moveInAfterConfirm());
	}
	
	private EventListener<Event> moveInAfterConfirm() {
		return new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				System.out.println("save:" + currentContainer.getContainerNum());
				info("Successfuly move IN container:" + currentContainer.getContainerNum());
			}
			
		};
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
