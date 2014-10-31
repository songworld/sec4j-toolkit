package com.toolkit2.client.component2ex.gantt;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.QuickTTreeTable;

import twaver.Element;
import twaver.TDataBox;
import twaver.table.TTableColumn;
import twaver.tree.TTree;
public class GanttTable extends QuickTTreeTable {
	private static final Calendar calendar = Calendar.getInstance();
	  private static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
	  private Date minDate = null;
	  private Date maxDate = null;
	  private int dayWidth = 50;
	  private int gap = 0;
	  private Font font = new Font(getFont().getName(), 0, (int)(getFont().getSize() * 0.8F));

	  public GanttTable(TDataBox box) {
	    super(box);
	    getTree().setRootVisible(false);
	    this.minDate = new Date();
	    calendar.setTime(this.minDate);
	    calendar.add(5, 10);
	    this.maxDate = calendar.getTime();
	    getColumnModel().getColumn(0).setIdentifier("Task");
	    TTableColumn column = new TTableColumn(WorkOrderUtil.getString("GanttTable.TimeSchedule"));
	    getColumnModel().addColumn(column);
	    column.setExtraWidthAssignable(true);
	    column.setMinPackWidth(600);
	    getTableHeader().setReorderingAllowed(false);
	    setAutoResizeMode(0);
	  }

	  private static int getDays(Date date1, Date date2)
	  {
	    if ((date1 != null) && (date2 != null)) {
	      date1 = getDateNoTime(date1);
	      date2 = getDateNoTime(date2);
	      long dayLength = 86400000L;
	      long ganttLength = date2.getTime() - date1.getTime();
	      if (ganttLength < 0L) {
	        return 0;
	      }
	      int result = (int)(ganttLength / dayLength);
	      return result + 1;
	    }
	    return 0;
	  }

	  private static Date getDateNoTime(Date date) {
	    calendar.setTime(date);
	    calendar.set(11, 0);
	    calendar.set(12, 0);
	    calendar.set(13, 0);
	    calendar.set(14, 0);
	    return calendar.getTime();
	  }

	  public int getDayWidth() {
	    return this.dayWidth;
	  }

	  public void setDayWidth(int dayWidth) {
	    this.dayWidth = dayWidth;
	    repaint();
	  }

	  private int getDateX(Date date) {
	    int days = getDays(this.minDate, date);
	    int treeColumnWidth = getGanttBounds().x;
	    return treeColumnWidth + (days - 1) * this.dayWidth;
	  }

	  private Rectangle getGanttBounds() {
	    int rowCount = getRowCount();
	    int columnCount = getColumnCount();
	    Rectangle rect1 = getCellRect(0, 1, true);
	    Rectangle rect2 = getCellRect(rowCount - 1, columnCount - 1, true);
	    rect1.add(rect2);
	    rect1.height -= 1;
	    return rect1;
	  }

	  private void paintDateGrid(Graphics2D g2d) {
	    int days = getDays(this.minDate, this.maxDate);
	    if (days > 0) {
	      Rectangle bounds = getGanttBounds();

	      int lastX = 0;
	      for (int day = 0; day < days; day++) {
	        int x = bounds.x + (day + 1) * this.dayWidth;
	        calendar.setTime(this.minDate);
	        calendar.add(5, day);
	        Date date = calendar.getTime();
	        boolean today = getDateNoTime(date).getTime() == getDateNoTime(new Date()).getTime();
	        if ((today) || (x - lastX > 70)) {
	          if (!today) {
	            lastX = x;
	          }
	          Color todayColor = Color.red.brighter();
	          if (today) {
	            g2d.setColor(todayColor);
	            g2d.setStroke(new BasicStroke(2.0F));
	          } else {
	            g2d.setColor(getGridColor());
	            g2d.setStroke(new BasicStroke(1.0F));
	          }
	          int y1 = bounds.y;
	          int y2 = bounds.y + bounds.height;
	          g2d.drawLine(x, y1, x, y2);
	          if (today)
	            g2d.setColor(todayColor);
	          else {
	            g2d.setColor(getForeground());
	          }
	          String text = format.format(date);
	          Rectangle2D textBounds = g2d.getFontMetrics().getStringBounds(text, g2d);
	          int textX = (int)(bounds.x + (this.dayWidth - textBounds.getWidth()) / 2.0D);
	          textX += day * this.dayWidth;
	          int textY = (int)textBounds.getHeight();
	          g2d.setFont(this.font);
	          g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	          g2d.drawString(text, textX, textY);
	        }
	      }
	    }
	  }

	  private void paintBackground(Graphics2D g2d) {
	    g2d.setColor(getBackground());
	    g2d.fill(getGanttBounds());
	    g2d.setColor(getSelectionBackground());
	    int[] rows = getSelectedRows();
	    for (int i = 0; i < rows.length; i++) {
	      int row = rows[i];
	      Rectangle bounds = getCellRect(row, 1, true);
	      g2d.fill(bounds);
	    }
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g;
	    paintBackground(g2d);
	    if (this.box.isEmpty()) {
	      return;
	    }
	    int days = getDays(this.minDate, this.maxDate);
	    if (days > 0) {
	      paintDateGrid(g2d);
	      int strokeWidth = 8;
	      BasicStroke stroke = new BasicStroke(strokeWidth, 2, 2);
	      for (int i = 0; i < getRowCount(); i++) {
	        Element element = getElementByRowIndex(i);
	        if ((element instanceof Task)) {
	          Task task = (Task)element;
	          Date startDate = task.getStartDate();
	          Date endDate = task.getEndDate();
	          if ((startDate != null) && (endDate != null)) {
	            int taskDays = getDays(startDate, endDate);
	            if (taskDays > 0) {
	              int x1 = getDateX(startDate);
	              int x2 = getDateX(endDate);
	              x2 += this.dayWidth;
	              int treeColumnWidth = getGanttBounds().x;
	              x1 = Math.max(treeColumnWidth, x1);
	              x2 = Math.max(treeColumnWidth, x2);
	              if (x1 < x2) {
	                x1 += strokeWidth / 2 + this.gap;
	                x2 -= strokeWidth / 2 + this.gap;
	                int y = getCellRect(i, 1, true).y + getRowHeight(i) / 2;
	                Line2D.Double line = new Line2D.Double(x1, y, x2, y);
	                Shape shape = stroke.createStrokedShape(line);
	                if (task.getColor() != null)
	                  g2d.setColor(task.getColor());
	                else {
	                  g2d.setColor(Color.lightGray);
	                }
	                g2d.fill(shape);
	                g2d.setColor(Color.black);
	                g2d.setStroke(new BasicStroke(1.0F));
	                g2d.draw(shape);
	              }
	            }
	          }
	        }
	      }
	    }
	  }

	  public Date getMaxDate() {
	    return this.maxDate;
	  }

	  public Date getMinDate() {
	    return this.minDate;
	  }

	  public void setMinDate(Date minDate) {
	    if (minDate == null) {
	      throw new NullPointerException("min date can not be null");
	    }

	    minDate = ClientUtil.getRoundDate(minDate);
	    this.minDate = minDate;

	    repaint();
	  }

	  public void setMaxDate(Date maxDate) {
	    if (maxDate == null) {
	      throw new NullPointerException("max date can not be null");
	    }

	    maxDate = ClientUtil.getRoundDate(maxDate);
	    this.maxDate = maxDate;

	    repaint();
	  }

	  public void pack()
	  {
	    Date min = null;
	    Date max = null;
	    Iterator it = this.box.iterator();
	    while (it.hasNext()) {
	      Object o = it.next();
	      if ((o instanceof Task)) {
	        Task task = (Task)o;
	        Date startDate = task.getStartDate();
	        Date endDate = task.getEndDate();
	        if ((endDate != null) && (startDate != null)) {
	          if ((min == null) || (min.after(startDate))) {
	            min = startDate;
	          }
	          if ((max == null) || (max.before(endDate))) {
	            max = endDate;
	          }
	        }
	      }
	    }
	    if ((min != null) && (max != null)) {
	      min = ClientUtil.getRoundDate(min);
	      max = ClientUtil.getRoundDate(max);
	      setMinDate(min);
	      setMaxDate(max);
	    }
	  }

	  public void setGanntColumnWidth(int width) {
	    int days = getDays(this.minDate, this.maxDate);
	    if (days > 0) {
	      int dayWidth = width / days;
	      setDayWidth(dayWidth);
	    }
	  }
}
