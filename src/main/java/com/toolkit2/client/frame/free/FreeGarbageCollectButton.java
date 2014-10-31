package com.toolkit2.client.frame.free;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import twaver.TWaverUtil;
 
 public class FreeGarbageCollectButton extends FreeToolbarButton
 {
   public FreeGarbageCollectButton()
   {
     setIcon(TWaverUtil.getIcon("/com/toolkit2/client/test/gc.png"));
     setToolTipText("Click to call garbage collector");
     addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         System.gc();
       }
     });
   }
 }