package com.toolkit2.client.frame.mian;

import java.util.Timer;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class MainTaskManager
{
  private static MainTaskManager instance = null;
  private Timer timer = null;
  private MemoryTask memoryTask = new MemoryTask();


  private boolean stop = false;

  private MainTaskManager() {

    this.timer = new Timer();
    this.timer.schedule(this.memoryTask, 0L, 5000L);

  }

  public static synchronized MainTaskManager getInstance() {
    if (instance == null) {
      instance = new MainTaskManager();
    }
    return instance;
  }

  public void setMemoryTaskDisplay(JComponent display) {
    this.memoryTask.setDisplay(display);
  }

  public synchronized void stop() {
    this.timer.cancel();
    this.timer = null;
    instance = null;
    this.stop = true;
  }

  public synchronized boolean isStop() {
    return this.stop;
  }
}