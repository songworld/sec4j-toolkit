package com.toolkit2.client.i18n;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Set;

import com.toolkit2.Util.ClientUtil;

public class DebugResourceBundle extends PropertyResourceBundle
{
  public static boolean debug = false;
  public static String MY_I18N_PATH = System.getProperty("user.dir") + "/myi18n/";
  private static ArrayList<String> ignores = new ArrayList();
  private static HashMap<String, DebugResourceBundle> allResourceBundles = new HashMap();
  private Locale locale = null;
  private String bundleFileName = null;
  private HashMap<String, Integer> lineNumbers = new HashMap();
  private HashMap<Integer, String> keys = new HashMap();
  private HashMap<Integer, String> lineStrings = new HashMap();
  private HashMap<String, String> myTranslations = new HashMap();

  public static void setDebug(boolean debug)
  {
    debug = debug;
    if (debug)
      DebugResourceUI.start();
  }

  public DebugResourceBundle(String bundleName) throws IOException
  {
    super(Translator.class.getResourceAsStream(bundleName));
    this.bundleFileName = bundleName;
    init();
  }

  public DebugResourceBundle(Locale locale, String bundleName) throws IOException {
    super(Translator.class.getResourceAsStream("_" + locale.toString() + "/" + bundleName));
    this.locale = locale;
    this.bundleFileName = bundleName;
    init();
  }

  private void init() throws IOException {
    this.bundleFileName = this.bundleFileName.substring(this.bundleFileName.lastIndexOf("/") + 1);
    allResourceBundles.put(createResouceBundleID(this.bundleFileName), this);
    if (debug)
    {
      String filePathName = null;
      if (this.locale != null)
        filePathName = "_" + this.locale.toString() + "/" + this.bundleFileName;
      else {
        filePathName = this.bundleFileName;
      }
      InputStream input = getClass().getResourceAsStream(filePathName);
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      String line = reader.readLine();
      int lineNumber = 1;
      while (line != null) {
        line = line.trim();
        if ((!line.isEmpty()) && (!line.startsWith("#"))) {
          String key = line;
          if (line.contains("=")) {
            int index = line.indexOf("=");
            key = line.substring(0, index);
            key = key.trim();
          }
          this.lineNumbers.put(key, Integer.valueOf(lineNumber));
          this.keys.put(Integer.valueOf(lineNumber), key);
          this.lineStrings.put(Integer.valueOf(lineNumber), line);
        }
        line = reader.readLine();
        lineNumber++;
      }
    }
    try
    {
      readMyTranslation();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Object handleGetObject(String key) {
    Object value = super.handleGetObject(key);
    if (ignores.contains(key)) {
      return value;
    }

    if (this.bundleFileName.equals("server_exception.properties")) {
      return value;
    }

    if (key != null) {
      boolean hasMyTranslation = this.myTranslations.containsKey(key);
      if (hasMyTranslation) {
        value = this.myTranslations.get(key);
      }
      if (debug) {
        int lineNumber = ((Integer)this.lineNumbers.get(key)).intValue();
        String bundleID = createResouceBundleID(this.bundleFileName);
        String debugString = value + "[" + bundleID + lineNumber + "]";
        if (hasMyTranslation) {
          debugString = debugString + "*";
        }
        return debugString;
      }
      return value;
    }

    return null;
  }

  public Object superHandleGetObject(String key) {
    return super.handleGetObject(key);
  }

  public String getMyTranslation(String key) {
    return (String)this.myTranslations.get(key);
  }

  public String getBundleFileName() {
    return this.bundleFileName;
  }

  public Locale getLocale()
  {
    return this.locale;
  }

  public String getKeyByLineNumber(int lineNumber) {
    return (String)this.keys.get(Integer.valueOf(lineNumber));
  }

  public String getLineStringByLineNumber(int lineNumber) {
    return (String)this.lineStrings.get(Integer.valueOf(lineNumber));
  }

  public void addMyTranslation(String key, String value) {
    this.myTranslations.put(key, value);
  }

  private static String createResouceBundleID(String bundleName)
  {
    if (bundleName.toLowerCase().startsWith("manage")) {
      return "MN";
    }
    return bundleName.substring(0, 2).toUpperCase();
  }

  public static DebugResourceBundle getResourceBundlesByID(String id) {
    return (DebugResourceBundle)allResourceBundles.get(id);
  }

  public static DebugResourceBundle getResourceBundlesByName(String name) {
    return (DebugResourceBundle)allResourceBundles.get(createResouceBundleID(name));
  }

  public static void saveAllMyTranslation()
  {
    Iterator itBundle = allResourceBundles.keySet().iterator();
    while (itBundle.hasNext()) {
      String key = (String)itBundle.next();
      DebugResourceBundle bundle = (DebugResourceBundle)allResourceBundles.get(key);
      try {
        bundle.saveMyTranslation();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void saveMyTranslation() throws Exception {
    File dir = new File(MY_I18N_PATH);
    if (!dir.exists()) {
      dir.mkdir();
    }
    if (!this.myTranslations.isEmpty()) {
      String filename = MY_I18N_PATH + this.bundleFileName;
      File file = new File(filename);
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      Iterator itMyTranslation = this.myTranslations.keySet().iterator();
      while (itMyTranslation.hasNext()) {
        String translationKey = (String)itMyTranslation.next();
        String translationValue = (String)this.myTranslations.get(translationKey);
        translationValue = ClientUtil.toUnicode(translationValue);
        String line = translationKey + "\t\t=\t" + translationValue + "\n";
        writer.write(line);
      }
      writer.close();
    }
  }

  public void readMyTranslation() throws Exception {
    String filename = MY_I18N_PATH + this.bundleFileName;
    File file = new File(filename);
    if (file.exists()) {
      Properties properties = new Properties();
      properties.load(new FileInputStream(file));
      if (!properties.isEmpty()) {
        Iterator it = properties.keySet().iterator();
        while (it.hasNext()) {
          String key = it.next().toString();
          String translation = properties.getProperty(key);
          this.myTranslations.put(key, translation);
        }
      }
    }
  }

  static
  {
    ignores.add("AbstractBarChart.YEAR");
  }
}
