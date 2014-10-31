package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Component;
 import java.awt.Graphics;
 import java.awt.Insets;
 import javax.swing.border.Border;
 
 public class FreePopupMenuBorder
   implements Border
 {
   private Color borderColor = FreeUtil.MENUITEM_SELECTED_BACKGROUND;
   private Color leftSider = new Color(214, 225, 200);
   private int lineThickness = 1;
   private int leftHeaderWidth = 7;
   private Insets insets = new Insets(this.lineThickness, this.lineThickness + this.leftHeaderWidth, this.lineThickness, this.lineThickness);
 
   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
   {
     g.setColor(this.leftSider);
     g.fillRect(this.lineThickness, this.lineThickness, this.leftHeaderWidth, height);
 
     g.setColor(this.borderColor);
     g.drawRect(0, 0, width - this.lineThickness, height - this.lineThickness);
   }
 
   public Insets getBorderInsets(Component c) {
     return this.insets;
   }
 
   public boolean isBorderOpaque() {
     return true;
   }
 }
