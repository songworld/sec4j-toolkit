 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JCheckBox;
 import javax.swing.JPanel;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.chart.BarChart;
 
 public class JapanChart extends Portlet
 {
   private Element subOfTotal = new Node();
   private Element subOf3G = new Node();
   private TDataBox box = new TDataBox();
   private BarChart chart = new BarChart(this.box);
 
   public JapanChart() {
     super.initialize(this.chart);
     this.chart.setBarType(2);
     this.chart.setTitle("<html>Japan's cellular subs & 3G penetration<br><center>by carrier,2005(millions)</center></html>");
     this.chart.setShadowOffset(10);
     this.chart.setYScaleTextVisible(true);
     this.chart.setYScaleMinTextVisible(true);
     this.chart.setUpperLimit(60.0D);
     this.chart.setYScaleValueGap(10.0D);
 
     this.chart.addXScaleText("NTT DoCoMo");
     this.chart.addXScaleText("KDDI");
     this.chart.addXScaleText("Vodafone");
 
     addElement(this.subOf3G, "3G subs", Color.GREEN.brighter());
     addElement(this.subOfTotal, "Total subs", Color.ORANGE.darker());
 
     addValue(this.subOfTotal, 50.560000000000002D, 24.690000000000001D, 15.109999999999999D);
     addValue(this.subOf3G, 20.120000000000001D, 20.579999999999998D, 2.31D);
   }
 
   private void addElement(Element element, String name, Color color) {
     element.setName(name);
     element.putChartColor(color);
     this.box.addElement(element);
   }
 
   private void addValue(Element element, double value1, double value2, double value3) {
     element.addChartValue(value1);
     element.addChartValue(value2);
     element.addChartValue(value3);
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     final JCheckBox check = new JCheckBox("Legend Visble");
     check.setOpaque(false);
     check.setSelected(this.chart.getLegendPane().isVisible());
     check.setPreferredSize(new Dimension(check.getPreferredSize().width, 20));
     check.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         JapanChart.this.chart.getLegendPane().setVisible(check.isSelected());
       }
     });
     toolbar.add(check);
     return toolbar;
   }
 }
