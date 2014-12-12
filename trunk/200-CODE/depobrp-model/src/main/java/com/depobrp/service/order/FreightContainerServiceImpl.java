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
import com.depobrp.model.report.MLODailyReport;
import com.depobrp.service.common.ObjectServiceImpl;
import com.depobrp.service.common.TablePager;

@Service("freightContainerService")
@Transactional
public class FreightContainerServiceImpl 
			extends ObjectServiceImpl
			implements FreightContainerService{
	
	public TablePager<FreightContainer> getReadyToMoveINList(
			final FreightContainer filter, 
			Integer activePage, 
			Integer maxRowPerPage){
		
		return 	getReadyToMoveINList(filter, null, null, activePage, maxRowPerPage);
	}
	
	@Override
	public TablePager<FreightContainer> getOnStorageList(FreightContainer filter,
			Date from, Date to, int activePage, int maxRowPerPage) {
		
		return getFreightContainerList(filter, from, to, activePage, maxRowPerPage, OrderStatus.ON_STORAGE);
	}
	
	public TablePager<FreightContainer> getReadyToMoveINList(
			final FreightContainer filter, 
			Date from, 
			Date to, 
			Integer activePage, 
			Integer maxRowPerPage){
		
		return getFreightContainerList(filter, from, to, activePage, maxRowPerPage, OrderStatus.DO_IN);
	}
	
	
	public TablePager<FreightContainer> getFreightContainerList(
			final FreightContainer filter, 
			Date from, 
			Date to, 
			Integer activePage, 
			Integer maxRowPerPage, 
			OrderStatus orderStatus){
		
		if(activePage == null) activePage = 1;
		if(maxRowPerPage == null) maxRowPerPage = 10;
		
		String hql = "from FreightContainer fc " +
				" left join fetch fc.doIN " +
				" left join fetch fc.doIN.owner " +
				" left join fetch fc.doIN.exVessel " +
				" left join fetch fc.doOUT " +
				" left join fetch fc.doOUT.owner " +
				" left join fetch fc.doOUT.nextVessel " +
				" where 1=1 ";
		
		String hqlCount = "select count(fc.id) " +
				" from FreightContainer fc " +
				" where 1=1 ";
				

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(orderStatus != null){
			hql = hql + " and fc.orderStatus = :orderStatus ";
			hqlCount = hqlCount + " and fc.orderStatus = :orderStatus ";
			paramMap.put("orderStatus", orderStatus);
		}
		
		
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
	
	@SuppressWarnings("unchecked")
	public List<FreightContainer> getFreightContainerByNameStatus(String filter, OrderStatus status){
		
		if(StringUtils.isEmpty(filter) || filter.length() < 3)
			return null;
		
		String hql = "from FreightContainer fc where fc.containerNum like ? and orderStatus = ?";
		
		String likeFilter = filter + "%";
		
		return super.getFromHql(hql, new Object[]{likeFilter, status}, 10);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public MLODailyReport getDailyReportData(Long mloId, Date reportDate) {

		String hql = " from FreightContainer fc " +
				" left join fetch fc.doIN din " +
				" left join fetch din.owner iow " +
				" left join fetch din.exVessel ev " +
				" left join fetch din.consignee con " +
				" left join fetch fc.doOUT out " +
				" left join fetch out.owner oow " +
				" left join fetch out.nextVessel nv " +
				" where 1=1 " +
				" and iow.id= ? ";
		
		String hqlMoveIn = hql + 
				" and fc.orderStatus= ? " +
				" and year(fc.moveINDate) = year(?) "+
				" and month(fc.moveINDate) = month(?) "+
				" and day(fc.moveINDate) = day(?) " ;
		List<FreightContainer> moveInList = super.getFromHql(hqlMoveIn, new Object[]{mloId, OrderStatus.ON_STORAGE, reportDate, reportDate, reportDate});
		System.out.println("movein list size: " + moveInList.size());
		
		String hqlMoveOut = hql + 
				" and fc.orderStatus= ? " +
				" and year(fc.moveOUTDate) = year(?) "+
				" and month(fc.moveOUTDate) = month(?) "+
				" and day(fc.moveOUTDate) = day(?) " ;;
		List<FreightContainer> moveOutList = super.getFromHql(hqlMoveOut, new Object[]{mloId, OrderStatus.MOVE_OUT, reportDate, reportDate, reportDate});
		System.out.println("move out size: " + moveOutList.size());
		
		String hqlOnStorage = hql + " and fc.orderStatus=? ";
		List<FreightContainer> onStorageList = super.getFromHql(hqlOnStorage, new Object[]{mloId, OrderStatus.ON_STORAGE});
		System.out.println("on storage size: " + onStorageList.size());
		
		MLODailyReport report = new MLODailyReport(moveInList, moveOutList, onStorageList);
		
		return report;
	}
	
	public static void main(String[] args) {
		String hql = " from FreightContainer fc " +
				" left join fetch fc.doIN din " +
				" left join fetch din.owner iow " +
				" left join fetch din.exVessel ev " +
				" left join fetch fc.doOUT out " +
				" left join fetch out.owner oow " +
				" left join fetch out.nextVessel nv " +
				" where 1=1 " +
				" and iow.id= ? ";
		
		System.out.println(hql);
	}

}

