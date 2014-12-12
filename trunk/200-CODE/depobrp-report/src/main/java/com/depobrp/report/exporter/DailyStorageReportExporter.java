package com.depobrp.report.exporter;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.depobrp.model.master.MLO;
import com.depobrp.model.order.FreightContainer;

public interface DailyStorageReportExporter {

	Workbook export(List<FreightContainer> moveIn, 
			List<FreightContainer> moveOut, 
			List<FreightContainer> onStorage,
			MLO mlo,
			Date fromPeriod, Date toPeriod);
	
}
