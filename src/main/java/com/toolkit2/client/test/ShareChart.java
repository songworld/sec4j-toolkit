 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JCheckBox;
 import javax.swing.JToolBar;
 import twaver.DataBoxSelectionModel;
 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.chart.PieChart;
 
 public class ShareChart extends Portlet
 {
   private TDataBox box = new TDataBox();
   private PieChart chart = new PieChart(this.box);
 
   public ShareChart() {
     super.initialize(this.chart);
     addElement("Sprint", 23.0D, Color.BLUE);
     addElement("Verizon", 26.0D, Color.YELLOW);
     addElement("AT&T", 26.0D, Color.GREEN);
     addElement("T-Mobile", 11.0D, Color.MAGENTA);
     addElement("Alltel", 5.0D, Color.CYAN);
     addElement("Rest", 9.0D, Color.RED);
 
     this.chart.setTitle("US Carrier Market Share");
     this.chart.setLegendOrientation(4);
     this.chart.setHollow(true);
     this.chart.set3D(true);
   }
 
   private void addElement(String name, double value, Color color) {
     Element element = new Node();
     element.setName(name);
     element.putChartValue(value);
     element.putChartColor(color);
     this.box.addElement(element);
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     toolbar.add(getRunButton());
     final JCheckBox percentage = new JCheckBox("Percentage");
     percentage.setPreferredSize(new Dimension(percentage.getPreferredSize().width, 20));
     percentage.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ShareChart.this.chart.setValueTextPercent(percentage.isSelected());
       }
     });
     final JCheckBox hollow = new JCheckBox("Hollow");
     hollow.setPreferredSize(new Dimension(hollow.getPreferredSize().width, 20));
     hollow.setSelected(true);
     hollow.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ShareChart.this.chart.setHollow(hollow.isSelected());
       }
     });
     final JCheckBox is3d = new JCheckBox("3D", false);
     is3d.setPreferredSize(new Dimension(is3d.getPreferredSize().width, 20));
     is3d.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ShareChart.this.chart.set3D(is3d.isSelected());
       }
     });
     is3d.setSelected(true);
 
     toolbar.add(percentage);
     toolbar.add(hollow);
     toolbar.add(is3d);
     return toolbar;
   }
 
   public void run() {
     if ((this.box.getSelectionModel().size() == 0) && (isRunning()))
       this.chart.setStartAngle(this.chart.getStartAngle() + 1.0D);
   }
 }
