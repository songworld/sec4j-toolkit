package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
 import javax.swing.JPanel;
 
 public class FreeContentPane extends JPanel
 {
   public FreeContentPane()
   {
     setLayout(new BorderLayout());
     setBackground(FreeUtil.CONTENT_PANE_BACKGROUND);
   }
 }