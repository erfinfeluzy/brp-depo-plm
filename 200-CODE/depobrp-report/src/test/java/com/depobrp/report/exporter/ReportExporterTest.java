package com.depobrp.report.exporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReportExporterTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring-ctx-test.xml");

//		DataSource ds = context.getBean("reportDataSource", BasicDataSource.class);
//		Connection dbConn = null;

		try {
			
			System.out.println("start");
//			dbConn = ds.getConnection();

//			JasperPrint jasperPrint = JasperFillManager.fillReport(
//					"/data/workspace/BRP/depobrp-report/src/main/resources/belajar-report.jasper",
//                    new HashMap<String, Object>(), 
//                    dbConn);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"/data/workspace/NEW-BRP-SVN/depobrp-report/src/main/resources/brp-template-report.jasper",
                    getParamMap(), 
                    getBeanCollectionDS());
			
			String pdfOutputname = "/Users/visitek/Desktop/TEMP-erfin-report-1.pdf";

			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputname);

			System.out.println("finishxxx");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		} finally {

		}
	}
	
	private static JRDataSource getBeanCollectionDS(){
		JRDataSource ds = new JRBeanCollectionDataSource(getDummyData());
		
		return ds;
	}
	
	private static List<SampleDTO> getDummyData(){
		List<SampleDTO> list = new ArrayList<SampleDTO>();
		
		list.add(new SampleDTO("erfin", "anggia"));
		list.add(new SampleDTO("elin", "sumarmo"));
		list.add(new SampleDTO("rengga", "wicitra"));
		
		return list;
	}
	
	private static Map<String, Object> getParamMap(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("shiper", "Shiper Name");
		
		return map;
	}

}
