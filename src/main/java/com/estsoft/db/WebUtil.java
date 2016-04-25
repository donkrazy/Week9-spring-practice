package com.estsoft.db;

public class WebUtil {
	public static boolean isNumeric(String value) {
		return value != null && value.matches("[-+]?\\d*\\.?\\d+");
	}
}