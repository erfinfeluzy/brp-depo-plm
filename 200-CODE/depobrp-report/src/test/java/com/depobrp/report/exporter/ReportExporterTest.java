package com.depobrp.report.exporter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReportExporterTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring-ctx-test.xml");

//		DataSource ds = context.getBean("reportDataSource", BasicDataSource.class);
//		Connection dbConn = null;

		try {
			
//			dbConn = ds.getConnection();

//			JasperPrint jasperPrint = JasperFillManager.fillReport(
//					"/data/workspace/BRP/depobrp-report/src/main/resources/belajar-report.jasper",
//                    new HashMap<String, Object>(), 
//                    dbConn);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"/data/workspace/BRP/depobrp-report/src/main/resources/belajar-report.jasper",
                    new HashMap<String, Object>(), 
                    getBeanCollectionDS());
			
			String pdfOutputname = "/Users/visitek/Desktop/belajar-report-java-3.pdf";

			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputname);

			System.out.println("finish");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		} finally {
//			if (dbConn != null)
//				try {
//					dbConn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
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
		
		return list;
	}

}
