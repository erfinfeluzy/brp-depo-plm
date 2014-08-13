package com.depobrp.report.exporter;

import java.util.Map;

import javax.sql.DataSource;

public interface ReportExporter {
	
	byte[] export(DataSource dataSource, 
			String jasperTemplateLocation);
	
	byte[] export(DataSource dataSource, 
			String jasperTemplateLocation,
			Map<String, Object> param);
	
	byte[] export(DataSource dataSource, 
			ReportType reportType, 
			String jasperTemplateLocation);

	byte[] export(DataSource dataSource, 
			ReportType reportType, 
			String jasperTemplateLocation, 
			Map<String, Object> param);
	
}
