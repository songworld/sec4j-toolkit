package com.toolkit2.client.component2ex.gantt;

import java.awt.Color;
import java.util.Date;

import com.toolkit2.client.vo.WorkOrderVO;

import twaver.Node;

public class WorkOrderTask extends Node
  implements Task
{
  private WorkOrderVO vo = null;

  public WorkOrderTask(WorkOrderVO vo) {
    this.vo = vo;
    setName(vo.getWorkOrderNumber());
    setIcon("/com/toolkit2/client/images/copy.gif");
  }

  public Date getStartDate()
  {
    return null;
  }

  public Date getEndDate() {
    return null;
  }

  public WorkOrderVO getWorkOrder() {
    return this.vo;
  }

  public Color getColor() {
    return null;
  }
}