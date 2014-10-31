 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Font;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.JComboBox;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.ElementCallbackHandler;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.chart.Marker;
 import twaver.chart.PercentChart;
 
 public class HealthChart extends Portlet
 {
   private TDataBox box = new TDataBox();
   private PercentChart chart = new PercentChart(this.box);
 
   public HealthChart() {
     super.initialize(this.chart);
     this.chart.setTitle("Server Health Monitor");
     this.chart.setGradient(true);
     this.chart.setPercentLabelFont(new Font("Forte", 0, 13));
     this.chart.setPercentLabelColor(Color.blue);
     this.chart.setSegmentCount(10);
     this.chart.setMarkerStartPosition(2);
     addElement("CPU", 16.0D, 50, 90, 3).setUserObject(Boolean.TRUE);
     addElement("Memory", 70.0D, 60, 80, 1).setUserObject(Boolean.FALSE);
     addElement("Storage", 64.0D, 30, 60, 2).setUserObject(Boolean.TRUE);
   }
 
   private Element addElement(String name, double current, int marker1, int marker2, int style) {
     List markers = new ArrayList();
     Marker marker = new Marker();
     marker.setColor(Color.GREEN);
     marker.setTextColor(marker.getColor());
     marker.setValue(0.0D);
     marker.setText("0%");
     markers.add(marker);
 
     marker = new Marker();
     marker.setColor(Color.ORANGE);
     marker.setTextColor(marker.getColor());
     marker.setValue(marker1);
     marker.setText(marker1 + "%");
     markers.add(marker);
 
     marker = new Marker();
     marker.setColor(Color.RED);
     marker.setTextColor(marker.getColor());
     marker.setValue(marker2);
     marker.setText(marker2 + "%");
     markers.add(marker);
 
     Element element = new Node();
     element.putChartPercentSpareFill(true);
     element.setName(name);
     element.putChartValue(current);
     element.putChartPercentStyle(style);
     element.putChartMarkers(markers);
 
     this.box.addElement(element);
     return element;
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
           value += 2.0D;
           if (value >= 100.0D)
             element.setUserObject(Boolean.FALSE);
         }
         else {
           value -= 2.0D;
           if (value <= 0.0D) {
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
     final JComboBox prorateCombobox = new JComboBox();
     prorateCombobox.addItem("0.1");
     prorateCombobox.addItem("0.2");
     prorateCombobox.addItem("0.3");
     prorateCombobox.addItem("0.4");
     prorateCombobox.addItem("0.5");
     prorateCombobox.addItem("0.6");
     prorateCombobox.addItem("0.7");
     prorateCombobox.addItem("0.8");
     prorateCombobox.addItem("0.9");
     prorateCombobox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         String value = prorateCombobox.getSelectedItem().toString();
         HealthChart.this.chart.setSegmentSectionProrate(Double.valueOf(value).doubleValue());
       }
     });
     prorateCombobox.setSelectedItem("0.5");
     prorateCombobox.setPreferredSize(new Dimension(prorateCombobox.getPreferredSize().width, 20));
     toolbar.add(prorateCombobox);
     final JComboBox combobox = new JComboBox();
     combobox.addItem("horizontal");
     combobox.addItem("vertical ");
     combobox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         if (combobox.getSelectedIndex() == 0) {
           HealthChart.this.chart.setPercentType(1);
           HealthChart.this.chart.setPercentLabelCenter(false);
         } else if (combobox.getSelectedIndex() == 1) {
           HealthChart.this.chart.setPercentType(2);
           HealthChart.this.chart.setPercentLabelCenter(true);
         }
       }
     });
     combobox.setPreferredSize(new Dimension(combobox.getPreferredSize().width, 20));
     toolbar.add(combobox);
     final JComboBox position = new JComboBox();
     position.addItem("Default");
     position.addItem("InnerDefault");
     position.addItem("Top");
     position.addItem("Bottom");
     position.addItem("Left");
     position.addItem("Right");
     position.addItem("InnerTop");
     position.addItem("InnerBottom");
     position.addItem("InnerLeft");
     position.addItem("InnerRight");
 
     position.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         if (position.getSelectedIndex() == 2)
           HealthChart.this.chart.setMarkerPosition(2);
         else if (position.getSelectedIndex() == 3)
           HealthChart.this.chart.setMarkerPosition(4);
         else if (position.getSelectedIndex() == 4)
           HealthChart.this.chart.setMarkerPosition(6);
         else if (position.getSelectedIndex() == 5)
           HealthChart.this.chart.setMarkerPosition(8);
         else if (position.getSelectedIndex() == 6)
           HealthChart.this.chart.setMarkerPosition(3);
         else if (position.getSelectedIndex() == 7)
           HealthChart.this.chart.setMarkerPosition(5);
         else if (position.getSelectedIndex() == 8)
           HealthChart.this.chart.setMarkerPosition(7);
         else if (position.getSelectedIndex() == 9)
           HealthChart.this.chart.setMarkerPosition(9);
         else if (position.getSelectedIndex() == 1)
           HealthChart.this.chart.setMarkerPosition(10);
         else
           HealthChart.this.chart.setMarkerPosition(1);
       }
     });
     position.setSelectedIndex(0);
     position.setPreferredSize(new Dimension(position.getPreferredSize().width, 20));
     toolbar.add(position);
     return toolbar;
   }
 }