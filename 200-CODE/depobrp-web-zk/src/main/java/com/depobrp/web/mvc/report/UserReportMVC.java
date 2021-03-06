package com.depobrp.web.mvc.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.depobrp.model.user.User;
import com.depobrp.report.exporter.ReportExporter;
import com.depobrp.report.exporter.ReportType;
import com.depobrp.service.user.UserService;

@Controller
public class UserReportMVC {
	
	@Autowired
	private UserService userService;
	
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
			
			String reportPath = "brp-template-report.jasper";

		
			try {
				
				List<User> users = userService.getAll(User.class);
				
				byte[] byteStream = reportExporter.export(
						ReportType.PDF, 
						users, 
						new HashMap<String, Object>(), 
						reportPath);
				
				response.setContentType(FILE_TYPE);
				response.setContentLength(byteStream.length);
				response.setHeader("Content-Disposition", "inline; filename=" + DOWNLOAD_FILE_NAME);
				
				FileCopyUtils.copy(byteStream, response.getOutputStream());

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		
		}else{
			
			System.out.println("report not supported");
		}
	}
}
