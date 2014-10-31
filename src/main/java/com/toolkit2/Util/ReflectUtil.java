package com.toolkit2.Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil
{
  private static final Class[] emptyArray = new Class[0];

  public static Constructor getConstructor(Class cls) {
    Constructor[] constructors = cls.getConstructors();
    return constructors[0];
  }

  public static Constructor getConstructor(String className) {
    try {
      Class cls = Class.forName(className);
      return getConstructor(cls);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }return null;
  }

  public static Object createInstance(String classNameAndType, Object[] params)
  {
    int left = classNameAndType.indexOf('(');
    if (left < 0) {
      try {
        Class cls = Class.forName(classNameAndType);
        return createInstance(cls, params);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return null;
      }
    }
    String className = classNameAndType.substring(0, left);
    String[] typeNames = classNameAndType.substring(left + 1, classNameAndType.length() - 1).split(",");
    try {
      Class cls = Class.forName(className);
      Class[] paramType = new Class[typeNames.length];
      for (int i = 0; i < typeNames.length; i++) {
        paramType[i] = Class.forName(typeNames[i]);
      }
      Constructor constructor = cls.getConstructor(paramType);
      return constructor.newInstance(params);
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static Object createInstance(Class<?> cls, Object[] params)
  {
    try
    {
      if ((params == null) || (params.length == 0))
      {
        return cls.newInstance();
      }

      Class[] paramType = new Class[params.length];
      for (int i = 0; i < params.length; i++) {
        if (params[i] == null) {
          return null;
        }
        paramType[i] = params[i].getClass();
      }
      Constructor constructor = cls.getConstructor(paramType);

      return constructor.newInstance(params);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }return null;
  }

  public static boolean ispropertyNameExist(Object object, String propertyName) throws Exception
  {
    if ((propertyName == null) || (propertyName.length() == 0)) {
      return false;
    }
    if (propertyName.indexOf(".") == -1) {
      String capitalize = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
      Method method = null;
      try {
        method = object.getClass().getMethod("get" + capitalize, emptyArray);
      } catch (NoSuchMethodException e) {
        try {
          method = object.getClass().getMethod("is" + capitalize, emptyArray);
        } catch (NoSuchMethodException ex) {
          return false;
        }
      }
      if (method == null) {
        return false;
      }
    }
    return true;
  }

  public static Object getObjectBeanValue(Object object, String propertyName) throws Exception {
    return getObjectBeanValue(object, propertyName, true);
  }

  public static Object getObjectBeanValue(Object object, String propertyName, boolean showException) throws Exception {
    if ((propertyName == null) || (propertyName.length() == 0)) {
      throw new Exception("property Name can not be Null or Empty");
    }
    if (object == null) {
      return null;
    }
    if (propertyName.indexOf(".") == -1) {
      if ((object instanceof Map)) {
        Map map = (Map)object;
        return map.get(propertyName);
      }
      String capitalize = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
      Method method = null;
      try {
        method = object.getClass().getMethod("get" + capitalize, emptyArray);
      } catch (NoSuchMethodException e) {
        try {
          method = object.getClass().getMethod("is" + capitalize, emptyArray);
        } catch (NoSuchMethodException ex) {
          if (showException)
            ClientUtil.showException(ex);
          else {
            throw ex;
          }
        }
      }
      if (method == null) {
        throw new Exception("can not find method: get" + capitalize + " or is" + capitalize);
      }
      return method.invoke(object, new Object[0]);
    }

    String first = propertyName.substring(0, propertyName.indexOf("."));
    propertyName = propertyName.substring(propertyName.indexOf(".") + 1, propertyName.length());
    Object o = getObjectBeanValue(object, first, showException);
    return getObjectBeanValue(o, propertyName, showException);
  }

  public static Object getObjectBeanValue(Class object, String propertyName) throws Exception
  {
    if ((propertyName == null) || (propertyName.length() == 0)) {
      throw new Exception("property Name can not be Null or Empty");
    }
    if (object == null) {
      return null;
    }
    if (propertyName.indexOf(".") == -1) {
      String capitalize = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
      Method method = null;
      try {
        method = object.getMethod(capitalize, emptyArray);
      } catch (NoSuchMethodException e) {
        try {
          method = object.getMethod("is" + capitalize, emptyArray);
        } catch (NoSuchMethodException ex) {
          try {
            method = object.getMethod("get" + capitalize, emptyArray);
          } catch (NoSuchMethodException nex) {
            method = object.getMethod("find" + capitalize, emptyArray);
          }
        }
      }
      if (method == null) {
        throw new Exception("can not find method: " + capitalize + " or get" + capitalize + " or is" + capitalize);
      }
      return method.invoke(object, new Object[0]);
    }

    String first = propertyName.substring(0, propertyName.indexOf("."));
    propertyName = propertyName.substring(propertyName.indexOf(".") + 1, propertyName.length());
    Object o = getObjectBeanValue(object, first);
    return getObjectBeanValue(o, propertyName);
  }

  public static void setBeanValue(Object object, String propertyName, Object value) throws Exception
  {
    if ((propertyName == null) || (propertyName.length() == 0)) {
      throw new Exception("property Name can not be Null or Empty");
    }
    if (propertyName.indexOf(".") < 0) {
      if ((object instanceof Map)) {
        Map map = (Map)object;
        map.put(propertyName, value);
        return;
      }
      String capitalize = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
      Method method = null;
      try {
        method = object.getClass().getMethod("get" + capitalize, emptyArray);
      } catch (NoSuchMethodException e) {
        method = object.getClass().getMethod("is" + capitalize, emptyArray);
      }
      if (method == null) {
        throw new Exception("can not find method: get" + capitalize);
      }
      Class type = method.getReturnType();
      method = object.getClass().getMethod("set" + capitalize, new Class[] { type });
      if (method == null) {
        throw new Exception("can not find method: set" + capitalize);
      }

      method.invoke(object, new Object[] { value });
    } else {
      String[] keys = propertyName.split("\\.");
      String key1 = keys[0];
      String capitalize = key1.substring(0, 1).toUpperCase() + key1.substring(1);
      Object subObject = getObjectBeanValue(object, capitalize);
      if (subObject == null) {
        Method method = null;
        try {
          method = object.getClass().getMethod("get" + capitalize, emptyArray);
        } catch (NoSuchMethodException e) {
          method = object.getClass().getMethod("is" + capitalize, emptyArray);
        }
        if (method == null) {
          throw new Exception("can not find method: get" + capitalize);
        }
        Class type = method.getReturnType();
        method = object.getClass().getMethod("set" + capitalize, new Class[] { type });
        if (method == null) {
          throw new Exception("can not find method: set" + capitalize);
        }

        subObject = type.newInstance();
        method.invoke(object, new Object[] { subObject });
      }
      setBeanValue(subObject, propertyName.substring(key1.length() + 1), value);
    }
  }

  public static Map<String, Object> getAllStaticFieldValueMap(String className) throws Exception {
    return getAllStaticFieldValueMap(Class.forName(className));
  }

  public static Map<String, Object> getAllStaticFieldValueMap(Class c) throws Exception {
    if (c == null) {
      return new HashMap();
    }
    Field[] fields = c.getDeclaredFields();
    Map result = new LinkedHashMap();
    for (int i = 0; i < fields.length; i++) {
      if ((fields[i].getModifiers() & 0x8) != 0) {
        fields[i].setAccessible(true);
        if (result.get(fields[i].getName()) == null) {
          result.put(fields[i].getName(), fields[i].get(null));
        }
      }
    }
    return result;
  }

  public static List<String> getAllDeclaredFields(Class c) {
    if (c == null) {
      return new ArrayList();
    }
    Field[] fields = c.getDeclaredFields();
    ArrayList list = new ArrayList();
    for (int i = 0; i < fields.length; i++) {
      fields[i].setAccessible(true);
      list.add(fields[i].getName());
    }
    return list;
  }
}
