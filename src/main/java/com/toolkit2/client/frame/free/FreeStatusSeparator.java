package com.toolkit2.client.frame.free;
 
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 
 public class FreeStatusSeparator extends JLabel
 {
   private ImageIcon imageIcon = FreeUtil.getImageIcon("statusbar_separator.png");
 
   public FreeStatusSeparator() {
     init();
   }
 
   private void init() {
     setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
     setOpaque(false);
     setIcon(this.imageIcon);
   }
 }
