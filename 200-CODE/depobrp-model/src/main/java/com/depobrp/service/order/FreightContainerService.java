package com.depobrp.service.order;

import java.util.Date;
import java.util.List;

import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.OrderStatus;
import com.depobrp.model.report.MLODailyReport;
import com.depobrp.service.common.ObjectService;
import com.depobrp.service.common.TablePager;

public interface FreightContainerService extends ObjectService {

	TablePager<FreightContainer> getReadyToMoveINList(FreightContainer filter, Integer activePage, Integer maxRowPerPage);

	TablePager<FreightContainer> getReadyToMoveINList(FreightContainer filter, Date from, Date to, Integer activePage, Integer maxRowPerPage);
	
	List<FreightContainer> getFreightContainerByName(String filter);

	TablePager<FreightContainer> getOnStorageList(FreightContainer filter,
			Date fromDate, Date toDate, int activePage, int maxRowPerPage);
	
	List<FreightContainer> getFreightContainerByNameStatus(String filter, OrderStatus status);
	
	TablePager<FreightContainer> getFreightContainerList(
			final FreightContainer filter, 
			Date from, 
			Date to, 
			Integer activePage, 
			Integer maxRowPerPage, 
			OrderStatus orderStatus);
	
	MLODailyReport getDailyReportData(Long mloId, Date reportDate);
}

