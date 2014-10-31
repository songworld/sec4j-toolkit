package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Component;
 import javax.swing.BorderFactory;
 import javax.swing.JTable;
 import javax.swing.border.Border;
 import javax.swing.table.DefaultTableCellRenderer;
 
 public class FreeTableCellRenderer extends DefaultTableCellRenderer
 {
   private Color backgroundEven = Color.white;
   private Color backgroundOdd = FreeUtil.TABLE_ODD_ROW_COLOR;
   private Color backgroundSelected = new Color(255, 223, 156);
   private Color selectedTextColor = Color.BLACK;
   private Color textColor = FreeUtil.TABLE_TEXT_COLOR;
   private Border border = BorderFactory.createEmptyBorder(0, 5, 0, 0);
 
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
   {
     super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
     setFont(FreeUtil.TABLE_CELL_FONT);
     setBorder(this.border);
     if (!isSelected) {
       if (row % 2 == 1)
         setBackground(this.backgroundOdd);
       else {
         setBackground(this.backgroundEven);
       }
       setForeground(this.textColor);
     } else {
       setBackground(this.backgroundSelected);
       setForeground(this.selectedTextColor);
     }
     return this;
   }
 }
