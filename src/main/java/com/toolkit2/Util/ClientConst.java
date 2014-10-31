package com.toolkit2.Util;

import java.io.File;
import java.util.Locale;

/********************************************************************************************************
 * 保存基于配置的常量信息 CS端信息全部保存在这里
 * 
 * */
public class ClientConst {
	/*
	 * 默认为英文，按照现在产品使用情况基本上都是中文
	 */
	public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
	/*
	 * 中文
	 */
	public static final Locale CHINESE_LOCALE = new Locale("zh", "CN");

	/*************************************************************************
	 * 	获取应用根目录
	 * **/
	public static String getUserHomeDir()
	  {
	    String FileName = System.getProperty("user.home") + File.separatorChar + "ToolKit2";
	    File File = new File(FileName);
	    if (!File.exists()) {
	      String bb2FileName = System.getProperty("user.home") + File.separatorChar + "TK2";
	      File bb2File = new File(bb2FileName);
	      if (bb2File.exists()) {
	        bb2File.renameTo(File);
	      } else {
	        String bb2FreeFileName = System.getProperty("user.home") + File.separatorChar + "TK2Free";
	        File bb2FreeFile = new File(bb2FreeFileName);
	        if (bb2FreeFile.exists())
	          bb2FreeFile.renameTo(File);
	        else {
	        	File.mkdir();
	        }
	      }
	    }
	    if (!ClientUtil.isNullObject(ClientContext.getWebstart())) {
	    	FileName = FileName + File.separatorChar + ClientContext.getWebstart();
	    }
	    return FileName;
	  }
	
	public static String getClientPropertyFile() {
		 return getUserHomeDir() + File.separatorChar + "Client.properties";
	}
}
