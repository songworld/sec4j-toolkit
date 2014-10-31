package com.toolkit2.client.frame.free;
 import java.awt.Dimension;
 import java.awt.FlowLayout;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.TexturePaint;
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JPanel;
 import twaver.TWaverUtil;
 
 public class FreeToolBar extends JPanel
 {
   private String backgroundImageURL = FreeUtil.getImageURL("toolbar_background.png");
   private int preferredHeight = TWaverUtil.getImageIcon(this.backgroundImageURL).getIconHeight();
   private TexturePaint paint = FreeUtil.createTexturePaint(this.backgroundImageURL);
   private int buttonGap = 2;
 
   public FreeToolBar() {
     init();
   }
 
   private void init() {
     setLayout(new FlowLayout(3, this.buttonGap, 0));
     setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));
   }
 
   protected void paintComponent(Graphics g)
   {
     super.paintComponent(g);
 
     Graphics2D g2d = (Graphics2D)g;
     g2d.setPaint(this.paint);
     g2d.fillRect(0, 0, getWidth(), getHeight());
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.preferredHeight);
   }
 }
