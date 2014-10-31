package com.toolkit2.client;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;
import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.component2ex.CommonUI;
import com.toolkit2.client.component2ex.base.MessageGlassPane;
import com.toolkit2.client.frame.free.FreeContentPane;
import com.toolkit2.client.frame.free.FreeGarbageCollectButton;
import com.toolkit2.client.frame.free.FreeListPane;
import com.toolkit2.client.frame.free.FreeMainUITreePane;
import com.toolkit2.client.frame.free.FreeMemoryBar;
import com.toolkit2.client.frame.free.FreeMenuBar;
import com.toolkit2.client.frame.free.FreeNetwork;
import com.toolkit2.client.frame.free.FreeOutlookBar;
import com.toolkit2.client.frame.free.FreeOutlookPane;
import com.toolkit2.client.frame.free.FreePagePane;
import com.toolkit2.client.frame.free.FreePopupMenuBorder;
import com.toolkit2.client.frame.free.FreeReportPage;
import com.toolkit2.client.frame.free.FreeStatusBar;
import com.toolkit2.client.frame.free.FreeStatusLabel;
import com.toolkit2.client.frame.free.FreeStatusMessageLabel;
import com.toolkit2.client.frame.free.FreeStatusTimeLabel;
import com.toolkit2.client.frame.free.FreeTabComponent;
import com.toolkit2.client.frame.free.FreeTabbedPane;
import com.toolkit2.client.frame.free.FreeTabbedPaneUI;
import com.toolkit2.client.frame.free.FreeToolbarButton;
import com.toolkit2.client.frame.free.FreeToolbarRoverButton;
import com.toolkit2.client.frame.free.FreeTreePaneHolder;
import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.frame.mian.ClientInitializer;
import com.toolkit2.client.frame.mian.MainTabCloseListener;
import com.toolkit2.client.test.CompositionDemo;
import com.toolkit2.client.test.GoogleStockDemo;
import com.toolkit2.client.test.PopulationDemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.table.DefaultTableModel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import twaver.TWaverConst;
import twaver.TWaverUtil;

public class App extends Shell {
	/**
	 * Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，
	 * JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如
	 * 果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。(InvalidCastException)
	 */
	private static final long serialVersionUID = 1L;
	
	private String menuBarXML = "/com/toolkit2/client/menubar.xml";
	private String toolbarXML = "/com/toolkit2/client/toolbar.xml";
	private String outlookPaneXML = "/com/toolkit2/client/outlook.xml";
	private ActionListener defaultAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			App.this.command(command);
		}
	};

	private static boolean isDisplayTree = true;

	private String productName = "ToolKit开发套件";
	private String companyName = "ToolKit软件";
	private FreeMenuBar menubar = FreeUtil.loadMenuBar(this.menuBarXML,
			this.defaultAction);
	private FreeContentPane contentPane = new FreeContentPane();
	private FreeStatusBar statusBar = new FreeStatusBar();
	private FreeMemoryBar memoryBar = new FreeMemoryBar();
	private FreeStatusMessageLabel lbStatusMessage = new FreeStatusMessageLabel();
	private FreeStatusTimeLabel lbStatusTime = new FreeStatusTimeLabel();
	private FreeStatusLabel lbServer = createStatusLabel("127.0.0.1",
			"/com/toolkit2/client/test/server.png");
	private FreeStatusLabel lbUser = createStatusLabel("管理员",
			"/com/toolkit2/client/test/user.png");
	private FreeStatusLabel lbVersion = createStatusLabel("v3.0.0", null);
	private FreeListPane shortcutPane = new FreeListPane();
	private FreeOutlookPane outlookPane = new FreeOutlookPane(this.shortcutPane);

	private FreeTreePaneHolder treePaneHolder = createTreePaneHolder();
	private FreeTabbedPane tab = new FreeTabbedPane();

	protected JPanel messagePane = new JPanel();

	public App() {
		initSwing();
		initOutlookPane();
		initTab();
		initShortcutList();
		initStatusBar();
		initMockers();
		setDisplayTree(false);
	}

	private void initSwing() {
		setTitle(this.productName + " v3.0 - [" + this.companyName + "]");
		setDefaultCloseOperation(3);
		// setSize(1200, 768);
		initWindowSize();
		setIconImage(TWaverUtil.getImage("/com/toolkit2/client/test/xcode.png"));

		setContentPane(this.contentPane);
		TWaverUtil.centerWindow(this);
		this.contentPane.add(this.menubar, "North");
		this.contentPane.add(this.statusBar, "South");

		JPanel centerPane = new JPanel(new BorderLayout());
		centerPane.setOpaque(true);
		centerPane.setBackground(FreeUtil.CONTENT_PANE_BACKGROUND);
		centerPane.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
		centerPane.add(this.shortcutPane, "East");
		if (isDisplayTree()) {
			// 添加工具栏
			FreeUtil.loadOutlookToolBar(this.toolbarXML,
					this.treePaneHolder.getHeader(), this.defaultAction);
			centerPane.add(this.treePaneHolder, "West");
		} else {
			centerPane.add(this.outlookPane, "West");
		}
		this.contentPane.add(centerPane, "Center");
		centerPane.add(this.tab, "Center");
		this.lbStatusMessage.setText("Server is connected");
	}

	/**
	 * 初始化工具栏
	 * */
	private void initOutlookPane() {
		// 工具栏
		FreeUtil.loadOutlookToolBar(this.toolbarXML,
				this.outlookPane.getHeader(), this.defaultAction);
		/*******************************************************************************
		 * 右侧工具栏 事件
		 * *****/
		ActionListener nodeActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if ((source instanceof FreeNetwork)) {
					FreeNetwork network = (FreeNetwork) source;
					FreeOutlookBar bar = App.this.outlookPane
							.getBarByNetwork(network);
					bar.setSelected(true);

					if (network.getParent() == null) {
						String title = bar.getTitle();
						App.this.tab
								.addTab(title, App.this.createPage(network));
					} else {
						FreePagePane pagePane = FreeUtil.getPagePane(network);
						App.this.tab.setSelectedComponent(pagePane);
					}
				}
			}
		};
		ActionListener nodeButtonAction = this.defaultAction;
		ActionListener shortcutAction = this.defaultAction;
		// 是初始化
		FreeUtil.loadOutlookPane(this.outlookPaneXML, this.outlookPane,
				nodeActionListener, nodeButtonAction, shortcutAction);
	}

	/**
	 * 初始化Tab选项卡
	 * */
	private void initTab() {
		this.tab.addMouseListener(new MouseAdapter() {
			private boolean isMaximized() {
				return (App.this.outlookPane.isShrinked())
						&& (App.this.shortcutPane.isShrinked());
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					TabbedPaneUI ui = App.this.tab.getUI();
					int tabIndex = ui.tabForCoordinate(App.this.tab, e.getX(),
							e.getY());

					if (tabIndex != -1) {
						boolean maxed = isMaximized();
						App.this.outlookPane.setShrink(!maxed);
						App.this.shortcutPane.setShrink(maxed);
					}
				}
			}
		});
		/* 测试数据用例 实际程序中此代码删除 */
		//this.tab.addTab("仪表盘", createPage(new CompositionDemo()));
		//this.tab.addTab("大数据订单加载实例", createReportPage());
		//this.tab.addTab("Google Stock", createPage(new GoogleStockDemo()));
		// this.tab.addTab("Global Customers", createPage(new
		// PopulationDemo()));
		showTab(new CompositionDemo());
		showTab(new GoogleStockDemo());
	}

	public void showTab(SubForm newUI, SubForm oldUI) {
		FreeTabbedPane tab = getTabbedPane();
		int index = tab.indexOfComponent(oldUI);
		if (index == -1) {
			showTab(newUI);
		} else {
			((FreeTabbedPane) tab).addTab(newUI.getTitle(), newUI, oldUI);
			tab.setSelectedComponent(newUI);
		}
		MessageGlassPane.getInstance().setClientUI(newUI);
	}

	public void showTab(SubForm clientUI) {
		if (clientUI != null) {
			if (((clientUI.getClientProperty("MainPage") instanceof Boolean))
					&& (((Boolean) clientUI.getClientProperty("MainPage"))
							.booleanValue())) {
				Window win = ClientUtil.getWindowForComponent(clientUI);
				/** 暂时注释 **/
				// if ((win instanceof DetachUI)) {
				// DetachUI detachUI = (DetachUI)win;
				// detachUI.dispose();
				// }

				if (this.tab.indexOfComponent(clientUI) == -1) {
					for (int i = 0; i < this.tab.getTabCount(); i++) {
						JComponent c = (JComponent) this.tab.getComponentAt(i);
						if (((c.getClientProperty("MainPage") instanceof Boolean))
								&& (((Boolean) c.getClientProperty("MainPage"))
										.booleanValue())) {
							this.tab.remove(c);
						}
					}
				}
			}

			if (this.tab.indexOfComponent(clientUI) == -1) {
				int index = 0;
				this.tab.add(clientUI, index);
				this.tab.setTitleAt(index, clientUI.getTitle());
				boolean showCloseButton = clientUI.isCloseable();
				FreeTabComponent header = new FreeTabComponent(this.tab,
						showCloseButton);
				header.setTitle(clientUI.getTitle());
				this.tab.setTabComponentAt(index, header);
				this.tab.setToolTipTextAt(index, clientUI.getTitle());
				this.tab.addTab(clientUI.getTitle(), clientUI);
				this.tab.setSelectedComponent(clientUI);
			} else {
				this.tab.setSelectedComponent(clientUI);
			}

			clientUI.requestFocus();
			MessageGlassPane.getInstance().setClientUI(clientUI);
		}
	}

	public boolean removeTab(SubForm ui) {
		FreeTabbedPane tab = getTabbedPane();
		ArrayList listeners = ui.getTabCloseListeners();
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			MainTabCloseListener listener = (MainTabCloseListener) it.next();
			if (listener != null) {
				boolean pass = listener.tabClosing();
				if (!pass) {
					return false;
				}
			}
		}
		tab.remove(ui);
		this.messagePane.setVisible(false);
		it = listeners.iterator();
		while (it.hasNext()) {
			MainTabCloseListener listener = (MainTabCloseListener) it.next();
			if (listener != null) {
				listener.tabClosed();
			}
		}
		// if ((ui instanceof BaseHomePage)) {
		// getInstance().getMainTreePane().getMainTree().getDataBox().getSelectionModel().clearSelection();
		// }
		MessageGlassPane.getInstance().setClientUI(
				(SubForm) getTabbedPane().getSelectedComponent());
		return true;
	}

	private FreeReportPage createReportPage() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Price");
		model.addColumn("Title");
		model.addColumn("Publisher");
		model.addColumn("Comments");

		// for (int i = 0; i < 500000; i++) {
		for (int i = 0; i < 10; i++) {
			Vector row = new Vector();
			row.add("5223");
			row.add("Bill Gates");
			row.add("$12,444.00");
			row.add("Sales Manager");
			row.add("Doubleday");
			row.add("director or manager misappropriates company funds or takes company funds and lends them to");
			model.addRow(row);
		}

		FreeReportPage page = new FreeReportPage();
		page.getTable().setModel(model);
		page.setDescription("所有的工作订单项根据零件号创建. 创建于 " + new Date().toString());

		return page;
	}

	private void initShortcutList() {
		this.shortcutPane.setTitle("任务栏");
	}

	private void initStatusBar() {
		this.statusBar.getLeftPane().add(this.lbStatusMessage, "Center");
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(this.memoryBar);
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(new FreeGarbageCollectButton());
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(this.lbServer);
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(this.lbUser);
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(this.lbStatusTime);
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(this.lbVersion);
		this.statusBar.addSeparator();
		this.statusBar.getRightPane().add(
				createStatusLabel("Powered by " + this.productName, null));
	}

	public void setServer(String server) {
		this.lbServer.setText(server);
	}

	public void setUser(String username) {
		this.lbUser.setText(username);
	}

	public void setVersion(String version) {
		this.lbVersion.setText(version);
	}

	private void initMockers() {
		Thread thread = new Thread() {
			public void run() {
				while (true)
					for (int i = 0; i < 3; i++)
						try {
							Thread.sleep(5000L);
							if (i == 0) {
								App.this.lbStatusMessage.setGreenLight();
								App.this.lbStatusMessage
										.setText("Server is connected");
							}
							if (i == 1) {
								App.this.lbStatusMessage.setOrangeLight();
								App.this.lbStatusMessage
										.setText("Server connection is slow");
							}
							if (i == 2) {
								App.this.lbStatusMessage.setRedLight();
								App.this.lbStatusMessage
										.setText("Server connection is broken");
							}
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
			}
		};
		thread.start();
	}

	private FreePagePane createPage(JComponent pageContent) {
		// System.out.println("App.java 创建page");
		FreePagePane page = new FreePagePane(pageContent);
		//setupPageToolbar(page);
		return page;
	}
	
	private FreeStatusLabel createStatusLabel(String text, String imageURL) {
		if (imageURL != null) {
			return new FreeStatusLabel(text, TWaverUtil.getIcon(imageURL));
		}
		return new FreeStatusLabel(text);
	}

	private static void setupLookAndFeel() {
		Locale.setDefault(TWaverConst.ZH_CN);
		TWaverUtil.setLocale(TWaverConst.ZH_CN);
		PlasticTheme theme = new ExperienceBlue() {
			public FontUIResource getControlTextFont() {
				return new FontUIResource(new Font("微软雅黑", 0, 12));
			}
		};
		PlasticLookAndFeel.setPlasticTheme(theme);
		try {
			// UIManager.setLookAndFeel("BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated");
			// 全局主题外观
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			try {
				org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper
						.launchBeautyEyeLNF();
			} catch (Exception e) {
				ClientUtil.showException(e);
			}
			// 自定义JToolBar ui的border
			Border bd = new org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarUI.ToolBarBorder(
					UIManager.getColor("ToolBar.shadow") // Floatable时触点的颜色
					, UIManager.getColor("ToolBar.highlight")// Floatable时触点的阴影颜色
					, new Insets(6, 0, 11, 0)); // border的默认insets
			UIManager.put("RootPane.setupButtonVisible", false);
			UIManager.put("ToolBar.border", new BorderUIResource(bd));

			UIManager.getDefaults().put("TabbedPaneUI",
					FreeTabbedPaneUI.class.getName());
			// System.out.println("App.java TabbedPaneUI:"+
			// FreeTabbedPaneUI.class.getName());
			UIManager.put("Menu.selectionBackground", FreeUtil.NO_COLOR);
			UIManager.put("MenuItem.selectionBackground",
					FreeUtil.MENUITEM_SELECTED_BACKGROUND);
			UIManager.put("PopupMenu.border", new FreePopupMenuBorder());
			UIManager.put("ToolTip.font", FreeUtil.FONT_14_BOLD);
			UIManager.put("TabbedPane.contentBorderInsets",
					FreeUtil.ZERO_INSETS);
			UIManager.put("TabbedPane.tabInsets", FreeUtil.ZERO_INSETS);
			UIManager.put("TabbedPane.selectedTabPadInsets",
					FreeUtil.ZERO_INSETS);
			UIManager.put("TabbedPane.tabAreaInsets", FreeUtil.ZERO_INSETS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void command(String action) {
		String message = "Perform action " + action + ".";
		// 执行对应的命令
		OperateCodeManager.getInstance().doAction(action);
		this.lbStatusMessage.setText(message);
	}

	private FreeTreePaneHolder createTreePaneHolder() {
		System.out.println("Tree加载被执行....");
		if (isDisplayTree()) {
			return new FreeTreePaneHolder(getMainTreeXML());
		}
		return null;
	}

	public String getMainTreeXML() {
		return "/com/toolkit2/client/MainTree.xml";
	}

	public boolean isDisplayTree() {
		return isDisplayTree;
	}

	public void setDisplayTree(boolean is) {
		isDisplayTree = is;
	}

	/*****************************************************************************************************
	 * 
	 * 
	 * ***/
	public FreeMainUITreePane getMainTreePane() {
		if (this.treePaneHolder != null) {
			return this.treePaneHolder.getTreePane();
		}
		return null;
	}

	public FreeTreePaneHolder getTreePaneHolder() {
		return this.treePaneHolder;
	}

	public FreeTabbedPane getTabbedPane() {
		return this.tab;
	}

	/*****************************************************************************************************
	 * 
	 * 全屏窗口
	 * ***/
	private void initWindowSize() {
		Dimension screenSize = getToolkit().getScreenSize();
		GraphicsConfiguration config = getGraphicsConfiguration();
		Insets screenInsets;
		if (config != null)
			screenInsets = getToolkit().getScreenInsets(config);
		else {
			screenInsets = TWaverConst.NONE_INSETS;
		}
		int gap = 20;
		int x = screenInsets.left + gap;
		int y = screenInsets.top + gap;
		int width = screenSize.width - screenInsets.left - screenInsets.right
				- 2 * gap;
		int height = screenSize.height - screenInsets.top - screenInsets.bottom
				- 2 * gap;
		setBounds(x, y, width, height);
	}

	/***************************************************************************************
	 * App初始化 =>这边是统一应用入口，在这会根据不同类型的应用 直接实例化出不同的界面
	 * 
	 * 目前应用初始化过程还是独立的一个应用 接下来要完成的是根据不同应用实例化不同界面
	 * *****/

	/***************************************************************************************************
	 * 
	 * 应用程程序调用入口
	 * 
	 * *************************************************************************************************/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			setupLookAndFeel();
			ClientInitializer.initToolKit2(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				App app = new App();
				CommonUI.closeSplashWindow();
				app.setVisible(true);
			}
		});
	}

}
