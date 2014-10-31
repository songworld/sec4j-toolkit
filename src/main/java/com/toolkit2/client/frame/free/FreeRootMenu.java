package com.toolkit2.client.frame.free;
 import java.awt.Color;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.TexturePaint;
 import javax.swing.BorderFactory;
 import javax.swing.JMenu;
 import javax.swing.border.Border;
 
 public class FreeRootMenu extends JMenu
 {
   private Color foregroundColor = FreeUtil.DEFAULT_TEXT_COLOR;
   private String selectedBackgroundImageURL = FreeUtil.getImageURL("menubar_background_selected.png");
   private TexturePaint paint = FreeUtil.createTexturePaint(this.selectedBackgroundImageURL);
   private Border border = BorderFactory.createEmptyBorder(0, 5, 0, 4);
 
   public FreeRootMenu() {
     init();
   }
 
   public FreeRootMenu(String text) {
     super(text);
     init();
   }
 
   private void init() {
     setFont(FreeUtil.FONT_14_BOLD);
     setBorder(this.border);
     setForeground(this.foregroundColor);
   }
 
   protected void paintComponent(Graphics g)
   {
     if (isSelected()) {
       Graphics2D g2d = (Graphics2D)g;
       g2d.setPaint(this.paint);
       g2d.fillRect(0, 0, getWidth(), getHeight());
 
       super.paintComponent(g);
     } else {
       super.paintComponent(g);
     }
   }
 }
