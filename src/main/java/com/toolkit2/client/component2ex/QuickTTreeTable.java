package com.toolkit2.client.component2ex;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.FontStyle;
import com.toolkit2.client.frame.free.FreeTableHeaderRenderer;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.ConfigurationBuilder;

import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.ElementAttribute;
import twaver.Generator;
import twaver.TDataBox;
import twaver.TUIManager;
import twaver.TWaverUtil;
import twaver.table.PageListener;
import twaver.table.TTableAdapter;
import twaver.table.TTableColumn;
import twaver.table.TTableModel;
import twaver.table.TTreeTable;
import twaver.tree.LazyLoader;
import twaver.tree.TTree;
import twaver.tree.TTreeCellIcon;
public class QuickTTreeTable extends TTreeTable
{
	  private int focusRow = -1;
	  private int focusColumn = -1;
	  private JButton btnDeleteButton = null;
	  private JButton btnUpdateButton = null;
	  private boolean showNoRecord;
	  protected HashMap<TTableColumn, String> sortByColumn = new HashMap();
	  private Hashtable<String, QuickLinkAction> QuickActionListeners = new Hashtable();
	  protected boolean invalidateColumns = true;

	  public QuickTTreeTable(TDataBox box) {
	    super(box);
	    init();
	  }

	  public QuickTTreeTable()
	  {
	    init();
	  }

	  public int getFocusColumn() {
	    return this.focusColumn;
	  }

	  public int getFocusRow() {
	    return this.focusRow;
	  }

	  public void setTreeCellRenderer(TreeCellRenderer treeCellRenderer) {
	    getTree().setCellRenderer(treeCellRenderer);
	  }

	  private void init() {
	    setTableHeaderPopupMenuFactory(null);
	    setColumnAutoResizable(false);
	    setHeadAutoResizable(false);
	    setElementIconGenerator();
	    if (getTableHeader() != null) {
	      getTableHeader().setReorderingAllowed(false);

	      getTableHeader().setDefaultRenderer(new FreeTableHeaderRenderer());
	    }

	    setRowHeight(20);
	    getTableModel().addPageListener(new PageListener()
	    {
	      public void pageChanged() {
	        QuickTTreeTable.this.invalidateColumns = true;
	      }
	    });
	    setTableBodyPopupMenuFactory(null);

	    getTableModel().addTableListener(new TTableAdapter()
	    {
	      public void rowClicked(int clickCount, Vector rowData, int columnIndex) {
	        if (clickCount == 1) {
	          TTableColumn column = QuickTTreeTable.this.getTableModel().getPublishedColumn(columnIndex);
	          TableCellRenderer renderer = column.getCellRenderer();
	          if ((renderer instanceof QuickLinkable)) {
	            Element fileElement = QuickTTreeTable.this.getElementByRowData(rowData);
	            if (QuickTTreeTable.this.getCursor() == Cursor.getPredefinedCursor(12))
	            {
	              String colName = column.getName();
	              if (colName.startsWith("CP:")) {
	                colName = colName.substring("CP:".length());
	              }
	              Object o = QuickTTreeTable.this.getQuickAction(colName);
	              if (o != null) {
//	                QuickLinkAction action = (QuickLinkAction)o;
//	                ClientUtil.showWaitCursor(QuickTTreeTable.this);
//	                action.QuickLinkClicked(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn, fileElement.getUserObject());
//	                ClientUtil.showDefaultCursor(QuickTTreeTable.this);
	              }
	            }
	          }
	        }
	      }
	    });
	    addMouseListener(new MouseAdapter()
	    {
	      public void mouseExited(MouseEvent e)
	      {
	        int oldFocusRow = QuickTTreeTable.this.focusRow;
	        int oldFocusCol = QuickTTreeTable.this.focusColumn;
	        focusRow = focusColumn = -1;
	        QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(oldFocusRow, oldFocusCol, true));
	      }

	      public void mouseClicked(MouseEvent e) {
	        if (e.getButton() == 1)
	          if ((QuickTTreeTable.this.focusRow != -1) && (QuickTTreeTable.this.focusColumn != -1)) {
	            Point p = e.getPoint();

	            QuickTTreeTable.this.focusRow = QuickTTreeTable.this.rowAtPoint(p);
	            QuickTTreeTable.this.focusColumn = QuickTTreeTable.this.columnAtPoint(p);

	            TTableColumn tCol = QuickTTreeTable.this.getTableModel().getPublishedColumn(QuickTTreeTable.this.focusColumn);
	            if (tCol == null) {
	              return;
	            }
	            String colName = tCol.getName();

	            if (colName.indexOf(":") >= 0) {
	              colName = colName.substring(colName.indexOf(":") + 1);
	            }

	            Object renderer = QuickTTreeTable.this.getCellRenderer(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn);
	            boolean clickTree = false;
	            if ((renderer instanceof TTree)) {
	              TTree tree = (TTree)renderer;
	              TreePath path = tree.getTreePathByElement(QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow));
	              if (!QuickTTreeTable.this.isLocationInExpandControl(tree, path, p.x - tree.getInsets().left, p.y)) {
	                renderer = QuickTTreeTable.this.getTree().getCellRenderer();
	                clickTree = true;
	              } else {
	                return;
	              }
	            }
//	            if ((renderer instanceof MRPActionRenderer))
//	              for (int i = 0; i < QuickTTreeTable.this.getTableModel().getColumnCount(); i++) {
//	                TableCellRenderer tempRenderer = QuickTTreeTable.this.getCellRenderer(QuickTTreeTable.this.focusRow, i);
//	                if (((tempRenderer instanceof PartRenderer)) || ((tempRenderer instanceof BB2PartRenderer)) || ((tempRenderer instanceof PartOnlyWithPARenderer)) || ((tempRenderer instanceof PartWithoutPARenderer)) || ((tempRenderer instanceof BB2PartWithoutPARenderer)))
//	                {
//	                  if (QuickTTreeTable.this.getCursor() == Cursor.getPredefinedCursor(12)) {
//	                    ClientUtil.showWaitCursor(QuickTTreeTable.this);
//	                    if (clickTree)
//	                      ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(QuickTTreeTable.this), QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow));
//	                    else {
//	                      ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(QuickTTreeTable.this), QuickTTreeTable.this.getValueAt(QuickTTreeTable.this.focusRow, i));
//	                    }
//	                    ClientUtil.showDefaultCursor(QuickTTreeTable.this);
//	                    return;
//	                  }
//	                }
//	              }
//	            else if ((renderer instanceof ActionRenderer)) {
//	              if (QuickTTreeTable.this.getCursor() == Cursor.getPredefinedCursor(12)) {
//	                ClientUtil.showWaitCursor(QuickTTreeTable.this);
//	                if ((renderer instanceof CustomRenderer)) {
//	                  ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(QuickTTreeTable.this), QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow).getUserObject());
//	                }
//	                else if (clickTree)
//	                  ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(QuickTTreeTable.this), QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow));
//	                else {
//	                  ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(QuickTTreeTable.this), QuickTTreeTable.this.getValueAt(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn));
//	                }
//
//	                ClientUtil.showDefaultCursor(QuickTTreeTable.this);
//	              }
//	            }
//	            else if ((renderer instanceof QuickLinkable))
//	            {
//	              if (QuickTTreeTable.this.getCursor() == Cursor.getPredefinedCursor(12))
//	              {
//	                QuickLinkAction action = QuickTTreeTable.this.getQuickAction(colName);
//	                if (action != null) {
//	                  ClientUtil.showWaitCursor(QuickTTreeTable.this);
//	                  Object vo = null;
//	                  vo = QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow).getUserObject();
//	                  action.QuickLinkClicked(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn, vo);
//	                  ClientUtil.showDefaultCursor(QuickTTreeTable.this);
//	                }
//	              }
//	            }
	          }
	          else {
	            QuickTTreeTable.this.getSelectionModel().clearSelection();
	          }
	      }
	    });
	    addMouseMotionListener(new MouseMotionAdapter()
	    {
	      public void mouseMoved(MouseEvent e) {
	        if (QuickTTreeTable.this.getCursor().equals(Cursor.getPredefinedCursor(11))) {
	          return;
	        }
	        Point p = e.getPoint();
	        int oldFocusRow = QuickTTreeTable.this.focusRow;
	        int oldFocusCol = QuickTTreeTable.this.focusColumn;
	        QuickTTreeTable.this.focusRow = QuickTTreeTable.this.rowAtPoint(p);
	        QuickTTreeTable.this.focusColumn = QuickTTreeTable.this.columnAtPoint(p);

	        if ((QuickTTreeTable.this.focusRow == -1) || (QuickTTreeTable.this.focusColumn == -1)) {
	          return;
	        }
	        Object renderer = QuickTTreeTable.this.getCellRenderer(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn);
	        if ((renderer instanceof TTree)) {
	          TTree tTree = (TTree)renderer;
	          if (QuickTTreeTable.this.isLocationInExpandControl(tTree, tTree.getTreePathByElement(QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow)), p.x, p.y)) {
	            ClientUtil.showDefaultCursor(QuickTTreeTable.this);
	          } else {
	            renderer = QuickTTreeTable.this.getTree().getCellRenderer();
//	            if ((renderer instanceof TreeQuickLinkable)) {
//	              if (((TreeQuickLinkable)renderer).isTreeQuickLinkable(QuickTTreeTable.this.getElementByRowIndex(QuickTTreeTable.this.focusRow)))
//	                ClientUtil.showQuickCursor(QuickTTreeTable.this);
//	              else
//	                ClientUtil.showDefaultCursor(QuickTTreeTable.this);
//	            }
//	            else {
	              ClientUtil.showDefaultCursor(QuickTTreeTable.this);
	       //     }
	          }
	          QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(oldFocusRow, oldFocusCol, true));
	          QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn, true));
	        } else if ((oldFocusRow != QuickTTreeTable.this.focusRow) || (oldFocusCol != QuickTTreeTable.this.focusColumn)) {
	          if ((QuickTTreeTable.this.focusRow != -1) && (QuickTTreeTable.this.focusColumn != -1)) {
	            if ((renderer instanceof QuickLinkable)) {
//	              if (((QuickLinkable)renderer).isQuickLinkable(QuickTTreeTable.this, QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn))
//	                ClientUtil.showQuickCursor(QuickTTreeTable.this);
//	              else
	                ClientUtil.showDefaultCursor(QuickTTreeTable.this);
	            }
	            else {
	              ClientUtil.showDefaultCursor(QuickTTreeTable.this);
	            }

	            QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(oldFocusRow, oldFocusCol, true));
	            QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn, true));
	          } else {
	            QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(oldFocusRow, oldFocusCol, true));
	            QuickTTreeTable.this.repaint(QuickTTreeTable.this.getCellRect(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn, true));
	            ClientUtil.showDefaultCursor(QuickTTreeTable.this);
	          }
	        }
	      }
	    });
	    addKeyListener(new KeyAdapter()
	    {
	      public void keyPressed(KeyEvent e) {
	        if (e.getKeyCode() == 27) {
	          SubForm win = ClientUtil.getClientUIForComponent(QuickTTreeTable.this);
//	          if (((win instanceof DetailUI)) || ((win instanceof AbstractListUI)))
//	            win.dispose();
	        }
	      }
	    });
	    Action action = new AbstractAction()
	    {
	      public void actionPerformed(ActionEvent e) {
	        if (QuickTTreeTable.this.getSelectedRowCount() <= 1) {
	          int row = QuickTTreeTable.this.getSelectedRow();
	          Rectangle cellBounds = QuickTTreeTable.this.getCellRect(row, 0, false);
	          int x = cellBounds.width / 2 + cellBounds.x;
	          int y = cellBounds.height / 2 + cellBounds.y;
	          MouseEvent event = new MouseEvent(QuickTTreeTable.this, 500, System.currentTimeMillis(), 16, x, y, 2, false);
	          QuickTTreeTable.this.dispatchEvent(event);
	        }
	      }
	    };
	    initExtendColumn();
	    getActionMap().put("BB2_ENTER_ACTION", action);
	    KeyStroke stroke = KeyStroke.getKeyStroke(10, 0, false);
	    getInputMap().put(stroke, "BB2_ENTER_ACTION");

	    getColumnByIndex(0).setDisplayName("");
	    getTableModel().addTableListener(new TTableAdapter()
	    {
	      public void rowClicked(int clickCount, Vector rowData, int columnIndex) {
	        if (clickCount != 2) {
	          return;
	        }
	        if (QuickTTreeTable.this.btnUpdateButton != null)
	          QuickTTreeTable.this.btnUpdateButton.doClick();
	      }
	    });
	    getDataBox().getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener()
	    {
	      public void selectionChanged(DataBoxSelectionEvent arg0) {
	        List selections = QuickTTreeTable.this.getDataBox().getSelectionModel().getAllSelectedElement();
	        if ((selections == null) || (selections.size() != 1)) {
	          if (QuickTTreeTable.this.btnDeleteButton != null) {
	            QuickTTreeTable.this.btnDeleteButton.setEnabled(false);
	          }
	          if (QuickTTreeTable.this.btnUpdateButton != null)
	            QuickTTreeTable.this.btnUpdateButton.setEnabled(false);
	        }
	        else {
	          if (QuickTTreeTable.this.btnDeleteButton != null) {
	            QuickTTreeTable.this.btnDeleteButton.setEnabled(true);
	          }
	          if (QuickTTreeTable.this.btnUpdateButton != null)
	            QuickTTreeTable.this.btnUpdateButton.setEnabled(true);
	        }
	      }
	    });
	    addMouseListener(new MouseAdapter()
	    {
	      public void mouseReleased(MouseEvent e) {
	        if (e.getButton() == 3)
	        {
	          int row = QuickTTreeTable.this.rowAtPoint(new Point(e.getX(), e.getY()));
	          int col = QuickTTreeTable.this.columnAtPoint(new Point(e.getX(), e.getY()));
	          if (row >= 0) {
	            QuickTTreeTable.this.setRowSelectionInterval(row, row);
	          }
	          final Element node = QuickTTreeTable.this.getElementByRowIndex(row);
	          final String text;
	          if (col == 0) {
	            if (node == null) {
	              return;
	            }
	            text = ClientUtil.getStringFromObject(node.getName());
	          }
	          else {
	            text = ClientUtil.getStringFromObject(QuickTTreeTable.this.getValueAt(row, col));
	          }
	          JPopupMenu popupMenu = new JPopupMenu();
	          JMenuItem copyButton = new JMenuItem(Translator.getString("ClientUtil.Copy"));
	          popupMenu.add(copyButton);
	          copyButton.setEnabled(false);
	          if (!text.equalsIgnoreCase("")) {
	            copyButton.setEnabled(true);
	          }
	          copyButton.addActionListener(new ActionListener()
	          {
	            public void actionPerformed(ActionEvent e) {
	              StringSelection ss = new StringSelection(text);
	              Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	            }
	          });
	          boolean canExpand = false;
	          if ((QuickTTreeTable.this.getTree().getLazyLoader() == null) && (node != null))
	            canExpand = node.childrenSize() > 0;
	          else if (node != null) {
	            canExpand = QuickTTreeTable.this.canExpand(node);
	          }
	          if (canExpand) {
	            if (QuickTTreeTable.this.getTree().isExpanded(node)) {
	              JMenuItem collapseButton = new JMenuItem(Translator.getString("ClientUtil.Collapse"));
	              popupMenu.add(collapseButton);
	              collapseButton.addActionListener(new ActionListener()
	              {
	                public void actionPerformed(ActionEvent e) {
	                  QuickTTreeTable.this.getTree().collapseAll(node);
	                } } );
	            }
	            else {
	              JMenuItem expandButton = new JMenuItem(Translator.getString("ClientUtil.Expand"));
	              popupMenu.add(expandButton);
	              expandButton.addActionListener(new ActionListener()
	              {
	                public void actionPerformed(ActionEvent e) {
	                  QuickTTreeTable.this.getTree().expandAll(node);
	                }
	              });
	            }
	          }
	          popupMenu.show(QuickTTreeTable.this, e.getX(), e.getY());
	        }
	      }
	    });
	    JTableHeader header = getTableHeader();
	    header.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e) {
	        Point p = e.getPoint();
	        QuickTTreeTable.this.focusRow = QuickTTreeTable.this.rowAtPoint(p);
	        QuickTTreeTable.this.focusColumn = QuickTTreeTable.this.columnAtPoint(p);
	        if ((e.getButton() == 1) && 
	          (QuickTTreeTable.this.focusRow != -1) && (QuickTTreeTable.this.focusColumn != -1)) {
	          TableCellRenderer renderer = QuickTTreeTable.this.getCellRenderer(QuickTTreeTable.this.focusRow, QuickTTreeTable.this.focusColumn);
//	          if ((renderer instanceof FiscalYearUpdateTreeTable.CopyToButtonRenderer))
//	            QuickTTreeTable.this.actionSonMethod(e, renderer);
	        }
	      }
	    });
	  }

	  public int getDepthOffset(JTree tree)
	  {
	    int depthOffset;
	    if (tree.isRootVisible())
	    {
	      if (tree.getShowsRootHandles())
	        depthOffset = 1;
	      else
	        depthOffset = 0;
	    }
	    else
	    {
	      if (tree.getShowsRootHandles())
	        depthOffset = 0;
	      else
	        depthOffset = -1;
	    }
	    return depthOffset;
	  }

	  protected int getRowX(JTree tree, int row, int depth) {
	    int rightChildIndent = ((Integer)UIManager.get("Tree.rightChildIndent")).intValue();
	    int leftChildIndent = ((Integer)UIManager.get("Tree.leftChildIndent")).intValue();
	    int totalChildIndent = rightChildIndent + leftChildIndent;
	    return totalChildIndent * (depth + getDepthOffset(tree));
	  }

	  private int findCenteredX(boolean leftToRight, int x, int iconWidth) {
	    return leftToRight ? x - (int)Math.ceil(iconWidth / 2.0D) : x - (int)Math.floor(iconWidth / 2.0D);
	  }

	  public boolean isLocationInExpandControl(JTree tree, TreePath path, int mouseX, int mouseY) {
	    Icon expandedIcon = (Icon)UIManager.get("Tree.expandedIcon");
	    boolean leftToRight = tree.getComponentOrientation().isLeftToRight();
	    int rightChildIndent = ((Integer)UIManager.get("Tree.rightChildIndent")).intValue();
	    int leftChildIndent = ((Integer)UIManager.get("Tree.leftChildIndent")).intValue();
	    if ((path != null) && (!tree.getModel().isLeaf(path.getLastPathComponent())))
	    {
	      Insets i = tree.getInsets();
	      int boxWidth;
	      if (expandedIcon != null)
	        boxWidth = expandedIcon.getIconWidth();
	      else
	        boxWidth = 8;
	      int boxLeftX = getRowX(tree, tree.getRowForPath(path), path.getPathCount() - 1);
	      if (leftToRight)
	        boxLeftX = boxLeftX + i.left - rightChildIndent + 1;
	      else {
	        boxLeftX = tree.getWidth() - boxLeftX - i.right + leftChildIndent - 1;
	      }
	      boxLeftX = findCenteredX(leftToRight, boxLeftX, boxWidth);
	      boolean b = (mouseX >= boxLeftX) && (mouseX < boxLeftX + boxWidth);
	      return b;
	    }
	    return false;
	  }

	  public void packColumns()
	  {
	    this.invalidateColumns = true;
	  }

	  public void initExtendColumn()
	  {
	    boolean hasExtendColumn = false;
	    for (int i = 0; i < getTableModel().getColumnCount(); i++) {
	      TTableColumn column = getTableModel().getPublishedColumn(i);
	      String displayName = column.getDisplayName();
	      if (displayName.indexOf(":") >= 0) {
	        displayName = ConfigurationBuilder.getI18NValue(displayName);
	      }
	      column.setDisplayName(displayName);
	      if (column.isExtraWidthAssignable()) {
	        hasExtendColumn = true;
	        column.setMaxPackWidth(100);
	      }
	      if ((column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.comment").toLowerCase()) != -1) || (column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.description").toLowerCase()) != -1) || (column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.note").toLowerCase()) != -1) || (column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.remark").toLowerCase()) != -1))
	      {
	        hasExtendColumn = true;
	        column.setExtraWidthAssignable(true);
	        column.setMaxPackWidth(100);
	      }
	      if (!column.getName().equals("table.column.tree"))
	      {
	        ElementAttribute attribute = column.getElementAttribute();
	        String orderBy = (String)attribute.getParam("sortBy");
	        if ((orderBy != null) && (!"".equalsIgnoreCase(orderBy)))
	          this.sortByColumn.put(column, orderBy);
	      }
	    }
	    if (!hasExtendColumn)
	      for (int i = 0; i < getTableModel().getColumnCount(); i++) {
	        TTableColumn column = getTableModel().getPublishedColumn(i);
	        if (!column.getName().equals("table.column.tree")) {
	          column.setExtraWidthAssignable(true);
	          column.setMaxPackWidth(100);
	        }
	      }
	  }

	  public void setQuickAction(String columnName, QuickLinkAction action)
	  {
	    if (getTableModel().getColumnByName(columnName) == null) {
	      return;
	    }
	    TableCellRenderer renderer = getTableModel().getColumnByName(columnName).getCellRenderer();
	    if ((renderer instanceof QuickLinkable))
	      this.QuickActionListeners.put(columnName, action);
	    else
	      throw new IllegalArgumentException("column " + columnName + " are not Quick link cell," + " can't set Quick action.");
	  }

	  public QuickLinkAction getQuickAction(String columnName)
	  {
	    return (QuickLinkAction)this.QuickActionListeners.get(columnName);
	  }

	  private void setQuickActions(Map actions)
	  {
	    if ((actions != null) && (actions.size() != 0)) {
	      Set keys = actions.keySet();
	      Iterator it = keys.iterator();
	      while (it.hasNext()) {
	        String colName = (String)it.next();
	        QuickLinkAction action = (QuickLinkAction)actions.get(colName);
	        setQuickAction(colName, action);
	      }
	    }
	  }

	  public void installTableData(Object data)
	  {
	    if (data == null) {
	      return;
	    }
	    if ((data != null) && ((data instanceof Collection)) && (((Collection)data).size() == 0)) {
	      packColumns();
	      return;
	    }
	    if ((data != null) && ((data instanceof Collection)) && (((Collection)data).size() != 0)) {
	      setData(data);
	    }
	    if ((data != null) && (!(data instanceof Collection))) {
	      setData(data);
	    }
	    setQuickActions(getQuickActions());
	  }

	  protected int getRow(int y, int row, int rowHeight) {
	    int result = 0;
	    if (row == 0) {
	      result = y / rowHeight;
	    } else {
	      for (int i = 0; i < row; i++) {
	        y -= getRowHeight(i);
	      }
	      result = y / rowHeight;
	    }
	    return result;
	  }

	  protected void setData(Object data)
	  {
	  }

	  public boolean isValueExist(String property, Object value)
	  {
	    return isValueExist(property, value, null);
	  }

	  public boolean isValueExist(String property, Object value, Element selectedNode) {
	    return isValueExist(property, value, selectedNode, true, false);
	  }

	  public boolean isValueExist(String property, Object value, Element selectedNode, boolean ignoreCase, boolean rootLevel)
	  {
	    for (Iterator i$ = (rootLevel ? getDataBox().getRootElements() : getDataBox().getAllElements()).iterator(); i$.hasNext(); ) { Object node = i$.next();
	      if ((selectedNode == null) || (!selectedNode.equals(node)))
	      {
	        Object tempValue = ((Element)node).getClientProperty("bb2." + property);
	        if (((value instanceof String)) && ((tempValue instanceof String)) && (ignoreCase)) {
	          if (((String)value).equalsIgnoreCase((String)tempValue)) {
	            return true;
	          }
	        }
	        else if (value.equals(tempValue)) {
	          return true;
	        }
	      }
	    }
	    return false;
	  }

	  public TKNode addElement(Object value) {
	    TKNode element = new TKNode();
	    element.setUserObject(value);
	    getDataBox().addElement(element);
	    return element;
	  }

	  protected Map getQuickActions()
	  {
	    return null;
	  }

	  public void setDeleteButton(JButton btnDelete) {
	    this.btnDeleteButton = btnDelete;
	    this.btnDeleteButton.setEnabled(false);
	  }

	  public void setUpdateButton(JButton btnUpdate) {
	    this.btnUpdateButton = btnUpdate;
	    this.btnUpdateButton.setEnabled(false);
	  }

	  public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
	    Component component = super.prepareRenderer(renderer, row, col);
	    if ((renderer instanceof CustomBackground)) {
	      return component;
	    }
	    if (getElementByRowIndex(row) == null) {
	      return component;
	    }
	    if ((this.focusRow == row) && (this.focusColumn == col) && ((renderer instanceof QuickLinkable))) {
//	      if ((renderer instanceof DefaultQuickRenderer)) {
//	        if (((DefaultQuickRenderer)renderer).isQuickLinkable(this, row, col))
//	          component.setBackground(QuickLinkable.FOCUS_COLOR);
//	      }
//	      else if (((renderer instanceof BooleanQuickRenderer)) && 
//	        (((BooleanQuickRenderer)renderer).getIcon() != null)) {
//	        component.setBackground(QuickLinkable.FOCUS_COLOR);
//	      }
	    }

//	    if ((isCellEditable(row, col)) && (isEditable()) && (!(renderer instanceof PanelRenderer)) && (col != 0)) {
//	      ((JComponent)component).setBorder(BorderFactory.createLineBorder(Color.RED));
//	    }
	    return component;
	  }

	  public void paintComponent(Graphics g) {
	    if (this.invalidateColumns) {
	      packAllColumns(true, 0);
	      this.invalidateColumns = false;
	    }
	    super.paintComponent(g);
	    if (this.showNoRecord) {
	      g.setFont(FontStyle.getInstance().getCurrentHighlightFont());
//	      BaseListTable.getNoRecordLabel().getBounds();
//	      g.drawString(BaseListTable.getNoRecordLabel().getText(), (getWidth() - BaseListTable.getNoRecordLabel().getPreferredSize().width) / 2, (getHeight() - BaseListTable.getNoRecordLabel().getPreferredSize().height) / 2);
	    }
	  }

	  public synchronized boolean isShowNoRecord()
	  {
	    return this.showNoRecord;
	  }

	  public synchronized void setShowNoRecord(boolean showNoRecord) {
	    this.showNoRecord = showNoRecord;
	    SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run() {
	        QuickTTreeTable.this.repaint();
	      }
	    });
	  }

	  public TableCellEditor getCellEditor(int row, int column) {
	    TableCellEditor editor = super.getCellEditor(row, column);
	    Component c = editor.getTableCellEditorComponent(this, getValueAt(row, column), false, row, column);
	    if (c != null) {
	      c.setFont(getFont());
	    }
	    return editor;
	  }

	  public OrderBy getOrderBy() {
	    LinkedHashMap map = new LinkedHashMap();
	    Iterator it = getTableModel().getSortColumnList().iterator();
	    while (it.hasNext()) {
	      TTableColumn column = (TTableColumn)it.next();
	      if (this.sortByColumn.containsKey(column))
	      {
	        String sorModel;
	        if (column.getSortMode() == 1)
	          sorModel = "asc";
	        else {
	          sorModel = "desc";
	        }
	        map.put(this.sortByColumn.get(column), sorModel);
	      }
	    }
	    if (map.isEmpty()) {
	      return null;
	    }
	    OrderBy orderBy = new OrderBy();
	    orderBy.setOrderBy(map);
	    return orderBy;
	  }

	  public void setElementIconGenerator()
	  {
	    getTree().setElementIconGenerator(new Generator()
	    {
	      public Object generate(Object object) {
	        Element node = (Element)object;
	        TTreeCellIcon icon = new TTreeCellIcon(node, QuickTTreeTable.this.getTree())
	        {
	          protected Icon getElementIcon()
	          {
	            String url;
	            if (this.tree.getLazyLoader() == null)
	            {
	              if (this.element.childrenSize() == 0) {
	                url = "leafIcon";
	              }
	              else
	              {
	                if (this.tree.isExpanded(this.element))
	                  url = "openIcon";
	                else
	                  url = "closedIcon";
	              }
	            }
	            else
	            {
	              if (this.tree.getLazyLoader().isLeaf(this.element)) {
	                url = "leafIcon";
	              }
	              else
	              {
	                if (this.tree.isExpanded(this.element))
	                  url = "openIcon";
	                else {
	                  url = "closedIcon";
	                }
	              }
	            }
	            Color color = (Color)TUIManager.getElementBodyColorGenerator().generate(this.element);
	            return TWaverUtil.getImageIcon(url, color);
	          }
	        };
	        return icon;
	      }
	    });
	  }

	  protected boolean canExpand(Element element) {
	    return false;
	  }

	  public void copyOrderBy(QuickTTreeTable table) {
	    if (table == null) {
	      return;
	    }
	    getTableModel().sortNoneColumn();
	    Iterator it = table.getTableModel().getSortColumnList().iterator();
	    int i = 0;
	    while (it.hasNext()) {
	      TTableColumn fcolumn = (TTableColumn)it.next();
	      TTableColumn column = getColumnByName(fcolumn.getName());
	      if (column != null) {
	        getTableModel().sortColumn(column, fcolumn.getSortMode(), i != 0);
	        i++;
	      }
	    }
	  }

	  public HashMap<TTableColumn, String> getSortByColumn() {
	    return this.sortByColumn;
	  }

	  public void actionSonMethod(MouseEvent e, TableCellRenderer renderer)
	  {
	  }
	}