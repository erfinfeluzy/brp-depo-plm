package com.depobrp.report.exporter;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

@Component("reportExporter")
public class ReportExporterImpl implements ReportExporter {

	public byte[] export(DataSource dataSource, 
			String jasperTemplateLocation,
			Map<String, Object> param) {
		
		return export(dataSource, ReportType.PDF, jasperTemplateLocation, param);
	}
	
	public byte[] export(
			DataSource dataSource, 
			String jasperTemplateLocation) throws RuntimeException{
		
		return export(dataSource, ReportType.PDF, jasperTemplateLocation, new HashMap<String, Object>());
	}
	
	public byte[] export(
			DataSource dataSource, 
			ReportType reportType,
			String jasperTemplateLocation) throws RuntimeException{
		
		return export(dataSource, reportType, jasperTemplateLocation, new HashMap<String, Object>());
	}
	
	public byte[] export(
			DataSource dataSource, 
			ReportType reportType,
			String jasperTemplateLocation,
			Map<String, Object> param) throws RuntimeException{

		Connection dbConn = null;
		
		try {
			
			if(dataSource == null)
				throw new RuntimeException("No Datasource supplied");
			
			dbConn = dataSource.getConnection();

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperTemplateLocation,
					param, 
					dbConn);
			
			byte[] output = null;
			
			if(reportType == ReportType.PDF){
				output = generatePDF(jasperPrint);
			}else{
				output = generateExcel(jasperPrint); 
			}
			
			return output;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed Generating Report");

		} finally {
			closeDatabaseConnection(dbConn);
		}
	}
	
	private byte[] generatePDF(JasperPrint jasperPrint) throws JRException{
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	
	}
	
	private byte[] generateExcel(JasperPrint jasperPrint) throws JRException{
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
		JRXlsExporter exporterXLS = new JRXlsExporter();
        
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.TRUE);
        
        exporterXLS.exportReport();
        
        return byteArrayOutputStream.toByteArray();
	}
	
	private void closeDatabaseConnection(Connection conn){
		
		if (conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
