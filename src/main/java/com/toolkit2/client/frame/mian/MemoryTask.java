package com.toolkit2.client.frame.mian;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.toolkit2.client.Shell;

public class MemoryTask extends MainTimerTask
{
  private static MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
  private JComponent display = null;

  public MemoryTask() {

  }

  public MemoryTask(MemoryBarChart display) {
    this.display = display;
  }

  public void setDisplay(JComponent display) {
    this.display = display;
  }

  public void runTask() {
    if ((Shell.isInstanced()) && (Shell.getInstance() != null)) {
      final long usedMemory = memorymbean.getHeapMemoryUsage().getUsed();
      final long totalMemory = memorymbean.getHeapMemoryUsage().getMax();
      if (this.display != null)
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run() {
            if ((MemoryTask.this.display instanceof MemoryBar))
              ((MemoryBar)MemoryTask.this.display).updateMemoryUsage(usedMemory, totalMemory);
          }
        });
    }
  }
}