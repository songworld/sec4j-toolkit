package com.toolkit2.client.frame.free;
 
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.TexturePaint;
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JFrame;
 import javax.swing.JPanel;
 import javax.swing.JTextField;
 import javax.swing.border.Border;
 import javax.swing.plaf.metal.MetalTextFieldUI;
 import twaver.TWaverUtil;
 
 public class FreeTextField extends JTextField
 {
   private String backgroundImageURL = FreeUtil.getImageURL("textfield_background.png");
   private Image backgroundLeftImage = FreeUtil.getImage("textfield_background_left.png");
   private Image backgroundRightImage = FreeUtil.getImage("textfield_background_right.png");
   private ImageIcon backgroundImageIcon = TWaverUtil.getImageIcon(this.backgroundImageURL);
   private TexturePaint paint = FreeUtil.createTexturePaint(this.backgroundImageURL);
   private Border border = BorderFactory.createEmptyBorder(1, 3, 1, 3);
 
   public FreeTextField() {
     init();
   }
 
   private void init() {
     setBorder(this.border);
     setUI(new MetalTextFieldUI()
     {
       protected void paintBackground(Graphics g)
       {
         Graphics2D g2d = (Graphics2D)g;
         g2d.setPaint(FreeTextField.this.paint);
         g2d.fillRect(0, 0, FreeTextField.this.getWidth(), FreeTextField.this.getHeight());
 
         g2d.drawImage(FreeTextField.this.backgroundLeftImage, 0, 0, null);
 
         g2d.drawImage(FreeTextField.this.backgroundRightImage, FreeTextField.this.getWidth() - FreeTextField.this.backgroundRightImage.getWidth(null), 0, null);
       }
     });
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.backgroundImageIcon.getIconHeight());
   }
 
   public static void main(String[] args) {
     JFrame frame = new JFrame();
     JPanel pane = new JPanel();
     FreeTextField text = new FreeTextField();
     text.setText("asdfasdfasdfadsfasdfa");
     pane.add(text);
     frame.add(pane);
     frame.setSize(400, 400);
     frame.setVisible(true);
   }
 }
