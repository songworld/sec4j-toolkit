 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import javax.swing.JCheckBox;
 import javax.swing.JComboBox;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.TUIManager;
 import twaver.chart.LineChart;
 
 public class MobileChart extends Portlet
 {
   private Element developedElement = new Node();
   private Element developingElement = new Node();
   private Element worldElement = new Node();
   private TDataBox box = new TDataBox();
   private LineChart chart = new LineChart(this.box);
 
   public MobileChart() {
     super.initialize(this.chart);
     this.chart.setTitle("<html>Mobile Cellular Telephone Subscribers<br><center>Per 100 Inhabitants,1994~2006</center></html>");
     this.chart.setLowerLimit(0.0D);
     this.chart.setUpperLimit(100.0D);
     this.chart.setYScaleValueGap(20.0D);
     this.chart.setYScaleTextVisible(true);
     this.chart.setXScaleLineVisible(true);
     this.chart.setXScaleTextOrientation(3);
     this.chart.setXGap(15);
     this.chart.setYGap(15);
     this.chart.setValueTextVisible(true);
     this.chart.setValueTextFont(TUIManager.getDefaultFont().deriveFont(9.0F));
     this.chart.setInflexionVisible(true);
 
     addElement(this.developedElement, "Developed", Color.BLUE, 4);
     addElement(this.worldElement, "World", Color.RED, 3);
     addElement(this.developingElement, "Developing", Color.GREEN, 2);
 
     addValue("1994", 5.2D, 1.0D, 0.19D);
     addValue("1995", 8.199999999999999D, 1.6D, 0.4D);
     addValue("1996", 12.699999999999999D, 2.5D, 0.6D);
     addValue("1997", 17.600000000000001D, 3.7D, 1.1D);
     addValue("1998", 24.600000000000001D, 5.4D, 1.9D);
     addValue("1999", 35.299999999999997D, 8.199999999999999D, 3.2D);
     addValue("2000", 49.600000000000001D, 12.199999999999999D, 5.4D);
     addValue("2001", 58.5D, 15.699999999999999D, 8.0D);
     addValue("2002", 64.700000000000003D, 18.800000000000001D, 10.800000000000001D);
     addValue("2003", 69.599999999999994D, 22.600000000000001D, 14.199999999999999D);
     addValue("2004", 76.799999999999997D, 27.699999999999999D, 19.100000000000001D);
     addValue("2005", 85.200000000000003D, 34.399999999999999D, 25.600000000000001D);
     addValue("2006", 90.900000000000006D, 41.0D, 32.399999999999999D);
   }
 
   private void addElement(Element element, String name, Color color, int style)
   {
     element.setName(name);
     element.putChartColor(color);
     element.putChartInflexionStyle(style);
     this.box.addElement(element);
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     final JCheckBox showText = new JCheckBox("Text");
     showText.setPreferredSize(new Dimension(showText.getPreferredSize().width + 5, 20));
     showText.setSelected(true);
     showText.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         MobileChart.this.chart.setValueTextVisible(showText.isSelected());
       }
     });
     final JCheckBox inflexion = new JCheckBox("Inflexion");
     inflexion.setPreferredSize(new Dimension(inflexion.getPreferredSize().width + 5, 20));
     inflexion.setSelected(true);
     inflexion.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         MobileChart.this.chart.setInflexionVisible(inflexion.isSelected());
       }
     });
     final JComboBox lineType = new JComboBox();
     lineType.addItem("Default");
     lineType.addItem("Area");
     lineType.addItem("Pole");
     lineType.setPreferredSize(new Dimension(lineType.getPreferredSize().width, 20));
     lineType.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         int index = lineType.getSelectedIndex();
         switch (index) {
         case 0:
           MobileChart.this.chart.setLineType(1);
           break;
         case 1:
           MobileChart.this.chart.setLineType(2);
           break;
         case 2:
           MobileChart.this.chart.setLineType(3);
         }
       }
     });
     lineType.setSelectedIndex(0);
 
     toolbar.add(showText);
     toolbar.add(inflexion);
     toolbar.add(lineType);
     return toolbar;
   }
 
   private void addValue(String year, double developed, double world, double developing) {
     this.chart.addXScaleText(year);
     this.developedElement.addChartValue(developed);
     this.worldElement.addChartValue(world);
     this.developingElement.addChartValue(developing);
   }
 }
