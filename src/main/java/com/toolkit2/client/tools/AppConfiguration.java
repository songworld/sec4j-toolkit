package com.toolkit2.client.tools;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.toolkit2.client.frame.free.FreeUtil;
public class AppConfiguration {
	 private static String actionCodeFile = null;
	  private static String IMAGE_URL_PREFIX = "/com/toolkit2/client/images/";
	  private static String AppName = "";
	  private static Properties p = null;

	  public static Properties initConfiguration(String configFileURL) {
	    if (((p == null) || (p.isEmpty())) && 
	      (configFileURL != null) && (!configFileURL.isEmpty())) {
	      p = loadProperties(configFileURL);
	      if (p != null) {
	        actionCodeFile = p.getProperty("actionCodeFile");
	        IMAGE_URL_PREFIX = p.getProperty("IMAGE_URL_PREFIX");
	        AppName = p.getProperty("AppName");
	      }
	    }

	    return p;
	  }

	  public static Properties loadProperties(String fileName) {
	    Properties p = new Properties();
	    try {
	      InputStream is = FreeUtil.class.getResource(fileName).openStream();
	      p.load(is);
	      is.close();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	    return p;
	  }

	  public static Properties loadOutsideProperties(String fileName) {
	    Properties result = new Properties();
	    File file = new File(fileName);
	    if (file.exists()) {
	      try {
	        FileInputStream is = new FileInputStream(file);
	        result.load(is);
	        is.close();
	      } catch (IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	    return result;
	  }

	  public static Properties getProperties() {
	    return p;
	  }

	  public static String getImageURLPrefix() {
	    return IMAGE_URL_PREFIX;
	  }

	  public static String getActionCodeFile() {
	    return actionCodeFile;
	  }

	  public static Color getColor(String key, Color defaultColor) {
	    Properties p = getProperties();
	    if (p != null) {
	      String contentPaneBackground = p.getProperty(key);
	      if (contentPaneBackground != null) {
	        String[] rgb = contentPaneBackground.split(",");
	        if (rgb.length == 3) {
	          return new Color(Integer.valueOf(rgb[0]).intValue(), Integer.valueOf(rgb[1]).intValue(), Integer.valueOf(rgb[2]).intValue());
	        }
	      }
	    }
	    return defaultColor;
	  }

//	  public static String getInitClass() {
//	    if (getProperties() != null) {
//	      String className = (String)getProperties().get("init_class");
//	      if ((className != null) && (!className.isEmpty())) {
//	        return className;
//	      }
//	    }
//	    return WizardUI.class.getName();
//	  }

	  public static String getAppName() {
	    if ((AppName == null) || (AppName.trim().isEmpty())) {
	      return "TOOLKIT2";
	    }
	    return AppName;
	  }
}
