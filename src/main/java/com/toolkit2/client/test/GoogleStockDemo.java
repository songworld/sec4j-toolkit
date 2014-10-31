 package com.toolkit2.client.test;
 
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.GridLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.BufferedReader;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.JCheckBox;
 import javax.swing.JPanel;

import com.toolkit2.client.component2ex.SubForm;

 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
import twaver.TWaverUtil;
 
 //public class GoogleStockDemo extends JPanel
 public class GoogleStockDemo extends SubForm
 {
   public GoogleStockDemo()
   {
	   super.setTitle("Google Stock");
     List dates = new ArrayList();
 
     Element open = new Node();
     Element high = new Node();
     Element low = new Node();
     Element history = new Node();
     Element volume = new Node();
     Element close = new Node();
     try
     {
       InputStream in = TWaverUtil.getInputStream("/com/toolkit2/client/test/google.txt");
       BufferedReader reader = new BufferedReader(new InputStreamReader(in));
       String line = null;
       while ((line = reader.readLine()) != null) {
         String[] ss = line.split("\\,");
         dates.add(0, ss[0]);
         open.getChartValues().add(0, Double.valueOf(ss[1]));
         high.getChartValues().add(0, Double.valueOf(ss[2]));
         low.getChartValues().add(0, Double.valueOf(ss[3]));
         history.getChartValues().add(0, Double.valueOf(ss[4]));
         volume.getChartValues().add(0, Double.valueOf(ss[5]));
         close.getChartValues().add(0, Double.valueOf(ss[4]));
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     open.setName("Open");
     high.setName("High");
     low.setName("Low");
     history.setName("Close");
     volume.setName("Volume");
     close.setName("Close");
 
     open.putChartColor(Color.GREEN);
     high.putChartColor(Color.RED);
     low.putChartColor(Color.YELLOW);
     history.putChartColor(Color.BLUE);
     volume.putChartColor(Color.BLUE);
     close.putChartColor(Color.BLUE);
 
     RangeChart rangeChart = new RangeChart();
     VolumeChart volumeChart = new VolumeChart();
     HistoryChart historyChart = new HistoryChart(rangeChart, volumeChart, close.getChartValues().size());
 
     historyChart.setXScaleTextList(dates);
     historyChart.getDataBox().addElement(history);
 
     rangeChart.setXScaleTextList(dates);
     TDataBox box = rangeChart.getDataBox();
     box.addElement(low);
     box.addElement(close);
     box.addElement(high);
     JPanel container = new JPanel();
     JPanel pane = new JPanel(new BorderLayout());
     pane.add(historyChart);
 
     volumeChart.setXScaleTextList(dates);
     volumeChart.getDataBox().addElement(volume);
 
     GridLayout layout = new GridLayout(3, 1);
     layout.setVgap(10);
     container.setLayout(layout);
     container.add(pane);
     container.add(rangeChart);
     container.add(volumeChart);
     
     add(container);
     
     /*父类中的控件创建，被子类中的布局覆盖，想要显示必须重新加入布局代码
      *  getToolBar在FreePagePane中实现
      * */
     add(this.getToolBar(),"North");
   }
 
   private JCheckBox createCheckBox(final Element element, final TDataBox box) {
     final JCheckBox checkBox = new JCheckBox(element.getName(), box.contains(element));
     checkBox.setForeground(element.getChartColor());
     checkBox.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         if (checkBox.isSelected())
           box.addElement(element);
         else
           box.removeElement(element);
       }
     });
     return checkBox;
   }
 }
