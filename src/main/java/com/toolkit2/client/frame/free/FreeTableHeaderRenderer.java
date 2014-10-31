package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Component;
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Insets;
 import javax.swing.JTable;
 import javax.swing.border.Border;
 import javax.swing.table.DefaultTableCellRenderer;
 
 public class FreeTableHeaderRenderer extends DefaultTableCellRenderer
 {
   private Color background = FreeUtil.TABLE_HEADER_BACKGROUND_COLOR;
   private Color textColor = new Color(126, 104, 127);
   private Color borderLightColor = FreeUtil.TABLE_HEADER_BORDER_BRIGHT_COLOR;
   private Color borderDarkColor = FreeUtil.TABLE_HEADER_BORDER_DARK_COLOR;
   private int tableHeaderHeight = 20;
   private Border border = new Border()
   {
     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
       g.setColor(FreeTableHeaderRenderer.this.borderDarkColor);
       g.drawRect(x, y, width - 1, height - 1);
 
       g.setColor(FreeTableHeaderRenderer.this.borderLightColor);
       g.drawLine(x, y, x, height - 1);
     }
 
     public Insets getBorderInsets(Component c) {
       return new Insets(1, 5, 1, 1);
     }
 
     public boolean isBorderOpaque() {
       return true;
     }
   };
 
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
   {
     super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
     setFont(FreeUtil.TABLE_HEADER_FONT);
     setBackground(this.background);
     setForeground(this.textColor);
     setBorder(this.border);
     return this;
   }
 
   public Dimension getPreferredSize()
   {
     Dimension size = super.getPreferredSize();
     return new Dimension(size.width, this.tableHeaderHeight);
   }
 }