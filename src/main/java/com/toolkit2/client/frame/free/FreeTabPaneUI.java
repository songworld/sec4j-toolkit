package com.toolkit2.client.frame.free;
 
 import java.awt.FontMetrics;
 import java.awt.Graphics;
 import java.awt.Rectangle;
 import javax.swing.plaf.metal.MetalTabbedPaneUI;
 
 public class FreeTabPaneUI extends MetalTabbedPaneUI
 {
   private FreeTabPane tab = null;
   private int firstTabIndent = 5;
 
   public FreeTabPaneUI(FreeTabPane tab) {
     this.tab = tab;
   }
 
   protected Rectangle getTabBounds(int tabIndex, Rectangle dest)
   {
     Rectangle bounds = super.getTabBounds(tabIndex, dest);
 
     bounds.x += this.firstTabIndent;
     return bounds;
   }
 
   protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
   {
     g.setColor(FreeUtil.TAB_BOTTOM_LINE_COLOR);
     int lineY = this.tab.getPreferredTabHeight() - 1;
     g.drawLine(0, lineY, this.firstTabIndent, lineY);
   }
 
   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
   {
   }
 
   protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics)
   {
     int width = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
 
     return width - 5;
   }
 
   protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
   {
     return this.tab.getPreferredTabHeight();
   }
 }
