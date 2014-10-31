package com.toolkit2.client;

import java.awt.Component;
import java.awt.Frame;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.tools.DebugConsoleUI;

public class OperateCodeManager {
	private static OperateCodeManager instance = null;
	 public static HashMap<String, OperateCode> operateCodeMap = new HashMap();

	public OperateCodeManager() {
		loadActionCodeMapFromXML();
	}

	public static OperateCodeManager getInstance() {
		if (instance == null)
			instance = new OperateCodeManager();
		return instance;
	}

	private static void loadActionCodeMapFromXML() {
		if (!operateCodeMap.isEmpty())
			return;
		String xml = "";
		if (xml == null || xml.isEmpty())
			xml = "/com/toolkit2/client/ActionCode.xml";
		try {
			InputStream is = FreeUtil.class.getResource(xml).openStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("codeType");
			int length = nodes.getLength();
			for (int i = 0; i < length; i++) {
				Element node = (Element) nodes.item(i);
				String type = node.getAttribute("id");
				NodeList list = node.getElementsByTagName("code");
				int len = list.getLength();
				for (int j = 0; j < len; j++) {
					Element child = (Element) list.item(j);
					String code = child.getAttribute("id");
					String className = child.getAttribute("className").trim();
					String description = child.getAttribute("description");
					String condition = child.getAttribute("condition").trim();
					boolean visible = condition == "true" ? true : false;
					if (description == null || description.trim().isEmpty()) {
						description = getDescription(className, type);
						child.setAttribute("description", description);
					}
					OperateCode operateCode = new OperateCode(code, type,
							className, description);
					operateCode.setVisible(visible);
					operateCodeMap.put(code, operateCode);
				}

			}

			initMoreOperateCode();
		} catch (Exception e) {

		}
	}

	private static void initMoreOperateCode() {
		OperateCode operateCode = new OperateCode("HOME", "sendNotice", "首页");
		operateCodeMap.put("condition", operateCode);
		operateCode = new OperateCode("DEBUG", "debug", "调试");
		operateCodeMap.put("DEBUG", operateCode);
		//System.out.println("OperateCodeManager.java operateCodeMap内容数量:"+operateCodeMap.size());
	}

	private static String getDescription(String className, String type) {

		return "";
	}

	public void doAction(String code) {
		OperateCode operateCode;
		String type;
		String className;
		//System.out.println("OperateCodeManager.java "+code+":"+operateCodeMap.size());
		if (operateCodeMap.get(code) == null) {
			System.out.println("OperateCodeManager.java Code is NULL:"+code);
			return;
		}
		operateCode = (OperateCode) operateCodeMap.get(code);
		//获取操作类型
		type = operateCode.getType();
		//获取class名字
		className = operateCode.getClassName();
		//获取方法名
		String methodName = operateCode.getMethodName();
		//方法和类验证
		if (className != null && !"".equals(className)) {
			System.out.println("OperateCodeManager.java ClassName is NULL");
			return;
		}
		if ("method".equalsIgnoreCase(type)) {
			if (methodName == null || "".equals(methodName)) {
				System.out.println("OperateCodeManager.java MethodName is NULL");
				return;
			}
		}
		try {

			if ("VKF8".equalsIgnoreCase(type)) {
				String method = "getInstance";
				Method m = null;
				try {
					m = Class.forName(className).getDeclaredMethod(method,
							new Class[0]);
				} catch (NoSuchMethodException e) {
					m = null;
					System.out.println("OperateCodeManager.java No Such Method Exception："+m);
				}
				JFrame ui = null;
				if (m != null)
					ui = (JFrame) m.invoke(null, new Object[0]);
				else
					ui = (JFrame) Class.forName(className).newInstance();
				ui.setVisible(true);
			} else if ("method".equalsIgnoreCase(type)){
				invokeMethod(operateCode.getMethodName());
				}
		} catch (Exception e) {

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void invokeMethod(String methodName) {
		try {
			Class clazz = OperateCodeManager.class;
			Method m = clazz.getMethod(methodName, new Class[0]);
			if (m == null)
				return;
			System.out.println("OpserateCodeManager.java invokeMethod:"+methodName);
			m.invoke(null, new Object[0]);
		} catch (Exception e) {

		}
	}

	public static Object getObjectFromCode(String code) throws Exception {
		Object obj = null;
		if (code != null && !code.isEmpty()) {
			OperateCode opcode = (OperateCode) operateCodeMap.get(code);
			if (opcode == null)
				return null;
			String className = opcode.getClassName();
			if (className != null)
				obj = constractClass(className);
		}
		return obj;
	}

	// 通过类获取对象
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object constractClass(String className) throws Exception {
		if (className.indexOf("@") > 0) {
		      String[] str = className.split("@");
		      className = str[0];
		      String[] values = str[1].split("\\|");
		      Class[] types = new Class[values.length];
		      Object[] value = new Object[values.length];
		      for (int i = 0; i < types.length; i++) {
		        String v = values[i];
		        if ((v.equalsIgnoreCase("true")) || (v.equalsIgnoreCase("false"))) {
		          types[i] = Boolean.TYPE;
		          value[i] = Boolean.valueOf(v);
		        } else {
		          types[i] = String.class;
		          value[i] = v;
		        }
		      }
		      Class clazz = Class.forName(className);
		      Constructor c = clazz.getConstructor(types);
		      return c.newInstance(value);
		    }
		    try {
		      return Class.forName(className).newInstance();
		    } catch (InstantiationException e) {
		      try {
		        if (className.indexOf("$") > 0) {
		          Class cla1 = Class.forName(className.substring(0, className.indexOf("$")));
		          Class cla2 = Class.forName(className);
		          Constructor[] c = cla2.getDeclaredConstructors();
		          Object instance = cla1.newInstance();
		          return c[0].newInstance(new Object[] { instance });
		        }
		        Class clazz = Class.forName(className);
		        Method m = clazz.getDeclaredMethod("getInstance", new Class[0]);
		        return m.invoke(null, new Object[0]);
		      }
		      catch (Exception ex) {
		      }
		    }
		    return new Object();
	}
	//调试信息窗体
	public static void debug() {
		DebugConsoleUI ui = DebugConsoleUI.getInstance();
		ui.setVisible(true);
	}
	//关于对话框
	public static void about() {
	   // new AboutUI(AbstractMainUI.getInstance()).setVisible(true);
	  }
	public static void exit() {

	}
	 static
	  {
	    CacheManager.registerCache(OperateCodeManager.class, "clearCache");
	  }
}
