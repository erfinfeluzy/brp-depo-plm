package com.depobrp.commons.security.util;


/**
 * 
 * 
 * @author Erfin Feluzy 
 * 
 */
public class SecurityUtil {

    /**
	 * Encrypt clear text to decrypted text using default salt.
	 * @author Erfin Feluzy
	 * @param clearText
	 * @return encrypted text
	 * @throws Exception
	 */
	public static String encrypt(String clearText){
		CryptoUtil cu = new CryptoUtil();
		return cu.encrypt(clearText);
	}
	
    /**
	 * Decrypt cipher text to clear text using default salt.
	 * @author Erfin Feluzy
	 * @param encryptedText
	 * @return clear text
	 * @throws Exception
	 */
	public static String decrypt(String encryptedText){
		CryptoUtil cu = new CryptoUtil();
		return cu.decrypt(encryptedText);
	}
	
	public static void main(String[] args) {
		
		String clear = "erfin.feluzy@gmail.com";
		
		String encrypt = SecurityUtil.encrypt(clear);
		
		System.out.println("encrypt:" + encrypt);
		
		System.out.println("decrypt:" + SecurityUtil.decrypt(encrypt));
	}

}