package com.depobrp.web.zk.vm.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Filedownload;

import com.depobrp.model.master.MLO;
import com.depobrp.model.report.MLODailyReport;
import com.depobrp.report.exporter.DailyStorageReportExporter;
import com.depobrp.service.order.FreightContainerService;
import com.depobrp.web.zk.common.BaseController;

@Component("MloDailyReportVM")
@Scope("prototype")
public class MloDailyReportVM extends BaseController{
	
	@Autowired
	@Qualifier("dailyStorageReportExporter")
	private DailyStorageReportExporter exporter;
	
	@Autowired
	@Qualifier("freightContainerService")
	private FreightContainerService service;
	
	private List<MLO> mloList = new ArrayList<MLO>();
	
	private MLO selectedMlo = null;
	
	private Date reportDate = null;
	
	@Init
	public void init(
			@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view){
		
		Selectors.wireComponents(view, this, false);
		this.mloList = service.getAll(MLO.class);
	}

	@Command
	public void showReport(){
		
		ByteArrayOutputStream bos = null;
		
		if(selectedMlo == null || reportDate == null){
			alert("Please select MLO and report date.");
			return;
		}
		
		try {
			
			MLODailyReport report = service.getDailyReportData(selectedMlo.getId(), reportDate);
			
			Workbook workbook = exporter.export(
					report.getMoveInList(), 
					report.getMoveOutList(), 
					report.getStorageList(), 
					selectedMlo, 
					reportDate, 
					reportDate);
			
			bos = new ByteArrayOutputStream();
			workbook.write(bos);

			byte[] reportInBytes = bos.toByteArray();
			
			Filedownload.save(reportInBytes, "application/vnd.ms-excel", "RPT-101-DAILY-REPORT-MLO.xlsx");
			
			super.info("Finish generating report");
			
		} catch (Exception e) {
			e.printStackTrace();
			super.alert("Failed generating report. Please contact system administrator");
		} 
		finally {
			try{
				bos.close();
			}catch(Exception e){
				e.printStackTrace();
				super.alert("Failed generating report. Please contact system administrator");
			}
		}			
	}

	public List<MLO> getMloList() {
		return mloList;
	}

	public void setMloList(List<MLO> mloList) {
		this.mloList = mloList;
	}

	public MLO getSelectedMlo() {
		return selectedMlo;
	}

	public void setSelectedMlo(MLO selectedMlo) {
		this.selectedMlo = selectedMlo;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
}
