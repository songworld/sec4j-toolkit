package com.toolkit2.client.component2ex.textfield;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.client.CacheManager;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.TKNode;

import twaver.DataBoxSelectionModel;
import twaver.TDataBox;
import twaver.list.TList;
import twaver.tree.TTree;

public class QuickActionTextField extends JTextField
{
  private static String SPLIT = "#";
  public static HashMap<String, String> actionMap = new HashMap();
 // private static HashMap<String, String> descriptionMap = new HashMap();
  private JPopupMenu popupMenu = new JPopupMenu();
  private TList list = new TList();
  private JScrollPane sp = new JScrollPane();
  private boolean action = true;
  private boolean isUpdate = false;
  public static boolean onEdit = false;
  private final String description = "Loading Description...";
  private boolean loading = false;

  public QuickActionTextField() {
    loadActionCodeMapFromXML();
    addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        String code = QuickActionTextField.this.getText();
        if (e.getKeyCode() == 10) {
          if (QuickActionTextField.this.popupMenu.isVisible()) {
            QuickActionTextField.this.selectNode();
            e.consume();
          } else {
            try {
              QuickActionTextField.this.doAction(code);
            } catch (Exception ex) {
              ClientUtil.showException(ex);
            }
          }
        }
        if ((e.getKeyCode() == 38) || (e.getKeyCode() == 40)) {
          if (!QuickActionTextField.this.popupMenu.isVisible()) {
            QuickActionTextField.this.actionCodeAssist();
          }
          QuickActionTextField.this.selectNext(e.getKeyCode() == 38);
          e.consume();
        }
      }
    });
    getDocument().addDocumentListener(new DocumentListener()
    {
      public void changedUpdate(DocumentEvent e) {
        QuickActionTextField.this.actionCodeAssist();
      }

      public void insertUpdate(DocumentEvent e) {
        QuickActionTextField.this.actionCodeAssist();
      }

      public void removeUpdate(DocumentEvent e) {
        QuickActionTextField.this.actionCodeAssist();
      }
    });
    this.popupMenu.add(this.sp);
    this.sp.getViewport().add(this.list);
    this.list.setIconVisible(false);
    this.list.setSelectionMode(0);
    this.list.addElementClickedActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        QuickActionTextField.this.selectNode();
      }
    });
   // descriptionMap = QuickActionCodeAssistUI.map;

    ClientUtil.setupWindow(this);
  }

  private void selectNext(boolean previous) {
    TKNode node = (TKNode)this.list.getDataBox().getSelectionModel().lastElement();
    int size = this.list.getDataBox().size();
    int index = node == null ? -1 : this.list.getDataBox().indexOf(node);
    if (index >= 0) {
      index = size - index - 1;
    }
    if (previous) {
      if (index <= 0)
        index = size - 1;
      else {
        index--;
      }
    }
    else if (index == size - 1)
      index = 0;
    else {
      index++;
    }

    this.list.setSelectedIndex(index);
    this.list.ensureIndexIsVisible(index);
  }

  private void selectNode() {
    if (null != actionMap.get(getText().substring(1, getText().length()))) {
      try {
        doAction(getText());
      } catch (Exception ex) {
        ClientUtil.showException(ex);
      }
    } else {
      if (this.list.getDataBox().isEmpty()) {
        return;
      }
      TKNode node = (TKNode)this.list.getDataBox().getSelectionModel().lastElement();
      if (node == null)
        node = (TKNode)this.list.getDataBox().getAllElementsReverse().iterator().next();
      try
      {
        doAction("/" + node.getUserObject());
      } catch (Exception ex) {
        ClientUtil.showException(ex);
      }
    }

    this.popupMenu.setVisible(false);
  }

  public void setText(String t) {
    this.isUpdate = true;
    super.setText(t);
    this.isUpdate = false;
  }

  protected String getPrefix() {
    return "/";
  }

  public void actionCodeAssist() {
//    String text = getText();
//    if ((this.isUpdate) || (!isAction()) || (!text.startsWith(getPrefix()))) {
//      this.popupMenu.setVisible(false);
//      return;
//    }
//    if ((descriptionMap.isEmpty()) && (!this.loading)) {
//      this.loading = true;
//      Thread t = new Thread()
//      {
//        public void run() {
//          QuickActionCodeAssistUI.loadActionCodeMapFromXML();
//          SwingUtilities.invokeLater(new Runnable()
//          {
//            public void run() {
//              QuickActionTextField.this.updateDescription();
//            }
//          });
//        }
//      };
//      t.start();
//    }
//    this.list.getDataBox().clear();
//    text = text.substring(getPrefix().length(), text.length());
//    Iterator it = actionMap.entrySet().iterator();
//    while (it.hasNext()) {
//      Map.Entry entry = (Map.Entry)it.next();
//      String code = (String)entry.getKey();
//      String regEx = text.toUpperCase();
//      boolean result = code.startsWith(regEx);
//      if (result) {
//        TKNode node = new TKNode();
//        String description = (String)descriptionMap.get(code);
//        if ((description == null) || (description.isEmpty())) {
//          getClass(); description = "Loading Description...";
//        }
//        node.setName(code + " " + description);
//        node.setIcon(null);
//        node.setUserObject(code);
//        this.list.getDataBox().addElement(node);
//      }
//    }
//    if (this.list.getDataBox().isEmpty()) {
//      this.popupMenu.setVisible(false);
//      return;
//    }
//    Point p = getLocation();
//    p.y += getPreferredSize().height;
//    p.x -= 40;
//    int width = getSize().width;
//    ClientUtil.setPreferredWidth(this.popupMenu, width + 25);
//    ClientUtil.setPreferredWidth(this.list, width);
//    this.popupMenu.show(this, p.x, p.y);
//    try {
//      SwingUtilities.invokeLater(new Runnable()
//      {
//        public void run() {
//          QuickActionTextField.this.requestFocus();
//        } } );
//    }
//    catch (Exception e) {
//      ClientUtil.showException(e);
//    }
  }

  protected Point getPopupMenuLocation() {
    Point p = getLocation();
    p.y += getPreferredSize().height;
    p.x -= 40;
    return p;
  }

  private void updateDescription() {
//    if (this.popupMenu.isVisible()) {
//      Iterator it = this.list.getDataBox().getAllElementsReverse().iterator();
//      while (it.hasNext()) {
//        TKNode node = (TKNode)it.next();
//        String code = node.getUserObject().toString();
//        String name = node.getName();
//        getClass(); if (name.equals(code + " " + "Loading Description..."))
//          node.setName(code + " " + (String)descriptionMap.get(code));
//      }
//    }
  }

  public void doAction(String code) throws Exception
  {
    if (!"/".equals(getPrefix())) {
      if (code.startsWith("/")) {
        code = code.substring(1, code.length());
      }
      setText(code);
      return;
    }
    if ((!isAction()) || (!code.startsWith("/"))) {
      return;
    }
    code = code.toUpperCase();
    setText(code);
    code = code.substring(1, code.length());
    String className = (String)actionMap.get(code);
    if ((className == null) || ("".equals(className))) {
      return;
    }
    String[] ss = className.split(SPLIT);
    if (ss.length != 2) {
      return;
    }
    className = ss[1];
    String type = ss[0];
    if ("Mrp".equals(type)) {
      if (Shell.getInstance().getMainTreePane().getMainTree().getDataBox().getSelectionModel().lastElement() != null) {
        Shell.getInstance().getMainTreePane().getMainTree().getDataBox().getSelectionModel().lastElement().setSelected(false);
      }
      String mrp = GeneralUtil.getString("MainTreeHelper.MRP");
      Shell.getInstance().getMainTreePane().getMainTree().getDataBox().getElementByName(mrp).setSelected(true);
//    } else if ("Add".equalsIgnoreCase(type)) {
//      addQuickAction(className);
//    } else if ("Update".equalsIgnoreCase(type)) {
//      updateQuickAction(className);
//    } else if ("Code".equalsIgnoreCase(type)) {
//      QuickActionCodeAssistUI ui = QuickActionCodeAssistUI.getInstance();
//      ui.setVisible();
    } else {
      lookupQuickAction(className);
    }
  }

  private void addQuickAction(String className) throws Exception {
//    if (className.equals("bb.gui.eng.trace.TraceHomePageSetPane")) {
//      lookupQuickActionforAdd(className);
//      return;
//    }
//    HomePageBaseSearchPane addPane = (HomePageBaseSearchPane)Class.forName(className).newInstance();
//    addPane.getSearchButton().doClick();
  }

  private void lookupQuickAction(String className) throws Exception {
//    HomePageBaseSearchPane panel = (HomePageBaseSearchPane)Class.forName(className).newInstance();
//    QuickActionUI dlg = new QuickActionUI(panel);
//    dlg.setVisible();
  }

  private void lookupQuickActionforAdd(String className) throws Exception {
//    HomePageBaseSearchPane panel = (HomePageBaseSearchPane)Class.forName(className).newInstance();
//    BaseHomePage.setAddPane(panel);
//    QuickActionUI dlg = new QuickActionUI(panel);
//    dlg.setVisible();
  }

  private void updateQuickAction(String className) throws Exception {
//    HomePageBaseSearchPane panel = (HomePageBaseSearchPane)Class.forName(className).newInstance();
//    final QuickActionUI dlg = new QuickActionUI(panel);
//    String title = dlg.getTitle();
//    title = title.replaceFirst("2LookUp", "2Update");
//    title = title.replaceFirst("2Lookup", "2Update");
//    title = title.replaceFirst("2Look up", "2Update");
//    title = title.replaceFirst("2Look UP", "2Update");
//    title = title.replaceFirst("2 LookUp", "2Update");
//    dlg.changeTitle(title);
//    dlg.setVisible();
//    dlg.addTabCloseListener(new MainTabCloseListener()
//    {
//      public void tabClosed() {
//        if (dlg.getCloseByUser()) {
//          return;
//        }
//        SwingUtilities.invokeLater(new Runnable()
//        {
//          public void run() {
//            ClientUI ui = (ClientUI)Shell.getInstance().getTabbedPane().getSelectedComponent();
//            UpdateButton btnUpdate = ClientUtil.getUpdateButton(ui);
//            if (btnUpdate != null) {
//              Shell.getInstance().removeTab(ui);
//              btnUpdate.doClick();
//            }
//          }
//        });
//      }
//
//      public boolean tabClosing() {
//        return true;
//      }
//    });
  }

  private void loadActionCodeMapFromXML() {
    try {
      actionMap.clear();
      InputStream is = QuickActionTextField.class.getResource("ActionCode.xml").openStream();
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      org.w3c.dom.Document doc = builder.parse(is);
      NodeList nodes = doc.getElementsByTagName("codeType");
      int length = nodes.getLength();
      for (int i = 0; i < length; i++) {
        org.w3c.dom.Element node = (org.w3c.dom.Element)nodes.item(i);
        String type = node.getAttribute("id");
        NodeList list = node.getElementsByTagName("code");
        int len = list.getLength();
        for (int j = 0; j < len; j++) {
          org.w3c.dom.Element child = (org.w3c.dom.Element)list.item(j);
          String id = child.getAttribute("id");
          String className = child.getAttribute("className");
          className = type + SPLIT + className;
          if (actionMap.get(id) == null)
            actionMap.put(id, className);
        }
      }
    }
    catch (Exception e)
    {
      ClientUtil.showException(e);
    }
  }

  public void setAction(boolean action) {
    this.action = action;
  }

  public boolean isAction() {
    return this.action;
  }

  public static void main(String[] s) {
    JFrame f = new JFrame("test");
    f.setLayout(new BorderLayout());
    f.add(new QuickActionTextField());
    f.pack();
    f.setVisible(true);
  }

  public static void clearCache() {
    actionMap.clear();
   // descriptionMap.clear();
  }

  public static JPopupMenu getAssistPopmenu(JPopupMenu menu, JList list, String text) {
    return menu;
  }

  static
  {
    CacheManager.registerCache(QuickActionTextField.class, "clearCache");
  }
}
