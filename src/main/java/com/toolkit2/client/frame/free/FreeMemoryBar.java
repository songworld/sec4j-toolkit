package com.toolkit2.client.frame.free;
 
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.lang.management.ManagementFactory;
 import java.lang.management.MemoryMXBean;
 import java.lang.management.MemoryUsage;
 import java.text.NumberFormat;
 import javax.swing.Timer;
 
 public class FreeMemoryBar extends FreeProgressBar
 {
   private static final int kilo = 1024;
   private static final String mega = "M";
   private static MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
   private static NumberFormat format = NumberFormat.getInstance();
   private int delay = 2000;
 
   public FreeMemoryBar() {
     super(0, 0, 100);
     ActionListener taskPerformer = new ActionListener()
     {
       public void actionPerformed(ActionEvent evt) {
         long usedMemory = FreeMemoryBar.memorymbean.getHeapMemoryUsage().getUsed();
         long totalMemory = FreeMemoryBar.memorymbean.getHeapMemoryUsage().getMax();
         FreeMemoryBar.this.updateMemoryUsage(usedMemory, totalMemory);
       }
     };
     new Timer(this.delay, taskPerformer).start();
   }
 
   private void updateMemoryUsage(long usedMemory, long totalMemory) {
     int percent = (int)(usedMemory * 100L / totalMemory);
     setValue(percent);
     String usedMega = format.format(usedMemory / 1024L / 1024L) + "M";
     String totalMega = format.format(totalMemory / 1024L / 1024L) + "M";
     String message = usedMega + "/" + totalMega;
     setString(message);
 
     setToolTipText("Memory used " + format.format(usedMemory) + " of total " + format.format(totalMemory));
   }
 }