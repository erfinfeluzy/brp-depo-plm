package com.depobrp.web.zk.common;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import com.depobrp.model.user.Module;
import com.depobrp.model.user.Page;
import com.depobrp.model.user.User;

public class EastSidebarAjaxbasedController extends SelectorComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 470419867872569241L;
	
	@Wire
	Tabbox eastSideBarTabbox;
	
	//wire service
	SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);

		Tabs tabs = eastSideBarTabbox.getTabs();
		Tabpanels tps = eastSideBarTabbox.getTabpanels();
		
		User user = pageConfig.getUser();
		
		for (Module module : user.getRole().getModules()) {
			Tab tab = new Tab(module.getName());
			tab.setParent(tabs);
			
			Tabpanel tabpanel = new Tabpanel() ;
			tabpanel.setParent(tps);
			
			Panel panel = new Panel();
			panel.setParent(tabpanel);
			Panelchildren pc = new Panelchildren();
			pc.setParent(panel);
			Vbox vbox = new Vbox();
			vbox.setParent(pc);
			
			for (Page page : module.getPages()) {
				
				Toolbarbutton tb = constructSidebarToolbarButton(page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
				vbox.appendChild(tb);
			}
		}
	}
	
	private Toolbarbutton constructSidebarToolbarButton(final String name,String label, String imageSrc, final String locationUri) {
		
		//construct component and hierarchy
		Toolbarbutton tb = new Toolbarbutton();
		
		tb.setImage(imageSrc);
		tb.setLabel(label);

		
		//new and register listener for events
		EventListener<Event> onActionListener = new SerializableEventListener<Event>(){
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				//redirect current url to new location
				if(locationUri.startsWith("http")){
					//open a new browser tab
					Executions.getCurrent().sendRedirect(locationUri);
				}else{
					//use iterable to find the first include only
					Include include = (Include)Selectors.iterable(eastSideBarTabbox.getPage(), "#mainInclude").iterator().next();
					include.setSrc(locationUri);
					
					//advance bookmark control, 
					//bookmark with a prefix
					if(name!=null){
//						getPage().getDesktop().setBookmark("p_"+name);
						getPage().getDesktop().setBookmark(Context.BOOKMARK_SEPARATOR + name);
					}
				}
			}
		};		
		tb.addEventListener(Events.ON_CLICK, onActionListener);

		return tb;
	}
	
}
