package com.toolkit2.Util;

import com.toolkit2.client.i18n.Translator;

/**********************************************************************************************
 * 获取通用字符串信息
 * 
 * */
public class GeneralUtil {
	public static String getString(String key) {
		return Translator.getString("general", key);
	}

	public static String getString(String key, String arg) {
		return Translator.getString("general", key, arg);
	}

	public static String getString(String key, String arg1, String arg2) {
		return Translator
				.getString("general", key, new String[] { arg1, arg2 });
	}

	public static String getString(String key, String[] arg) {
		return Translator.getString("general", key, arg);
	}

	public static String getALL() {
		return getString("General.ALL");
	}

	public static String getConfirmDeleteString() {
		return getString("General.SureToDelete");
	}

	public static String getProperties() {
		return getString("General.Properties");
	}

	public static String getColon() {
		return getString("General.Colon");
	}

	public static String getBlank() {
		return Translator.getBlank();
	}

	
	/*****************************************************************************************
	 * 	是否显示数字尾部的0
	 * 
	 * **/
	public static boolean isShowNumberTailZero() {
		return false;
	}
}
