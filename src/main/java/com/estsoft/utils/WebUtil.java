package com.estsoft.utils;

public class WebUtil {
	public static boolean isNumeric(String value) {
		return value != null && value.matches("[-+]?\\d*\\.?\\d+");
	}
}