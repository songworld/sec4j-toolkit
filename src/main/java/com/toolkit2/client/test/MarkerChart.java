 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import javax.swing.JComboBox;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.ElementCallbackHandler;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.TWaverUtil;
 import twaver.chart.BarChart;
 import twaver.chart.Marker;
 
 public class MarkerChart extends Portlet
 {
   private TDataBox box = new TDataBox();
   private BarChart chart = new BarChart(this.box)
   {
     public Color getColor(Element element) {
       double value = element.getChartValue();
       if (value < -70.0D) {
         return Color.RED;
       }
       if (value > 70.0D) {
         return Color.ORANGE;
       }
       return Color.GREEN;
     }
   };
 
   public MarkerChart()
   {
     super.initialize(this.chart);
     this.chart.setSelectedOffset(0);
     this.chart.setShadowOffset(0);
     this.chart.setGradient(true);
     this.chart.setUpperLimit(100.0D);
     this.chart.setLowerLimit(-100.0D);
     this.chart.setYScaleValueGap(10.0D);
     this.chart.setYScaleTextVisible(true);
     this.chart.setYScaleMinTextVisible(true);
 
     Marker marker = new Marker(70.0D, Color.ORANGE);
     marker.setText("high level marker");
     marker.setColor(Color.ORANGE);
     this.chart.addMarker(marker);
 
     marker = new Marker(-70.0D, Color.RED);
     marker.setText("low level marker");
     marker.setColor(Color.RED);
     this.chart.addMarker(marker);
 
     for (int i = 1; i < 8; i++) {
       Element element = new Node();
       element.putChartValue(TWaverUtil.getRandomInt(100));
       element.setUserObject(TWaverUtil.getRandomBoolean());
       element.setName("C" + i);
       this.box.addElement(element);
     }
   }
 
   public void run() {
     if (!isRunning()) {
       return;
     }
     this.box.iterator(new ElementCallbackHandler()
     {
       public boolean processElement(Element element) {
         double value = element.getChartValue();
         if (Boolean.TRUE.equals(element.getUserObject())) {
           value += 3.0D;
           if (value >= 98.0D)
             element.setUserObject(Boolean.FALSE);
         }
         else {
           value -= 3.0D;
           if (value <= -98.0D) {
             element.setUserObject(Boolean.TRUE);
           }
         }
         element.putChartValue(value);
         return true;
       }
     });
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     toolbar.add(getRunButton());
     final JComboBox combobox = new JComboBox();
     combobox.setPreferredSize(new Dimension(combobox.getPreferredSize().width, 20));
     combobox.addItem("South");
     combobox.addItem("East");
     combobox.addItem("West");
     combobox.addItem("North");
 
     combobox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         if (combobox.getSelectedItem().toString().equals("South")) {
           MarkerChart.this.chart.setLegendLayout(1);
           MarkerChart.this.chart.setLegendOrientation(1);
         } else if (combobox.getSelectedItem().toString().equals("East")) {
           MarkerChart.this.chart.setLegendLayout(2);
           MarkerChart.this.chart.setLegendOrientation(1);
         } else if (combobox.getSelectedItem().toString().equals("West")) {
           MarkerChart.this.chart.setLegendLayout(7);
           MarkerChart.this.chart.setLegendOrientation(1);
         } else if (combobox.getSelectedItem().toString().equals("North")) {
           MarkerChart.this.chart.setLegendLayout(5);
           MarkerChart.this.chart.setLegendOrientation(2);
         }
       }
     });
     toolbar.add(combobox);
 
     return toolbar;
   }
 }
