package com.depobrp.report.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.depobrp.model.master.Consignee;
import com.depobrp.model.master.MLO;
import com.depobrp.model.order.DeliveryOrderIN;
import com.depobrp.model.order.FreightContainer;
import com.depobrp.model.order.FreightContainer.Condition;
import com.depobrp.model.order.FreightContainer.EmptyFull;
import com.depobrp.model.order.FreightContainer.Size;
import com.depobrp.model.order.FreightContainer.Type;

public class DailyStorageReportExporterTest2 {

	
	public static void main(String[] args) {
		
		try {
			
			DailyStorageReportExporter exporter = new DailyStorageReportExporterImpl();
			
			MLO mlo = new MLO();
			mlo.setName("SITC - es i te ce");
			
			Workbook workbook = exporter.export(
					getDummyMoveIn(), 
					getDummyMoveIn(), 
					getDummyMoveIn(), 
					mlo, 
					new Date(), 
					new Date());
			
			FileOutputStream out = new FileOutputStream(new File(
					"/Users/visitek/Desktop/new.xlsx"));
			
			workbook.write(out);
			
			out.close();
			
			System.out.println("finishy");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static List<FreightContainer> getDummyMoveIn(){
		
		List<FreightContainer> result = new ArrayList<FreightContainer>();
		
		for (int i = 0; i < 10; i++) {
			
			DeliveryOrderIN doIN = new DeliveryOrderIN();
			doIN.setNoteIn("NOTE-" + i);
			
			Consignee c = new Consignee();
			c.setName("ADOVELIN");
			doIN.setConsignee(c);
			
			FreightContainer e = new FreightContainer(
					"NO-123", (i+5), Size.F_20, Type.GP, Condition.AV, 1200, EmptyFull.MTY);
			
			e.setMoveINDate(new Date());
			
			e.setDoIN(doIN);
			
			result.add(e);
			
		}
		
		return result;
	}
}
