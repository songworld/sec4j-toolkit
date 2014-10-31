package com.toolkit2.client.component2ex;

import java.util.HashSet;
import java.util.Map;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.ReflectUtil;
import com.toolkit2.client.tools.ConfigurationBuilder;
import com.toolkit2.client.component2ex.base.PropetyConstruct;
import twaver.BaseElement;

public class TKNode extends BaseElement
{
	  public TKNode()
	  {
	  }

	  public TKNode(Object id)
	  {
	    super(id);
	  }

	  public void putClientProperty(Object key, Object value) {
	    if (key.toString().startsWith("bb2."))
	      try {
	        String keyName = key.toString().substring("bb2.".length());
	        Object oldValue = null;
	        if (keyName.startsWith("Object:")) {
	          keyName = keyName.substring(7);
	          int index = keyName.indexOf(':');
	          String className = keyName.substring(0, index);
	          String paramName = keyName.substring(index + 1);
	          String[] paramNames = paramName.split("\\+");
	          Object[] param = new Object[paramNames.length];
	          for (int i = 0; i < paramNames.length; i++) {
	            param[i] = getSubObject(paramNames[i]);
	          }
//	          if ((value instanceof SetUserObjectValue)) {
//	            SetUserObjectValue v = (SetUserObjectValue)value;
//	            v.setValue(getUserObject(), paramNames);
//	          }
	          oldValue = ReflectUtil.createInstance(className, param);
	          firePropertyChange("CP:" + key, oldValue, value);
	        } else if (keyName.startsWith("I18n:")) {
	          super.putClientProperty(key, ConfigurationBuilder.getI18NValue(keyName.substring(5, keyName.length())));
	        } else if (keyName.startsWith("text:")) {
	          super.putClientProperty(key, value.toString());
	        } else {
	          String[] keys = keyName.split("\\.");
	          int i = 0;
	          Object subObject = getUserObject();
	          while (i < keys.length - 1) {
	            subObject = ReflectUtil.getObjectBeanValue(subObject, keys[i]);
	            i++;
	          }
	          oldValue = getMapObject(keys[(keys.length - 1)], subObject);
	          if (keys[i].indexOf('@') < 0) {
	            ReflectUtil.setBeanValue(subObject, keys[(keys.length - 1)], value);
	          } else {
	            String itemMapName = keys[i].substring(0, keys[i].indexOf('@'));
	            String itemKeyName = keys[i].substring(keys[i].indexOf('@') + 1);
	            Map map = (Map)ReflectUtil.getObjectBeanValue(subObject, itemMapName);
	            map.put(itemKeyName, value);
	          }
	          firePropertyChange("CP:" + key, oldValue, value);
	        }
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }
	    else
	      super.putClientProperty(key, value);
	  }

	  public Object getClientProperty(Object key)
	  {
	    if (key.toString().startsWith("bb2."))
	    {
	      String keyName = key.toString().substring("bb2.".length());

	      if (keyName.startsWith("PropetyConstruct:")) {
	        keyName = keyName.substring(17);
	        return ((PropetyConstruct)ReflectUtil.createInstance(keyName, new Object[0])).construct(getUserObject());
	      }

	      if (keyName.startsWith("text:")) {
	        return keyName.substring(5);
	      }

	      if (keyName.startsWith("Object:")) {
	        keyName = keyName.substring(7);
	        int index = keyName.indexOf(':');
	        String className = keyName.substring(0, index);
	        String paramName = keyName.substring(index + 1);
	        String[] paramNames = paramName.split("\\+");
	        Object[] param = new Object[paramNames.length];
	        for (int i = 0; i < paramNames.length; i++) {
	          param[i] = getSubObject(paramNames[i]);
	        }
	        return ReflectUtil.createInstance(className, param);
	      }if (keyName.startsWith("refDoc1:")) {
	        keyName = keyName.substring(8);
	        HashSet refDocs = (HashSet)getSubObject(keyName);
	        return ClientUtil.getRefDocs(refDocs, true);
	      }if (keyName.startsWith("refDoc2:")) {
	        keyName = keyName.substring(8);
	        HashSet refDocs = (HashSet)getSubObject(keyName);
	        return ClientUtil.getRefDocs(refDocs, false);
	      }if (keyName.startsWith("I18n:"))
	        return ConfigurationBuilder.getI18NValue(keyName.substring(5, keyName.length()));
	      if (keyName.indexOf("@") > 0)
	        try {
	          return getMapObject(keyName, getUserObject());
	        } catch (Exception e) {
	          e.printStackTrace();
	          return null;
	        }
	      if (keyName.indexOf("+") >= 0)
	      {
	        String result = "<html>";
	        String[] keys = keyName.split("\\+");
	        for (int i = 0; i < keys.length; i++) {
	          result = result + getSubObject(keys[i]);
	          if (i == keys.length - 1)
	            result = result + "</html>";
	          else {
	            result = result + "<br>";
	          }
	        }
	        return result;
	      }if (keyName.indexOf(" ") >= 0)
	      {
	        String result = "";
	        String[] keys = keyName.split(" ");
	        for (int i = 0; i < keys.length; i++) {
	          Object sub = getSubObject(keys[i]);
	          if (i == 0)
	            result = result + sub;
	          else {
	            result = result + " " + sub;
	          }
	        }
	        return result;
	      }

	      return getSubObject(keyName);
	    }

	    return super.getClientProperty(key);
	  }

	  private Object getSubObject(String key)
	  {
	    try
	    {
	      String[] keys = key.split("\\.");
	      Object subObject = getUserObject();
	      for (int i = 0; i < keys.length; i++) {
	        if ((key.startsWith("customProperties")) && (keys.length > 1)) {
	          subObject = getMapObject(keys[0], subObject);
	          i++;
	          if (subObject == null) {
	            return null;
	          }
	          Map map = (Map)subObject;
	          if (map.isEmpty()) {
	            return null;
	          }
	          if (map.containsKey(keys[1])) {
	            return map.get(keys[1]);
	          }
	        }
	        subObject = getMapObject(keys[i], subObject);
	        if (subObject == null) {
	          return null;
	        }
	      }
	      return subObject;
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }return null;
	  }

	  private Object getMapObject(String key, Object subObject)
	    throws Exception
	  {
	    if ((key.equalsIgnoreCase("true")) || (key.equalsIgnoreCase("false"))) {
	      return key;
	    }
	    if (key.indexOf('@') < 0) {
	      if (key.indexOf('$') < 0) {
	        return ReflectUtil.getObjectBeanValue(subObject, key);
	      }
	      return key.subSequence(1, key.length());
	    }

	    String mapName = key.substring(0, key.indexOf('@'));
	    String keyName = key.substring(key.indexOf('@') + 1);
	    Map map = (Map)ReflectUtil.getObjectBeanValue(subObject, mapName);
	    return map.get(keyName);
	  }
	}