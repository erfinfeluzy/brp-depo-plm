package com.depobrp.service.order;

import com.depobrp.model.order.DeliveryOrderOUT;
import com.depobrp.service.common.ObjectService;

public interface DeliveryOrderOUTService extends ObjectService {

	public void saveDoOutWithContainer(DeliveryOrderOUT deliveryOrderOUT);
	
}
