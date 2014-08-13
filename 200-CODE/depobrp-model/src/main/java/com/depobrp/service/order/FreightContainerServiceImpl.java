package com.depobrp.service.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.OrderStatus;
import com.depobrp.service.common.ObjectServiceImpl;
import com.depobrp.service.common.TablePager;

@Service("freightContainerService")
@Transactional
public class FreightContainerServiceImpl 
			extends ObjectServiceImpl
			implements FreightContainerService{
	
	public TablePager<FreightContainer> getReadyToMoveINList(final FreightContainer filter, Integer activePage, Integer maxRowPerPage){
		
		return 	getReadyToMoveINList(filter, null, null, activePage, maxRowPerPage);
	}
	
	
	public TablePager<FreightContainer> getReadyToMoveINList(
			final FreightContainer filter, 
			Date from, 
			Date to, 
			Integer activePage, 
			Integer maxRowPerPage){
		
		if(activePage == null) activePage = 1;
		if(maxRowPerPage == null) maxRowPerPage = 10;
		
		String hql = "from FreightContainer fc " +
				" left join fetch fc.doIN " +
				" left join fetch fc.doIN.owner " +
				" left join fetch fc.doIN.exVessel " +
				" where 1=1 " +
				" and fc.orderStatus = :orderStatus ";
		
		String hqlCount = "select count(fc.id) " +
				" from FreightContainer fc " +
				" where 1=1 " +
				" and fc.orderStatus = :orderStatus ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderStatus", OrderStatus.DO_IN);
		
		if(filter!=null && filter.getContainerNum() != null){
			hql = hql + " and fc.containerNum like :containerNum ";
			hqlCount = hqlCount + " and fc.containerNum like :containerNum ";
			paramMap.put("containerNum", filter.getContainerNum() + "%");
		}
		
		if(filter.getDoIN() != null 
				&& filter.getDoIN().getOwner() != null
				&& filter.getDoIN().getOwner().getId() != null){
			
			hql = hql + " and fc.doIN.owner.id = :ownerId";
			hqlCount = hqlCount + " and fc.doIN.owner.id = :ownerId";
			paramMap.put("ownerId", filter.getDoIN().getOwner().getId());
		}
		
		if(filter.getDoIN() != null 
				&& filter.getDoIN().getExVessel() != null
				&& filter.getDoIN().getExVessel().getId() != null){
			
			hql = hql + " and fc.doIN.exVessel.id = :exVesselId";
			hqlCount = hqlCount + " and fc.doIN.exVessel.id = :exVesselId";
			paramMap.put("exVesselId", filter.getDoIN().getExVessel().getId());
		}
		
		if(from != null && to != null){
			hql = hql + " and (fc.createdDate between :dateFrom and :dateTo) ";
			hqlCount = hqlCount + " and (fc.createdDate between :dateFrom and :dateTo) ";
			paramMap.put("dateFrom", from);
			paramMap.put("dateTo", to);
		}
		
		return super.getPageTable(hql, hqlCount, paramMap, maxRowPerPage, activePage, FreightContainer.class);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<FreightContainer> getFreightContainerByName(String filter){
		
		if(StringUtils.isEmpty(filter) || filter.length() < 3)
			return null;
		
		String hql = "from FreightContainer fc where fc.containerNum like ?";
		
		String likeFilter = filter + "%";
		
		return super.getFromHql(hql, new Object[]{(likeFilter)}, 10);
		
	}
	
}

