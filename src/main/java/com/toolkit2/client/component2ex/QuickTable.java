package com.toolkit2.client.component2ex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.toolkit2.Util.ClientContext;
import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.FontStyle;
import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.Util.StringUtil;
import com.toolkit2.client.frame.free.FreeTableHeaderRenderer;
import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.component2ex.base.StrikethroughFilter;
import com.toolkit2.client.i18n.Translator;

import twaver.TWaverUtil;
import twaver.table.PageListener;
import twaver.table.TTable;
import twaver.table.TTableColumn;
import twaver.table.TTableModel;

public class QuickTable extends TTable{
	public static final Color INTERVAL_COLOR = new Color(238, 238, 238);
	  protected int focusRow = -1;
	  protected int focusColumn = -1;
	  private static final int COLUMN_INDEX_OFFSET = 2;
	  private Hashtable<Integer, QuickLinkAction> QuickActionListeners = new Hashtable();
	  private boolean showNoRecord;
	  protected boolean invalidateRows = true;
	  protected boolean invalidateColumns = true;
	  private StrikethroughFilter strikethroughFilter = null;
	  private ArrayList<Integer> strikeList = new ArrayList();
	  private Color fillColor = new Color(100, 200, 200, 100);
	  private Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
	  private Color backgroundEven = Color.white;
	  private Color backgroundOdd = FreeUtil.TABLE_ODD_ROW_COLOR;
	  private Color selectedTextColor = Color.BLACK;
	  private Color verticalGridColor = Color.white;
	  
	  
	  public QuickTable()
	  {
	    init();
	  }

	  private void init() {
	    setColumnResizable(false);
	    setTableHeaderPopupMenuFactory(null);
	    setRowHeight(20);
	    setColumnAutoResizable(false);
	    setHeadAutoResizable(false);
	    if (getTableHeader() != null) {
	      getTableHeader().setReorderingAllowed(false);
	    }

	    setTableBodyPopupMenuFactory(null);
	    setColumnIdentifiers(StringUtil.toUpperCaseFirstWord(getHeaderNames()));
	    setRenderers(getRenderers());
	    setEditors(getEditors());
	    setQuickActions(getQuickActions());

	    boolean hasExtendColumn = false;
	    for (int i = 0; i < getColumnCount(); i++) {
	      TTableColumn column = getColumnByIndex(i);
	      if ((column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.comment").toLowerCase()) != -1) || (column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.description").toLowerCase()) != -1) || (column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.note").toLowerCase()) != -1) || (column.getName().toLowerCase().indexOf(Translator.getString("BaseListTable.remark").toLowerCase()) != -1))
	      {
	        hasExtendColumn = true;
	        column.setExtraWidthAssignable(true);
	        column.setMaxPackWidth(100);
	      }

	      column.setHeaderRenderer(new FreeTableHeaderRenderer());

	      if ((column.getRenderer() instanceof MaxRowHeight)) {
	        column.setRowPackParticipable(true);
	      }
	      column.setMinPackWidth(15);
	    }
	    if (!hasExtendColumn) {
	      for (int i = 0; i < getColumnCount(); i++) {
	        TTableColumn column = getTableModel().getPublishedColumn(i);
	        column.setExtraWidthAssignable(true);
	        column.setMaxPackWidth(100);
	      }
	    }
	    if (!ClientContext.isEnglishFontOnly()) {
	      getTableHeader().setFont(FontStyle.getInstance().getNativeFont());
	    }
	    getTableModel().addPageListener(new PageListener()
	    {
	      public void pageChanged() {
	        QuickTable.this.invalidateRows = true;
	        QuickTable.this.invalidateColumns = true;
	      }
	    });
	    addMouseListener(new MouseAdapter()
	    {
	      public void mouseExited(MouseEvent e)
	      {
	        int oldFocusRow = QuickTable.this.focusRow;
	        int oldFocusCol = QuickTable.this.focusColumn;
	        QuickTable.this.focusRow = (QuickTable.this.focusColumn = -1);
	        QuickTable.this.repaint(QuickTable.this.getCellRect(oldFocusRow, oldFocusCol, true));
	      }

	      public void mouseReleased(MouseEvent e)
	      {
	    	  QuickTable.this.doMouseClicked(e);
	      }
	    });
	    addMouseMotionListener(new MouseMotionAdapter()
	    {
	      public void mouseMoved(MouseEvent e) {
//	        if (QuickTable.this.getCursor().equals(Cursor.getPredefinedCursor(11))) {
//	          return;
//	        }
//	        QuickTable.this.doMouseMoved(e);
	      }
	    });
	    addKeyListener(new KeyAdapter()
	    {
	      public void keyPressed(KeyEvent e) {
//	        if (e.getKeyCode() == 27) {
//	          SubForm win = ClientUtil.getClientUIForComponent(QuickTable.this);
//	          if (((win instanceof DetailUI)) || ((win instanceof AbstractListUI)))
//	            win.dispose();
//	        }
	      }
	    });
	    Action action = new AbstractAction()
	    {
	      public void actionPerformed(ActionEvent e) {
	        if (QuickTable.this.getSelectedRowCount() <= 1) {
	          int row = QuickTable.this.getSelectedRow();
	          Rectangle cellBounds = QuickTable.this.getCellRect(row, 0, false);
	          int x = cellBounds.width / 2 + cellBounds.x;
	          int y = cellBounds.height / 2 + cellBounds.y;
	          MouseEvent event = new MouseEvent(QuickTable.this, 500, System.currentTimeMillis(), 16, x, y, 2, false);
	          QuickTable.this.dispatchEvent(event);
	        }
	      }
	    };
	    getActionMap().put("BB2_ENTER_ACTION", action);
	    KeyStroke stroke = KeyStroke.getKeyStroke(10, 0, false);
	    getInputMap().put(stroke, "BB2_ENTER_ACTION");
	    try
	    {
	      setFont(FreeUtil.getFont12Plain());
	      getTableHeader().setFont(FreeUtil.getFont12Plain());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    setBorder(null);
	    setRowSelectionAllowed(true);
	    setShowGrid(false);
	    setShowHorizontalLines(false);
	    setShowVerticalLines(true);
	    setGridColor(this.verticalGridColor);
	    setRowMargin(0);
	    setRowHeight(15);
	    setBackground(Color.white);

	    ClientUtil.addCopyTextPopupMenuToTable(this);
	    packColumns();
	  }

	  public void doMouseMoved(MouseEvent e) {
	    Point p = e.getPoint();
	    int oldFocusRow = this.focusRow;
	    int oldFocusCol = this.focusColumn;
	    this.focusRow = rowAtPoint(p);
	    this.focusColumn = columnAtPoint(p);

	    if ((oldFocusRow != this.focusRow) || (oldFocusCol != this.focusColumn))
	      if ((this.focusRow != -1) && (this.focusColumn != -1))
	      {
	        repaint(getCellRect(oldFocusRow, oldFocusCol, true));
	        repaint(getCellRect(this.focusRow, this.focusColumn, true));
	        TableCellRenderer renderer = getCellRenderer(this.focusRow, this.focusColumn);
	        if (renderer == null) {
	          ClientUtil.showDefaultCursor(this);
	          return;
	        }
	        if ((renderer instanceof QuickLinkable)) {
//	          if (((QuickLinkable)renderer).isQuickLinkable(this, this.focusRow, this.focusColumn))
//	            ClientUtil.showQuickCursor(this);
//	          else
	            ClientUtil.showDefaultCursor(this);
	        }
	        else {
	          ClientUtil.showDefaultCursor(this);
	        }

	        repaint(getCellRect(oldFocusRow, oldFocusCol, true));
	        repaint(getCellRect(this.focusRow, this.focusColumn, true));
	      } else {
	        repaint(getCellRect(oldFocusRow, oldFocusCol, true));
	        repaint(getCellRect(this.focusRow, this.focusColumn, true));
	        ClientUtil.showDefaultCursor(this);
	      }
	  }

	  public void doMouseClicked(MouseEvent e)
	  {
	    doMouseMoved(e);
//	    if (e.getButton() == 1)
//	      if ((this.focusRow != -1) && (this.focusColumn != -1)) {
//	        Point p = e.getPoint();
//
//	        this.focusRow = rowAtPoint(p);
//	        this.focusColumn = columnAtPoint(p);
//
//	        TTableColumn tCol = getTableModel().getPublishedColumn(this.focusColumn);
//	        String colName = tCol.getName();
//
//	        int rawIndex = getTableModel().getRawColumnIndexByName(colName);
//	        TableCellRenderer renderer = getCellRenderer(this.focusRow, this.focusColumn);
//	        if ((renderer instanceof MRPActionRenderer)) {
//	          for (int i = 0; i < getTableModel().getColumnCount(); i++) {
//	            TableCellRenderer tempRenderer = getCellRenderer(this.focusRow, i);
//	            if (((tempRenderer instanceof PartRenderer)) || ((tempRenderer instanceof BB2PartRenderer)) || ((tempRenderer instanceof PartWithoutPARenderer)) || ((tempRenderer instanceof BB2PartWithoutPARenderer)))
//	            {
//	              if (getCursor() == Cursor.getPredefinedCursor(12)) {
//	                ClientUtil.showWaitCursor(this);
//	                ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(this), getValueAt(this.focusRow, i));
//	                ClientUtil.showDefaultCursor(this);
//	                return;
//	              }
//	            }
//	          }
//	          if (((renderer instanceof QuickLinkable)) && 
//	            (getCursor() == Cursor.getPredefinedCursor(12)))
//	          {
//	            QuickLinkAction action = getQuickAction(rawIndex);
//	            if (action != null) {
//	              ClientUtil.showWaitCursor(this);
//	              action.QuickLinkClicked(this.focusRow, this.focusColumn, getValueAt(this.focusRow, this.focusColumn));
//	              ClientUtil.showDefaultCursor(this);
//	            }
//	          }
//	        }
//	        else if ((renderer instanceof ActionRenderer)) {
//	          if (getCursor() == Cursor.getPredefinedCursor(12)) {
//	            ClientUtil.showWaitCursor(this);
//	            if ((renderer instanceof JTable)) {
//	              JTable table = (JTable)renderer.getTableCellRendererComponent(this, getValueAt(this.focusRow, this.focusColumn), false, false, this.focusRow, this.focusColumn);
//	              ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(this), table.getValueAt(getRow(e.getY(), this.focusRow, table.getRowHeight()), 0));
//	            } else if ((renderer instanceof CustomRenderer)) {
//	              Vector currentRow = (Vector)getTableModel().getSelectedRows().get(0);
//	              Object o = currentRow.get(currentRow.size() - 1);
//	              ((CustomRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(this), o);
//	            } else {
//	              ((ActionRenderer)renderer).actionPerformed(ClientUtil.getClientUIForComponent(this), getValueAt(this.focusRow, this.focusColumn));
//	            }
//	            ClientUtil.showDefaultCursor(this);
//	          }
//	        }
//	        else if (((renderer instanceof QuickLinkable)) && 
//	          (getCursor() == Cursor.getPredefinedCursor(12)))
//	        {
//	          QuickLinkAction action = getQuickAction(rawIndex);
//	          if (action != null) {
//	            ClientUtil.showWaitCursor(this);
//	            action.QuickLinkClicked(this.focusRow, this.focusColumn, getValueAt(this.focusRow, this.focusColumn));
//	            ClientUtil.showDefaultCursor(this);
//	          }
//	        }
//	      }
//	      else
//	      {
//	        getSelectionModel().clearSelection();
//	      }
	  }

	  public void setQuickAction(int columnIndex, QuickLinkAction action)
	  {
	    TableCellRenderer renderer = getColumn(columnIndex).getCellRenderer();
	    columnIndex += 2;
	    if ((renderer instanceof QuickLinkable))
	      this.QuickActionListeners.put(new Integer(columnIndex), action);
	    else
	      throw new IllegalArgumentException("column " + columnIndex + " are not Quick link cell," + " can't set Quick action.");
	  }

	  public QuickLinkAction getQuickAction(int columnIndex)
	  {
	    return (QuickLinkAction)this.QuickActionListeners.get(new Integer(columnIndex));
	  }

	  public int getFocusColumn() {
	    return this.focusColumn;
	  }

	  public int getFocusRow() {
	    return this.focusRow;
	  }

	  public void packColumns()
	  {
	    this.invalidateColumns = true;
	    this.invalidateRows = true;
	    if ((getRowCount() == 0) && (!(getParent() instanceof JViewport)))
	      packAllColumns(true, 0);
	  }

	  public TTableColumn getColumn(int columnIndex)
	  {
	    TTableModel model = getTableModel();
	    return (TTableColumn)model.getRawColumn().get(columnIndex + 2);
	  }

	  public void setColumnIdentifiers(Object[] colNames)
	  {
	    getTableModel().clearAllColumns();
	    if ((colNames == null) || (colNames.length == 0)) {
	      return;
	    }
	    List cols = new ArrayList();
	    for (int i = 0; i < colNames.length; i++) {
	      Object name = colNames[i];
	      if (name == null) {
	        name = "";
	      }
	      cols.add(new TTableColumn(name.toString()));
	    }
	    addColumns(cols);
	  }

	  private void setRenderers(Map renderers)
	  {
	    if ((renderers != null) && (renderers.size() != 0)) {
	      Set keys = renderers.keySet();
	      Iterator it = keys.iterator();
	      while (it.hasNext()) {
	        Integer colIndex = (Integer)it.next();
	        TTableColumn col = getColumn(colIndex.intValue());
	        Object obj = renderers.get(colIndex);
	        if ((obj instanceof TableCellRenderer))
	          col.setCellRenderer((TableCellRenderer)obj);
	      }
	    }
	  }

	  private void setEditors(Map editors)
	  {
	    if ((editors != null) && (editors.size() != 0)) {
	      Set keys = editors.keySet();
	      Iterator it = keys.iterator();
	      while (it.hasNext()) {
	        Integer colIndex = (Integer)it.next();
	        TTableColumn col = getColumn(colIndex.intValue());
	        Object obj = editors.get(colIndex);
	        col.setCellEditor((TableCellEditor)obj);
	      }
	    }
	  }

	  private void setQuickActions(Map actions)
	  {
	    if ((actions != null) && (actions.size() != 0)) {
	      Set keys = actions.keySet();
	      Iterator it = keys.iterator();
	      while (it.hasNext()) {
	        Integer colIndex = (Integer)it.next();
	        QuickLinkAction action = (QuickLinkAction)actions.get(colIndex);
	        setQuickAction(colIndex.intValue(), action);
	      }
	    }
	  }

	  public void installTableData(Object data)
	  {
	    getTableModel().clearRawData();
	    if ((data == null) || (((data instanceof Collection)) && (((Collection)data).isEmpty()))) {
	      setShowNoRecord(true);
	    } else {
	      setShowNoRecord(false);
	      setData(data);
	    }
	    packColumns();
	  }

	  protected void setData(Object data)
	  {
	  }

	  protected Map getRenderers()
	  {
	    return null;
	  }

	  protected Map getEditors()
	  {
	    return null;
	  }

	  protected Map getQuickActions()
	  {
	    return null;
	  }

	  protected String[] getHeaderNames()
	  {
	    return null;
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

	  public Color getRowColor(int row) {
	    if (row % 2 == 0) {
	      return INTERVAL_COLOR;
	    }
	    return Color.WHITE;
	  }

	  public void prepareTableHeaderRenderer(JLabel label, TTableColumn column)
	  {
	    super.prepareTableHeaderRenderer(label, column);
	  }

	  public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
	    Component component = super.prepareRenderer(renderer, row, col);
	    Color oldColor = component.getBackground();
	    int[] rows = getSelectedRows();
	    int i = 0;
	    boolean selected = false;
	    while (i < rows.length) {
	      if (row == rows[i]) {
	        selected = true;
	        break;
	      }
	      i++;
	    }
	    //设置字体，字体一般为了一直性应从配置文件中读取，目前直接写死，后续修改
	    component.setFont(FreeUtil.getFont12Plain());
	    ((JComponent)component).setBorder(this.border);
	    if (!selected) {
	      if ((renderer instanceof CustomBackground)) {
	        if ((oldColor != null) && (!oldColor.equals(UIManager.getColor("Table.background")))) {
	          component.setBackground(oldColor);
	        }
	      }
	      else if (row % 2 == 1) {
	        component.setBackground(this.backgroundOdd);
	      }
	      else {
	        component.setBackground(this.backgroundEven);
	      }

	      component.setForeground(Color.BLACK);
	    } else {
	      component.setForeground(this.selectedTextColor);
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

//	    if ((isCellEditable(row, col)) && (!(renderer instanceof PanelRenderer))) {
//	      ((JComponent)component).setBorder(BorderFactory.createLineBorder(ColorStore.BACKGROUND_WARNING_FIELD));
//	    }
	    return component;
	  }

	  public TableCellEditor getCellEditor(int row, int column) {
	    TableCellEditor editor = super.getCellEditor(row, column);
	    Component c = editor.getTableCellEditorComponent(this, getValueAt(row, column), false, row, column);
	    c.setFont(getFont());
	    return editor;
	  }

	  protected void paintChildren(Graphics g) {
	    super.paintChildren(g);
	    this.strikeList.clear();
	    if (this.strikethroughFilter != null) {
	      Graphics2D g2d = (Graphics2D)g;
	      for (int i = 0; i < super.getRowCount(); i++) {
	        Object data = getRowDataByRowIndex(i).lastElement();
	        boolean needStrikethrough = this.strikethroughFilter.needStrikethrough(data);
	        if (needStrikethrough) {
	          this.strikeList.add(Integer.valueOf(i + 2));
	          Rectangle firstBounds = getCellRect(i, 0, true);
	          Rectangle lastBounds = getCellRect(i, getColumnCount() - 1, true);
	          firstBounds.add(lastBounds);
	          Rectangle rowBounds = firstBounds;
	          g.setColor(this.fillColor);
	          g2d.fill(rowBounds);
	          g2d.setColor(Color.red);
	          g2d.setStroke(TWaverUtil.createStroke(1));
	          g.drawLine(rowBounds.x, rowBounds.y + rowBounds.height / 2, rowBounds.x + rowBounds.width, rowBounds.y + rowBounds.height / 2);
	        }
	      }
	    }
	  }

	  public void paintComponent(Graphics g) {
	    if (this.invalidateColumns) {
	      packAllColumns(true, 0);
	      this.invalidateColumns = false;
	    }
	    if (this.invalidateRows) {
	      packAllRows();
	      this.invalidateRows = false;
	    }
	    super.paintComponent(g);
	    if (this.showNoRecord)
	    {
	      g.setFont(FreeUtil.getFont12Plain());

	      getNoRecordLabel().getBounds();
	      g.setColor(Color.lightGray);
	      g.drawString(getNoRecordLabel().getText(), (getWidth() - getNoRecordLabel().getPreferredSize().width) / 2, (getHeight() - getNoRecordLabel().getPreferredSize().height) / 2);
	    }
	  }

	  public synchronized boolean isShowNoRecord() {
	    return this.showNoRecord;
	  }

	  public synchronized void setShowNoRecord(boolean showNoRecord) {
	    this.showNoRecord = showNoRecord;
	    SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run() {
	        QuickTable.this.repaint();
	      }
	    });
	  }

	  public void setStrikethroughFilter(StrikethroughFilter strikethroughFilter) {
	    this.strikethroughFilter = strikethroughFilter;
	  }

	  public StrikethroughFilter getStrikethroughFilter() {
	    return this.strikethroughFilter;
	  }

	  public ArrayList<Integer> getStrikeList() {
	    return this.strikeList;
	  }

	  public static final JLabel getNoRecordLabel() {
	    return new JLabel(GeneralUtil.getString("QuickTable.NoData"));
	  }
	}
