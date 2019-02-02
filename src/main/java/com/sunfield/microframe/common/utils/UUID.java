package com.sunfield.microframe.common.utils;

public class UUID {

	public static String getUUID(){
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}
	
}
