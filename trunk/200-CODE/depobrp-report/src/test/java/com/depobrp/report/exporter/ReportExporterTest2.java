package com.depobrp.report.exporter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportExporterTest2 {

	public static void main(String[] args) {

		try {
			
			System.out.println("startxxx");
			
			
			ReportExporterTest2 report = new ReportExporterTest2();
			
			String template = "brp-template-report.jasper";
			String pdfOutputname = "/Users/visitek/Desktop/TEMP-erfin-report-1.pdf";
			
			report.exportReport(getDummyData(), getParamMap(), template, pdfOutputname);

			System.out.println("finish zzz");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		} finally {

		}
	}
	
	private void exportReport(List<?> data, 
			Map<String, Object> param, 
			String classpathReportTemplateLocation, 
			String outputReportLocation) throws Exception{
		
		InputStream in = ReportExporterTest2.class.getClassLoader().getResourceAsStream(classpathReportTemplateLocation);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				in,
                param, 
                new JRBeanCollectionDataSource(data));
		
		

		JasperExportManager.exportReportToPdfFile(jasperPrint, outputReportLocation);
		
	}
	
	
	
	private static List<SampleDTO> getDummyData(){
		List<SampleDTO> list = new ArrayList<SampleDTO>();
		
		list.add(new SampleDTO("erfin", "anggia"));
		list.add(new SampleDTO("elin", "sumarmo"));
		list.add(new SampleDTO("rengga", "wicitra"));
		list.add(new SampleDTO("gavin", "arrazy"));
		
		return list;
	}

	private static Map<String, Object> getParamMap(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shiper", "Shiper Name");
		
		return map;
	}
}
