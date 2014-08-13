package com.depobrp.service.order;

import com.depobrp.model.order.DeliveryOrderIN;
import com.depobrp.service.common.ObjectService;

public interface DeliveryOrderINService extends ObjectService {

	public void saveDoInWithContainer(DeliveryOrderIN deliveryOrderIN);
	
}
