package com.toolkit2.Util;
import java.net.URL;
import com.toolkit2.client.i18n.Translator;

public final class MessageUtil
{
  public static final String URL_PREFIX = "http://";
  private static final String MESSAGE_BUNDLE = "message";

  public static String getString(String key)
  {
    return Translator.getString("message", key);
  }

  public static String getString(String key, String arg) {
    return Translator.getString("message", key, arg);
  }

  public static String getString(String key, Object[] args) {
    return Translator.getString("message", key, args);
  }


  

 
  public static String createNoticeHyperLink(String orderNumber, String displayText) {
    return "<a href='http://" + orderNumber + ".toolkit.com'>" + displayText + "</a>";
  }

  public static String getOrderNumberFromURL(URL url) {
    if (url != null) {
      String urlString = url.toString();
      urlString = urlString.substring("http://".length());
      urlString = urlString.substring(0, urlString.indexOf("."));
      return urlString;
    }
    return null;
  }
}