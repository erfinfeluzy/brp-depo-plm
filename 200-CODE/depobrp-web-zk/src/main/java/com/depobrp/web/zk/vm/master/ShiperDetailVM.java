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

import com.depobrp.model.master.Shiper;
import com.depobrp.service.common.ObjectService;
import com.depobrp.web.zk.common.BaseController;


@Component("ShiperDetailVM")
@Scope("prototype")
public class ShiperDetailVM extends BaseController {

	private Shiper shiper;
	
	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	@Wire("#shiperDetailDialog")
	private Window window;
	
	@Init
    public void init(
    		@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view,
            @ExecutionArgParam ("selectedShiper") Shiper shiperFromParentWindow) {
        
		Selectors.wireComponents(view, this, false);
        this.shiper = shiperFromParentWindow;
    }
	
	@Command
	public void saveShiper(){
		
		confirm("Are you sure to SAVE?", saveShiperEvent());
	}

	private EventListener<Event> saveShiperEvent() {
		return new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equals("onOK")) {
					shiper.setUpdatedBy(getUsername());
					shiper.setUpdatedDate(new Date());
					service.save(shiper);
					info("Successfully saved");
					BindUtils.postGlobalCommand(null, null, "refreshShiperList", null);
					window.detach();
				} 
			}
		};
	}

	public Shiper getShiper() {
		return shiper;
	}

	public void setShiper(Shiper shiper) {
		this.shiper = shiper;
	}
}
