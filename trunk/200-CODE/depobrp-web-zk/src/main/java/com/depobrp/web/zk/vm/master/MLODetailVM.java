package com.depobrp.web.zk.vm.master;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.depobrp.model.master.MLO;
import com.depobrp.service.master.MLOService;
import com.depobrp.web.zk.common.BaseController;

@Component("MLODetailVM")
@Scope("prototype")
public class MLODetailVM  extends BaseController{
	
	@Autowired
	private MLOService service;
	
	private MLO mlo;
	
	@Wire("#mloDetailDialog")
	private Window window;
	
	@Init
    public void init(
    		@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view,
            @ExecutionArgParam ("selectedMlo") MLO mloFromParentWindow) {
        
		Selectors.wireComponents(view, this, false);
        this.mlo = mloFromParentWindow;
    }
 
	@Command
	public void saveMlo(){
		
		confirm("Are you sure to SAVE?", saveMloEvent());
	}

	private EventListener<Event> saveMloEvent(){
		
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					mlo.setUpdatedBy(getUsername());
					mlo.setUpdatedDate(new Date());
					service.save(mlo);
					info("Successfully saved");
					BindUtils.postGlobalCommand(null, null, "refreshMloList", null);
					window.detach();
				} 
			}
		};
	}

	public MLO getMlo() {
		return mlo;
	}

	public void setMlo(MLO mlo) {
		this.mlo = mlo;
	}

}
