package com.depobrp.service.order;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.model.order.DeliveryOrderIN;
import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.OrderStatus;
import com.depobrp.service.common.ObjectServiceImpl;

@Service("deliveryOrderINService")
@Transactional
public class DeliveryOrderINServiceImpl
		extends ObjectServiceImpl 
		implements DeliveryOrderINService {

	public void saveDoInWithContainer(DeliveryOrderIN deliveryOrderIN){
		
		Date trxDate = new Date();
		
		Set<FreightContainer> containerList = deliveryOrderIN.getContainers();
		
		deliveryOrderIN.setCreatedDate(trxDate);
		
		super.save(deliveryOrderIN);
		
		for (FreightContainer freightContainer : containerList) {
			freightContainer.setOrderStatus(OrderStatus.DO_IN);
			freightContainer.setCreatedDate(trxDate);
			freightContainer.setDoIN(deliveryOrderIN);
			
			super.save(freightContainer);
		}
		
	}
	
}
