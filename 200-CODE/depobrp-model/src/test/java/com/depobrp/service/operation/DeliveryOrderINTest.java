package com.depobrp.service.operation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.depobrp.model.master.Consignee;
import com.depobrp.model.master.MLO;
import com.depobrp.model.master.Vessel;
import com.depobrp.model.order.DeliveryOrderIN;
import com.depobrp.model.order.FreightContainer;
import com.depobrp.service.common.ObjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-ctx-test.xml")
public class DeliveryOrderINTest {

	@Autowired
	@Qualifier("objectService")
	private ObjectService service;
	
	@Test
	public void saveTest(){
		
		MLO mlo = service.find(MLO.class, 28L);
		Consignee consignee = service.find(Consignee.class, 1L);
		Vessel vessel = service.find(Vessel.class, 5L);
		
		FreightContainer container1 = 
				new FreightContainer(
						"WANU123456", 
						3, 
						FreightContainer.Size.F_40, 
						FreightContainer.Type.HC, 
						FreightContainer.Condition.AV, 
						286,
						FreightContainer.EmptyFull.MTY);
		
		FreightContainer container2 = 
				new FreightContainer(
						"WANU54321", 
						3, 
						FreightContainer.Size.F_40, 
						FreightContainer.Type.HC, 
						FreightContainer.Condition.AV, 
						286,
						FreightContainer.EmptyFull.MTY);
		
		Set<FreightContainer> containers = new HashSet<FreightContainer>();
		containers.add(container1);
		containers.add(container2);
		
		
		DeliveryOrderIN doIN = new DeliveryOrderIN();
		doIN.setOwner(mlo);
		doIN.setConsignee(consignee);
		doIN.setDoNumber("TEST123");
		doIN.setDeliveryDate(new Date());
		doIN.setExVessel(vessel);
		doIN.setExVesselVoyageNo("123");
		doIN.setCreatedDate(new Date());
		doIN.setCreatedBy("JUNIT");
		doIN.setInitialContainerCount(containers.size());
		
		service.save(doIN);
		
		Assert.assertNotNull(doIN.getId());
		
		container1.setDoIN(doIN);
		container2.setDoIN(doIN);
		
		service.save(container1);
		service.save(container2);
		
		Assert.assertNotNull(container1.getId());
		Assert.assertNotNull(container2.getId());
		
		
	}
}
