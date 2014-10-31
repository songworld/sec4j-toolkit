 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import javax.swing.JComboBox;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.chart.BarChart;
 
 public class TravelChart extends Portlet
 {
   private TDataBox box = new TDataBox();
   private BarChart chart = new BarChart(this.box);
 
   public TravelChart() {
     super.initialize(this.chart);
     this.chart.setTitle("Travel expenditure on legisure trips");
     this.chart.setBarType(4);
     this.chart.setShadowOffset(0);
     this.chart.setSelectedOffset(0);
     this.chart.setXScaleTextOrientation(4);
     this.chart.setXScaleTextColor(Color.CYAN.darker());
 
     this.chart.addXScaleText("Europe");
     this.chart.addXScaleText("Oceania");
     this.chart.addXScaleText("North America");
     this.chart.addXScaleText("<html>Africa<br>Central America<br>South America</html>");
     this.chart.addXScaleText("<html>HK<br>Macao</html>");
     this.chart.addXScaleText("Other Asian regions");
 
     Element before = new Node();
     Element total = new Node();
 
     before.putChartColor(new Color(102, 153, 255).darker());
     total.putChartColor(new Color(102, 153, 255));
 
     before.setName("Before the trip");
     total.setName("Total");
 
     before.putChartValueTextPosition(1);
 
     before.addChartValue(2108.0D);
     before.addChartValue(2369.0D);
     before.addChartValue(1475.0D);
     before.addChartValue(1826.0D);
     before.addChartValue(459.0D);
     before.addChartValue(821.0D);
 
     total.addChartValue(5253.0D);
     total.addChartValue(4978.0D);
     total.addChartValue(3786.0D);
     total.addChartValue(2991.0D);
     total.addChartValue(2185.0D);
     total.addChartValue(1904.0D);
 
     this.box.addElement(before);
     this.box.addElement(total);
   }
 
   public JToolBar getControlPanel()
   {
     JToolBar toolbar = super.getControlPanel();
     final JComboBox comboBox = new JComboBox();
     comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 20));
     comboBox.addItem("Group");
     comboBox.addItem("Stack");
     comboBox.addItem("Layer");
     comboBox.addItem("PerCent");
     comboBox.setSelectedIndex(2);
     comboBox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         if (comboBox.getSelectedItem().toString().equals("Group"))
           TravelChart.this.chart.setBarType(2);
         else if (comboBox.getSelectedItem().toString().equals("Stack"))
           TravelChart.this.chart.setBarType(3);
         else if (comboBox.getSelectedItem().toString().equals("Layer"))
           TravelChart.this.chart.setBarType(4);
         else if (comboBox.getSelectedItem().toString().equals("PerCent"))
           TravelChart.this.chart.setBarType(5);
       }
     });
     toolbar.add(comboBox);
 
     return toolbar;
   }
 }