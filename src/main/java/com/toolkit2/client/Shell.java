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
import java.awt.Component;
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
import javax.swing.JInternalFrame;
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
//抽象类，为各个功能以及界面调用主界面提供了一个调用接口
public abstract class Shell extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Shell instance = null;
	private static boolean instanced = false;
	 protected JPanel messagePane = new JPanel();
	 
	 
	public Shell() {
		instanced = true;
		instance = this;
	}

	public static synchronized Shell getInstance() {
		if (instance == null) {
			instance = new App();
			instanced = true;
		}
		return instance;
	}
	public void showTab(SubForm newUI, SubForm oldUI) {
	  }

	  public abstract void showTab(SubForm paramClientUI);

	  public boolean removeTab(SubForm ui) {
		  FreeTabbedPane tab = getTabbedPane();
	    ArrayList listeners = ui.getTabCloseListeners();
	    Iterator it = listeners.iterator();
	    while (it.hasNext()) {
	      MainTabCloseListener listener = (MainTabCloseListener)it.next();
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
	      MainTabCloseListener listener = (MainTabCloseListener)it.next();
	      if (listener != null) {
	        listener.tabClosed();
	      }
	    }
//	    if ((ui instanceof BaseHomePage)) {
//	      getInstance().getMainTreePane().getMainTree().getDataBox().getSelectionModel().clearSelection();
//	    }
	    MessageGlassPane.getInstance().setClientUI((SubForm)getTabbedPane().getSelectedComponent());
	    return true;
	  }

	/************************************************************************************************
	 * Get 方法区
	 * 
	 * **/

	public FreeTabbedPane getTabbedPane() {
		return null;
	}

	 public FreeMainUITreePane getMainTreePane() {
		    return null;
		  }

	/************************************************************************************************
	 * Set 方法区
	 * 
	 * **/

	/***************************************************************************************************
	 * 委托调用方法
	 * 
	 * 根据目前的设计暂时不需要
	 * 
	 * *************************************************************************************************/
	public Object invokeMethod(String className, String methodName,
			Class[] parameterTypes, Object[] parameters) {
		return invokeMethod(className, methodName, parameterTypes, parameters,
				true);
	}

	public Object invokeMethod(String className, String methodName,
			Class[] parameterTypes, Object[] parameters, boolean isStatic) {
		try {
			className = getGUIMainPackage() + "." + className;
			Class clazz = Class.forName(className);
			Method m = clazz.getDeclaredMethod(methodName, parameterTypes);
			return m.invoke(isStatic ? null : clazz.newInstance(), parameters);
		} catch (Exception e) {
			ClientUtil.showException(e);
		}
		return null;
	}

	// 委托调用方法
	public String getGUIMainPackage() {
		return "com.toolkit2.client";
	}

	public void removeTreePane() {
	}

	public void addTreePane() {
	}

	public JInternalFrame getTreeFrame() {
		return null;
	}

	public Dimension getMainAreaSize() {
		return null;
	}

	public String getActionCodeFile() {
		return "";
	}

	/****************************************************************************************************
	 * 主窗体是否被实例化
	 * 
	 * @return boolean 对象是否被实例化
	 * ****/
	public static boolean isInstanced() {
		return instanced;
	}

	public boolean isSelectRootOnStart() {
		return true;
	}



}