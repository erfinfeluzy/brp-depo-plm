package com.depobrp.commons.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class FormatUtils{

	public static String format(Number value, String pattern){
		
		NumberFormat nf = new DecimalFormat(pattern);
		
		return nf.format(value);
	}
	
	public static Double round(Double arg, String pattern){
		
		DecimalFormat df = new DecimalFormat(pattern);
		
		return Double.valueOf(df.format(arg));
	}
	
	public static String format(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
}
