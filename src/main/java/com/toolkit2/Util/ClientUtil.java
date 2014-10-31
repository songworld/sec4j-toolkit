package com.toolkit2.Util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import twaver.DataBoxJarReader;
import twaver.DataBoxJarWriter;
import twaver.TDataBox;

import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.component2ex.QuickTable;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.component2ex.CommonUI;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.images.ResourceAgent;
import com.toolkit2.client.vo.RefdocVO;

/*
 * 		本地的基本配置信息
 * 
 * */
public class ClientUtil {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat
			.getNumberInstance(ClientContext.getLocale());
	private static TimeZone TIME_ZONE = null;
	public static final SimpleDateFormat DATE_FORMAT_DATE_AND_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat DATE_FORMAT_MONTH_DATE_YEAR = new SimpleDateFormat(
			"MM/dd/yyyy");
	private static final SimpleDateFormat DATE_FORMAT__MONTH_DATE_HOUR_MINUTE = new SimpleDateFormat(
			"MM-dd HH:mm");
	private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat(
			"HH:mm:ss");
	private static final SimpleDateFormat DATE_FORMAT_YEAR_AND_MONTH = new SimpleDateFormat(
			"yyyy-MM");
	private static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat(
			"yyyy");

	private static final Map<String, ImageIcon> IMAGE_ICON_CACHE = new Hashtable();

	/*****************************************************************************************************
	 * 判断返回当前是否为本地语言是否为中国
	 * 
	 * */
	public static boolean isChineseLocale() {
		return isChineseLocale(ClientContext.getLocale());
	}

	public static boolean isChineseLocale(Locale locale) {
		if (locale != null) {
			String language = locale.toString();
			return language.equalsIgnoreCase(ClientConst.CHINESE_LOCALE
					.toString());
		}
		return false;
	}

	/***************************************************************************************************
	 * 获取本地配置信息
	 * 
	 * ***/
	public static ArrayList<Locale> getLocaleConf() {
		ArrayList locales = new ArrayList();
		try {
			String file = "locales.conf";
			InputStream input = ClientUtil.class.getResourceAsStream(file);
			if (input == null) {
				Locale local = new Locale("en", "US");
				locales.add(local);
				local = new Locale("zh", "CN");
				locales.add(local);
			} else {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
				String line = reader.readLine();
				while (line != null) {
					line = line.trim();
					if ((!line.isEmpty()) && (!line.startsWith("#"))) {
						String key = line;
						String value = "";
						if (line.contains("=")) {
							int index = line.indexOf("=");
							key = line.substring(0, index);
							value = line.substring(index + 1);
						}
						key = key.trim();
						value = value.trim();
						if (key.equalsIgnoreCase("locale")) {
							if (!value.contains("_")) {
								throw new RuntimeException(new StringBuilder()
										.append("Invalid language config: ")
										.append(line).toString());
							}
							String language = value.substring(0,
									value.indexOf("_"));
							String country = value
									.substring(value.indexOf("_") + 1);
							Locale locale = new Locale(language, country);
							locales.add(locale);
						}
					}
					line = reader.readLine();
				}
			}
		} catch (IOException ex) {
			showException(ex);
		}
		return locales;
	}

	/**********************************************************************************************
	 * CS端框架的异常统一处理 默认直接抛出异常
	 * **/
	public static void showException(Exception e) {
		// if (AbstractMainUI.isInstanced()) {
		// showException(AbstractMainUI.getInstance(), e);
		// } else {
		// JFrame loginUI = LoginUI.getInstance();
		// if ((loginUI != null) && (loginUI.isShowing()))
		// showException(loginUI, e);
		// else if ((WizardUI.getInstance() != null) &&
		// (WizardUI.getInstance().isShowing()))
		// showException(WizardUI.getInstance(), e);
		// else
		showException(null, e);
		// }
	}

	public static void showException(Component parent, Exception e) {
		if ((e != null)
				&& (e.getMessage() != null)
				&& (e.getMessage().contains("java.io.StreamCorruptedException"))) {
			showException(
					parent,
					new Exception(GeneralUtil
							.getString("General.StartServerFailure")));
			return;
		}
		CommonUI.showException(parent, e);
	}

	/*********************************************************************************************
	 * 当前语言转化成字符串输出
	 * */
	public static String getLanguageCodeByLocale(Locale locale) {
		return locale.toString();
	}

	/***********************************************************************************************
	 * 字符串转Unicode输出
	 * 
	 * */
	public static String toUnicode(String translationValue) {
		String result = "";
		for (int i = 0; i < translationValue.length(); i++) {
			char c = translationValue.charAt(i);
			if (c < '?')
				result = new StringBuilder().append(result).append(c)
						.toString();
			else {
				result = new StringBuilder().append(result).append("\\u")
						.append(Integer.toHexString(c)).toString();
			}
		}
		return result;
	}

	public static Border createTitleBorder(String title, Insets insets) {
		Border etchedBorder = BorderFactory.createEtchedBorder(Color.white,
				new Color(164, 163, 165));
		Border titledBorder = null;

		titledBorder = new TitledBorder(etchedBorder, title, 0, 0,
				FreeUtil.getFont13Bold(), new Color(134, 137, 164));

		Border emptyBorder = null;
		if (insets == null)
			emptyBorder = BorderFactory.createEmptyBorder(-5, 10, 0, 10);
		else {
			emptyBorder = BorderFactory.createEmptyBorder(insets.top,
					insets.left, insets.bottom, insets.right);
		}
		return BorderFactory.createCompoundBorder(titledBorder, emptyBorder);
	}

	public static Border createTitleBorder(String title) {
		return createTitleBorder(title, null);
	}

	public static Border createEmptyBorder() {
		return BorderFactory.createEmptyBorder(10, 10, 0, 10);
	}

	public static Border createGeneralTitleBorder() {
		return createTitleBorder(
				Translator.getString("ClientUtil.GeneralInformation"), null);
	}

	/******************************************************************************************
	 * 字符串替换
	 * **/
	public static String replaceString(String text, String find,
			String replacement) {
		if (text == null) {
			return null;
		}
		if (find == null) {
			return text;
		}
		String result = "";
		StringTokenizer tokenizer = new StringTokenizer(text, find);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			result = new StringBuilder().append(result).append(token)
					.toString();
			if ((replacement != null) && (tokenizer.hasMoreTokens())) {
				result = new StringBuilder().append(result).append(replacement)
						.toString();
			}
		}
		return result;
	}

	/*****************************************************************************************
	 * 组件中获取窗口
	 * 
	 * **/
	public static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException {
		if (parentComponent == null) {
			return null;
		}
		while (!(parentComponent instanceof Window)) {
			if (parentComponent == null) {
				return null;
			}
			parentComponent = parentComponent.getParent();
		}
		return (Window) parentComponent;
	}

	/******************************************************************************************
	 * 高亮显示组件
	 * 
	 * ***/
	public static void setHighlightComponent(JComponent com) {
		com.putClientProperty("highlight", "true");
	}

	public static String getNumberStringWithoutEndZeroBySwitch(
			String numberString) {
		return getNumberStringWithoutEndZeroBySwitch(numberString, false);
	}

	public static String getNumberStringWithoutEndZeroBySwitch(
			String numberString, boolean aboutCost) {
		boolean isShowNumberTailZero = GeneralUtil.isShowNumberTailZero();
		if ((!isShowNumberTailZero) && (!aboutCost))
			numberString = getNumberStringWithoutEndZero(numberString);
		else if ((!isShowNumberTailZero) && (aboutCost)) {
			numberString = getNumberStringWithTwoZero(numberString);
		}
		return numberString;
	}

	/**************************************************************************************************
	 * 字符串转换为数字
	 * 
	 * **/
	public static String getStringNumber(String str) {
		if (str == null) {
			return "";
		}
		if (str.startsWith(".")) {
			return str.trim().replaceFirst("\\.", "0.");
		}
		return getNumberStringWithoutEndZeroBySwitch(str.trim().replaceAll(",",
				""));
	}

	public static String getStringNumber(String str, int length) {
		if (str == null) {
			return "";
		}
		if (str.startsWith(".")) {
			return new BigDecimal(str.trim().replaceFirst("\\.", "0."))
					.setScale(length, 4).toString();
		}
		return getNumberStringWithoutEndZeroBySwitch(new BigDecimal(str.trim()
				.replaceAll(",", "")).setScale(length, 4).toString());
	}

	public static String getNumberStringWithTwoZero(Number number) {
		return getNumberStringWithTwoZero(getNumberString(number));
	}

	public static String getNumberStringWithTwoZero(String numberString) {
		String[] str = numberString.split("\\.");
		String pointAfter = "";
		if (str.length == 2) {
			pointAfter = str[1];
		}
		while ((pointAfter.length() > 0) && (pointAfter.endsWith("0"))) {
			pointAfter = pointAfter.substring(0, pointAfter.length() - 1);
		}
		int length = pointAfter.length();
		if (length == 0) {
			pointAfter = ".00";
		} else if ((length > 0) && (length < 2)) {
			while (length < 2) {
				pointAfter = new StringBuilder().append(pointAfter).append("0")
						.toString();
				length++;
			}
			pointAfter = new StringBuilder().append(".").append(pointAfter)
					.toString();
		} else {
			pointAfter = new StringBuilder().append(".").append(pointAfter)
					.toString();
		}
		numberString = new StringBuilder().append(str[0]).append(pointAfter)
				.toString();
		return numberString;
	}

	public static String getNumberString(Number number) {
		return getNumberString(number, 4);
	}

	public static String getNumberString(Number number, int digit) {
		NUMBER_FORMAT.setMaximumFractionDigits(digit);
		NUMBER_FORMAT.setMinimumFractionDigits(digit);
		if (number == null) {
			return getNumberStringWithoutEndZeroBySwitch(NUMBER_FORMAT
					.format(0L));
		}
		if (((number instanceof BigDecimal)) || ((number instanceof Double))
				|| ((number instanceof Float))) {
			return getNumberStringWithoutEndZeroBySwitch(NUMBER_FORMAT
					.format(number));
		}
		NUMBER_FORMAT.setMaximumFractionDigits(0);
		NUMBER_FORMAT.setMinimumFractionDigits(0);
		return getNumberStringWithoutEndZeroBySwitch(NUMBER_FORMAT
				.format(number));
	}

	public static String getNumberStringWithoutEndZero(Number number) {
		return getNumberStringWithoutEndZero(getNumberString(number));
	}

	public static String getNumberStringWithoutEndZero(String numberString) {
		while ((numberString.split("\\.").length == 2)
				&& (numberString.endsWith("0"))) {
			numberString = numberString.substring(0, numberString.length() - 1);
		}
		if (numberString.endsWith(".")) {
			numberString = numberString.replace(".", "");
		}
		return numberString;
	}

	/************************************************************************************************
	 * 获取字符串
	 * **/
	public static String getStringWithoutNull(String str) {
		if (str == null) {
			return "";
		}
		String result = str
				.toString()
				.replaceAll("<br>", "\n")
				.replaceAll(
						"<html>|<center>|</center>|<b>|</b>|<p>|</p>|<br>|<u>|</u>|<div align=right>|</div>|<font[^>]*>|<p align=right>|</font>|</html>|&NBSP;|&nbsp;",
						"");

		if (result.equalsIgnoreCase("null")) {
			return "";
		}
		return result;
	}

	/****************************************************************************************************
	 * 获取本地时间字符串
	 * ****/
	public static String getDateLocalString(Date date) {
		if (date == null) {
			return "";
		}
		DATE_FORMAT_DATE.setTimeZone(getServerTimeZone());
		String result = DATE_FORMAT_DATE.format(date);
		return result;
	}

	public static String getTimeLocalString(Date date) {
		boolean showTimeZone = false;
		return getTimeLocalString(date, showTimeZone);
	}

	// public static String getChineseFormatDateString(Date date) {
	// Calendar c = Calendar.getInstance();
	// c.setTime(date);
	// return new
	// StringBuilder().append(c.get(1)).append(CompanySettingsUtil.getString("AbstractBarChart.YEAR")).append(c.get(2)
	// < 9 ? new StringBuilder().append("0").append(c.get(2) + 1).toString() :
	// Integer.valueOf(c.get(2) +
	// 1)).append(AccountingUtil.getString("AccountingInfoDocumentListUI.yearCode")).append(c.get(5)
	// < 10 ? new StringBuilder().append("0").append(c.get(5)).toString() :
	// Integer.valueOf(c.get(5))).append(CompanySettingsUtil.getString("TaskEditPane.Date")).toString();
	// }
	//
	// public static String getChineseFormatDateStringWithoutDay(Date date)
	// {
	// if (date == null) {
	// return "";
	// }
	// Calendar c = Calendar.getInstance();
	// c.setTime(date);
	// return new
	// StringBuilder().append(c.get(1)).append(CompanySettingsUtil.getString("AbstractBarChart.YEAR")).append(c.get(2)
	// < 9 ? new StringBuilder().append("0").append(c.get(2) + 1).toString() :
	// Integer.valueOf(c.get(2) +
	// 1)).append(AccountingUtil.getString("AccountingInfoDocumentListUI.yearCode")).toString();
	// }

	public static String getYearLocalString(Date date) {
		if (date == null) {
			return "";
		}
		DATE_FORMAT_YEAR.setTimeZone(getServerTimeZone());
		String result = DATE_FORMAT_YEAR.format(date);
		return result;
	}

	public static String getTimeLocalString(Date date, boolean timezone) {
		if (date == null) {
			return "";
		}
		DATE_FORMAT_DATE_AND_TIME.setTimeZone(getServerTimeZone());
		String result = DATE_FORMAT_DATE_AND_TIME.format(date);
		if (timezone) {
			result = new StringBuilder().append(result).append(" ")
					.append(getTimeZoneString(getServerTimeZone(), true))
					.toString();
		}
		return result;
	}

	public static String getMonthDateHourMinuteString(Date date) {
		if (date == null) {
			return "";
		}
		DATE_FORMAT__MONTH_DATE_HOUR_MINUTE.setTimeZone(getServerTimeZone());
		return DATE_FORMAT__MONTH_DATE_HOUR_MINUTE.format(date);
	}

	public static String getTimeOnlyLocalString(Date date) {
		if (date == null) {
			return "";
		}
		DATE_FORMAT_TIME.setTimeZone(getServerTimeZone());
		String result = DATE_FORMAT_TIME.format(date);
		return result;
	}

	/**********************************************************************************************
	 * 获取服务器时区 暂时直接返回本地时区
	 * 
	 * **/
	public static TimeZone getServerTimeZone() {
		// try {
		// if (TIME_ZONE == null) {
		// TIME_ZONE = FacadeWrapper.getInstance().getServerTimeZone();
		// }
		// return TIME_ZONE;
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		return TimeZone.getDefault();
	}

	public static String getTimeZoneString(TimeZone timeZone, boolean shortStyle) {
		int rawOffset = timeZone.getRawOffset() / 60000;
		int hours = rawOffset / 60;
		int minutes = Math.abs(rawOffset) % 60;
		String hrStr = "";
		if (Math.abs(hours) < 10) {
			if (hours < 0)
				hrStr = new StringBuilder().append("-0")
						.append(Math.abs(hours)).toString();
			else
				hrStr = new StringBuilder().append("0").append(Math.abs(hours))
						.toString();
		} else {
			hrStr = Integer.toString(hours);
		}
		String minStr = minutes < 10 ? new StringBuilder().append("0")
				.append(Integer.toString(minutes)).toString() : Integer
				.toString(minutes);
		if (shortStyle) {
			return new StringBuilder().append("GMT")
					.append(timeZone.getRawOffset() >= 0 ? "+" : "")
					.append(hrStr).append(":").append(minStr).toString();
		}
		return new StringBuilder().append("(GMT ")
				.append(timeZone.getRawOffset() >= 0 ? "+" : "").append(hrStr)
				.append(":").append(minStr).append(") ")
				.append(timeZone.getID()).append(", ")
				.append(timeZone.getDisplayName(ClientContext.getLocale()))
				.toString();
	}

	/******************************************************************************************
	 * 获取字符串从HTML中
	 * **/
	public static String getStringFromHtml(String string) {
		return getStringWithoutNull(string);
	}

	public static SubForm getClientUIForComponent(Component parentComponent)
			throws HeadlessException {
		if (parentComponent == null) {
			return null;
		}
		while (!(parentComponent instanceof Component)) {
			if (parentComponent == null) {
				return null;
			}
			parentComponent = parentComponent.getParent();
		}
		return (SubForm) parentComponent;
	}

	public static void addStringPopupMenu(JTextComponent text) {
		WindowUtil.addStringPopupMenu(text);
	}

	public static void setupWindow(Component component) {
		WindowUtil.setupWindow(component);
	}

	public static boolean isNullObject(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof Collection)) {
			return ((Collection) obj).isEmpty();
		}
		String text = obj.toString();
		if ((text.equalsIgnoreCase(""))
				|| (text.toLowerCase().equalsIgnoreCase("null"))
				|| (text.toLowerCase().equalsIgnoreCase("&nbsp;"))) {
			return true;
		}
		return false;
	}

	public static boolean isNullObject(Object[] objs) {
		if (objs == null) {
			return true;
		}
		if (objs.length == 0) {
			return true;
		}
		for (Object obj : objs) {
			if (isNullObject(obj)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotNullString(String text) {
		return (text != null) && (!"".equals(text));
	}

	public static Properties loadClientProperties() {
		Properties result = new Properties();
		File file = new File(ClientConst.getClientPropertyFile());
		if (file.exists()) {
			try {
				FileInputStream is = new FileInputStream(file);
				result.load(is);
				is.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String loadClientProperty(String key) {
		return loadClientProperties().getProperty(key);
	}

	public static String getClientProperty(String key) {
		return loadClientProperty(key);
	}

	public static String readClientProperty(String key) {
		return loadClientProperty(key);
	}

	public static void saveClientProperties(Properties p) {
		if ((p == null) || (p.size() == 0)) {
			return;
		}
		File file = new File(ClientConst.getClientPropertyFile());
		try {
			if ((!isNullObject(ClientContext.getWebstart()))
					&& (!file.getParentFile().getParentFile().exists())) {
				file.getParentFile().getParentFile().mkdir();
			}

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			OutputStream out = new FileOutputStream(file, false);
			p.store(out, "******* ToolKit2 Client Property File *******");
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void saveClientProperty(String key, String value) {
		Properties p = loadClientProperties();
		p.setProperty(key, value);
		saveClientProperties(p);
	}

	public static void setupEnterKey(JComponent component,
			final JButton enterButton) {
		String ENTER_ACTION_KEY = "ENTER_ACTION_KEY";
		KeyStroke enterKey = KeyStroke.getKeyStroke(10, 0, false);
		InputMap inputMap = component.getInputMap(1);
		inputMap.put(enterKey, ENTER_ACTION_KEY);
		AbstractAction enterAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				enterButton.doClick();
			}
		};
		component.getActionMap().put(ENTER_ACTION_KEY, enterAction);
	}

	public static void setupCancelKey(Window win, final JButton cancelButton) {
		if (cancelButton == null) {
			return;
		}
		JRootPane rootPane = null;
		if ((win instanceof JFrame)) {
			rootPane = ((JFrame) win).getRootPane();
		}
		if ((win instanceof JDialog)) {
			rootPane = ((JDialog) win).getRootPane();
		}
		if ((win instanceof JWindow)) {
			rootPane = ((JWindow) win).getRootPane();
		}
		if (rootPane == null) {
			return;
		}
		String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
		int noModifiers = 0;
		KeyStroke escapeKey = KeyStroke.getKeyStroke(27, noModifiers, false);
		InputMap inputMap = rootPane.getInputMap(1);
		inputMap.put(escapeKey, CANCEL_ACTION_KEY);
		AbstractAction cancelAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cancelButton.doClick();
			}
		};
		rootPane.getActionMap().put(CANCEL_ACTION_KEY, cancelAction);
	}

	public static void setupRefreshKey(Window win, final JButton refreshButton) {
		JRootPane rootPane = null;
		if (refreshButton == null) {
			return;
		}
		if ((win instanceof JFrame)) {
			rootPane = ((JFrame) win).getRootPane();
		}
		if ((win instanceof JDialog)) {
			rootPane = ((JDialog) win).getRootPane();
		}
		if ((win instanceof JWindow)) {
			rootPane = ((JWindow) win).getRootPane();
		}
		if (rootPane == null) {
			return;
		}
		String REFRESH_ACTION_KEY = "REFRESh_ACTION_KEY";
		int noModifiers = 0;
		KeyStroke key = KeyStroke.getKeyStroke(116, noModifiers, false);
		InputMap inputMap = rootPane.getInputMap(1);
		inputMap.put(key, REFRESH_ACTION_KEY);
		AbstractAction action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (refreshButton != null)
					refreshButton.doClick();
			}
		};
		rootPane.getActionMap().put(REFRESH_ACTION_KEY, action);
	}

	public static void setPreferredWidth(final Component c,
			final int preferredWidth) {
		if (c == null) {
			return;
		}
		if (c.getPreferredSize() == null) {
			return;
		}
		if (SwingUtilities.isEventDispatchThread())
			c.setPreferredSize(new Dimension(preferredWidth, c
					.getPreferredSize().height));
		else
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					c.setPreferredSize(new Dimension(preferredWidth, c
							.getPreferredSize().height));
				}
			});
	}

	public static void setPreferredHeight(final Component c,
			final int preferredHeight) {
		if (c == null) {
			return;
		}
		if (c.getPreferredSize() == null) {
			return;
		}
		if (SwingUtilities.isEventDispatchThread())
			c.setPreferredSize(new Dimension(c.getPreferredSize().width,
					preferredHeight));
		else
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if ((c != null) && (c.getPreferredSize() != null))
						c.setPreferredSize(new Dimension(
								c.getPreferredSize().width, preferredHeight));
				}
			});
	}

	/***************************************************************************************
	 * 设置组件不透明物
	 * 
	 * *****/
	public static void setOpaque(JComponent componet, boolean isOpaque) {
		componet.setOpaque(isOpaque);
		componet.putClientProperty("opaque", Boolean.valueOf(isOpaque));
		int count = componet.getComponentCount();
		if (count != 0)
			for (int i = 0; i < count; i++) {
				Component comp = componet.getComponent(i);
				if (!(comp instanceof JButton)) {
					if (!(comp instanceof JTextField)) {
						if ((comp instanceof JComponent)) {
							setOpaque((JComponent) comp, isOpaque);
						}
					}
				}
			}
	}

	/***************************************************************************************
	 * 获取图标
	 * 
	 * *****/
	public static Icon getIcon(String filename) {
		return getImageIcon(filename);
	}

	public static String getIconPath(String imageName) {
		return new StringBuilder().append("/com/toolkit2/client/images/")
				.append(imageName).toString();
	}

	public static ImageIcon getImageIcon(String url) {
		if (url == null) {
			return null;
		}
		if (IMAGE_ICON_CACHE.get(url) == null) {
			ImageIcon image = null;
			InputStream in = ResourceAgent.getInstance().getClass()
					.getResourceAsStream(url);
			if (in != null) {
				try {
					byte[] buffer = new byte[in.available()];
					int i = 0;
					for (int n = in.available(); i < n; i++) {
						buffer[i] = ((byte) in.read());
					}
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image img = toolkit.createImage(buffer);
					image = new ImageIcon(img);
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					return null;
				}
			}
			if (image == null) {
				if (ClassLoader.getSystemResource(url) != null)
					image = new ImageIcon(ClassLoader.getSystemResource(url));
				else {
					image = new ImageIcon(url);
				}
			}
			if (image == null)
				System.err.println(new StringBuilder()
						.append("can't load image '").append(url).append("'")
						.toString());
			else {
				IMAGE_ICON_CACHE.put(url, image);
			}
		}
		return (ImageIcon) IMAGE_ICON_CACHE.get(url);
	}

	/***************************************************************************************
	 * 获得Class名字
	 * 
	 * ****/
	public static String getClassNameWithoutPackage(Object object) {
		if (object == null)
			return null;
		String name;
		if ((object instanceof Class))
			name = ((Class) object).getName();
		else {
			name = object.getClass().getName();
		}
		return name.substring(name.lastIndexOf(".") + 1);
	}

	public static void setupDetailUICustomPane(SubForm clientUI) {
		// TODO Auto-generated method stub

	}

	public static boolean getPopupMainTreeOnMouseOver() {
		String value = readClientProperty(FreeUtil.POPUP_MAINTREE_ON_MOUSEOVER);
		if ((value == null) || (value.trim().isEmpty())) {
			return true;
		}
		return Boolean.valueOf(value).booleanValue();
	}

	/************************************************************************************
	 * 控制滚动条滚动 ==》 滚动条滚动到顶端
	 * ***/
	public static void scrollOnTop(final JScrollPane scroll) {
		if (scroll != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scroll.getViewport().setViewPosition(new Point(0, 0));
				}
			});
	}

	/************************************************************************************
	 * 控制滚动条滚动 ==》 滚动条滚动到底端
	 * ***/
	public static void scrollToBottom(final JScrollPane scroll) {
		if ((scroll != null) && (scroll.getVerticalScrollBar().isVisible()))
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scroll.getViewport().setViewPosition(
							new Point(0, 2147483647));
				}
			});
	}

	/************************************************************************************
	 * 字符串替换
	 * ***/
	public static String replaceStringNew(String source, String from, String to) {
		if ((from == null) || (from.equals(""))) {
			return source;
		}
		String strDest = "";
		int intFromLen = from.length();
		int intPos;
		while ((intPos = source.indexOf(from)) != -1) {
			strDest = new StringBuilder().append(strDest)
					.append(source.substring(0, intPos)).toString();
			strDest = new StringBuilder().append(strDest).append(to).toString();
			source = source.substring(intPos + intFromLen);
		}
		strDest = new StringBuilder().append(strDest).append(source).toString();
		return strDest;
	}

	public static byte[] getDataBoxJarData(TDataBox box) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataBoxJarWriter writer = new DataBoxJarWriter(out);
		writer.write(box);
		out.close();
		return out.toByteArray();
	}

	public static void setDataBoxByJarData(TDataBox box, byte[] data)
			throws IOException {
		if (data == null) {
			return;
		}
		File tempFile = File.createTempFile("twaver", "");
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(data);

		box.clear();
		String filename = tempFile.getAbsolutePath();

		if (filename.startsWith("/")) {
			filename = new StringBuilder().append("/").append(filename)
					.toString();
		}
		String jarURLString = new StringBuilder().append("jar:file:/")
				.append(filename).append("!/").toString();
		URL url = new URL(jarURLString);
		JarURLConnection jarConnection = (JarURLConnection) url
				.openConnection();
		DataBoxJarReader reader = new DataBoxJarReader(jarConnection);
		reader.read(box);
	}

	
	public static Collection getRefDocs(HashSet refDocOnes, boolean isRefDocOne) {
	    Vector result = new Vector();
	    if ((refDocOnes != null) && (refDocOnes.size() != 0)) {
	      Iterator it = refDocOnes.iterator();
	      while (it.hasNext()) {
	        RefdocVO item = (RefdocVO)it.next();
	        if (item != null)
	        {
	          result.addElement(getRefDocString(item, isRefDocOne));
	        }
	      }
	    }
	    return result;
	  }

	  public static String getRefDocString(RefdocVO item, boolean isRefDocOne) {
	    if (isRefDocOne) {
	      if (item.getFitemNumber().isEmpty()) {
	        return new StringBuilder().append(item.getForderNumber()).append(":").append(getNumberStringWithoutEndZero(item.getQty())).toString();
	      }
	      return new StringBuilder().append(item.getForderNumber()).append(".").append(item.getFitemNumber()).append(" :").append(getNumberStringWithoutEndZero(item.getQty())).toString();
	    }
	    return new StringBuilder().append(item.getTorderNumber()).append(".").append(item.getTitemNumber()).append(" :").append(getNumberStringWithoutEndZero(item.getQty())).toString();
	  }

	 
	  public static boolean isDoubleDigit(String property, String value, Component parent, boolean negative) {
		    if ((value == null) || (value.equals(""))) {
		      CommonUI.showMessage(parent, Translator.getGeneralString("CommonUI.Required", StringUtil.removeLastColon(property)));
		      return false;
		    }
		    if (!value.replaceAll(",", "").matches(new StringBuilder().append("^").append(negative ? "(-)?" : "").append("[0-9]*(\\.[0-9]*)?$").toString())) {
		      CommonUI.showMessage(parent, new StringBuilder().append(Translator.getString("ClientUtil.InvalidEntry")).append(" ").append(StringUtil.removeLastColon(property)).toString());
		      return false;
		    }
		    return true;
		  }

		  public static boolean isDoubleDigit(String value, boolean negative) {
		    if ((value == null) || (value.equals(""))) {
		      return false;
		    }
		    if (!value.replaceAll(",", "").matches(new StringBuilder().append("^").append(negative ? "(-)?" : "").append("[0-9]*(\\.[0-9]*)?$").toString())) {
		      return false;
		    }
		    return true;
		  }

		  public static boolean isDoubleDigit(String property, JTextField value, Component parent) {
		    return isDoubleDigit(property, value, parent, false, true);
		  }

		  public static boolean isDoubleDigit(String property, JTextField value, Component parent, boolean allowNegative) {
		    return isDoubleDigit(property, value, parent, allowNegative, true);
		  }

		  public static boolean isDoubleDigit(String property, JTextField value, Component parent, boolean allowNegative, boolean allowZero) {
		    return isDoubleDigit(property, value, parent, allowNegative, allowZero, BigDecimal.ZERO, BigDecimal.ZERO);
		  }

		  public static boolean isDoubleDigit(String property, String value, Component parent, boolean allowNegative, boolean allowZero, BigDecimal small, BigDecimal big, boolean needNote) {
		    if ((value == null) || (value.equals(""))) {
		      if (needNote) {
		        CommonUI.showMessage(parent, Translator.getGeneralString("CommonUI.Required", StringUtil.removeLastColon(property)));
		      }
		      return false;
		    }
		    if ((!value.replaceAll(",", "").matches(new StringBuilder().append("^").append(allowNegative ? "(-)?" : "").append("[0-9]*(\\.[0-9]*)?$").toString())) || ((!allowZero) && (BigDecimal.ZERO.compareTo(new BigDecimal(value.replaceAll(",", "").trim())) == 0)))
		    {
		      if (needNote) {
		        CommonUI.showMessage(parent, new StringBuilder().append(Translator.getString("ClientUtil.InvalidEntry")).append(" ").append(StringUtil.removeLastColon(property)).toString());
		      }
		      return false;
		    }
		    if (small.compareTo(big) < 0) {
		      BigDecimal nowValue = new BigDecimal(value.replaceAll(",", "").trim());
		      if ((nowValue.compareTo(small) < 0) || (nowValue.compareTo(big) > 0)) {
		        if (needNote) {
		          CommonUI.showMessage(parent, new StringBuilder().append(Translator.getString("ClientUtil.InvalidEntry")).append(" ").append(StringUtil.removeLastColon(property)).toString());
		        }
		        return false;
		      }
		    }
		    return true;
		  }

		  public static boolean isDoubleDigit(String property, JTextField value, Component parent, boolean allowNegative, boolean allowZero, BigDecimal small, BigDecimal big) {
		    if ((value.getText() == null) || (value.getText().equals(""))) {
		      value.requestFocus();
		      CommonUI.showMessage(parent, Translator.getGeneralString("CommonUI.Required", StringUtil.removeLastColon(property)));
		      WindowUtil.setComponentVisableInScrollPane(value);
		      return false;
		    }
		    if ((!value.getText().replaceAll(",", "").matches(new StringBuilder().append("^").append(allowNegative ? "(-)?" : "").append("[0-9]*(\\.[0-9]*)?$").toString())) || ((!allowZero) && (BigDecimal.ZERO.compareTo(new BigDecimal(value.getText().replaceAll(",", "").trim())) == 0)))
		    {
		      CommonUI.showMessage(parent, new StringBuilder().append(Translator.getString("ClientUtil.InvalidEntry")).append(" ").append(StringUtil.removeLastColon(property)).toString());
		      WindowUtil.setComponentVisableInScrollPane(value);
		      value.requestFocus();
		      return false;
		    }
		    if (small.compareTo(big) < 0) {
		      BigDecimal nowValue = new BigDecimal(value.getText().replaceAll(",", "").trim());
		      if ((nowValue.compareTo(small) < 0) || (nowValue.compareTo(big) > 0)) {
		        value.requestFocus();
		        CommonUI.showMessage(parent, new StringBuilder().append(Translator.getString("ClientUtil.InvalidEntry")).append(" ").append(StringUtil.removeLastColon(property)).toString());
		        WindowUtil.setComponentVisableInScrollPane(value);
		        return false;
		      }
		    }
		    return true;
		  }
	  
	  
	  /*********************************************************************************
	   * 获取四舍五入日期
	   * **/
	  public static Date getRoundDate(Date date) {
		    if (date != null) {
		      Calendar c = Calendar.getInstance();
		      c.setTime(date);
		      c.set(11, 0);
		      c.set(12, 0);
		      c.set(13, 0);
		      c.set(14, 0);
		      return c.getTime();
		    }
		    return null;
		  }

	  public static String getStringFromObject(Object obj)
	  {
	    if (obj == null)
	      return "";
	    if ((obj instanceof Boolean))
	      return ((Boolean)obj).booleanValue() ? "Y" : "";
	    if ((obj instanceof Date))
	      return getDateLocalString((Date)obj);
	    if ((obj instanceof Number))
	      return getNumberString((Number)obj);
	    if ((obj instanceof Collection)) {
	      Collection col = (Collection)obj;
	      Iterator it = col.iterator();
	      String result = "";
	      while (it.hasNext()) {
	        Object o = it.next();
	        if (o != null) {
	          result = new StringBuilder().append(result).append(getStringFromObject(o)).toString();
	        }
	        if (it.hasNext()) {
	          result = new StringBuilder().append(result).append("\n").toString();
	        }
	      }
	      return result;
	    }
	    return getStringWithoutNull(obj.toString());
	  }

	  public static void addCopyTextPopupMenuToTable(final JTable table) {
		    table.addMouseListener(new MouseAdapter()
		    {
		      public void mouseReleased(MouseEvent e) {
		        if (e.getButton() == 3)
		        {
		          int row = table.rowAtPoint(new Point(e.getX(), e.getY()));
		          int col = table.columnAtPoint(new Point(e.getX(), e.getY()));
		          if (row >= 0)
		            table.setRowSelectionInterval(row, row);
		          else {
		            return;
		          }
		          Component component = table.getCellRenderer(row, col).getTableCellRendererComponent(table, table.getValueAt(row, col), false, false, row, col);
		          String text = "";
		          Object obj = table.getValueAt(row, col);
		         
		            text = ClientUtil.getStringFromObject(table.getValueAt(row, col));
		          
		          if ((obj instanceof Collection)) {
		            JLabel c = (JLabel)component;
		            text = c.getText();
		          }
		          if (ClientUtil.isDoubleDigit(text, true)) {
		            text = ClientUtil.getStringNumber(text);
		          }
		          JPopupMenu popupMenu = null;
		          boolean newPopupMenu = false;
		          
		          if (popupMenu == null) {
		            popupMenu = new JPopupMenu();
		            newPopupMenu = true;
		          }
		          JMenuItem copyButton = new JMenuItem(Translator.getString("ClientUtil.Copy"));
		          popupMenu.insert(copyButton, 0);
		          if (!newPopupMenu) {
		            popupMenu.insert(new JPopupMenu.Separator(), 1);
		          }
		          copyButton.setEnabled(false);
		          if (!text.equalsIgnoreCase("")) {
		            copyButton.setEnabled(true);
		          }
		          final String str = text;
		          copyButton.addActionListener(new ActionListener()
		          {
		            public void actionPerformed(ActionEvent e) {
		              StringSelection ss = new StringSelection(str);
		              Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		            }
		          });
		          popupMenu.show(table, e.getX(), e.getY());
		        }
		      }
		    });
		  }
/*********************************************************************************
 * 设置鼠标状态
 * 
 * *******/
	  public static void showDefaultCursor(Component o)
	  {
	    if (o != null)
	      o.setCursor(Cursor.getPredefinedCursor(0));
	  }
	  public static void showWaitCursor(Component o)
	  {
	    if (o != null)
	      o.setCursor(Cursor.getPredefinedCursor(3));
	  }

	  public static void showHyperCursor(Component o)
	  {
	    if (o != null)
	      o.setCursor(Cursor.getPredefinedCursor(12));
	  }

	public static void showQuickCursor(QuickTable quickTable) {
		// TODO Auto-generated method stub
		
	}

}
