package com.depobrp.commons.util;


public class NumberToTextENUtils {

	private static final String[] strtens = { "ten", "twenty", "thirty", "fourty", "fifty",
			"sixty", "seventy", "eighty", "ninety", "hundred" };
	
	private static final String [] zeroToTwenty =
		{"", "one", "two", "three", "four", "five", "six",
			"seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen",
			"fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
			"nineteen",};

	public static String speech(Long number){

		String result = "";
	
		if(number == 0){
			return "zero";
		}
		else if (number<20){
			Integer longToInt = new Integer(number.toString());
			result = "" + zeroToTwenty[longToInt.intValue()];
		}
		else if(number<100){
			Integer longToInt = new Integer(number.intValue());
			result = strtens[(longToInt/10)-1] + " " + speech(number%10);
		}
		else if(number<1000){
			result = speech(number/100) + " hundred " + speech(number%100);
		}
		else if(number<1000000){
			result = speech(number/1000) + " thousand " + speech(number%1000);
		}
		else if(number<1000000000){
			result = speech(number/1000000) + " million " + speech(number%1000000);
		}
		else if(number<1000000000000L){
			result = speech(number/1000000000) + " billion " + speech(number%1000000000);
		}
		else{
			throw new RuntimeException("Unsupported value.");
		}
		
		return result.trim();
	}
	
	public static void main(String[] args) {
		
		long [] test = {10, //ten
						11, //eleven
						12, //twelve
						20, //twenty
						99, //1 hundred minus 1
						100, //1 hundred
						200, //2 hundred
						999, //1 thousand minus 1
						1000, //1 thousand
						2000, //2 thousand
						9999, //10 thousand minus 1
						10000, //10 thousand
						20100, //20 thousand
						99999, //hundred thousand minus 1
						100000, //hundred thousand
						200000, //2 hundred thousand
						999999, //million minus 1
						1000000, //1 million
						2000000, //2 million
						9999999, //ten million minus 1
						10000000, //ten million
						20000000, //twenty million
						99999999, //1 hundred mio minus 1
						100000000, //1 hundred mio 
						200000000, //2 hundred mio
						999999999, //1 bio minus 1
						1000000000, //1 bio 
						9999999999L, //10 bio minus 1 
						10000000000L, //10 bio  
						20000000000L, //20 bio  
						99999999999L, //1 zio minus 1
						999999999999L, //1 zio minus 1
						
		} ; 
	
		for (long i : test) {
			System.out.println(i + "=" + NumberToTextENUtils.speech(i));
		}
		
		System.out.println("finish");
	}

}
