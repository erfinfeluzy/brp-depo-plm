package com.depobrp.web.zk.vm.master;

import java.util.Map;
import java.util.Set;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class MLOValidator extends AbstractValidator {

	@Override
	public void validate(ValidationContext ctx) {

		//all the bean properties
        Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
        
        Set<String> keySet = beanProps.keySet();
        for (String key : keySet) {
			System.out.println(key +":" + beanProps.get(key).getValue());
		}

		String name = (String) beanProps.get("name").getValue();
		String email = (String)beanProps.get("email").getValue();

		System.out.println("name = " + name);
		System.out.println("email = " + email);
		
		if (name == null || name.length() < 1)
			addInvalidMessage(ctx, "name", "Invalid Name");

		if (email == null || !email.matches(".+@.+\\.[a-z]+")) {
			this.addInvalidMessage(ctx, "email", "Please enter a valid email!");
		}
	}

}
