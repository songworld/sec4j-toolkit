package com.toolkit2.client.tools;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.StringUtil;
import com.toolkit2.client.CacheManager;
import com.toolkit2.client.i18n.Translator;

import twaver.ElementAttribute;

public class ConfigurationBuilder
{
  public static final String TABLE_ROW_HEIGHT = "rowHeight";
  public static final String COLUMN = "column";
  public static final String NAME = "name";
  public static final String RES_ID = "resID";
  public static final String VISIBLE = "visible";
  public static final String AUTORESIZE = "autoResize";
  public static final String RENDERER_CLASS = "rendererClass";
  public static final String READ_METHOD = "readMethod";
  public static final String ALIGNMENT = "alignment";
  public static final String PRINTED_WIDTH = "printedWidth";
  public static final String IS_PRINT = "isPrint";
  public static final String SORT_BY = "sortBy";
  public static final String EXTEND = "extend";
  public static final String ROWPACKPARTICIPABLE = "rowPackParticipable";
  public static final String PRINTNEWLINE = "printNewLine";
  public static final String EDITORCLASS = "editorClass";
  public static final String EDITABLE = "editable";
  private static DocumentBuilderFactory factory;
  private static DocumentBuilder builder;
  private static Hashtable<String, ConfigurationItem[]> itemCache;
  private static Hashtable<String, TableConfiguration> tableCache;

  public static void clearCache()
  {
    itemCache.clear();
  }

  public static TableConfiguration getTableConfiguration(Class clazz) {
    String name = clazz.getName();
    name = name.substring(name.lastIndexOf(".") + 1) + ".xml";
    return getTableConfiguration(name);
  }

  public static TableConfiguration getTableConfiguration(String confFileName) {
    try {
      if (tableCache.get(confFileName) == null) {
        InputStream is = ConfigurationBuilder.class.getResource(confFileName).openStream();
        Document doc = builder.parse(is);
        Element element = doc.getDocumentElement();
        TableConfiguration result = new TableConfiguration();
        if (element.getAttribute("rowHeight") != null) {
          String value = element.getAttribute("rowHeight");
          if (!value.trim().equals("")) {
            int height = Integer.valueOf(value).intValue();
            result.setRowHeight(height);
          }
        }
        tableCache.put(confFileName, result);
      }
      return (TableConfiguration)tableCache.get(confFileName);
    } catch (Exception e) {
      ClientUtil.showException(e);
    }return null;
  }

  public static ArrayList<ElementAttribute> getElementTableAttribute(ArrayList<ElementAttribute> atts, Class clazz)
  {
    String name = clazz.getName();
    name = name.substring(name.lastIndexOf(".") + 1) + ".xml";
    return getElementTableAttribute(atts, name);
  }

  public static ArrayList<ElementAttribute> getElementTableAttribute(ArrayList<ElementAttribute> atts, String confFileName) {
    if (atts == null)
      atts = new ArrayList();
    try
    {
      InputStream is = null;
      try {
        is = ConfigurationBuilder.class.getResource(confFileName).openStream();
      } catch (NullPointerException ex) {
        ClientUtil.showException(ex);
        return null;
      }
      Document doc = builder.parse(is);
      Element element = doc.getDocumentElement();
      int length = element.getElementsByTagName("column").getLength();
      for (int i = 0; i < length; i++) {
        Node node = element.getElementsByTagName("column").item(i);
        ElementAttribute att = new ElementAttribute();
        boolean editable = false;
        for (int j = 0; j < node.getChildNodes().getLength(); j++) {
          Node child = node.getChildNodes().item(j);
          if (child.hasChildNodes()) {
            String property = child.getAttributes().getNamedItem("name").getNodeValue();
            String value = child.getChildNodes().item(0).getNodeValue();
            if ((property.equals("editable")) && (value != null) && (value.equals("true"))) {
              editable = true;
            }
            getAttibute(att, property, value);
          }
        }
        att.setEditable(editable);
        atts.add(att);
      }
      return atts;
    } catch (Exception e) {
      ClientUtil.showException(e);
    }return null;
  }

  public static ConfigurationItem[] getItemConfiguration(String confFileName)
  {
    try {
      if (itemCache.get(confFileName) == null) {
        InputStream is = null;
        try {
          is = ConfigurationBuilder.class.getResource(confFileName).openStream();
        } catch (NullPointerException ex) {
          ClientUtil.showException(ex);
          return null;
        }
        Document doc = builder.parse(is);
        Element element = doc.getDocumentElement();
        int length = element.getElementsByTagName("column").getLength();
        ConfigurationItem[] items = new ConfigurationItem[length];
        for (int i = 0; i < length; i++) {
          ConfigurationItem item = new ConfigurationItem();
          Node node = element.getElementsByTagName("column").item(i);
          for (int j = 0; j < node.getChildNodes().getLength(); j++) {
            Node child = node.getChildNodes().item(j);
            if (child.hasChildNodes()) {
              String property = child.getAttributes().getNamedItem("name").getNodeValue();
              String value = child.getChildNodes().item(0).getNodeValue();
              setProperty(item, property, value);
            }
          }
          items[i] = item;
        }
        itemCache.put(confFileName, items);
      }
      return (ConfigurationItem[])itemCache.get(confFileName);
    } catch (Exception e) {
      ClientUtil.showException(e);
    }return null;
  }

  public static ConfigurationItem[] getItemConfiguration(Class clazz)
  {
    String name = clazz.getName();
    name = name.substring(name.lastIndexOf(".") + 1) + ".xml";
    return getItemConfiguration(name);
  }

  public static String getI18NValue(String key)
  {
    if ((key == null) || (key.indexOf(":") == -1)) {
      return key;
    }
    String moduleName = key.substring(0, key.indexOf(":"));
    String realKey = key.substring(key.indexOf(":") + 1);
    return StringUtil.toUpperCaseFirstWordAndRemoveLastColon(Translator.getString(moduleName, realKey));
  }

  private static ElementAttribute getAttibute(ElementAttribute attribute, String property, String value) {
    if (attribute == null) {
      return null;
    }
    if (value == null) {
      return null;
    }
    if (property.equals("name"))
    {
      attribute.setDisplayName(getI18NValue(value));
    }
    if (property.equals("extend")) {
      attribute.setExtraWidthAssignable("true".equalsIgnoreCase(value));
    }
    if (property.equals("autoResize")) {
      attribute.setExtraWidthAssignable("true".equalsIgnoreCase(value));
    }
    if (property.equals("editorClass")) {
      attribute.setEditorClass(value);
    }
    if (property.equals("rendererClass")) {
      if (value.trim().equals(""))
        attribute.setRendererClass(null);
      else {
        attribute.setRendererClass(value);
      }
    }
    if (property.equals("readMethod")) {
      String prefix = "bb2.";
      attribute.setClientPropertyKey(prefix + getClientPropertyKeyFromReadMethod(value));
    }
    if ((property.equals("printedWidth")) && 
      (!value.trim().equals(""))) {
      Map printWidthMap = new HashMap();
      printWidthMap.put("printWidth", value);
      attribute.setParams(printWidthMap);
    }

    if (property.equals("editable")) {
      if ((value != null) && ("".equals(value)))
        attribute.setEditable(value.equals("true"));
      else
        attribute.setEditable(false);
    }
    else {
      attribute.setEditable(attribute.isEditable());
    }
    return attribute;
  }

  private static String getClientPropertyKeyFromReadMethod(String readMethod) {
    if ((readMethod == null) || (readMethod.equals(""))) {
      return "";
    }
    if (readMethod.indexOf(".") > 0) {
      readMethod = readMethod.replaceAll(".get", ".");
      readMethod = readMethod.replaceAll(".is", ".");
    }
    if (readMethod.startsWith("get"))
      readMethod = readMethod.replaceFirst("get", "");
    else if (readMethod.startsWith("is")) {
      readMethod = readMethod.replaceFirst("is", "");
    }
    return readMethod;
  }

  private static void setProperty(ConfigurationItem item, String property, String value) {
    if (item == null) {
      return;
    }
    if (value == null) {
      value = "";
    }
    if (property.equals("name"))
    {
      item.setKey(value);
      item.setName(getI18NValue(value));
    }
    if (property.equals("resID")) {
      item.setResID(value);
    }
    if (property.equals("visible")) {
      item.setVisible(Boolean.valueOf(value).booleanValue());
    }
    if (property.equals("autoResize")) {
      item.setAutoResize(Boolean.valueOf(value).booleanValue());
    }
    if (property.equals("isPrint")) {
      item.setPrint(Boolean.valueOf(value).booleanValue());
    }
    if (property.equals("extend")) {
      item.setExtend("true".equalsIgnoreCase(value));
    }
    if (property.equals("rowPackParticipable")) {
      item.setRowPackParticipable("true".equalsIgnoreCase(value));
    }
    if (property.equals("sortBy")) {
      item.setSortBy(value);
    }
    if (property.equals("editorClass")) {
      item.setEditorClass(value);
    }

    if (property.equals("rendererClass")) {
      if (value.trim().equals(""))
        item.setRendererClass(null);
      else {
        item.setRendererClass(value);
      }
    }
    if (property.equals("readMethod")) {
      if (value.trim().equals(""))
        item.setReadMethod(null);
      else {
        item.setReadMethod(value);
      }
    }
    if (property.equals("alignment")) {
      if (value.trim().equals(""))
        item.setAlignment(null);
      else {
        item.setAlignment(value);
      }
    }
    if (property.equals("printedWidth")) {
      if (value.trim().equals(""))
        item.setPrintedWidth(10);
      else {
        item.setPrintedWidth(Integer.parseInt(value));
      }
    }
    if (property.equals("printNewLine"))
      item.setPrintNewLine(value.trim().equalsIgnoreCase("true"));
  }

  static
  {
    CacheManager.registerCache(ConfigurationBuilder.class, "clearCache");

    factory = DocumentBuilderFactory.newInstance();
    builder = null;
    itemCache = new Hashtable();
    tableCache = new Hashtable();

    factory.setValidating(false);
    factory.setNamespaceAware(false);
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
  }
}