package com.depobrp.web.mvc.report;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.depobrp.report.exporter.ReportExporter;

@Controller
public class UserReportMVC {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ReportExporter reportExporter;
	
	private String DOWNLOAD_FILE_NAME = "REPORT.pdf";
	private String FILE_TYPE = "application/pdf";

	@RequestMapping(value = "/user.{reportType}")
	public void generateUserReport(
			@PathVariable String reportType, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if("pdf".equalsIgnoreCase(reportType)){
			
			String reportPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/report")  + "/belajar-report.jasper";
		
			try {
				
				byte[] byteStream = reportExporter.export(dataSource, reportPath);
				
				response.setContentType(FILE_TYPE);
				response.setContentLength(byteStream.length);
				response.setHeader("Content-Disposition", "inline; filename=" + DOWNLOAD_FILE_NAME);
				
				FileCopyUtils.copy(byteStream, response.getOutputStream());

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				
			}
		
		}else{
			
			System.out.println("report not supported");
		}
	}
}
