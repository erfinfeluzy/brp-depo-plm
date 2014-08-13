package com.depobrp.commons.util;

public class ISO6346Utils {

	private static int[] map = { 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
			23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 34, 35, 36, 37, 38 };
	
	private static int[] weights = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512 };


	public static void main(String[] args) {
		
		String containerNumber = "csqu3054383";
		String invalidContainerNumber = "abcd3054383";
		
		System.out.println("is valid for " + containerNumber +"? " + ISO6346Utils.isValidContainerNumber(containerNumber)); 
		System.out.println("is valid for " + invalidContainerNumber +"? " + ISO6346Utils.isValidContainerNumber(invalidContainerNumber)); 
		
		System.out.println("CRC: " + ISO6346Utils.calculateCheckDigit(containerNumber));
		System.out.println("CRC: " + ISO6346Utils.calculateCheckDigit(invalidContainerNumber));
	}
	
	public static boolean isValidContainerNumber(String containerNumber){
		
		//any char on first 3 digit
		//allow only U, J, Z, R on 4th character
		//allow only numeric 0-9 on 5th - 10th or 11th character
		return containerNumber.matches("[a-zA-Z]{3}[u|j|z|r|U|J|Z|R]{1}[0-9]{6,7}");
	}

	public static int calculateCheckDigit(String containerNumber) {
		
		int[] digits = new int[10];
		try {
			
			//check validity with regex
			if(!isValidContainerNumber(containerNumber))
				throw new RuntimeException("Invalid container ISO number");
			
			//calculate first 4 character
			for (int i = 0; i < 4; i++) {
				digits[i] = getNumber(containerNumber.charAt(i));
			}

			//calculate numeric character 5-10
			for (int i = 4; i < 10; i++) {
				digits[i] = Integer.parseInt(containerNumber.substring(i, i + 1));
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Invalid container ISO number");
		}

		//calculate each digit * weight
		for (int i = 0; i < 10; i++) {
			digits[i] = digits[i] * weights[i];
		}

		//sum total
		int total = 0;
		for (int i = 0; i < 10; i++) {
			total = total + digits[i];
		}

		//return total modulo 11
		return total % 11;
	}

	private static int getNumber(char c) {
		
		return map[Character.getNumericValue(c) - 10]; // 10 is the offset from
														// the returning value
														// of A to 0
	}

}