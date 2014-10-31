package com.toolkit2.client.component2ex.gantt;

import java.awt.Color;
import java.util.Date;

public abstract interface Task
{
  public abstract Date getStartDate();

  public abstract Date getEndDate();

  public abstract Color getColor();
}