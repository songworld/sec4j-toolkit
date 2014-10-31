package com.toolkit2.client.frame.free;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.toolkit2.Util.ClientConst;
import com.toolkit2.Util.ClientContext;
import com.toolkit2.client.images.ResourceAgent;
import com.toolkit2.client.tools.ConfigurationBuilder;

import twaver.Group;
import twaver.TDataBox;
import twaver.TWaverUtil;

public class FreeUtil {
	// 缓存图片 为加载图片提供高速方法
	private static final Map<String, ImageIcon> IMAGE_ICON_CACHE = new Hashtable();

	public static final int DEFAULT_ICON_SIZE = 16;
	public static Icon BLANK_ICON = new Icon() {
		public void paintIcon(Component c, Graphics g, int x, int y) {
		}

		public int getIconWidth() {
			return 16;
		}

		public int getIconHeight() {
			return 16;
		}
	};
	public static final int DEFAULT_BUTTON_SIZE = 20;
	public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
	public static final int LIST_SHRINKED_WIDTH = 37;
	public static final int OUTLOOK_SHRINKED_WIDTH = 37;
	public static final int DEFAULT_SPLIT_WIDTH = 4;
	public static final int TABLE_CELL_LEADING_SPACE = 5;
	public static final Color BUTTON_ROVER_COLOR = new Color(196, 196, 197);
	public static final Color TABLE_HEADER_BACKGROUND_COLOR = new Color(239,
			240, 241);
	public static final Color TABLE_HEADER_BORDER_BRIGHT_COLOR = Color.white;
	public static final Color TABLE_HEADER_BORDER_DARK_COLOR = new Color(215,
			219, 223);
	public static final Color TABLE_ODD_ROW_COLOR = new Color(233, 231, 235);
	public static final Color TABLE_TEXT_COLOR = new Color(74, 74, 81);
	public static final Color NETWORK_BACKGROUND = new Color(226, 228, 229);
	public static final Color TAB_BOTTOM_LINE_COLOR = new Color(167, 173, 175);
	public static final Color OUTLOOK_TEXT_COLOR = new Color(120, 120, 125);
	public static final Color OUTLOOK_SPLIT_COLOR = new Color(174, 171, 162);
	public static final Color LIST_SPLIT_COLOR = new Color(105, 113, 120);
	public static final Color LIST_BACKGROUND = new Color(175, 174, 176);
	public static final Color LIST_TEXT_COLOR = new Color(49, 52, 58);
	public static final Color CONTENT_PANE_BACKGROUND = new Color(92, 153, 45);
	public static final Color MENUITEM_SELECTED_BACKGROUND = new Color(166,
			188, 140);
	public static final Color MENUITEM_BACKGROUND = new Color(228, 235, 218);
	public static final Color DEFAULT_TEXT_COLOR = new Color(37, 81, 54);
	public static final Color NO_COLOR = new Color(0, 0, 0, 0);
	public static final Font TABLE_HEADER_FONT = new Font("Tahoma", 1, 11);
	public static final Font TABLE_CELL_FONT = new Font("Tahoma", 0, 11);
	public static final Font FONT_14_BOLD = new Font("微软雅黑", 1, 14);
	public static final Font FONT_12_BOLD = new Font("微软雅黑", 1, 12);
	public static final Font FONT_14_PLAIN = new Font("微软雅黑", 0, 14);
	public static final Font FONT_12_PLAIN = new Font("微软雅黑", 0, 12);
	private static final String IMAGE_URL_PREFIX = "/com/toolkit2/client/images/";

	public static final Color ALL_UI_BACKGROUD_COLOR = new Color(226, 228, 229);

	public static Color HighLighter = Color.green;

	public static String POPUP_MAINTREE_ON_MOUSEOVER = "PopupMainTreeOnMouseOver";

	/*
	 * TexturePaint
	 */
	public static TexturePaint createTexturePaint(String imageURL) {
		ImageIcon icon = TWaverUtil.getImageIcon(imageURL);
		int imageWidth = icon.getIconWidth();
		int imageHeight = icon.getIconHeight();
		BufferedImage bi = new BufferedImage(imageWidth, imageHeight, 2);
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(icon.getImage(), 0, 0, null);
		g2d.dispose();
		return new TexturePaint(bi,
				new Rectangle(0, 0, imageWidth, imageHeight));
	}

	public static String getImageURL(String imageName) {
		return "/com/toolkit2/client/images/" + imageName;
	}

	public static Image getImage(String imageName) {
		return TWaverUtil.getImage(getImageURL(imageName));
	}

	public static ImageIcon getImageIcon(String imageName) {
		return TWaverUtil.getImageIcon(getImageURL(imageName));
	}

	public static BufferedImage createDropShadow(BufferedImage image, int size) {
		BufferedImage shadow = new BufferedImage(image.getWidth() + 4 * size,
				image.getHeight() + 4 * size, 2);

		Graphics2D g2 = shadow.createGraphics();
		g2.drawImage(image, size * 2, size * 2, null);

		g2.setComposite(AlphaComposite.SrcIn);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());

		g2.dispose();

		shadow = getGaussianBlurFilter(size, true).filter(shadow, null);
		shadow = getGaussianBlurFilter(size, false).filter(shadow, null);

		return shadow;
	}

	public static ConvolveOp getGaussianBlurFilter(int radius,
			boolean horizontal) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}

		int size = radius * 2 + 1;
		float[] data = new float[size];

		float sigma = radius / 3.0F;
		float twoSigmaSquare = 2.0F * sigma * sigma;
		float sigmaRoot = (float) Math
				.sqrt(twoSigmaSquare * 3.141592653589793D);
		float total = 0.0F;

		for (int i = -radius; i <= radius; i++) {
			float distance = i * i;
			int index = i + radius;
			data[index] = ((float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot);
			total += data[index];
		}

		for (int i = 0; i < data.length; i++) {
			data[i] /= total;
		}

		Kernel kernel = null;
		if (horizontal)
			kernel = new Kernel(size, 1, data);
		else {
			kernel = new Kernel(1, size, data);
		}
		return new ConvolveOp(kernel, 1, null);
	}

	public static Image getNodeShadowImage(twaver.Node node) {
		Object value = node.getUserProperty("shadow");
		if ((value instanceof Image)) {
			return (Image) value;
		}
		return null;
	}

	public static void setNodeShadowImage(twaver.Node node, Image shadowImage) {
		node.putUserProperty("shadow", shadowImage);
	}

	public static FreeMenuBar loadMenuBar(String xml, ActionListener action) {
		FreeMenuBar menuBar = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(FreeUtil.class.getResource(xml)
					.openStream());

			Element root = doc.getDocumentElement();
			NodeList rootMenus = root.getChildNodes();
			if (rootMenus != null) {
				menuBar = new FreeMenuBar();
				for (int i = 0; i < rootMenus.getLength(); i++) {
					org.w3c.dom.Node menu = rootMenus.item(i);
					if (menu.getNodeType() == 1) {
						if (menu.getNodeName().equalsIgnoreCase("menu")) {
							String text = getStringAttribute(menu, "text");
							System.out.println("FreeUtil.java text:" + text);
							FreeRootMenu rootMenu = new FreeRootMenu();
							rootMenu.setText(text);
							menuBar.add(rootMenu);

							processMenuItem(menu, rootMenu, action);
						}
						if (menu.getNodeName().equalsIgnoreCase("logo")) {
							String tooltip = getStringAttribute(menu, "tooltip");
							String imageURL = getStringAttribute(menu, "image");

							menuBar.add(Box.createGlue());
							JLabel label = new JLabel(
									TWaverUtil.getImageIcon(imageURL));
							label.setBorder(BorderFactory.createEmptyBorder(0,
									5, 0, 5));
							label.setToolTipText(tooltip);
							menuBar.add(label);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return menuBar;
	}

	private static void processMenuItem(org.w3c.dom.Node menu,
			JMenuItem parentMenu, ActionListener action) {
		NodeList children = menu.getChildNodes();
		if (children != null)
			for (int j = 0; j < children.getLength(); j++) {
				org.w3c.dom.Node itemNode = children.item(j);
				if (itemNode.getNodeType() == 1) {
					boolean isMenuItem = itemNode.getNodeName()
							.equalsIgnoreCase("menuitem");
					boolean isMenu = itemNode.getNodeName().equalsIgnoreCase(
							"menu");
					if ((isMenuItem) || (isMenu)) {
						String text = getStringAttribute(itemNode, "text");
						String tooltip = getStringAttribute(itemNode, "tooltip");
						Icon icon = getIconAttribute(itemNode, "icon");
						String command = getStringAttribute(itemNode, "action");

						JMenuItem menuItem = null;

						if (isMenu) {
							menuItem = new FreeMenu();
						} else {
							menuItem = new FreeMenuItem();
							menuItem.addActionListener(action);
						}
						menuItem.setText(text);
						menuItem.setToolTipText(tooltip);
						menuItem.setActionCommand(command);
						menuItem.setIcon(icon);
						parentMenu.add(menuItem);

						if (isMenu)
							processMenuItem(itemNode, menuItem, action);
					}
				}
			}
	}

	private static String getStringAttribute(org.w3c.dom.Node node, String name) {
		org.w3c.dom.Node attribute = node.getAttributes().getNamedItem(name);
		if (attribute != null) {
			return attribute.getNodeValue();
		}
		return null;
	}

	public static String getStringAttribute(org.w3c.dom.Node node, String name,
			boolean removeHtml) {
		org.w3c.dom.Node attribute = node.getAttributes().getNamedItem(name);
		if (attribute != null) {
			String str = attribute.getNodeValue();
			if (removeHtml) {
				return removeHtmlString(ConfigurationBuilder.getI18NValue(str));
			}
			return ConfigurationBuilder.getI18NValue(str);
		}

		return null;
	}

	private static Icon getIconAttribute(org.w3c.dom.Node node, String name) {
		String iconURL = getStringAttribute(node, name);
		if ((iconURL != null) && (!iconURL.isEmpty())) {
			return TWaverUtil.getIcon(iconURL);
		}
		return null;
	}

	private static int getIntAttribute(org.w3c.dom.Node node, String name) {
		String value = getStringAttribute(node, name);
		if ((value != null) && (!value.isEmpty())) {
			return Integer.valueOf(value).intValue();
		}
		return 0;
	}

	public static void loadOutlookToolBar(String xml, FreeOutlookHeader header,
			ActionListener action) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(FreeUtil.class.getResource(xml)
					.openStream());

			Element root = doc.getDocumentElement();
			NodeList buttons = root.getChildNodes();
			if (buttons != null)
				for (int i = 0; i < buttons.getLength(); i++) {
					org.w3c.dom.Node buttonNode = buttons.item(i);
					if (buttonNode.getNodeType() == 1) {
						if (buttonNode.getNodeName().equalsIgnoreCase("button")) {
							String tooltip = getStringAttribute(buttonNode,
									"tooltip");
							Icon icon = getIconAttribute(buttonNode, "icon");
							String command = getStringAttribute(buttonNode,
									"action");
							header.addButton(icon, tooltip, action, command);
						}
						if (buttonNode.getNodeName().equalsIgnoreCase(
								"separator"))
							header.addSeparator();
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 加载Outlookbar 并绑定事件 *
	 */
	public static void loadOutlookPane(String xml, FreeOutlookPane outlookPane,
			ActionListener nodeAction, ActionListener nodeButtonAction,
			ActionListener shortcutAction) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(FreeUtil.class.getResource(xml)
					.openStream());

			Element root = doc.getDocumentElement();
			NodeList modules = root.getChildNodes();
			if (modules != null) {
				for (int i = 0; i < modules.getLength(); i++) {
					org.w3c.dom.Node moduleNode = modules.item(i);
					if ((moduleNode.getNodeType() == 1)
							&& (moduleNode.getNodeName()
									.equalsIgnoreCase("module"))) {
						String text = getStringAttribute(moduleNode, "text");
						Icon icon = getIconAttribute(moduleNode, "icon");
						Icon iconSelected = getIconAttribute(moduleNode,
								"selected_icon");
						String networkXml = getStringAttribute(moduleNode,
								"network");
						FreeOutlookBar bar = outlookPane.addBar(text, icon,
								iconSelected);
						loadNetwork(networkXml, bar.getNetwork(), nodeAction,
								nodeButtonAction, shortcutAction);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void loadNetwork(String xml, FreeNetwork network,
			ActionListener nodeAction, ActionListener nodeButtonAction,
			ActionListener shortcutAction) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(FreeUtil.class.getResource(xml)
					.openStream());
			Element root = doc.getDocumentElement();
			NodeList elements = root.getChildNodes();
			if (elements != null)
				for (int i = 0; i < elements.getLength(); i++) {
					org.w3c.dom.Node elementNode = elements.item(i);
					if (elementNode.getNodeType() == 1) {
						if (elementNode.getNodeName().equalsIgnoreCase("node")) {
							String networkName = getHtmlLabelText(getStringAttribute(
									elementNode, "network_text"));
							String text = getStringAttribute(elementNode,
									"text");
							int x = getIntAttribute(elementNode, "x");
							int y = getIntAttribute(elementNode, "y");
							String moduleIcon = getStringAttribute(elementNode,
									"icon");
							String tooltip = getStringAttribute(elementNode,
									"tooltip");
							String listIcon = getStringAttribute(elementNode,
									"list_icon");

							FreeNode twaverNode = new FreeNode();
							twaverNode.setLocation(x, y);
							twaverNode.setModuleIcon(moduleIcon);
							twaverNode.setIcon(listIcon);
							twaverNode.setName(text);
							twaverNode.setNetworkName(networkName);
							twaverNode.setToolTipText(tooltip);
							twaverNode.setAction(nodeAction);

							readNodeButtons(elementNode, twaverNode,
									nodeButtonAction);

							TDataBox shortcutBox = new TDataBox();
							readNodeShortcuts(elementNode, shortcutBox,
									shortcutAction);
							twaverNode.setShortcuts(shortcutBox);

							network.getDataBox().addElement(twaverNode);
						}
						if (elementNode.getNodeName().equalsIgnoreCase("arrow")) {
							int x = getIntAttribute(elementNode, "x");
							int y = getIntAttribute(elementNode, "y");
							String direction = getStringAttribute(elementNode,
									"direction");
							int rotation = getIntAttribute(elementNode,
									"rotation");

							FreeLink arrow = new FreeLink();
							arrow.setLocation(x, y);
							arrow.setAngle(rotation);
							arrow.setDirection(getArrowDirection(direction));

							network.getDataBox().addElement(arrow);
						}
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static FreeNetwork createNetwork(String xml) {
		FreeNetwork network = new FreeNetwork();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			if (FreeUtil.class.getResource(xml) == null) {
				return network;
			}
			Document doc = db.parse(FreeUtil.class.getResource(xml)
					.openStream());
			Element root = doc.getDocumentElement();
			NodeList elements = root.getChildNodes();
			if (elements != null) {
				for (int i = 0; i < elements.getLength(); i++) {
					org.w3c.dom.Node elementNode = elements.item(i);
					if (elementNode.getNodeType() == 1) {
						if (elementNode.getNodeName().equalsIgnoreCase("node")) {
							String networkName = getHtmlLabelText(getStringAttribute(
									elementNode, "network_text", false));
							String text = getStringAttribute(elementNode,
									"text", false);
							int x = getIntAttribute(elementNode, "x");
							int y = getIntAttribute(elementNode, "y");
							String moduleIcon = getStringAttribute(elementNode,
									"icon");
							String tooltip = getStringAttribute(elementNode,
									"tooltip", false);
							String listIcon = getStringAttribute(elementNode,
									"list_icon");

							String treeNodeName = getStringAttribute(
									elementNode, "treeNodeName");
							FreeModuleNode twaverNode = new FreeModuleNode();
							twaverNode.setLocation(x, y);
							twaverNode.setModuleIcon(moduleIcon);
							twaverNode.setIcon(listIcon);
							twaverNode.setName(text);
							twaverNode.setNetworkName(networkName);
							twaverNode.setToolTipText(tooltip);
							twaverNode.setTreeNodeName(treeNodeName);
							network.getDataBox().addElement(twaverNode);

						} else if (elementNode.getNodeName().equalsIgnoreCase(
								"arrow")) {
							int x = getIntAttribute(elementNode, "x");
							int y = getIntAttribute(elementNode, "y");
							String direction = getStringAttribute(elementNode,
									"direction");
							int rotation = getIntAttribute(elementNode,
									"rotation");
							FreeLink arrow = new FreeLink();
							arrow.setLocation(x, y);
							arrow.setAngle(rotation);
							arrow.setDirection(getArrowDirection(direction));
							network.getDataBox().addElement(arrow);
						}

					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return network;
	}

	private static void readNodeShortcuts(org.w3c.dom.Node moduleNode,
			TDataBox box, ActionListener shortcutAction) {
		NodeList children = moduleNode.getChildNodes();
		if (children != null)
			for (int i = 0; i < children.getLength(); i++) {
				org.w3c.dom.Node child = children.item(i);
				if ((child.getNodeType() == 1)
						&& (child.getNodeName().equalsIgnoreCase("shortcuts"))) {
					NodeList shortcuts = child.getChildNodes();
					if (shortcuts != null)
						for (int j = 0; j < shortcuts.getLength(); j++) {
							org.w3c.dom.Node shortcut = shortcuts.item(j);
							if (shortcut.getNodeType() == 1) {
								if (shortcut.getNodeName().equalsIgnoreCase(
										"shortcut")) {
									String text = getStringAttribute(shortcut,
											"text");
									String tooltip = getStringAttribute(
											shortcut, "tooltip");
									String iconURL = getStringAttribute(
											shortcut, "icon");
									String command = getStringAttribute(
											shortcut, "action");

									twaver.Node node = new twaver.Node();
									node.setName(text);
									node.setToolTipText(tooltip);
									if ((iconURL == null)
											|| (iconURL.trim().isEmpty()))
										node.setIcon("-");
									else {
										node.setIcon(iconURL);
									}
									node.setUserObject(shortcutAction);
									node.setBusinessObject(command);
									box.addElement(node);
								}
								if (shortcut.getNodeName().equalsIgnoreCase(
										"separator")) {
									Group group = new Group();
									String text = getStringAttribute(shortcut,
											"text");
									group.setName(text);
									box.addElement(group);
								}
							}
						}
				}
			}
	}

	public static String removeHtmlString(String text) {
		if (text == null) {
			return "";
		}
		if (ClientContext.getLocale().equals(ClientConst.CHINESE_LOCALE))
			text = text.replaceAll("<br>", "");
		else {
			text = text.replaceAll("<br>", " ");
		}
		String regex = "<[^>]*>";
		return text.replaceAll(regex, "");
	}

	private static String getHtmlLabelText(String text) {
		if ((text != null) && (!text.isEmpty())) {
			text = text.trim();
			while (text.contains("\\n")) {
				int index = text.indexOf("\\n");
				String leading = text.substring(0, index).trim();
				String tailing = text.substring(index + 2).trim();
				text = leading + "<br>" + tailing;
			}
			text = "<html><center>" + text + "</center></html>";

			return text;
		}
		return null;
	}

	private static int getArrowDirection(String directionInXML) {
		if (directionInXML.equalsIgnoreCase("right")) {
			return 3;
		}
		if (directionInXML.equalsIgnoreCase("left")) {
			return 7;
		}
		if (directionInXML.equalsIgnoreCase("up")) {
			return 1;
		}
		if (directionInXML.equalsIgnoreCase("down")) {
			return 5;
		}
		if (directionInXML.equalsIgnoreCase("right_up")) {
			return 2;
		}
		if (directionInXML.equalsIgnoreCase("right_down")) {
			return 4;
		}
		if (directionInXML.equalsIgnoreCase("left_up")) {
			return 8;
		}
		if (directionInXML.equalsIgnoreCase("left_down")) {
			return 6;
		}

		throw new RuntimeException("Unknown direction: " + directionInXML);
	}

	private static void readNodeButtons(org.w3c.dom.Node moduleNode,
			FreeNode twaverNode, ActionListener action) {
		NodeList buttons = moduleNode.getChildNodes();
		if (buttons != null)
			for (int i = 0; i < buttons.getLength(); i++) {
				org.w3c.dom.Node buttonNode = buttons.item(i);
				if (buttonNode.getNodeType() == 1) {
					boolean isButton1 = buttonNode.getNodeName()
							.equalsIgnoreCase("button1");
					boolean isButton2 = buttonNode.getNodeName()
							.equalsIgnoreCase("button2");
					boolean isButton3 = buttonNode.getNodeName()
							.equalsIgnoreCase("button3");
					if ((isButton1) || (isButton2) || (isButton3)) {
						String tooltip = getStringAttribute(buttonNode,
								"tooltip");
						String iconURL = getStringAttribute(buttonNode, "icon");
						String command = getStringAttribute(buttonNode,
								"action");

						if (isButton1) {
							twaverNode.setButtonTooltip1(tooltip);
							twaverNode.setButtonIcon1(iconURL);
							twaverNode.setActionCommand1(command);
							twaverNode.setButtonAction1(action);
						}
						if (isButton2) {
							twaverNode.setButtonTooltip2(tooltip);
							twaverNode.setButtonIcon2(iconURL);
							twaverNode.setActionCommand2(command);
							twaverNode.setButtonAction2(action);
						}
						if (isButton3) {
							twaverNode.setButtonTooltip3(tooltip);
							twaverNode.setButtonIcon3(iconURL);
							twaverNode.setActionCommand3(command);
							twaverNode.setButtonAction3(action);
						}
					}
				}
			}
	}

	/**
	 * 递归调用获取FreePagePane面板，当不存在时返回null
	 * 
	 * @param component
	 *            当前控件父级FreePagePane控件
	 * */
	public static FreePagePane getPagePane(Component component) {
		if ((component != null) && (component.getParent() != null)) {
			Component parent = component.getParent();
			if ((parent instanceof FreePagePane)) {
				return (FreePagePane) parent;
			}
			return getPagePane(parent);
		}

		return null;
	}

	/****************** 普通图片处理方法 开始 *********************/
	public static Image iconToImage(Icon icon) {
		if ((icon instanceof ImageIcon)) {
			return ((ImageIcon) icon).getImage();
		}
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		BufferedImage image = new BufferedImage(w, h, 2);
		Graphics2D g = image.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		g.dispose();
		return image;
	}

	public static ImageIcon createDyedIcon(ImageIcon icon, Color color) {
		if (color == null) {
			return icon;
		}
		int iconWidth = icon.getIconWidth();
		int iconHeight = icon.getIconHeight();
		BufferedImage bi = new BufferedImage(iconWidth, iconHeight, 2);
		Graphics2D g2d = bi.createGraphics();
		icon.paintIcon(null, g2d, 0, 0);
		g2d.dispose();
		Image dyedImage = createDyedImage(bi, color);
		return new ImageIcon(dyedImage);
	}

	public static Image createDyedImage(Image image, Color color) {
		if (color == null) {
			return image;
		}
		if (image != null) {
			int w = image.getWidth(null);
			int h = image.getHeight(null);

			int[] pixels = new int[w * h];
			PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, pixels, 0, w);
			try {
				pg.grabPixels();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				return null;
			}

			BufferedImage bi = new BufferedImage(w > 1 ? w : 1, h > 1 ? h : 1,
					2);

			for (int i = 0; i < pixels.length; i++) {
				int pixel = pixels[i];
				int row = i / w;
				int col = i % w;
				if ((color != null) && (pixel != 0)) {
					pixel = color.getRGB();
				}

				bi.setRGB(col, row, pixel);
			}

			return bi;
		}
		return null;
	}

	public static Icon createMovedIcon(Icon icon) {
		// System.out.println(icon.hashCode()+"调用重绘Icon函数");
		return createMovedIcon(icon, 1, 1);
	}

	public static Icon createMovedIcon(final Icon icon, final int offsetX,
			final int offsetY) {
		return new Icon() {
			public void paintIcon(Component c, Graphics g, int x, int y) {
				/*
				 * 本身方法是绘制图标代码，在Icon的实现类中因该是已经实现，在没有返回值的情况下不应调
				 * 用自身，下面代码应该是来反编译器自动添加导致问题出现
				 * fixed:调用icon的paintIcon方法，之后调用时createMovedIcon方法重的icon参数由Icon
				 * icon 改成了final Icon icon 递归调用问题就此解决。
				 */
				icon.paintIcon(c, g, x + offsetX, y + offsetY);
			}

			public int getIconWidth() {
				return getIconWidth();
			}

			public int getIconHeight() {
				return getIconHeight();
			}
		};
	}

	/*********************** 普通图片处理方法 结束 *****************************/

	/***************************** 带有缓存功能的图片处理方法 ********************/
	public static Image getImageByCache(String filename) {
		return getImageIconByCache(filename).getImage();
	}

	@SuppressWarnings("unused")
	public static ImageIcon getImageIconByCache(String url) {
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

	/*
	 * 设置字体
	 */

	public static Font getFont12Plain() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Font getFont13Bold() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Font getFont14Plain() {
		// TODO Auto-generated method stub
		return null;
	}
}
