package com.toolkit2.client.frame.free;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.toolkit2.Util.ClientContext;
import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.FontStyle;
import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.client.CacheManager;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.frame.mian.CustomerMainTree;
import com.toolkit2.client.frame.mian.TreeToolbar;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeColorStore;
import com.toolkit2.client.tools.FreeIconStore;

import twaver.DataBoxSelectionAdapter;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.Generator;
import twaver.TDataBox;
import twaver.VisibleFilter;
import twaver.tree.TTree;

public class FreeMainUITreePane extends JPanel
{
	  private static final String workFlowName = "ToolKit Work Flow";
	  private boolean custom = false;
	  private FreeTreeToolbar toolbar = null;
	  private FreeQuickSearchBox txtQuickLaunch = new FreeQuickSearchBox();
	  TTree mainTree = new TTree()
	  {
	    protected boolean removeDescendantSelectedPaths(TreePath path, boolean includePath)
	    {
	      return false;
	    }
	  };

	  FreeTabPane tab = new FreeTabPane();
//	  TTree myTree = new TTree()
//	  {
//	    protected boolean removeDescendantSelectedPaths(TreePath path, boolean includePath) {
//	      return false;
//	    }
//	  };

	CustomerMainTree customTree = CustomerMainTree.getInstance();
	  JPanel mainTreePane = new JPanel(new BorderLayout());
	  JPanel myTreePane = new JPanel(new BorderLayout());
	  JPanel customerPane = new JPanel(new BorderLayout());
	 // TreeOrderFinalizer finalizer = new TreeOrderFinalizer(this);
	  private static Hashtable<String, SubForm> cachedHomepages = new Hashtable();
	  private static SubForm singleHomepageClientUI = null;

	  private int getElementLevel(Element element)
	  {
	    int level = 0;
	    while (element.getParent() != null) {
	      level++;
	      element = element.getParent();
	    }
	    return level;
	  }

	  private String getHomepageKeyName(Element element) {
	    return element.getClientProperty("homeClass") == null ? "" : element.getClientProperty("homeClass").toString();
	  }

	  public FreeMainUITreePane(String xml_name)
	  {
	    this.custom = initCustomTreeData();
	    initTreeData(xml_name);
	 //   initMyTreeData();
	    init();
	   // MainUIActionManager.addSysteFinalizer(this.finalizer);
	  }

	  private void init()
	  {
	    this.mainTree.setRootVisible(false);
	    this.mainTree.getSelectionModel().setSelectionMode(1);
//	    this.myTree.setRootVisible(false);
//	    this.myTree.getSelectionModel().setSelectionMode(1);

	  //  this.myTree.addVisibleFilter(this.visibleFilter);

	    MouseListener showHomePageListener = new MouseAdapter()
	    {
	      public void mousePressed(MouseEvent e)
	      {
	        if ((FreeMainUITreePane.this.mainTree.isShowing()) && (FreeMainUITreePane.this.mainTree.getPathForLocation(e.getX(), e.getY()) == null)) {
	          return;
	        }
//	        if ((FreeMainUITreePane.this.myTree.isShowing()) && (FreeMainUITreePane.this.myTree.getPathForLocation(e.getX(), e.getY()) == null)) {
//	          return;
//	        }

	        if (FreeMainUITreePane.singleHomepageClientUI != null)
	          Shell.getInstance().showTab(FreeMainUITreePane.singleHomepageClientUI);
	      }
	    };
	    this.mainTree.addMouseListener(showHomePageListener);
//	    this.myTree.addMouseListener(showHomePageListener);

	    if (!ClientContext.isEnglishFontOnly()) {
	      if ((this.mainTree.getCellRenderer() instanceof Component)) {
	        Font font = FontStyle.getInstance().getCurrentHighlightFont();

	        Font newFont = new Font(font.getFontName(), font.getStyle(), font.getSize());
	        this.mainTree.setFont(newFont);
	        UIManager.put("Tree.textBackground", this.mainTree.getBackground());
	      }
//	      if ((this.myTree.getCellRenderer() instanceof Component)) {
//	        Font font = FontStyle.getInstance().getCurrentHighlightFont();
//
//	        Font newFont = new Font(font.getFontName(), font.getStyle(), font.getSize());
//	        this.myTree.setFont(newFont);
//	        UIManager.put("Tree.textBackground", this.mainTree.getBackground());
//	      }
	    }
//	    this.myTree.getDataBox().setName(GeneralUtil.getString("MainUITreePane.TabTitleMy2BizBox"));
	    setLayout(new BorderLayout());
	    add(this.tab, "Center");
	    setOpaque(true);
	    setBackground(FreeColorStore.BACKGROUND_HOMEPAGE);
	    this.mainTreePane.add(new JScrollPane(this.mainTree), "Center");
	    JPanel northPane = new JPanel(new BorderLayout());
	    this.toolbar = new FreeTreeToolbar(this.mainTree);
	    northPane.add(this.toolbar, "North");
	    northPane.add(this.txtQuickLaunch, "Center");
	    this.txtQuickLaunch.setToolTipText(Translator.getString("MainUIToolBar.QuickSearch"));
	    this.txtQuickLaunch.setIcon(FreeIconStore.SEARCH_ICON);
	    this.mainTreePane.add(northPane, "North");
//	    this.myTreePane.add(new JScrollPane(this.myTree), "Center");
	    if (!this.custom) {
	      this.tab.add(this.mainTreePane, GeneralUtil.getString("MainUITreePane.TabTitle"));
	    }
	    this.tab.setTabLayoutPolicy(1);
	//    adjustMyTreeVisible();
	    this.mainTree.putClientProperty("JTree.lineStyle", "Angled");
	    BasicTreeUI treeUI = (BasicTreeUI)this.mainTree.getUI();
	    treeUI.setLeftChildIndent(8);
	    treeUI.setRightChildIndent(10);
	    this.txtQuickLaunch.getTextField().addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e) {
	        String text = FreeMainUITreePane.this.txtQuickLaunch.getTextField().getText().trim();
	        if (ClientUtil.isNullObject(text)) {
	          return;
	        }
	        if (text.startsWith("/")) {
	          return;
	        }
	        if (Shell.isInstanced()) {
	        	FreeMainUITreePane treePane = Shell.getInstance().getMainTreePane();
	     //     TTree currentTree = treePane.getMainTree().isShowing() ? treePane.getMainTree() : treePane.getMyTree();
	            TTree currentTree =  treePane.getMainTree();
	          text = text.trim().toLowerCase();
	          List list = new ArrayList();
	          Iterator it = treePane.getMainTree().getDataBox().iteratorReverse();
	          while (it.hasNext()) {
	            Element element = (Element)it.next();
	            if ((currentTree.isVisible(element)) && (element.getName().toLowerCase().contains(text))) {
	              list.add(element);
	            }
	          }
	          if (!list.isEmpty()) {
	            Element currentElement = currentTree.getDataBox().getSelectionModel().lastElement();
	            if (currentElement == null) {
	              currentTree.getDataBox().getSelectionModel().setSelection((Element)list.get(0));
	            }
	            else if (!list.contains(currentElement)) {
	              currentTree.getDataBox().getSelectionModel().setSelection((Element)list.get(0));
	            } else {
	              Iterator elementIterator = list.iterator();
	              while (elementIterator.hasNext()) {
	                Element element = (Element)elementIterator.next();
	                if (currentElement == element)
	                {
	                  if (elementIterator.hasNext())
	                    currentTree.getDataBox().getSelectionModel().setSelection((Element)elementIterator.next());
	                  else {
	                    currentTree.getDataBox().getSelectionModel().setSelection((Element)list.get(0));
	                  }
	                }
	              }
	            }

	          }

	        }

	        Thread thread = new Thread()
	        {
	          public void run() {
	            try {
	              Thread.sleep(500L);
	            } catch (Exception ex) {
	            }
	            FreeMainUITreePane.this.txtQuickLaunch.getTextField().requestFocus();
	          }
	        };
	        thread.start();
	      }
	    });
	    if (this.custom) {
	      this.tab.add(this.customerPane, "ToolKit2");
	      this.customerPane.add(new JScrollPane(this.customTree), "Center");
	      JPanel toolPane = new JPanel(new BorderLayout());
	      toolPane.add(new TreeToolbar(this.customTree), "North");
	      this.customerPane.add(toolPane, "North");
	    }

	    if (Shell.getInstance().isSelectRootOnStart())
	      SwingUtilities.invokeLater(new Runnable()
	      {
	        public void run()
	        {
	          List roots = FreeMainUITreePane.this.mainTree.getDataBox().getRootElements();
	          if ((roots != null) && (!roots.isEmpty())) {
	        	  //加载首页
//	            Element element = (Element)roots.get(0);
//
//	            FreeMainUITreePane.HomePageLoader loader = new FreeMainUITreePane.HomePageLoader(FreeMainUITreePane.this);
//	            try {
//	              Object o = loader.queryFromServer();
//	              loader.actionPerformed(o);
//	            } catch (Exception ex) {
//	              ClientUtil.showException(FreeMainUITreePane.this.mainTree, ex);
//	            }
	          }
	        }
	      });
	  }

	  private void initTreeData(String xml_name)
	  {
	    try {
	      this.mainTree.setDataBox(FreeMainTreeHelper.createMainTree(xml_name));
	    } catch (final Exception ex) {
	      ClientUtil.showException(this, ex);

	      SwingUtilities.invokeLater(new Runnable()
	      {
	        public void run() {
	          ClientUtil.showException(FreeMainUITreePane.this.mainTree, ex);
	        }
	      });
	    }
	    this.mainTree.getDataBox().getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionAdapter()
	    {
	      public void selectionChanged(DataBoxSelectionEvent evt) {
	        if (1 == evt.getType())
	        	FreeMainUITreePane.this.loadHomePage();
	      }
	    });
	    String dataBoxIcon = "/com/toolkit2/client/images/home.png";
	    this.mainTree.setDataBoxIconURL(dataBoxIcon);
	 //   this.myTree.setDataBoxIconURL(dataBoxIcon);
	    this.customTree.setDataBoxIconURL(dataBoxIcon);

	    List roots = this.mainTree.getDataBox().getRootElements();
	    if ((roots != null) && (!roots.isEmpty())) {
	      Element element = (Element)roots.get(0);
	      this.mainTree.expand(element);
	    }
	  }
/****************************************************************************************
 * 	初始化用户 tree数据
 * 
 * ******/
	  private boolean initCustomTreeData() {
	    try {
//	      WorkFlowVO vo = CompanySettingServerActionManager.getInstance().getWorkFlowByName("Toolkit Work Flow");
//	      if ((vo == null) || (vo.getWorkFlowData() == null) || (vo.getWorkFlowData().length == 0)) {
//	        return false;
//	      }
//	      this.customTree.setData(vo.getWorkFlowData());
//	      if (this.customTree.isCustom())
//	        return true;
	    }
	    catch (Exception e)
	    {
	      ClientUtil.showException(e);
	    }
	    return false;
	  }

	  private void loadHomePage() {
	    loadHomePageImpl();
	  }

	  private synchronized void loadHomePageImpl() {
	    Element node = this.mainTree.getDataBox().getSelectionModel().lastElement();
	    if (node != null) {
	      String keyName = getHomepageKeyName(node);
	      SubForm homepage = (SubForm)cachedHomepages.get(keyName);
	      if (homepage != null) {
	        Shell.getInstance().showTab(homepage, singleHomepageClientUI);
	        singleHomepageClientUI = homepage;
	      } 
	    }
	  }

	  public SubForm getHomePage(Element element) {
	    String homeClass = getHomepageKeyName(element);
	    try {
	      SubForm ui = (SubForm)cachedHomepages.get(homeClass);
	      if (ui != null) {
	        return ui;
	      }
	      SubForm homepage = null;
	      boolean displayNetwork = homeClass.toLowerCase().endsWith(".xml");
	      if (displayNetwork) {
	        FreeNetwork network = FreeUtil.createNetwork(homeClass);
	        homepage = new SubForm();
	        homepage.setTitle(element.getName());
	        homepage.add(network, "Center");
	      } else {
	        homepage = (SubForm)Class.forName(homeClass).newInstance();
	      }
	      cachedHomepages.put(homeClass, homepage);
	      return homepage;
	    }
	    catch (Exception e) {
	      ClientUtil.showException(e);
	    }
	    return null;
	  }

//	  public void initMyTreeData() {
//	    this.mainTree.getDataBox().getSelectionModel().clearSelection();
//
//	    this.myTree.setDataBox(this.mainTree.getDataBox());
//	    this.myTree.setElementLabelGenerator(new Generator()
//	    {
//	      public Object generate(Object o)
//	      {
//	        if ((o instanceof Element)) {
//	          Element e = (Element)o;
//	          if (e.getParent() == null) {
//	            return GeneralUtil.getString("MainUITreePane.TabTitleMy2BizBox");
//	          }
//	          return e.getName();
//	        }
//	        return null;
//	      }
//	    });
//	    this.myTree.expandRoot();
//	  }

//	  public void adjustMyTreeVisible() {
//	    this.myTree.setDataBox(new TDataBox());
//	    this.myTree.setDataBox(this.mainTree.getDataBox());
//	    this.myTree.expand(1);
//	    boolean treeNotEmpty = this.myTree.getRowCount() > 1;
//	    if (!this.custom)
//	      if (treeNotEmpty) {
//	        this.tab.add(this.myTreePane, GeneralUtil.getString("MainUITreePane.TabTitleMy2BizBox"));
//	        this.tab.setSelectedComponent(this.myTreePane);
//	      } else {
//	        this.tab.remove(this.myTreePane);
//	      }
//	  }

	  public TTree getMainTree()
	  {
	    return this.mainTree;
	  }

	  public JPanel getMainTreePane() {
	    return this.mainTreePane;
	  }

	  public JTabbedPane getTabbedPane() {
	    return this.tab;
	  }

//	  public TTree getMyTree() {
//	    return this.myTree;
//	  }

	  public static void clearCache() {
	    cachedHomepages.clear();
	  }

	  public static void clearHomepageCache(Object key) {
	    cachedHomepages.remove(key);
	  }

	  public void setFocusForQuickSearchBox() {
	    this.txtQuickLaunch.getTextField().requestFocus();
	  }

//	  public QuickSearchBox getQuickSearchBox() {
//	    return this.txtQuickLaunch;
//	  }

	  public boolean isCustom() {
	    return this.custom;
	  }

//	  public CustomerMainTree getCustomTree() {
//	    return this.customTree;
//	  }

	  public Dimension getPreferredSize() {
	    return new Dimension(0, super.getPreferredSize().height);
	  }

	  static
	  {
	    CacheManager.registerCache(FreeMainUITreePane.class, "clearCache");
	  }

	}