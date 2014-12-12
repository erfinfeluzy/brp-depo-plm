package com.depobrp.report.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DailyStorageReportExporterTest {

	public static void main(String[] args) {

		try {

			FileInputStream templateFile = new FileInputStream(new File("/Users/visitek/Desktop/report-template.xlsx"));

			Workbook workbook = new XSSFWorkbook(templateFile);
			Sheet sheet = workbook.getSheet("MV-IN");

			Cell cell = null;
			cell = sheet.getRow(2).getCell(2);
			cell.setCellValue((String) "SITC");
			
			cell = sheet.getRow(3).getCell(2);
			cell.setCellValue((String) "Periode here");
			
			int total40rowIndex = sheet.getLastRowNum() - 1 ;
			int total20rowIndex = sheet.getLastRowNum() - 2;
			
			System.out.println("total20rowIndex : " + total20rowIndex);
			System.out.println("total40rowIndex : " + total40rowIndex);
	
			System.out.println(sheet.getRow(total20rowIndex).getCell(1).getStringCellValue());
			System.out.println(sheet.getRow(total40rowIndex).getCell(1).getStringCellValue());
//			
//			Row row = sheet.createRow(20);
//			row = sheet.createRow(21);
//			
//			sheet.shiftRows(20, total20rowIndex + 1, 1);
//			sheet.shiftRows(21, total40rowIndex + 1, 1);
			
			
			for (int i = 7;  i < 18; i++) {
				
				Row row = sheet.createRow(i);
				row.createCell(0).setCellValue("1");
				row.createCell(1).setCellValue("container num");
				row.createCell(2).setCellValue("cd");
				row.createCell(3).setCellValue("size");
				row.createCell(4).setCellValue("type");
				row.createCell(5).setCellValue("mlo Name");
				row.createCell(6).setCellValue("cond1");
				row.createCell(7).setCellValue("cond2");
				row.createCell(7).setCellValue("empty");
				row.createCell(8).setCellValue("payload kgs");
				row.createCell(9).setCellValue("date in");
				row.createCell(10).setCellValue("time in");
				row.createCell(11).setCellValue("truck no in");
				row.createCell(12).setCellValue("notify party");
				row.createCell(13).setCellValue("note in");
				
				sheet.shiftRows((i + 3), (total40rowIndex ++), 1);
				sheet.shiftRows((i + 2), (total20rowIndex ++), 1);
//				sheet.shiftRows((i + 2), (total40rowIndex ++), 1);
				
			}
			


			
			FileOutputStream out = new FileOutputStream(new File(
					"/Users/visitek/Desktop/new.xlsx"));
			workbook.write(out);
			out.close();
			
			System.out.println("Excel written successfullxxx at : ");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
}
