package com.depobrp.web.zk.security;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.commons.util.CollectionUtils;
import com.depobrp.model.user.Module;
import com.depobrp.model.user.User;


@Service("customUserDetailService")
@Transactional(readOnly = true, 
			propagation = Propagation.SUPPORTS)
public class CustomUserDetailService 
	implements UserDetailsService{

	public final static String ATTR_USER_ID = "ATTR_USER_ID";
	
	private HibernateTemplate hibernate;
	
	@Autowired
	public CustomUserDetailService(SessionFactory sessionFactory) {
		this.hibernate = new HibernateTemplate(sessionFactory);
		System.out.println("initialized : CustomUserDetailService");
	}

	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
	
		List<User> result = hibernate.find("from User u where u.username = ?",
				new Object[] { username });
		
		if(CollectionUtils.isEmpty(result)){
			throw new UsernameNotFoundException("Invalid Username and Password");
		}
		
		User user = result.get(0);
		
		Hibernate.initialize(user.getRole().getModules());
		
		for (Module module : user.getRole().getModules()) {
			Hibernate.initialize(module.getPages());
		}
		
		try{
			
			UserDetails details = new CustomUserDetail(user);
			
			return details;
		
		}catch(Exception e){
			throw new UsernameNotFoundException("Invalid Username and Password");
		}
		
	}
	
}
