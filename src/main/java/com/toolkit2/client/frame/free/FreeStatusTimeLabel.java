package com.toolkit2.client.frame.free;
 
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.Date;
import javax.swing.Timer;

import com.toolkit2.Util.ClientUtil;
 
 public class FreeStatusTimeLabel extends FreeStatusLabel
 {
   private int delay = 1000;
 
   public FreeStatusTimeLabel() {
     ActionListener taskPerformer = new ActionListener()
     {
       public void actionPerformed(ActionEvent evt) {
         FreeStatusTimeLabel.this.setText(ClientUtil.getTimeLocalString(new Date()));
       }
     };
     new Timer(this.delay, taskPerformer).start();
   }
 }