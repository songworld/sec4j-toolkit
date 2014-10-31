package com.toolkit2.client.frame.mian;

import java.util.TimerTask;
/***************************************************************************************
 * Shell页面的定时任务===>统一的调度程序
 * *****/
public abstract class MainTimerTask extends TimerTask
{
  public void run()
  {
    if (!isStop())
      runTask();
  }

  public boolean isStop()
  {
    return MainTaskManager.getInstance().isStop();
  }

  public abstract void runTask();
}
