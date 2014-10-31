package com.toolkit2.client;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * 		应用全局缓存管理器
 * 
 * **/
public class CacheManager {
	@SuppressWarnings("rawtypes")
	private static Map<Class, String> systemCaches = new Hashtable();

	@SuppressWarnings("rawtypes")
	public static void registerCache(Class cacheClass, String methodName) {
		if ((cacheClass != null) && (methodName != null))
			synchronized (systemCaches) {
				systemCaches.put(cacheClass, methodName);
			}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void clearAllCaches() {
		synchronized (systemCaches) {
			Iterator it = systemCaches.keySet().iterator();
			while (it.hasNext()) {
				Class clazz = (Class) it.next();
				if (clazz != null) {
					String methodName = (String) systemCaches.get(clazz);
					try {
						Method method = clazz.getMethod(methodName,
								new Class[0]);
						if (method != null)
							method.invoke(null, new Object[0]);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public static void clear() {
		clearAllCaches();
	}
}
