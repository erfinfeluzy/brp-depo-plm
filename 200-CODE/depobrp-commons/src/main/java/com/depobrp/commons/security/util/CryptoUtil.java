package com.depobrp.commons.security.util;

import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.depobrp.commons.security.exception.SecurityException;


/**
 * @author Erfin Feluzy 
 * 
 */
public class CryptoUtil {
	

	private static final String UNICODE_FORMAT = "utf-8";
	private static final String TRIPLE_DES = "DESede";

	private KeySpec keySpec;
	private SecretKeyFactory secretKeyFactory;
	private byte[] keyAsBytes;
	private SecretKey key;

    public CryptoUtil(){
		super();
		
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
	        byte[] digestOfPassword = md.digest(DEFAULT_CRYPTO_SALT.getBytes(UNICODE_FORMAT));
	         
			keyAsBytes = Arrays.copyOf(digestOfPassword, 24);
	        for (int j = 0,  k = 16; j < 8;)
	        {
	        	keyAsBytes[k++] = keyAsBytes[j++];
	        }
	        
			keySpec = new DESedeKeySpec(keyAsBytes);
			secretKeyFactory = SecretKeyFactory.getInstance(TRIPLE_DES);
			key = secretKeyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
		
	}

    private static final String DEFAULT_CRYPTO_SALT = "8686c1d59af4ff4c17ee3e3e02c9e2f4";

    /**
	 * Method To Encrypt The String
	 */
	public String encrypt(String unencryptedString)  throws SecurityException{
		
		String encryptedString = null;
		try {
			
			Cipher cipher = Cipher.getInstance(TRIPLE_DES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			
			encryptedString = Base64.encodeBase64String(encryptedText);
			
			return encryptedString;
			
		} catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
		
	}
	/**
	 * Method To Decrypt An Ecrypted String
	 */
	public String decrypt(String encryptedString) throws SecurityException {
		String decryptedText=null;
		
		try {
			Cipher cipher = Cipher.getInstance(TRIPLE_DES);
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText= bytesToString(plainText);
			
		} catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
		return decryptedText;
	}
	/**
	 * Returns String From An Array Of Bytes
	 */
	private static String bytesToString(byte[] bytes) {
		
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		
		return stringBuffer.toString();
	}
	
	public static void main(String[] args) throws Exception{
		
		CryptoUtil util = new CryptoUtil();
		
		String text = "password";
		String encrypt = util.encrypt(text);
		String decrypt = util.decrypt(encrypt);
		
		System.out.println(encrypt);
		System.out.println(decrypt);
	}
	
}
