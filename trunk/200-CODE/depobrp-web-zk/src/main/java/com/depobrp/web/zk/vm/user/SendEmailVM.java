package com.depobrp.web.zk.vm.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.depobrp.integration.email.EmailSender;
import com.depobrp.model.user.User;
import com.depobrp.service.user.UserService;
import com.depobrp.web.zk.common.BaseController;

@Component("SendEmailVM")
@Scope("prototype")
public class SendEmailVM extends BaseController {
	
	private String to;
	private String subject;
	private String messageText;
	
	@Wire("#sendEmailDialog")
	private Window window;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	@Qualifier("userService")
	private UserService service;
	
	@Init
    public void init(@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view) {
		//support MVC @Wire
        Selectors.wireComponents(view, this, false);
     }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void sendEmail(){
		
		Messagebox.show(
				"Do you want to send this email?", "Confirm Dialog",
				Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							sendEmailAfterConfirmation();
						} 
					}
				}
			);
	}
	
	private void sendEmailAfterConfirmation(){
		try {
			emailSender.sendMail(getUsername(), this.to, this.subject, this.messageText);
			audit("send message|sender:" + getUsername() + 
					"|to:" + this.to + 
					"|subject:" + this.subject + 
					"|message:" + this.messageText);
			info("Message has been successfully sent");
			window.detach();
		} catch (Exception e) {
			e.printStackTrace();
			info("Error:" + e.getMessage());
		}
		
	}
	
	@Command
	public void autocompleteRecipient(@BindingParam("event") Event event){
		InputEvent inputEvent = (InputEvent) event;
        String inputText = inputEvent.getValue();
        
        if(inputText != null && inputText.length() < 3) return;
        
        List<User> users = service.searchUserByUsername(inputText);
        
        Combobox autocompleteBox = (Combobox) event.getTarget();
        autocompleteBox.getChildren().clear();
        
        for (User user : users) {
        	Comboitem item = new Comboitem();
            item.setLabel(user.getUsername());
            item.setValue(user.getUsername());
            autocompleteBox.appendChild(item);
		}
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

}
