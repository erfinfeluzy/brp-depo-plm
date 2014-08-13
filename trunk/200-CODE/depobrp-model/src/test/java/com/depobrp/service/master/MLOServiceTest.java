package com.depobrp.service.master;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.depobrp.model.master.MLO;
import com.depobrp.service.master.MLOService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-ctx-test.xml")
public class MLOServiceTest {

	@Autowired
	@Qualifier("mloService")
	private MLOService mloService;
	
	@Test
	public void getByFilterTest(){
		
		MLO mlo = new MLO();
		mlo.setName("MAERSK");
		
		List<MLO> list = mloService.getAllByFilter(mlo);
		
		System.out.println(mloService);
		System.out.println(list);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		
	}
}
