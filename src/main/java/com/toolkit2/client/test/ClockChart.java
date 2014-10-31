 package com.toolkit2.client.test;
 import java.awt.Color;
 import java.awt.Component;
 import java.awt.Dimension;
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import java.util.Calendar;
 import javax.swing.Icon;
 import javax.swing.JComboBox;
 import javax.swing.JList;
 import javax.swing.JPanel;
 import javax.swing.JToolBar;
 import javax.swing.ListCellRenderer;
 import javax.swing.plaf.basic.BasicComboBoxRenderer;
 import twaver.Element;
 import twaver.EnumType;
 import twaver.EnumTypeManager;
 import twaver.Node;
 import twaver.TDataBox;
 import twaver.TUIManager;
 import twaver.TWaverUtil;
 import twaver.TaskAdapter;
 import twaver.TaskScheduler;
 import twaver.chart.DialChart;
 
 public class ClockChart extends Portlet
 {
   private Element hour;
   private Element minute;
   private Element second;
   private TDataBox box = new TDataBox();
   private DialChart chart = new DialChart(this.box)
   {
     public String getToolTipText(Element element) {
       double value = element.getChartValue();
       if (element == ClockChart.this.hour) {
         return element.getName() + ":" + (int)value;
       }
       return element.getName() + ":" + (int)(value * 5.0D);
     }
   };
 
   public ClockChart()
   {
     super.initialize(this.chart);
     this.chart.setTitle("Clock");
     this.chart.setStartAngle(-90.0D);
     this.chart.setEndAngle(270.0D);
     this.chart.setMaxValue(12.0D);
     this.chart.setScaleMajorCount(12);
     this.chart.setScaleMinorCount(4);
     this.chart.setScaleInside(true);
     this.chart.setScaleLength(12.0D);
     this.chart.setScaleStyle(1);
     this.chart.setBallSize(8.0D);
     this.chart.getLegendPane().setVisible(false);
     this.chart.setRingFillColor(Color.DARK_GRAY);
     this.chart.setRingGradientColor(Color.LIGHT_GRAY);
     this.chart.setRingGradientFactory(10);
     this.chart.setRingStroke("solid.5");
     this.chart.setValueTextVisible(false);
 
     Calendar calendar = Calendar.getInstance();
     double secondValue = calendar.get(13);
     double minuteValue = calendar.get(12) + secondValue / 60.0D;
     double hourValue = calendar.get(10) + minuteValue / 60.0D;
 
     this.hour = new Node();
     this.hour.setName("hour");
     this.hour.putChartDialHandLength(0.7D);
     this.hour.putChartDialHandStyle(3);
     this.hour.putChartValue(hourValue);
     this.hour.putChartColor(Color.GREEN.darker());
     this.hour.putChartStroke("solid.6");
 
     this.minute = new Node();
     this.minute.setName("minute");
     this.minute.putChartDialHandLength(0.8D);
     this.minute.putChartDialHandStyle(2);
     this.minute.putChartValue(minuteValue / 5.0D);
     this.minute.putChartColor(Color.ORANGE);
     this.minute.putChartStroke("solid.12");
 
     this.second = new Node();
     this.second.setName("second");
     this.second.putChartDialHandLength(0.9D);
     this.second.putChartDialHandStyle(1);
     this.second.putChartValue(secondValue / 5.0D);
     this.second.putChartColor(Color.BLACK);
     this.box.addElement(this.hour);
     this.box.addElement(this.minute);
     this.box.addElement(this.second);
 
     TaskScheduler.getInstance().register(new TaskAdapter()
     {
       public int getInterval() {
         return 1000;
       }
 
       public void run(long clock) {
         if (!ClockChart.this.isRunning()) {
           return;
         }
         Calendar calendar = Calendar.getInstance();
         double secondValue = calendar.get(13);
         double minuteValue = calendar.get(12) + secondValue / 60.0D;
         double hourValue = calendar.get(10) + minuteValue / 60.0D;
         ClockChart.this.hour.putChartValue(hourValue);
         ClockChart.this.minute.putChartValue(minuteValue / 5.0D);
         ClockChart.this.second.putChartValue(secondValue / 5.0D);
       }
     });
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = super.getControlPanel();
     toolbar.add(getRunButton());
     final JComboBox combobox = new JComboBox();
     final BasicComboBoxRenderer renderer = new BasicComboBoxRenderer();
     TWaverUtil.setHorizontalAlignment(renderer, TUIManager.getString("table.alignment.enumtype"));
     combobox.setRenderer(new ListCellRenderer()
     {
       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
         String text = null;
         Icon icon = null;
         Color background = null;
         Color foreground = null;
         if ((value instanceof EnumType)) {
           EnumType enumType = (EnumType)value;
           text = enumType.toString();
           icon = enumType.getIcon();
           background = enumType.getBackground();
           foreground = enumType.getForeground();
           if ((background != null) && (isSelected)) {
             background = background.darker();
           }
         }
         renderer.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
         renderer.setIcon(icon);
         renderer.setToolTipText(text);
         if (foreground != null) {
           renderer.setForeground(foreground);
         }
         if (background != null) {
           renderer.setBackground(background);
         }
         return renderer;
       }
     });
     combobox.setPreferredSize(new Dimension(combobox.getPreferredSize().width, 20));
     final Object[] gradientTypes = EnumTypeManager.getInstance().getEnumTypes("twaver.gradient");
     final int length = gradientTypes.length;
     for (int i = 0; i < length; i++) {
       EnumType enumeration = (EnumType)gradientTypes[i];
       combobox.addItem(enumeration);
       if (10 == ((Integer)enumeration.getValue()).intValue()) {
         combobox.setSelectedIndex(i);
       }
     }
     combobox.addItemListener(new ItemListener()
     {
       public void itemStateChanged(ItemEvent e) {
         Object object = combobox.getSelectedItem();
         for (int i = 0; i < length; i++) {
           EnumType enumeration = (EnumType)gradientTypes[i];
           if (enumeration == object)
             ClockChart.this.chart.setRingGradientFactory(((Integer)enumeration.getValue()).intValue());
         }
       }
     });
     toolbar.add(combobox);
     return toolbar;
   }
 }