package com.toolkit2.client.frame.free;
 
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.TexturePaint;
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JMenuBar;
 import javax.swing.border.Border;
 import twaver.TWaverUtil;
 
 public class FreeMenuBar extends JMenuBar
 {
   private String backgroundImageURL = FreeUtil.getImageURL("menubar_background.png");
   private Image backgroundLeftImage = FreeUtil.getImage("menubar_background_left.png");
   private Image backgroundRightImage = FreeUtil.getImage("menubar_background_right.png");
   private ImageIcon backgroundImageIcon = TWaverUtil.getImageIcon(this.backgroundImageURL);
   private TexturePaint paint = FreeUtil.createTexturePaint(this.backgroundImageURL);
   private Border border = BorderFactory.createEmptyBorder();
 
   public FreeMenuBar() {
     init();
   }
 
   private void init() {
     setBorder(this.border);
   }
 
   protected void paintComponent(Graphics g)
   {
     super.paintComponent(g);
     //禁用菜单背景绘制
    /* Graphics2D g2d = (Graphics2D)g;
     g2d.setPaint(this.paint);
     g2d.fillRect(0, 0, getWidth(), getHeight());
 
     g2d.drawImage(this.backgroundLeftImage, 0, 0, null);
 
     g2d.drawImage(this.backgroundRightImage, getWidth() - this.backgroundRightImage.getWidth(null), 0, null);
     */
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.backgroundImageIcon.getIconHeight());
   }
 }
