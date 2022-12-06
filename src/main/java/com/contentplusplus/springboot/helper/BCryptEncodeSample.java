package com.contentplusplus.springboot.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncodeSample {

	public static void main(String[] args) {
		
		String strToBeEncoded = "admin@admin";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedHashedPassword = passwordEncoder.encode(strToBeEncoded);
		System.out.println("Encoded/Hashed value for <"+ strToBeEncoded + "> is: "+ encodedHashedPassword);
	}
	
}
