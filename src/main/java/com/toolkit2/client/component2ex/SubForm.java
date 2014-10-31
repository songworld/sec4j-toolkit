package com.toolkit2.client.component2ex;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.PrintStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import twaver.TWaverUtil;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.frame.free.FreePagePane;
import com.toolkit2.client.frame.free.FreeTabComponent;
import com.toolkit2.client.frame.free.FreeToolbarButton;
import com.toolkit2.client.frame.free.FreeToolbarRoverButton;
import com.toolkit2.client.frame.mian.MainTabCloseListener;
/**********************************************************************************
 * 页面显示基准窗体，所有的功能页面都要继承此窗体,以方便在框架中调用
 * 
 * 
 * *****/
public class SubForm extends FreePagePane
  implements HelpTarget
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private ArrayList<MainTabCloseListener> closeListeners = new ArrayList();
  private String title = null;
  private Icon icon = null;
  private boolean hasSetupDetailUICustomPane = false;
  private boolean showTabCloseButton = true;

  public SubForm() { this(null); }


  public static void getCaller()
  {
    StackTraceElement[] stack = new Throwable().getStackTrace();
    for (int i = 0; i < stack.length; i++) {
      StackTraceElement ste = stack[i];
      System.out.println(ste.getClassName() + ". " + ste.getMethodName() + "(...) ");
      System.out.println(i + "-- " + ste.getMethodName());
      System.out.println(i + "-- " + ste.getFileName());
      System.out.println(i + "-- " + ste.getLineNumber());
    }
  }

  public SubForm(String title) {
    setTitle(title);
    init();
    addHierarchyListener(new HierarchyListener()
    {
    	//监听层次变化
      public void hierarchyChanged(HierarchyEvent e) {
        SubForm.this.updateIcon();
      }
    });
    addComponentListener(new ComponentListener()
    {
      public void componentHidden(ComponentEvent e) {
      }

      public void componentMoved(ComponentEvent e) {
        if ((!SubForm.this.hasSetupDetailUICustomPane) && (SubForm.this.isNeedSetupDetailUICustomPane())) {
          ClientUtil.setupDetailUICustomPane(SubForm.this);
          SubForm.this.hasSetupDetailUICustomPane = true;
        }
      }

      public void componentResized(ComponentEvent e) {
        if ((!SubForm.this.hasSetupDetailUICustomPane) && (SubForm.this.isNeedSetupDetailUICustomPane())) {
          ClientUtil.setupDetailUICustomPane(SubForm.this);
          SubForm.this.hasSetupDetailUICustomPane = true;
        }
      }

      public void componentShown(ComponentEvent e) {
        if ((!SubForm.this.hasSetupDetailUICustomPane) && (SubForm.this.isNeedSetupDetailUICustomPane())) {
          ClientUtil.setupDetailUICustomPane(SubForm.this);
          SubForm.this.hasSetupDetailUICustomPane = true;
        }
      }
    });
  }

  public boolean isNeedSetupDetailUICustomPane() {
    return true;
  }

  private void init() {
    setLayout(new BorderLayout());
    if (getClass().getProtectionDomain().getCodeSource().getLocation().getProtocol().equals("file")){
      System.out.println(getClass().getName());
    }
    
    setupPageToolbar();
    
  }

  /***
   * 	创建常用工具栏
   * 
   * ****/
  private void setupPageToolbar() {
		this.getToolBar().add(
				createButton("/com/toolkit2/client/test/home.png", "主页", true));
		// 添加后退按钮
		FreeToolbarButton btn_back = createButton(
				"/com/toolkit2/client/test/left.png", "后退", true);
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shell.getInstance().getTabbedPane().getNavigator().back();
			}
		});
		this.getToolBar().add(btn_back);
		//添加后退按钮
		FreeToolbarButton btn_next = createButton(
				"/com/toolkit2/client/test/right.png", "前进", true);
		btn_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shell.getInstance().getTabbedPane().getNavigator().next();
			}
		});
		this.getToolBar().add(btn_next);
		this.getToolBar().add(Box.createGlue());
//		this.getToolBar().add(
//				createButton("/com/toolkit2/client/test/add.png", "添加", true));
//		this.getToolBar()
//				.add(createButton("/com/toolkit2/client/test/update.png", "更新",
//						true));
//		this.getToolBar().add(
//				createButton("/com/toolkit2/client/test/refresh.png", "刷新",
//						true));
//		this.getToolBar()
//				.add(createButton("/com/toolkit2/client/test/print.png", "打印",
//						true));
		
		System.out.println("SubForm.java toolbar has created！");
	}
  /**
   * 	创建通用按钮
   * **/
  private FreeToolbarButton createButton(String icon, String tooltip,
			boolean rover) {
		FreeToolbarButton button = null;
		if (rover)
			button = new FreeToolbarRoverButton();
		else {
			button = new FreeToolbarButton();
		}
		button.setIcon(TWaverUtil.getIcon(icon));
		button.setToolTipText(tooltip);

		return button;
	}
  
  public void addTabCloseListener(MainTabCloseListener l)
  {
    this.closeListeners.add(l);
  }

  public ArrayList<MainTabCloseListener> getTabCloseListeners() {
    return this.closeListeners;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;

    if (Shell.isInstanced())
    {
      if (Shell.getInstance().getTabbedPane() != null) {
        int index = Shell.getInstance().getTabbedPane().indexOfComponent(this);
        if (index != -1)
        {
          FreeTabComponent freeTabComponent = (FreeTabComponent)Shell.getInstance().getTabbedPane().getTabComponentAt(index);
          freeTabComponent.setTitle(this.title);
        }
      }
    }
  }

  public void dispose()
  {
    if (Shell.isInstanced()) {
      Shell.getInstance().removeTab(this);
      Window window = ClientUtil.getWindowForComponent(this);
//      if ((window instanceof DetachUI)) {
//        ((DetachUI)window).setNeedToPutBackToMainUI(false);
//        ((DetachUI)window).dispose();
//      }
    }
  }

  public void setSize(int width, int height) {
    super.setSize(width, height);
  }

  public void setVisible() {
    Shell.getInstance().showTab(this);
  }

  public boolean checkLicense()
  {
    return true;
  }

  public JButton getDefaultButton()
  {
    return null;
  }

  public String getHelpKey() {
    return ClientUtil.getClassNameWithoutPackage(this);
  }

  public String getMessage()
  {
    return null;
  }

  public JComponent getMessageComponennt()
  {
    return null;
  }

  protected void finalize() throws Throwable
  {
  }

  public Icon getIcon() {
    return this.icon;
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
    updateIcon();
  }

  private void updateIcon() {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run() {
        Container parent = SubForm.this.getParent();
        if ((parent instanceof JTabbedPane)) {
          JTabbedPane tab = (JTabbedPane)parent;
          int index = tab.indexOfComponent(SubForm.this);
          if (index >= 0) {
            tab.setIconAt(index, SubForm.this.icon);
            Component tabComponent = tab.getTabComponentAt(index);
            if ((tabComponent instanceof FreeTabComponent)) {
              FreeTabComponent freeTabComponent = (FreeTabComponent)tabComponent;
              freeTabComponent.setIcon(SubForm.this.icon);
            }
            if ((tabComponent instanceof MainTabHeader)) {
              MainTabHeader defaultTabComponent = (MainTabHeader)tabComponent;
              defaultTabComponent.setIcon(SubForm.this.icon);
            }
          }
        }
      }
    });
  }

  public boolean isCloseable() {
    return this.showTabCloseButton;
  }
}