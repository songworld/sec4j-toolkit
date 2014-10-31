package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
 import java.awt.Dimension;
 import java.awt.FlowLayout;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.TexturePaint;
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JPanel;
 import javax.swing.border.Border;
 import twaver.TWaverUtil;
 
 public class FreeStatusBar extends JPanel
 {
   private String backgroundImageURL = FreeUtil.getImageURL("statusbar_background.png");
   private Image backgroundLeftImage = FreeUtil.getImage("statusbar_background_left.png");
   private Image backgroundRightImage = FreeUtil.getImage("statusbar_background_right.png");
   private ImageIcon backgroundImageIcon = TWaverUtil.getImageIcon(this.backgroundImageURL);
   private TexturePaint paint = FreeUtil.createTexturePaint(this.backgroundImageURL);
   private JPanel leftPane = new JPanel(new BorderLayout());
   private JPanel rightPane = new JPanel(new FlowLayout(3, 0, 0));
   private Border border = BorderFactory.createEmptyBorder(2, 10, 0, 0);
 
   public FreeStatusBar() {
     init();
   }
 
   private void init() {
     setLayout(new BorderLayout());
     add(this.leftPane, "Center");
     add(this.rightPane, "East");
     setBorder(this.border);
     this.leftPane.setOpaque(false);
     this.rightPane.setOpaque(false);
   }
 
   protected void paintComponent(Graphics g)
   {
     super.paintComponent(g);
 //新皮肤测试去掉绿色主题的绘制
    /* Graphics2D g2d = (Graphics2D)g;
     g2d.setPaint(this.paint);
     g2d.fillRect(0, 0, getWidth(), getHeight());
 
     g2d.drawImage(this.backgroundLeftImage, 0, 0, null);
 
     g2d.drawImage(this.backgroundRightImage, getWidth() - this.backgroundRightImage.getWidth(null), 0, null);
     */
   }
 
   public JPanel getLeftPane() {
     return this.leftPane;
   }
 
   public JPanel getRightPane() {
     return this.rightPane;
   }
 
   public void addSeparator() {
     this.rightPane.add(new FreeStatusSeparator());
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.backgroundImageIcon.getIconHeight());
   }
 }