package com.depobrp.commons.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CurrencyUtils {
	
	public static final Currency DEFAULT_CURRENCY = Currency.IDR;
	public static final Language DEFAULT_LANGUAGE = Language.id_ID;
	public static final String DEFAULT_FORMAT = "#,###.00";

	public static String speech(Language language, Currency currency, Long number){
		
		if(Language.id_ID.equals(language)){
			return NumberToTextIDUtils.speech(number) + " " + currency.getSpeech();
		}
		
		return NumberToTextENUtils.speech(number) + " " + currency.getSpeech();
		
	}
	
	public static String speech(Currency currency, Long number){
		return speech(currency.getLanguage(), currency, number);
	}
	
	public static String speech(Long number){
		return speech(DEFAULT_LANGUAGE, DEFAULT_CURRENCY, number);
	}
	
	public static String toString(Currency currency, Long number){
		NumberFormat fmt = new DecimalFormat("#,###.00");
		return currency.getCode() + " " + fmt.format(number);
	}
	
	public static void main(String[] args) {
		Long number = 99999999L;
		NumberFormat fmt = new DecimalFormat("#,###.00");
		
		System.out.println(fmt.format(number));
		System.out.println(CurrencyUtils.speech(number));
		System.out.println(CurrencyUtils.speech(Language.en_US, Currency.IDR, number));
		System.out.println(CurrencyUtils.speech(Currency.IDR, number));
		System.out.println(CurrencyUtils.speech(Currency.USD, number));
		System.out.println(CurrencyUtils.speech(Currency.GBP, number));
		
		System.out.println(CurrencyUtils.toString(Currency.USD, number));
	}
}
