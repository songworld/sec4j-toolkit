package com.toolkit2.client.i18n;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.frame.free.FreeListRenderer;
import com.toolkit2.client.component2ex.CommonUI;
import com.toolkit2.client.component2ex.DisplayTextField;

import twaver.Element;
import twaver.Group;
import twaver.TWaverUtil;
import twaver.network.TNetwork;
import twaver.swing.LabelValueLayout;
import twaver.tree.TTree;
/******************************************************************************
 * 	调试信息界面
 * 
 * **/
public class DebugResourceUI extends JFrame
{
  private static long lastMouseMovedTime = 0L;
  private static MouseEvent lastMouseEvent = null;
  private static boolean ctrlPressed = false;
  private static DebugResourceUI instance = new DebugResourceUI();
  private static boolean started = false;
  private JTextField txtMyBundleFileName = createReadOnlyTextField();
  private JTextField txtPickedText = createReadOnlyTextField();
  private JTextField txtPickedTextWithoutHtml = createReadOnlyTextField();
  private JTextField txtBundle = createReadOnlyTextField();
  private JTextField txtLanguage = createReadOnlyTextField();
  private JTextField txtLineNumber = createReadOnlyTextField();
  private JTextField txtKey = createReadOnlyTextField();
  private JTextField txtLineString = createReadOnlyTextField();
  private JTextField txtOldTranslation = createReadOnlyTextField();
  private JTextField txtOldMyTranslation = createReadOnlyTextField();
  private JTextArea txtNewTranslation = createTextArea();
  private JTextField txtNewLineString = createReadOnlyTextField();
  private JTextField txtNewLineStringUnicode = createReadOnlyTextField();
  private JButton btnSaveChange = new JButton();
  private JButton btnInputPick = new JButton();
  private JButton btnSaveFile = new JButton();

  private DebugResourceUI() {
    JPanel bigPane = new JPanel(new LabelValueLayout(10, 2, false));
    bigPane.add(new JLabel("My Bundle File:"));
    bigPane.add(this.txtMyBundleFileName);
    bigPane.add(new JLabel("Picked:"));
    bigPane.add(this.txtPickedText);
    bigPane.add(new JLabel("Without HTML:"));
    bigPane.add(this.txtPickedTextWithoutHtml);
    bigPane.add(new JLabel("Language:"));
    bigPane.add(this.txtLanguage);
    bigPane.add(new JLabel("Resource Bundle:"));
    bigPane.add(this.txtBundle);
    bigPane.add(new JLabel("Line #:"));
    bigPane.add(this.txtLineNumber);
    bigPane.add(new JLabel("Key:"));
    bigPane.add(this.txtKey);
    bigPane.add(new JLabel("Line String:"));
    bigPane.add(this.txtLineString);
    bigPane.add(new JLabel("Old Translation:"));
    bigPane.add(this.txtOldTranslation);
    bigPane.add(new JLabel("Old My Translation:"));
    bigPane.add(this.txtOldMyTranslation);
    bigPane.add(new JLabel("New Translation:"));
    bigPane.add(new JScrollPane(this.txtNewTranslation));
    bigPane.add(new JLabel("Change Preview:"));
    bigPane.add(this.txtNewLineString);
    bigPane.add(new JLabel("Unicode Preview:"));
    bigPane.add(this.txtNewLineStringUnicode);
    bigPane.setBorder(ClientUtil.createTitleBorder("General"));
    JPanel buttonPane = new JPanel(new FlowLayout(4, 0, 0));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.btnSaveChange.setText("Save Change");
    this.btnSaveChange.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        DebugResourceUI.this.save();
      }
    });
    buttonPane.add(this.btnSaveChange);
    this.btnInputPick.setText("Input");
    this.btnInputPick.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        DebugResourceUI.this.input();
      }
    });
    buttonPane.add(this.btnInputPick);
    this.btnSaveFile.setText("Save File");
    this.btnSaveFile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        DebugResourceUI.this.saveToFile();
      }
    });
    buttonPane.add(this.btnSaveFile);
    JPanel centerPane = new JPanel(new BorderLayout());
    centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
    centerPane.add(bigPane, "Center");
    getContentPane().add(centerPane, "Center");
    getContentPane().add(buttonPane, "South");
    pack();
    setLocation(100, 100);
    setTitle("2BizBox i18n Helper");
    setDefaultCloseOperation(1);
    setIconImage(TWaverUtil.getImage("/free/images/logo.png"));
    this.txtNewTranslation.getDocument().addDocumentListener(new DocumentListener()
    {
      public void insertUpdate(DocumentEvent e) {
        checkUpdate();
      }

      public void removeUpdate(DocumentEvent e) {
        checkUpdate();
      }

      public void changedUpdate(DocumentEvent e) {
        checkUpdate();
      }

      private void checkUpdate() {
        DebugResourceUI.this.updateTranslation();
      }
    });
    //ClientUtil.setupWindow(this);
    Font font = this.txtNewTranslation.getFont();
    this.txtNewTranslation.setFont(new Font("Dialog", font.getStyle(), font.getSize()));
  }

  private void saveToFile() {
    save();
    DebugResourceBundle.saveAllMyTranslation();
    this.btnSaveFile.setEnabled(false);
  }

  private void save() {
    DebugResourceBundle bundle = DebugResourceBundle.getResourceBundlesByName(this.txtBundle.getText());
    String myNewTranslation = this.txtNewTranslation.getText().trim();
    bundle.addMyTranslation(this.txtKey.getText(), myNewTranslation);
    this.btnSaveChange.setEnabled(false);
  }

  private void input() {
    String id = CommonUI.getInputText(this, "Input i18n resource ID");
    if ((id != null) && (!id.trim().isEmpty())) {
      clearTextField();
      String pickedString = "[" + id.toUpperCase() + "]";
      setPickedText(pickedString);
    }
  }

  private void updateTranslation() {
    String myTranslation = this.txtOldMyTranslation.getText();
    boolean hasMyTranslation = (myTranslation != null) && (!myTranslation.isEmpty());
    boolean same = this.txtNewTranslation.getText().equals(this.txtOldTranslation.getText());
    if (hasMyTranslation) {
      same = this.txtNewTranslation.getText().equals(myTranslation);
    }
    this.btnSaveChange.setEnabled(!same);
    this.btnSaveFile.setEnabled(this.btnSaveChange.isEnabled());
    String newLineString = this.txtKey.getText() + "=" + this.txtNewTranslation.getText();
    this.txtNewLineString.setText(newLineString);
    this.txtNewLineStringUnicode.setText(ClientUtil.toUnicode(newLineString));
  }

  private JTextArea createTextArea() {
    JTextArea area = new JTextArea();
    area.setLineWrap(true);
    area.setRows(5);
    area.setWrapStyleWord(false);
    return area;
  }
  /**************************************************************************
   * 	创建只读字段
   * 
   * **/
  private JTextField createReadOnlyTextField() {
    DisplayTextField textField = new DisplayTextField();
    textField.setColumns(50);
    return textField;
  }
/**************************************************************************
 * 	控件内容清空
 * 
 * **/
  private void clearTextField() {
    this.txtPickedText.setText("");
    this.txtPickedTextWithoutHtml.setText("");
    this.txtBundle.setText("");
    this.txtLanguage.setText("");
    this.txtLineNumber.setText("");
    this.txtKey.setText("");
    this.txtLineString.setText("");
    this.txtOldTranslation.setText("");
    this.txtOldMyTranslation.setText("");
    this.txtNewTranslation.setText("");
    this.txtNewLineString.setText("");
    this.txtNewLineStringUnicode.setText("");
  }
  /**************************************************************************
   * 	设置截取信息
   * 
   * **/
  private void setPickedText(String text) {
    clearTextField();
    this.txtPickedText.setText(text);
    if ((text != null) && (!text.trim().isEmpty())) {
      text = text.trim();
      text = ClientUtil.getStringFromHtml(text);
      this.txtPickedTextWithoutHtml.setText(text);
      text = ClientUtil.replaceString(text, "\n", "");
      if ((text != null) && (!text.trim().isEmpty())) {
        boolean hasLeftBracket = text.contains("[");
        boolean hasRightBracket = text.contains("]");
        if ((hasRightBracket) && (hasLeftBracket)) {
          int leftBracketIndex = text.indexOf("[");
          int rightBracketIndex = text.indexOf("]");
          if (leftBracketIndex < rightBracketIndex) {
            String resourceBundleID = text.substring(leftBracketIndex + 1, leftBracketIndex + 3);
            DebugResourceBundle bundle = DebugResourceBundle.getResourceBundlesByID(resourceBundleID);
            if (bundle != null)
              try {
                int lineNumber = Integer.valueOf(text.substring(leftBracketIndex + 3, rightBracketIndex)).intValue();
                String key = bundle.getKeyByLineNumber(lineNumber);
                String lineString = bundle.getLineStringByLineNumber(lineNumber);
                Object translateValue = bundle.superHandleGetObject(key);
                String myTranslation = bundle.getMyTranslation(key);
                String translation = "";
                if (translateValue != null) {
                  translation = translateValue.toString();
                }
                String myBundleFileName = DebugResourceBundle.MY_I18N_PATH + bundle.getBundleFileName();
                myBundleFileName = ClientUtil.replaceString(myBundleFileName, "/", "\\");
                this.txtMyBundleFileName.setText(myBundleFileName);
                this.txtLineString.setText(lineString);
                this.txtNewLineString.setText(lineString);
                this.txtKey.setText(key);
                this.txtBundle.setText(bundle.getBundleFileName());
                this.txtLanguage.setText(bundle.getLocale().toString());
                this.txtLineNumber.setText(lineNumber + "");
                this.txtOldTranslation.setText(translation);
                this.txtOldMyTranslation.setText(myTranslation);
                if (myTranslation == null)
                  this.txtNewTranslation.setText(translation);
                else
                  this.txtNewTranslation.setText(myTranslation);
              }
              catch (NumberFormatException e)
              {
              }
          }
        }
      }
    }
  }
  /**************************************************************************
   * 	取得鼠标最后的活动室I教案
   * 
   * **/
  private static synchronized long getLastMouseMovedTime() {
    return lastMouseMovedTime;
  }

  private static synchronized MouseEvent getLastMouseEvent() {
    return lastMouseEvent;
  }

  private static synchronized void updateMouseMovedEvent() {
    updateMouseMovedEvent(null);
  }

  private static synchronized void updateMouseMovedEvent(MouseEvent lastMouseEvent) {
    lastMouseMovedTime = System.currentTimeMillis();
    if (lastMouseEvent != null)
      lastMouseEvent = lastMouseEvent;
  }

  private static synchronized void setCtrlPressed(boolean pressed)
  {
    ctrlPressed = pressed;
  }

  private static synchronized boolean isCtrlPressed() {
    return ctrlPressed;
  }

  private static synchronized void reset() {
    lastMouseMovedTime = 0L;
    lastMouseEvent = null;
    ctrlPressed = false;
  }

  public static void start() {
    if (started) {
      return;
    }
    Thread timerThread = new Thread()
    {
      public void run() {
        while (true)
          try {
            Thread.sleep(100L);
            if (DebugResourceUI.lastMouseMovedTime != 0L) {
              long now = System.currentTimeMillis();
              long lasted = now - DebugResourceUI.getLastMouseMovedTime();
              if ((lasted < 500L) || (!DebugResourceUI.isCtrlPressed())) {
            	  DebugResourceUI.trigger();
					DebugResourceUI.reset();
              }
            }
          }
          catch (Exception ex)
          {
          }
      }
    };
    timerThread.start();

    AWTEventListener hotkeyListener = new AWTEventListener()
    {
      public void eventDispatched(AWTEvent event) {
        if ((event instanceof MouseEvent)) {
          MouseEvent e = (MouseEvent)event;
          if (e.getID() == 503) {
            DebugResourceUI.updateMouseMovedEvent(e);
          }
        }
        if ((event instanceof KeyEvent)) {
          KeyEvent e = (KeyEvent)event;
          if ((e.getModifiers() & 0x2) != 0) {
            if ((e.getID() == 401) && 
              (!DebugResourceUI.isCtrlPressed())) {
              DebugResourceUI.setCtrlPressed(true);
              DebugResourceUI.updateMouseMovedEvent();
            }

            if (e.getID() == 402)
              DebugResourceUI.setCtrlPressed(false);
          }
        }
      }
    };
    Toolkit.getDefaultToolkit().addAWTEventListener(hotkeyListener, 32L);
    Toolkit.getDefaultToolkit().addAWTEventListener(hotkeyListener, 8L);
    started = true;
  }

  private static void trigger() {
    MouseEvent e = getLastMouseEvent();
    if (e == null) {
      return;
    }
    Object eventSource = e.getSource();
    if ((eventSource instanceof Component)) {
      Component eventSourceComponent = (Component)eventSource;
      Window eventWindow = ClientUtil.getWindowForComponent(eventSourceComponent);

      if (eventWindow != instance) {
        Point point = e.getPoint();
        Component source = findComponentAt(eventSourceComponent, point.x, point.y);
        String result = "";
        if (source != null) {
          Method getTextMethod = null;
          Method getTooltipTextMethod = null;
          try {
            getTextMethod = source.getClass().getMethod("getText", new Class[0]);
            getTooltipTextMethod = source.getClass().getMethod("getToolTipText", new Class[0]);
          }
          catch (Exception ex) {
          }
          String value = null;
          try {
            if (getTextMethod != null)
              value = (String)getTextMethod.invoke(source, new Object[0]);
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
          if (((value == null) || (value.trim().isEmpty())) && 
            (getTooltipTextMethod != null))
            try {
              value = (String)getTooltipTextMethod.invoke(source, new Object[0]);
            }
            catch (Exception exception)
            {
            }
          if (value != null) {
            result = value;
          }
        }
        instance.setPickedText(result);
        if ((result != null) && (!result.isEmpty()))
          instance.setVisible(true);
      }
    }
  }
  /**************************************************************************
   * 	获取指定位置的组件
   * 
   * **/

  private static Component findComponentAt(Component component, int x, int y)
  {
    if (component.contains(x, y)) {
      while (component != null) {
        if ((component instanceof JScrollPane)) {
          JScrollPane scroll = (JScrollPane)component;
          component = (JComponent)scroll.getViewport().getView();
          x += scroll.getViewport().getViewPosition().x;
          y += scroll.getViewport().getViewPosition().y;
        }
        if ((component instanceof JTabbedPane)) {
          JTabbedPane tab = (JTabbedPane)component;
          BasicTabbedPaneUI tabUI = (BasicTabbedPaneUI)tab.getUI();
          int index = tabUI.tabForCoordinate(tab, x, y);
          if (index >= 0) {
            Component tabComponent = tab.getTabComponentAt(index);
            if (tabComponent == null) {
              String title = tab.getTitleAt(index);
              JLabel label = new JLabel();
              label.setText(title);
              label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
              return label;
            }
            component = (JComponent)tabComponent;
            x -= component.getX();
            y -= component.getY();
          }
          else {
            component = (JComponent)tab.getSelectedComponent();
            x -= component.getX();
            y -= component.getY();
          }
        }
        if ((component instanceof JTableHeader)) {
          JTableHeader header = (JTableHeader)component;
          int index = header.columnAtPoint(new Point(x, y));
          if (index != -1) {
            TableColumn column = header.getColumnModel().getColumn(index);
            TableCellRenderer render = column.getCellRenderer();
            if (render == null) {
              render = header.getDefaultRenderer();
            }
            JTable table = header.getTable();
            Object headerValue = column.getHeaderValue();
            JLabel label = new JLabel();
            if ((headerValue instanceof String)) {
              label.setText(headerValue.toString());
            } else {
              component = (JComponent)render.getTableCellRendererComponent(table, headerValue, false, false, -1, index);
              if ((component instanceof JLabel)) {
                label.setText(((JLabel)component).getText());
              }
            }
            label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
            return label;
          }
        }
        if ((component instanceof JTable)) {
          JTable table = (JTable)component;
          Point p = new Point(x, y);
          int column = table.columnAtPoint(p);
          int row = table.rowAtPoint(p);
          if ((column >= 0) && (row >= 0)) {
            TableCellRenderer render = table.getCellRenderer(row, column);
            if (render != null) {
              Object value = table.getValueAt(row, column);
              component = (JComponent)render.getTableCellRendererComponent(table, value, false, false, row, column);
              if ((component instanceof JLabel)) {
                JLabel label = new JLabel();
                label.setText(((JLabel)component).getText());
                label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
                return label;
              }
            }
          }
        }
        if ((component instanceof TTree)) {
          TTree tree = (TTree)component;
          int row = tree.getRowForLocation(x, y);
          Element element = tree.getElementByRowIndex(row);
          if (element != null) {
            String value = element.getName();
            JLabel label = new JLabel();
            label.setText(value);
            label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
            return label;
          }
        }
        if ((component instanceof ComboBoxEditor)) {
          ComboBoxEditor editor = (ComboBoxEditor)component;
          JLabel label = new JLabel();
          Object value = editor.getItem();
          if (value != null) {
            label.setText(value.toString());
          }
          label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
          return label;
        }
        if ((component instanceof JList)) {
          JList list = (JList)component;

          int index = list.locationToIndex(new Point(x, y));
          if (index != -1) {
            ListCellRenderer render = list.getCellRenderer();
            Object value = list.getModel().getElementAt(index);
            if ((render instanceof FreeListRenderer)) {
              if ((value instanceof Group)) {
                JPanel pane = (JPanel)render.getListCellRendererComponent(list, value, index, false, false);
                pane = (JPanel)pane.getComponent(0);
                component = pane.getComponent(1);
              } else {
                JPanel pane = (JPanel)render.getListCellRendererComponent(list, value, index, false, false);
                component = pane.getComponent(0);
              }
            }
            else component = (JComponent)render.getListCellRendererComponent(list, value, index, false, false);

            if ((component instanceof JLabel)) {
              JLabel label = new JLabel();
              label.setText(((JLabel)component).getText());
              label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
              return label;
            }
          }
        }
        if ((component instanceof JComboBox)) {
          JComboBox combo = (JComboBox)component;
          ListCellRenderer render = combo.getRenderer();
          return (JComponent)render;
        }
        if ((component instanceof MetalComboBoxButton)) {
          JComboBox combo = ((MetalComboBoxButton)component).getComboBox();
          ListCellRenderer render = combo.getRenderer();
          return (JComponent)render;
        }
        if (component.getClass().getName().equals("twaver.network.TNetwork$_A")) {
          TNetwork network = (TNetwork)component.getParent().getParent().getParent().getParent();
          Element element = network.getElementPhysicalAt(x, y);
          if (element != null) {
            JLabel label = new JLabel();
            label.setText(element.getName());
            label.setBounds(0, 0, label.getPreferredSize().width * 2, label.getPreferredSize().height);
            return label;
          }
        }
        if ((component instanceof Window)) {
          if ((component instanceof JFrame)) {
            JFrame frame = (JFrame)component;
            component = frame.getContentPane();
            x -= frame.getRootPane().getX();
            y -= frame.getRootPane().getY();
          }
          if ((component instanceof JDialog)) {
            JDialog dialog = (JDialog)component;
            component = dialog.getContentPane();
            x -= dialog.getRootPane().getX();
            y -= dialog.getRootPane().getY();
          }
          if ((component instanceof JWindow)) {
            JWindow window = (JWindow)component;
            component = window.getContentPane();
            x -= window.getRootPane().getX();
            y -= window.getRootPane().getY();
          }
        }
        if ((!(component instanceof Container)) || (((Container)component).getComponentCount() <= 0)) break;
        JComponent nextComponent = (JComponent)component.getComponentAt(x, y);
        if ((nextComponent == null) || (nextComponent == component)) break;
        int nextX = nextComponent.getX();
        int nextY = nextComponent.getY();
        x -= nextX;
        y -= nextY;
        component = nextComponent;
      }

    }

    return component;
  }
}