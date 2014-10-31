package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import javax.swing.BorderFactory;
 import javax.swing.JMenu;
 import javax.swing.border.Border;
 
 public class FreeMenu extends JMenu
 {
   private Color backgroundColor = FreeUtil.MENUITEM_BACKGROUND;
   private Color foregroundColor = FreeUtil.DEFAULT_TEXT_COLOR;
   private int borderThickness = 1;
   private Border border = BorderFactory.createLineBorder(this.backgroundColor, this.borderThickness);
   private int preferredHeight = 25;
 
   public FreeMenu() {
     init();
   }
 
   public FreeMenu(String text) {
     super(text);
     init();
   }
 
   private void init() {
     setForeground(this.foregroundColor);
     setFont(FreeUtil.FONT_14_BOLD);
     setOpaque(true);
     setBackground(this.backgroundColor);
     setBorder(this.border);
   }
 
   protected void paintComponent(Graphics g)
   {
     if (isSelected()) {
       Graphics2D g2d = (Graphics2D)g;
       g2d.setColor(FreeUtil.MENUITEM_SELECTED_BACKGROUND);
       g2d.fillRect(0, 0, getWidth(), getHeight());
       super.paintComponent(g);
     } else {
       super.paintComponent(g);
     }
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.preferredHeight);
   }
 }
