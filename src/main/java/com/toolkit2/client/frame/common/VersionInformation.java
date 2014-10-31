package com.toolkit2.client.frame.common;

public class VersionInformation {
	 public static String getVersionString()
	  {
	    return "3.0";
	  }

	  public static String getWhatsNew()
	  {
	    return "http://localhost:8080";
	  }

	  public static String getWhatsNewAdderss()
	  {
	    return "http://localhost:8080";
	  }

	  public static boolean isMandatory()
	  {
	    return false;
	  }

	  public static String getVersionInfo() {
	    String result = getVersionString().trim() + "@" + isMandatory() + "@" + getWhatsNew();
	    return result;
	  }

	  public String toString() {
	    return getVersionString() + "[" + getWhatsNew() + "]";
	  }
}
