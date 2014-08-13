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

import com.depobrp.model.master.Vessel;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;


@Component("VesselDetailVM")
@Scope("prototype")
public class VesselDetailVM extends BaseController {

	private Vessel vessel;
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	@Wire("#vesselDetailDialog")
	private Window window;
	
	@Init
    public void init(
    		@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view,
            @ExecutionArgParam ("selectedVessel") Vessel vesselFromParentWindow) {
        
		Selectors.wireComponents(view, this, false);
        this.vessel = vesselFromParentWindow;
    }
	
	@Command
	public void saveVessel(){
		
		confirm("Are you sure to SAVE?", saveVesselEvent());
	}

	private EventListener<Event> saveVesselEvent() {
		return new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equals("onOK")) {
					vessel.setUpdatedBy(getUsername());
					vessel.setUpdatedDate(new Date());
					service.save(vessel);
					info("Successfully saved");
					BindUtils.postGlobalCommand(null, null, "refreshVesselList", null);
					window.detach();
				} 
			}
		};
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

}
