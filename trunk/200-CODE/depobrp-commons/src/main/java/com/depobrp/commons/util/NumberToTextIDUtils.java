package com.depobrp.commons.util;


public class NumberToTextIDUtils {

	public static String speech(Long number){
	
		String [] zeroToEleven =
			{"","satu","dua","tiga","empat","lima","enam","tujuh","delapan","sembilan","sepuluh","sebelas"};
		
		String result = "";
	
		if(number == 0){
			return "nol";
		}
		
		else if (number<12){
			Integer longToInt = new Integer(number.toString());
			result = "" + zeroToEleven[longToInt.intValue()];
		}
		else if(number<20){
			result = speech(number-10) + " belas";
		}
		else if(number<100){
			result = speech(number/10) + " puluh " + speech(number%10);
		}
		else if(number<200){
			result = " seratus" + speech(number-100);
		}
		else if(number<1000){
			result = speech(number/100) + " ratus " + speech(number%100);
		}
		else if(number<2000){
			result = " seribu "+ speech(number-1000);
		}
		else if(number<1000000){
			result = speech(number/1000) + " ribu " + speech(number%1000);
		}
		else if(number<1000000000){
			result = speech(number/1000000) + " juta " + speech(number%1000000);
		}
		else if(number<1000000000000L){
			result = speech(number/1000000000) + " miliar " + speech(number%1000000000);
		}else{
			throw new RuntimeException("Unsupported value");
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
			System.out.println(NumberToTextIDUtils.speech(i));
		}
		
		System.out.println("finish");
	}
}
