package com.toolkit2.client.frame.mian;

import java.util.TimeZone;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.CommonUI;
import com.toolkit2.client.i18n.Translator;

public class ClientInitializer {
	public static void initToolKit2(boolean defaultInit) throws Exception {
		CommonUI.showSpalshWindow(Translator
				.getString("ClientInitializer.ConnectingToServer"));
		Thread.sleep(1000);
		CommonUI.showSpalshWindow(Translator
				.getString("ClientInitializer.Gettingcompanyinformation"));
		Thread.sleep(1000);
		// 设置默认时区
		TimeZone.setDefault(ClientUtil.getServerTimeZone());
		CommonUI.showSpalshWindow(Translator
				.getString("ClientInitializer.Getservertime"));

		// 默认启动检查项
		if (defaultInit) {

		}
	}
}
