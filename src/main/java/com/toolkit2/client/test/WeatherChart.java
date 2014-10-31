 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.JCheckBox;
 import javax.swing.JComboBox;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.chart.RadarChart;
 
 public class WeatherChart extends Portlet
 {
   private TDataBox box = new TDataBox();
   private RadarChart chart = new RadarChart(this.box);
 
   public WeatherChart() {
     super.initialize(this.chart);
     this.chart.setScaleMajorCount(5);
     this.chart.setRingMinVisible(false);
     this.chart.setShapeFillGradient(false);
     this.chart.setRadarFillColor(new Color(50, 170, 160));
     this.chart.setAxisTextFont(new Font("dialog", 0, 12));
     this.chart.setScaleMajorCount(4);
     this.chart.setTitle("Weather Status");
     this.chart.setScaleMajorTextVisible(false);
 
     this.chart.addAxisText("Rainy");
     this.chart.addAxisText("Snowy");
     this.chart.addAxisText("Windy");
     this.chart.addAxisText("Cloudy");
     this.chart.addAxisText("Iced");
     this.chart.addAxisText("Fine");
     List janList = new ArrayList();
     janList.add("0.1");
     janList.add("0.4");
     janList.add("0.6");
     janList.add("0.4");
     janList.add("0.5");
     janList.add("0.2");
     List febList = new ArrayList();
     febList.add("0.4");
     febList.add("0.2");
     febList.add("0.5");
     febList.add("0.6");
     febList.add("0.2");
     febList.add("0.5");
 
     this.chart.setScaleMaxValue(0.8D);
     addElement("Jan.", Color.GREEN, 1, "solid.1", janList);
     addElement("Feb.", Color.ORANGE, 1, "solid.1", febList);
   }
 
   public void addElement(String name, Color color, int style, String stroke, List list) {
     Element element = new Node();
     element.setName(name);
     element.putChartColor(color);
     element.putChartInflexionStyle(style);
     element.putChartStroke(stroke);
     int size = list.size();
     for (int i = 0; i < size; i++) {
       element.addChartValue(Double.valueOf(list.get(i).toString()).doubleValue());
     }
     this.box.addElement(element);
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     final JCheckBox checkBox = new JCheckBox("Fill");
     checkBox.setPreferredSize(new Dimension(checkBox.getPreferredSize().width + 5, 20));
     checkBox.setSelected(true);
     checkBox.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         WeatherChart.this.chart.setAreaFill(checkBox.isSelected());
       }
     });
     final JComboBox combobox = new JComboBox();
     combobox.setPreferredSize(new Dimension(combobox.getPreferredSize().width, 20));
     combobox.addItem("Line");
     combobox.addItem("Ellipse");
     combobox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         if (combobox.getSelectedItem().toString().equals("Line"))
           WeatherChart.this.chart.setRingStyle(1);
         else
           WeatherChart.this.chart.setRingStyle(2);
       }
     });
     toolbar.add(checkBox);
     toolbar.add(combobox);
     return toolbar;
   }
 }
