package com.depobrp.web.mvc.report;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.depobrp.report.exporter.DailyStorageReportExporter;
import com.depobrp.service.order.FreightContainerService;

@Controller
public class MLOReportController {
	
	@Autowired
	@Qualifier("freightContainerService")
	private FreightContainerService service;
	
	@Autowired
	@Qualifier("dailyStorageReportExporter")
	private DailyStorageReportExporter reportExporter;
	
	private static final String DOWNLOAD_FILE_NAME = "RPT-101-DAILY-REPORT-$MLO-CODE$.xlsx";
	private static final String CONTENT_TYPE = "application/vnd.ms-excel";

	@RequestMapping("")
	public void getMloDailyExcelReport(HttpServletResponse response){
		
		try {
			
			//service.
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
