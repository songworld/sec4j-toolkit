package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import javax.swing.JTable;
 import javax.swing.table.JTableHeader;
 import javax.swing.table.TableCellRenderer;
 
 public class FreeTable extends JTable
 {
   private Color verticalGridColor = Color.white;
   private FreeTableCellRenderer renderer = new FreeTableCellRenderer();
   private FreeTableHeaderRenderer headerRenderer = new FreeTableHeaderRenderer();
 
   public FreeTable() {
     init();
   }
 
   private void init() {
     setFont(FreeUtil.FONT_12_PLAIN);
     getTableHeader().setFont(FreeUtil.FONT_12_BOLD);
     getTableHeader().setDefaultRenderer(this.headerRenderer);
     setBorder(null);
     setRowSelectionAllowed(true);
     setShowHorizontalLines(false);
     setShowVerticalLines(true);
     setGridColor(this.verticalGridColor);
     setRowMargin(0);
   }
 
   public TableCellRenderer getCellRenderer(int row, int column)
   {
     return this.renderer;
   }
 
   public boolean isCellEditable(int row, int column)
   {
     return false;
   }
 }
