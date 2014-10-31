package com.toolkit2.client.frame.free;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.AppConfiguration;

import twaver.Node;
import twaver.TDataBox;
public class FreeMainTreeHelper {
	public static final String TABLE_ROW_HEIGHT = "rowHeight";
	  public static final String NODE = "node";
	  public static final String NAME = "name";
	  public static final String HOME_CLASS = "homeClass";
	  public static final String INVISIBLE = "invisible";
	  public static final String DEFAULT_INDEX = "defaultIndex";

	  public static TDataBox createMainTree(String xml_name)
	    throws Exception
	  {
	    TDataBox box = new TDataBox("ToolKit2");
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;
	    Hashtable allTreeNodes = new Hashtable();
	    factory.setValidating(false);
	    factory.setNamespaceAware(false);
	    builder = factory.newDocumentBuilder();
	    InputStream is = null;
	    is = FreeMainTreeHelper.class.getResource(xml_name).openStream();

	    Document doc = builder.parse(is);

	    NodeList nodes = doc.getElementsByTagName("node");
	    Node root = new Node();
	    String appName = AppConfiguration.getAppName();
	    if ((appName == null) || ("".equals(appName.trim()))) {
	      appName = "ToolKit2";
	    }
	    root.setName(appName);
	    root.setIcon(AppConfiguration.getImageURLPrefix() + "home.png");
//修改显示首页
	   // root.putClientProperty("homeClass", Shell.class.getName());
	    box.addElement(root);
	    System.out.println("FreeMainTreeHelper.java 共有node："+nodes.getLength());
	    for (int i = 0; i < nodes.getLength(); i++)
	    {
	      org.w3c.dom.Element node = (org.w3c.dom.Element)nodes.item(i);
	      String nameKey = node.getAttribute("name").trim();
	      String nameValue = getI18nNameByKey(nameKey);

	      String homeClass = node.getAttribute("homeClass").trim();
	      String invisibleString = node.getAttribute("invisible");
	      boolean invisible = (invisibleString != null) && ("true".equalsIgnoreCase(invisibleString));
	      if (homeClass.equals("")) {
	        homeClass = null;
	      }

	      if ((!invisible))
	      {
	        Node element = new Node(node.getAttribute("name").trim());
	        element.setName(nameValue);
	        element.setIcon(AppConfiguration.getImageURLPrefix() + "submodule.png");
	        element.putClientProperty("homeClass", homeClass);
	        element.putClientProperty("nameKey", nameKey);

	        element.putClientProperty("defaultIndex", i);

	        Object parentNodeID = allTreeNodes.get(node.getParentNode());

	        if (parentNodeID != null) {
	          twaver.Element parentElement = box.getElementByID(parentNodeID);
	          element.setParent(parentElement);
	          parentElement.setIcon(AppConfiguration.getImageURLPrefix() + "module.png");
	        } else {
	          element.setParent(root);
	        }
	        box.addElement(element);
	        allTreeNodes.put(node, element.getID());
	      }

	    }

	    orderTree(box);
	    return box;
	  }

	  private static void orderTree(TDataBox box) {
	    List list = ((twaver.Element)box.getRootElements().get(0)).getChildren();
	    TreeMap orders = new TreeMap();
	    for (int i = 0; i < list.size(); i++) {
	      twaver.Element element = (twaver.Element)list.get(i);
	      String nameKey = (String)element.getClientProperty("nameKey");
	      if (nameKey != null) {
	        String indexStr = ClientUtil.readClientProperty(nameKey);
	        if (indexStr != null) {
	          int index = Integer.valueOf(indexStr).intValue();
	          orders.put(Integer.valueOf(index), element);
	        }
	      }
	    }

	    Vector keys = new Vector(orders.keySet());
	    for (int i = keys.size(); i > 0; i--) {
	      int key = ((Integer)keys.get(i - 1)).intValue();
	      twaver.Element element = (twaver.Element)orders.get(Integer.valueOf(key));
	      box.moveToTop(element);
	    }
	  }

	  public static String getI18nNameByKey(String nameKey) {
	    return Translator.getString("MainTreeHelper." + nameKey);
	  }
	}