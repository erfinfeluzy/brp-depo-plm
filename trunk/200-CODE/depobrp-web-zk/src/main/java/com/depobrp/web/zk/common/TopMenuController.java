package com.depobrp.web.zk.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

@Component("TopMenuController")
@Scope("prototype")
public class TopMenuController extends BaseController {

	
	@Command
	public void newUserDetail(){
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/user-detail.zul", null, null);
		
		popupWin.doModal();
	} 
	
	@Command
	public void roleConfiguration(){
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/role-configuration.zul", null, null);
		
		popupWin.doModal();
	}
	
	@Command
	public void sendEmail(){
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/send-email.zul", null, null);
		
		popupWin.doModal();
	}
	
	

	@Command
	public void showPdf(){
		
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/show-pdf.zul", null, null);
		
		popupWin.doModal();
	}
	
	@Command
	public void mloDailyReport(){
		Window popupWin = (Window) Executions.createComponents(
				"/widgets/modal/mlo-daily-report.zul", null, null);
		
		popupWin.doModal();
	}
	
}
