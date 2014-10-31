package com.toolkit2.client.component2ex.gantt;

import java.awt.Color;
import java.util.Date;

import com.toolkit2.client.vo.WorkOrderItemVO;

import twaver.Node;

public class WorkOrderItemTask extends Node
  implements Task
{
  private WorkOrderItemVO vo = null;

  public WorkOrderItemTask(WorkOrderItemVO vo) {
    this.vo = vo;
    setName(vo.getPk().getWorkOrderItemNumber());
    setIcon("/com/toolkit2/client/images/leaf.png");
  }

  public Date getStartDate() {
    return this.vo.getStartDate();
  }

  public Date getEndDate() {
    return this.vo.getDateRequired();
  }

  public WorkOrderItemVO getWorkOrderItem() {
    return this.vo;
  }

  public Color getColor() {
    if (this.vo.getStatus().equals("OPEN")) {
      if (this.vo.getDateRequired() != null) {
        Date currentDate = new Date();
        long gap = this.vo.getDateRequired().getTime() - currentDate.getTime();
        if (gap > 0L) {
          return Color.green;
        }
        return Color.red;
      }

      return null;
    }

    return Color.black;
  }
}