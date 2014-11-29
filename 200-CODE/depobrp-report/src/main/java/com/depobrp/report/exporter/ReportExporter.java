package com.depobrp.report.exporter;

import java.util.List;
import java.util.Map;

public interface ReportExporter {
	
	byte[] export(
			ReportType reportType,
			List<?> data, 
			Map<String, Object> param, 
			String classpathReportTemplateLocation) throws Exception;
	
}
