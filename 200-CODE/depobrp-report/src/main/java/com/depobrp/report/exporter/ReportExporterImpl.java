package com.depobrp.report.exporter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.springframework.stereotype.Component;

@Component("reportExporter")
public class ReportExporterImpl implements ReportExporter {
	
	@Override
	public byte[] export(
			ReportType reportType, 
			List<?> data,
			Map<String, Object> param, 
			String classpathReportTemplateLocation) 
					throws Exception {
		
		InputStream in = ReportExporterImpl.class.getClassLoader().getResourceAsStream(classpathReportTemplateLocation);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				in,
                param, 
                new JRBeanCollectionDataSource(data));

		byte[] output = null;
		
		if(reportType == ReportType.PDF){
			output = generatePDF(jasperPrint);
		}else{
			output = generateExcel(jasperPrint); 
		}
		
		return output;
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

}
