package com.depobrp.web.zk.vm.master;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import com.depobrp.model.master.Consignee;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;

@Component("ConsigneeDetailVM")
@Scope("prototype")
public class ConsigneeDetailVM extends BaseController {

	private Consignee consignee;
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	@Wire("#consigneeDetailDialog")
	private Window window;
	
	@Init
    public void init(
    		@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view,
            @ExecutionArgParam ("selectedConsignee") Consignee consigneeFromParentWindow) {
        
		Selectors.wireComponents(view, this, false);
        this.consignee = consigneeFromParentWindow;
    }
	
	@Command
	public void saveConsignee(){
		
		confirm("Are you sure to SAVE?", saveConsigneeEvent());
	}

	private EventListener<Event> saveConsigneeEvent() {
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equals("onOK")) {
					consignee.setUpdatedBy(getUsername());
					consignee.setUpdatedDate(new Date());
					service.save(consignee);
					info("Successfully saved");
					BindUtils.postGlobalCommand(null, null, "refreshConsigneeList", null);
					window.detach();
				} 
			}
		};
	}

	public Consignee getConsignee() {
		return consignee;
	}

	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}
	
	
	
}
