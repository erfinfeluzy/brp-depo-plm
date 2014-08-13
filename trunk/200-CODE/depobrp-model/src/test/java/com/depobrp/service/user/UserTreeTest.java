package com.depobrp.service.user;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.depobrp.model.user.User;
import com.depobrp.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-ctx-test.xml")
public class UserTreeTest {

	@Autowired
	@Qualifier("userService")
	private UserService service;

//	@Test
	public void testSupervisor(){
		User dimas = service.find(User.class, 2L); 
		User erfin = service.find(User.class, 1L);
		
		dimas.setSupervisor(erfin);
		
		service.save(dimas);
		service.save(erfin);
		
		Assert.assertEquals(dimas.getSupervisor().getUsername(), erfin.getUsername());
	}
	
//	@Test
	public void searchUserByUsernameTest(){
		String keyword = "erfin.feluzy@gmail.com";
		List<User> users = service.searchUserByUsername(keyword);
		for (User user : users) {
			System.out.println(user.getUsername());
			Assert.assertNotNull(user.getUsername());
		}
		
	}
	
	@Test
	public void fetchChildrenTest(){
		
		User user = service.getUserWithAllSubOrdinates(1L);
//		System.out.println("root:" + user.getUsername() + 
//				", all children size: " + user.getAllSubOrdinates().size() + 
//				", direct children size: " + user.getSubOrdinates().size()
//		);
//		
//		if(!CollectionUtils.isEmpty(user.getAllSubOrdinates())){
//			System.out.println("his/her subOrdinates are:");
//			for (User subOrdinate : user.getAllSubOrdinates()) {
//				Assert.assertNotNull(subOrdinate.getUsername());
//				System.out.println("subOrdinate name:" + subOrdinate.getUsername());
//			}
//		}
		
		traverse(user);
		
	}
	
	public void traverse(User parentNode) {
	    // traverse all nodes that belong to the parent
		if(parentNode.getSubOrdinates().size() < 1) return;
		
		System.out.println("parent: " + parentNode.getUsername());
		
	    for(User subOrdinate : parentNode.getSubOrdinates()) {
	      
	    	// print node information
	      System.out.println("TRAVERSE:"+ subOrdinate.getUsername());
	      
	      // traverse children
	      traverse(subOrdinate);
	    }
	  }
}
