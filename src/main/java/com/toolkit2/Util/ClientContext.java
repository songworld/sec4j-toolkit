package com.toolkit2.Util;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.UIManager;

/*
 * 		 保存客户端的环境信息
 * */
public class ClientContext {
	private static boolean englishFontOnly = false;
	private static Locale locale = null;
	private static String webstart = null;

	/*****************************************************************************************
	 * 获取语言环境 默认为 中国 中文
	 * 
	 * **/
	public static Locale getLocale() {
		if (locale == null) {
			Locale defaultLocale = Locale.getDefault();
			ArrayList supportLocales = ClientUtil.getLocaleConf();
			if ((supportLocales != null) && (defaultLocale != null)
					&& (supportLocales.contains(defaultLocale))) {
				locale = defaultLocale;
				return locale;
			}

			return ClientConst.CHINESE_LOCALE;
		}
		return locale;
	}

	public static boolean isChineseLocale() {
		return ClientUtil.isChineseLocale(locale);
	}

	/*****************************************************************************************
	 * 设置语言环境 默认为 中国 中文
	 * 
	 * **/
	public static void setLocale(Locale locale) {
		if (locale == null) {
			locale = ClientConst.CHINESE_LOCALE;
		}
		locale = locale;
		Locale.setDefault(locale);
		System.setProperty("user.language", locale.getLanguage());
		System.setProperty("user.country", locale.getCountry());
		UIManager.getDefaults().setDefaultLocale(locale);
	}

	/******************************************************************************************
	 * 获取当前语言环境编码
	 * */
	public static String getLanguageCode() {
		return ClientUtil.getLanguageCodeByLocale(getLocale());
	}

	/******************************************************************************************
	 * 获取web启动初始地址
	 * */
	public static String getWebstart() {
		return webstart;
	}

	static void setWebstart(String webstart) {
		webstart = webstart;
	}

	/*****************************************************************************************
	 * 是否只有英文字体
	 * 
	 * ****/
	public static boolean isEnglishFontOnly() {
		return englishFontOnly;
	}

	public static void setEnglishFontOnly(boolean englishFontOnly) {
		englishFontOnly = englishFontOnly;
	}

}
