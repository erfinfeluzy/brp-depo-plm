package com.depobrp.service.user;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.depobrp.model.user.Module;
import com.depobrp.model.user.User;
import com.depobrp.service.common.ObjectServiceImpl;

@Service("userService")
@Transactional
public class UserServiceImpl 
	extends ObjectServiceImpl 
	implements UserService{

	public User getInitializedUser(Long userId) {
		
		User user = super.find(User.class, userId);
		
		Hibernate.initialize(user.getRole().getModules());
		Hibernate.initialize(user.getSupervisor());
			
		for(Module m : user.getRole().getModules()){
			Hibernate.initialize(m.getPages());
		}
		
		return user;
	}
	
	public List<User> getAllUsersWithRole(){
		
		List<User> users = super.getAll(User.class);
		
		for (User user : users) {
			Hibernate.initialize(user.getRole());
			Hibernate.initialize(user.getSupervisor());
		}
		
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> searchUserByUsername(final String keyword){
		
		final String hql = "from User u where u.username like ? ";
		
		return (List<User>) super.getFromHql(hql, new Object[]{keyword + "%"}, 10);
		
	}

	@SuppressWarnings("unchecked")
	public User getUserWithAllSubOrdinates(Long id) {
		
		Long start = System.currentTimeMillis();

		String hql = "from User u left join fetch u.subOrdinates where u.id = ?";
		
		List<User> users = getFromHql(hql, new Object[]{id}, 1);
		
		if(users == null || users.size() < 1) return null;
		
		User user = users.get(0);
		
		if(!CollectionUtils.isEmpty(user.getSubOrdinates())){
			user.fetchChildInSession(user, user);
		}
		
		System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
		
		return user;
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {

		final String hql = "from User u where u.username = ? ";
		
		List<User> users = (List<User>) super.getFromHql(hql, new Object[]{username}, 1);
		
		if(users != null && users.size() == 1){
			return users.get(0);
		}
		
		return null;
	}

}
