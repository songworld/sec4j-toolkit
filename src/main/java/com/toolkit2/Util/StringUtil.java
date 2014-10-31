package com.toolkit2.Util;
/*
 * 		字符串操作方法
 * */
public class StringUtil {
	public static String toUpperCaseFirstWordAndRemoveLastColon(String str) {
		if ((str == null) || (str.equalsIgnoreCase(""))) {
			return "";
		}
		if (str.endsWith(":")) {
			str = str.substring(0, str.length() - 1);
		}
		str = removeLastColon(str);
		if (str.length() == 1) {
			return str.toUpperCase();
		}
		String first = str.substring(0, 1).toUpperCase();
		return first + str.substring(1);
	}

	public static String[] toUpperCaseFirstWord(String[] str) {
		if (str == null) {
			return null;
		}
		int i = 0;
		for (int n = str.length; i < n; i++) {
			str[i] = toUpperCaseFirstWordAndRemoveLastColon(str[i]);
		}
		return str;
	}

	public static String removeLastColon(String text) {
		if ((text.endsWith(":")) || (text.endsWith("："))) {
			return text.substring(0, text.length() - 1);
		}
		return text;
	}
}
