 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import javax.swing.JCheckBox;
 import javax.swing.JComboBox;
 import javax.swing.JLabel;
 import javax.swing.JToolBar;
 import twaver.Element;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.chart.BarChart;
 
 public class AdoptionChart extends Portlet
 {
   private TDataBox box = new TDataBox();
   private BarChart chart = new BarChart(this.box);
 
   public AdoptionChart() {
     super.initialize(this.chart);
     this.chart.setTitle("Enterprise 2.0 Adoption");
     this.chart.setYAxisText("<html>North American and European IT<br>decision-makers at enterprises and SMBs</html>");
     this.chart.setBarType(5);
     this.chart.addXScaleText("Small");
     this.chart.addXScaleText("Medium-Small");
     this.chart.addXScaleText("Medium-Large");
     this.chart.addXScaleText("Large");
     this.chart.addXScaleText("Very-Large");
     this.chart.addXScaleText("Global2000");
     this.chart.setValueTextCenter(true);
     addElement("Not Considering", 0.6800000000000001D, 0.58D, 0.52D, 0.44D, 0.44D, 0.37D, Color.GREEN.darker().darker());
     addElement("Considering Only", 0.12D, 0.16D, 0.15D, 0.15D, 0.16D, 0.12D, Color.GREEN.darker());
     addElement("Buying", 0.2D, 0.26D, 0.33D, 0.41D, 0.4D, 0.51D, Color.GREEN);
   }
 
   public void addElement(String name, double v1, double v2, double v3, double v4, double v5, double v6, Color color) {
     Element node = new Node();
     node.putChartColor(color);
     node.setName(name);
     node.addChartValue(v1);
     node.addChartValue(v2);
     node.addChartValue(v3);
     node.addChartValue(v4);
     node.addChartValue(v5);
     node.addChartValue(v6);
     this.box.addElement(node);
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     final JCheckBox checkBox = new JCheckBox("Value");
     checkBox.setPreferredSize(new Dimension(checkBox.getPreferredSize().width + 5, 20));
     checkBox.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         AdoptionChart.this.chart.setPercentTypeValueVisible(checkBox.isSelected());
       }
     });
     toolbar.add(checkBox);
     JLabel label = new JLabel("Bundle:");
     toolbar.add(label);
     final JComboBox combobox = new JComboBox();
     combobox.setPreferredSize(new Dimension(combobox.getPreferredSize().width, 20));
     combobox.setLightWeightPopupEnabled(true);
     combobox.addItem("1");
     combobox.addItem("2");
     combobox.addItem("3");
     toolbar.add(combobox);
     combobox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         AdoptionChart.this.chart.setBundleSize(new Integer(combobox.getSelectedItem().toString()).intValue());
       }
     });
     return toolbar;
   }
 }