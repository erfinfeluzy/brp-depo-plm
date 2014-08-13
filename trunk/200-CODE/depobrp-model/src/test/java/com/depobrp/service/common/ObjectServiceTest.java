package com.depobrp.service.common;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.depobrp.model.master.MLO;
import com.depobrp.service.common.ObjectService;
import com.depobrp.service.master.MLOService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-ctx-test.xml")
public class ObjectServiceTest {

	@Autowired
	private ObjectService objectService;
	
	@Autowired
	@Qualifier("mloService")
	private MLOService mloService;
	
	@Test
	public void testSave(){
		
		MLO mlo = new MLO();
		
		mlo.setName("DIMAS");
		mlo.setCreatedDate(new Date());
		mlo.setEmail("dimas@gmail.com");
		
		objectService.save(mlo);
		
		List<MLO> list = objectService.getAll(MLO.class);
		
		System.out.println("======== MLO list ========");
		
		for (MLO mlo2 : list) {
			System.out.println(mlo2.getName() + ":" + mlo2.getEmail());
		}
		
		mloService.save(mlo);
		
		Assert.assertNotNull("id not null", mlo.getId());
		
		System.out.println(mlo.getId());
	}
}
