package com.depobrp.report.exporter;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.record.cf.BorderFormatting;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.depobrp.commons.util.FormatUtils;
import com.depobrp.model.master.MLO;
import com.depobrp.model.order.FreightContainer;

@Component("dailyStorageReportExporter")
public class DailyStorageReportExporterImpl implements DailyStorageReportExporter {

	public Workbook export(
			List<FreightContainer> moveInList, 
			List<FreightContainer> moveOutList, 
			List<FreightContainer> onStorageList,
			MLO mlo,
			Date fromPeriod, Date toPeriod){
		
		try {
			
			InputStream input = DailyStorageReportExporterImpl.class
					.getClassLoader().getResourceAsStream("daily-storage-report-template.xlsx");
			
			Workbook workbook = new XSSFWorkbook(input);
			
			setMoveInSheet(workbook, moveInList, mlo, fromPeriod, toPeriod);
			setMoveOutSheet(workbook, moveOutList, mlo, fromPeriod, toPeriod);
			setOnStorageSheet(workbook, onStorageList, mlo, fromPeriod, toPeriod);
			
			
			return workbook;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void setMoveInSheet(Workbook workbook, 
					List<FreightContainer> moveInList, 
					MLO mlo, Date from, Date to){
		
		Sheet sheet = workbook.getSheet("MV-IN");

		Cell cell = null;
		cell = sheet.getRow(2).getCell(2);
		//set MLO NAME
		cell.setCellValue((String) mlo.getName());
		
		cell = sheet.getRow(3).getCell(2);
		cell.setCellValue((String) from.toString() + " to " + to.toString());
		
		int total40rowIndex = sheet.getLastRowNum() ;
		int total20rowIndex = sheet.getLastRowNum() - 1;
		
		cell = sheet.getRow(total40rowIndex).getCell(2);
		cell.setCellValue(moveInList.size());
		
		cell = sheet.getRow(total20rowIndex).getCell(2);
		cell.setCellValue(moveInList.size());

		int startDataRowIndex = 7;
		int i = 0;
		for (FreightContainer container : moveInList) {
			
			Row row = sheet.createRow(startDataRowIndex + i);
			row.createCell(0).setCellValue((i+1));
			row.createCell(1).setCellValue(container.getContainerNum());
			row.createCell(2).setCellValue(container.getCheckDigit());
			row.createCell(3).setCellValue(container.getSize().getDescription());
			row.createCell(4).setCellValue(container.getType().toString());
			row.createCell(5).setCellValue(mlo.getName());
			row.createCell(6).setCellValue(container.getConditionIN().getDescription());
			row.createCell(7).setCellValue("WW");
			row.createCell(7).setCellValue(container.getEmptyFullIN().getDescription());
			row.createCell(8).setCellValue(container.getPayloadKGIN());
			row.createCell(9).setCellValue(FormatUtils.format(container.getMoveINDate(), "dd-MMM-yyyy") );
			row.createCell(10).setCellValue(FormatUtils.format(container.getMoveINDate(), "HH:mm a"));
			row.createCell(11).setCellValue("truck no in");
			row.createCell(12).setCellValue(container.getDoIN().getConsignee().getName());
			row.createCell(13).setCellValue(container.getDoIN().getNoteIn());
			
			setCellStyle(row, workbook);		
			
			sheet.shiftRows((startDataRowIndex + i + 3), (total40rowIndex ++), 1);
			sheet.shiftRows((startDataRowIndex + i + 2), (total20rowIndex ++), 1);
			i++;
		}

	}
	
	private void setCellStyle(Row row, Workbook workbook){
		
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderFormatting.BORDER_THIN);
		style.setBorderTop(BorderFormatting.BORDER_THIN);
		style.setBorderLeft(BorderFormatting.BORDER_THIN);
		style.setBorderRight(BorderFormatting.BORDER_THIN);
		
		row.getCell(0).setCellStyle(style);
		row.getCell(1).setCellStyle(style);
		row.getCell(2).setCellStyle(style);
		row.getCell(3).setCellStyle(style);
		row.getCell(4).setCellStyle(style);
		row.getCell(5).setCellStyle(style);
		row.getCell(6).setCellStyle(style);
		row.getCell(7).setCellStyle(style);
		row.getCell(8).setCellStyle(style);
		row.getCell(9).setCellStyle(style);
		row.getCell(10).setCellStyle(style);
		row.getCell(11).setCellStyle(style);
		row.getCell(12).setCellStyle(style);
		row.getCell(13).setCellStyle(style);
	}
	
	
	private void setMoveOutSheet(Workbook workbook, 
					List<FreightContainer> moveOutList, 
					MLO mlo, Date from, Date to){
		
		Sheet sheet = workbook.getSheet("MV-OT");

		Cell cell = null;
		cell = sheet.getRow(2).getCell(2);
		//set MLO NAME
		cell.setCellValue((String) mlo.getName());
		
		cell = sheet.getRow(3).getCell(2);
		cell.setCellValue((String) from.toString() + " to " + to.toString());
		
		int total40rowIndex = sheet.getLastRowNum()  ;
		int total20rowIndex = sheet.getLastRowNum() - 1;
		
		cell = sheet.getRow(total40rowIndex).getCell(2);
		cell.setCellValue(moveOutList.size());
		
		cell = sheet.getRow(total20rowIndex).getCell(2);
		cell.setCellValue(moveOutList.size());

		int startDataRowIndex = 7;
		int i = 0;
		for (FreightContainer container : moveOutList) {
			
			Row row = sheet.createRow(startDataRowIndex + i);
			row.createCell(0).setCellValue((i+1));
			row.createCell(1).setCellValue(container.getContainerNum());
			row.createCell(2).setCellValue(container.getCheckDigit());
			row.createCell(3).setCellValue(container.getSize().getDescription());
			row.createCell(4).setCellValue(container.getType().toString());
			row.createCell(5).setCellValue(mlo.getName());
			row.createCell(6).setCellValue(container.getConditionIN().getDescription());
			row.createCell(7).setCellValue("WW");
			row.createCell(7).setCellValue(container.getEmptyFullIN().getDescription());
			row.createCell(8).setCellValue(container.getPayloadKGIN());
			row.createCell(9).setCellValue(FormatUtils.format(container.getMoveINDate(), "dd-MMM-yyyy") );
			row.createCell(10).setCellValue(FormatUtils.format(container.getMoveINDate(), "HH:mm a"));
			row.createCell(11).setCellValue("truck no in");
			row.createCell(12).setCellValue(container.getDoIN().getConsignee().getName());
			row.createCell(13).setCellValue(container.getDoIN().getNoteIn());
			
			setCellStyle(row, workbook);		
			
			sheet.shiftRows((startDataRowIndex + i + 3), (total40rowIndex ++), 1);
			sheet.shiftRows((startDataRowIndex + i + 2), (total20rowIndex ++), 1);
			i++;
		}
	}
	
	private void setOnStorageSheet(Workbook workbook, 
					List<FreightContainer> onStorageList,
					MLO mlo, Date from, Date to){
		
		Sheet sheet = workbook.getSheet("S-LIST");

		Cell cell = null;
		cell = sheet.getRow(2).getCell(2);
		
		//set MLO NAME
		cell.setCellValue((String) mlo.getName());
		
		cell = sheet.getRow(3).getCell(2);
		cell.setCellValue((String) from.toString() + " to " + to.toString());
		
		int total40rowIndex = sheet.getLastRowNum();
		int total20rowIndex = sheet.getLastRowNum() - 1;
		
		cell = sheet.getRow(total40rowIndex).getCell(2);
		cell.setCellValue(onStorageList.size());
		
		cell = sheet.getRow(total20rowIndex).getCell(2);
		
		cell.setCellValue(onStorageList.size());

		int startDataRowIndex = 7;
		int i = 0;
		for (FreightContainer container : onStorageList) {
			
			Row row = sheet.createRow(startDataRowIndex + i);
			row.createCell(0).setCellValue((i+1));
			row.createCell(1).setCellValue(container.getContainerNum());
			row.createCell(2).setCellValue(container.getCheckDigit());
			row.createCell(3).setCellValue(container.getSize().getDescription());
			row.createCell(4).setCellValue(container.getType().toString());
			row.createCell(5).setCellValue(mlo.getName());
			row.createCell(6).setCellValue(container.getConditionIN().getDescription());
			row.createCell(7).setCellValue("WW");
			row.createCell(7).setCellValue(container.getEmptyFullIN().getDescription());
			row.createCell(8).setCellValue(container.getPayloadKGIN());
			row.createCell(9).setCellValue(FormatUtils.format(container.getMoveINDate(), "dd-MMM-yyyy") );
			row.createCell(10).setCellValue(FormatUtils.format(container.getMoveINDate(), "HH:mm a"));
			row.createCell(11).setCellValue("truck no in");
			row.createCell(12).setCellValue(container.getDoIN().getConsignee().getName());
			row.createCell(13).setCellValue(container.getDoIN().getNoteIn());
			
			setCellStyle(row, workbook);		
			
			sheet.shiftRows((startDataRowIndex + i + 3), (total40rowIndex ++), 1);
			sheet.shiftRows((startDataRowIndex + i + 2), (total20rowIndex ++), 1);
			i++;
		}
	}
	
}
