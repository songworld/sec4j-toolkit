package com.toolkit2.client.frame.free;
 
 import javax.swing.BorderFactory;
 import javax.swing.Icon;
 import javax.swing.JLabel;
 
 public class FreeStatusLabel extends JLabel
 {
   public FreeStatusLabel()
   {
     this(null, null);
   }
 
   public FreeStatusLabel(String text) {
     this(text, null);
   }
 
   public FreeStatusLabel(Icon icon) {
     this(null, icon);
   }
 
   public FreeStatusLabel(String text, Icon icon) {
     super(text, icon, 10);
     init();
   }
 
   protected void init() {
     setOpaque(false);
     setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
     setFont(FreeUtil.FONT_12_BOLD);
     setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
     setVerticalAlignment(0);
     setVerticalTextPosition(0);
     setIconTextGap(5);
   }
 }
