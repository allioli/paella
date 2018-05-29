package com.mytesting;

import java.security.SecureRandom;

public final class Utils {
	
	private static SecureRandom random = new SecureRandom();
	
	private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static String generateTestPassword(int len) {
		
	   String result = "";
	   for (int i = 0; i < len; i++) {
		   int index = random.nextInt(ALPHANUMERIC.length());
		   result += ALPHANUMERIC.charAt(index);
	   }
	   return result;
	}
	
	public static String generateTestEmail() {
		return "test_user" + (int)Math.floor(Math.random() * 1000) + "@test.com";
	}
	
	 
	 

}
 