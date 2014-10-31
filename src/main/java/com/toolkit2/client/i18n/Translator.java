package com.toolkit2.client.i18n;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import com.toolkit2.Util.ClientConst;
import com.toolkit2.Util.ClientContext;
/***************************************************************************************************
 * 语言转换，根据不同语言环境显示不同语言信息
 * **/
public class Translator {
	private static boolean warned = false;
	  private static final String BUNDLE_PATH = "/com/toolkit2/client/i18n";
	  private static Hashtable<String, Hashtable<Locale, ResourceBundle>> moduleBundles = new Hashtable();

	  private static ResourceBundle getBundle(String module, Locale locale)
	  {
	    Hashtable bundles = (Hashtable)moduleBundles.get(module);
	    if (bundles == null) {
	      bundles = new Hashtable();
	      moduleBundles.put(module, bundles);
	    }
	    if (locale == null) {
	    	//locale is null set chinese
	      locale = ClientConst.CHINESE_LOCALE;
	    }
	    ResourceBundle bundle = (ResourceBundle)bundles.get(locale);
	    if (bundle == null) {
	      String bundleName = module.toLowerCase();
	      if (locale != Locale.ROOT)
	        bundleName = bundleName + "_" + locale.toString() + ".properties";
	      else
	        bundleName = bundleName + ".properties";
	      try
	      {
	        if (locale == Locale.ROOT)
	          bundle = new DebugResourceBundle(bundleName);
	        else
	          bundle = new DebugResourceBundle(locale, bundleName);
	      }
	      catch (Exception ex) {
	        ex.printStackTrace();
	        if (!warned) {
	          JOptionPane.showMessageDialog(null, "Can't find i18n resource file:\n" + bundleName);
	          warned = true;
	        }
	      }
	      if (bundle == null) {
	        throw new MissingResourceException(bundleName, "", "");
	      }
	      bundles.put(locale, bundle);
	    }
	    return bundle;
	  }

	  private static Locale getClientLocale() {
	    return ClientContext.getLocale();
	  }

	  public static String getString(String key) {
	    return getString(key, getClientLocale());
	  }

	  public static String getString(String key, Locale locale) {
	    return getString("general", key, locale);
	  }

	  public static String getString(String module, String key, Locale locale) {
	    if (key == null) {
	      return "";
	    }
	    String result = key.trim();
	    ResourceBundle bundle = getBundle(module, locale);
	    try {
	      result = getStringImpl(bundle, key.trim());
	    } catch (MissingResourceException ex) {
	      if (locale.equals(new Locale("zh", "TW"))) {
	        result = getString(module, key, ClientConst.CHINESE_LOCALE);
	      }
	      System.out.println("missing i18n key(module='" + module + "'): " + key);
	    }
	  //  result = filterTranslation(module, result);
	    return result;
	  }

	  public static String getString(String module, String key) {
	    return getString(module, key, (Object[])null);
	  }

	  public static String getStringWithColon(String module, String key) {
	    if (ClientContext.getLanguageCode().equalsIgnoreCase(ClientConst.CHINESE_LOCALE.toString())) {
	      return getString(module, key) + "：";
	    }
	    return getString(module, key) + ":";
	  }

	  public static String getString(String module, String key, String arg) {
	    return getString(module, key, new String[] { arg });
	  }

	  public static String getString(String module, String key, Object[] args) {
	    return getString(module, key, args, getClientLocale());
	  }

	  public static String getString(String module, String key, Object[] args, Locale locale) {
	    if (key == null) {
	      return null;
	    }
	    String result = key.trim();
	    int dotIndex = result.indexOf(".");
	    if ((dotIndex > 0) && (dotIndex < result.length() - 1)) {
	      result = result.substring(result.lastIndexOf(".") + 1).replaceAll("_", " ");
	    }
	    module = module.toLowerCase();
	    int end = module.indexOf("access") <= 0 ? module.length() : module.indexOf("access");
	    module = module.substring(0, end);
	    try {
	      ResourceBundle bundle = getBundle(module, locale);
	      result = getStringImpl(bundle, key.trim());

	      if ((args != null) && (args.length > 0))
	        result = MessageFormat.format(result, args);
	    }
	    catch (MissingResourceException ex) {
	      if (locale.equals(new Locale("zh", "TW")))
	        result = getString(module, key, args, ClientConst.CHINESE_LOCALE);
	      else {
	        result = result.replace("*", ".");
	      }
	      System.out.println("missing i18n key(module=" + module + "): " + key);
	    }
//	    if (locale != Locale.ROOT) {
//	      result = filterTranslation(module, result);
//	    }
	    return result;
	  }
/******************************************************************************
 * 服务器同步结果 不一致直接替换以服务器为准
 * 
 * ****/
//	  public static String filterTranslation(String module, String result)
//	  {
//	    Collection customTranslations = CompanySettingServerActionManager.getCustomTranslations();
//	    if (customTranslations != null) {
//	      Iterator it = customTranslations.iterator();
//	      while (it.hasNext()) {
//	        CustomTranslationVO vo = (CustomTranslationVO)it.next();
//	        String orignalTranslation = vo.getOriginalTranslation();
//	        String customTranslation = vo.getCustomTranslation();
//	        result = result.replaceAll(orignalTranslation, customTranslation);
//	      }
//	    }
//	    customTranslations = null;
//	    return result;
//	  }

	  public static String getGeneralString(String key, String arg) {
	    return getString("general", key, arg);
	  }

	  private static String getStringImpl(ResourceBundle bundle, String key) {
	    String value = bundle.getString(key);
	    return value;
	  }

	  public static String getBlank() {
	    if (ClientContext.getLanguageCode().equalsIgnoreCase(ClientConst.CHINESE_LOCALE.toString())) {
	      return " ";
	    }
	    return "";
	  }
}
