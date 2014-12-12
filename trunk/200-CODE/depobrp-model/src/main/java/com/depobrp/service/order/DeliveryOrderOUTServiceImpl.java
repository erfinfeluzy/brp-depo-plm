package com.depobrp.service.order;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.model.order.DeliveryOrderOUT;
import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.OrderStatus;
import com.depobrp.service.common.ObjectServiceImpl;

@Service("deliveryOrderOUTService")
@Transactional
public class DeliveryOrderOUTServiceImpl
		extends ObjectServiceImpl 
		implements DeliveryOrderOUTService 
		{

	public void saveDoOutWithContainer(DeliveryOrderOUT deliveryOrderOUT){
		
		Date trxDate = new Date();
		
		Set<FreightContainer> containerList = deliveryOrderOUT.getContainers();
		
		deliveryOrderOUT.setCreatedDate(trxDate);
		
		super.save(deliveryOrderOUT);
		
		for (FreightContainer freightContainer : containerList) {
			freightContainer.setOrderStatus(OrderStatus.DO_OUT);
			freightContainer.setCreatedDate(trxDate);
			freightContainer.setDoOUT(deliveryOrderOUT);
			
			super.save(freightContainer);
		}
		
	}

	
}
