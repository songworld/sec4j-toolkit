package com.toolkit2.client.frame.free;
 
 import javax.swing.ImageIcon;
 
 public class FreeStatusMessageLabel extends FreeStatusLabel
 {
   private static final ImageIcon ICON_ORANGE = FreeUtil.getImageIcon("statusbar_message_light_orange.png");
   private static final ImageIcon ICON_RED = FreeUtil.getImageIcon("statusbar_message_light_red.png");
   private static final ImageIcon ICON_GREEN = FreeUtil.getImageIcon("statusbar_message_light_green.png");
 
   protected void init()
   {
     super.init();
     setFont(FreeUtil.FONT_14_BOLD);
     setGreenLight();
   }
 
   public void setRedLight() {
     setIcon(ICON_RED);
   }
 
   public void setGreenLight() {
     setIcon(ICON_GREEN);
   }
 
   public void setOrangeLight() {
     setIcon(ICON_ORANGE);
   }
 }
