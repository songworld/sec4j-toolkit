package com.toolkit2.client.component2ex.gantt;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.toolkit2.client.vo.WorkOrderItemVO;
import com.toolkit2.client.vo.WorkOrderVO;

import twaver.TDataBox;
import twaver.tree.TTree;

public class GanttPane extends JPanel
{
  private TDataBox box = new TDataBox("2BizBox");
  private GanttTable table = new GanttTable(this.box);

  public GanttPane() {
    setLayout(new BorderLayout());
    add(new JScrollPane(this.table), "Center");
  }

  public void setWorkOrderVO(WorkOrderVO vo)
  {
    this.box.clear();
    if (vo != null) {
      WorkOrderTask task = new WorkOrderTask(vo);
      this.box.addElement(task);
      if (vo.getWorkOrderItem() != null) {
        Iterator it = vo.getWorkOrderItem().iterator();
        while (it.hasNext()) {
          WorkOrderItemVO item = (WorkOrderItemVO)it.next();
          if (item != null) {
            WorkOrderItemTask itemTask = new WorkOrderItemTask(item);
            itemTask.setParent(task);
            this.box.addElement(itemTask);
          }
        }
      }
    }
    this.table.getTree().expandAll();
    this.table.pack();
    this.table.setGanntColumnWidth(800);
  }

  public GanttTable getTable() {
    return this.table;
  }
}