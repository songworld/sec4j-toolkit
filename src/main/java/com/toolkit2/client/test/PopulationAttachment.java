 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.text.NumberFormat;
 import java.util.Vector;
 import twaver.TWaverConst;
 import twaver.TWaverUtil;
 import twaver.chart.BarChart;
 import twaver.chart.Item;
 import twaver.network.ui.ComponentAttachment;
 import twaver.network.ui.ElementUI;
 
 public class PopulationAttachment extends ComponentAttachment
 {
   private static final Color bodyColor = new Color(0, 0, 0, 168);
   private static final Color gradientColor = new Color(25, 255, 255, 108);
   private static BarChart bar;
   private static Item c2000;
   private static Item p2005;
   private static Item p2010;
   private static Item p2015;
   private static Item p2020;
   private static Item p2025;
   private static Item p2030;
 
   public PopulationAttachment(String name, ElementUI ui)
   {
     super(name, ui);
     setStyle(2);
     setPosition(1);
     setBorderVisible(true);
     setBorderColor(Color.BLACK);
     setBodyVisible(true);
     setBodyGradient(true);
     setBodyColor(bodyColor);
     setBodyGradientColor(gradientColor);
     setBodyGradientType(7);
     setBorderColor(Color.RED);
     setBorderVisible(false);
     setStyle(2);
     setComponent(bar);
     setWidth(200);
     setHeight(100);
   }
 
   protected void update() {
     updateAttachment();
     invalidate();
     super.update();
   }
 
   private void updateAttachment() {
     StateNode node = (StateNode)this.element;
 
     setDirection(node.getAttachmentDirection());
 
     c2000.setValue(node.getC2000());
     p2005.setValue(node.getP2005());
     p2010.setValue(node.getP2010());
     p2015.setValue(node.getP2015());
     p2020.setValue(node.getP2020());
     p2025.setValue(node.getP2025());
     p2030.setValue(node.getP2030());
 
     c2000.setName("2000 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(c2000.getValue()));
     p2005.setName("2005 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(p2005.getValue()));
     p2010.setName("2010 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(p2010.getValue()));
     p2015.setName("2015 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(p2015.getValue()));
     p2020.setName("2020 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(p2020.getValue()));
     p2025.setName("2025 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(p2025.getValue()));
     p2030.setName("2030 > " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(p2030.getValue()));
   }
 
   static
   {
     Vector items = new Vector();
     items.add(PopulationAttachment.c2000 = new Item("Census April 1, 2000", new Color(255, 0, 255)));
     items.add(PopulationAttachment.p2005 = new Item("Projections July 1, 2005", new Color(220, 0, 255)));
     items.add(PopulationAttachment.p2010 = new Item("Projections July 1, 2010", new Color(170, 0, 255)));
     items.add(PopulationAttachment.p2015 = new Item("Projections July 1, 2015", new Color(130, 0, 255)));
     items.add(PopulationAttachment.p2020 = new Item("Projections July 1, 2020", new Color(100, 0, 255)));
     items.add(PopulationAttachment.p2025 = new Item("Projections July 1, 2025", new Color(70, 0, 255)));
     items.add(PopulationAttachment.p2030 = new Item("Projections July 1, 2030", new Color(40, 0, 255)));
     bar = new BarChart(items, null, Color.BLACK);
     bar.setLegendLayout(2);
     bar.setValueTextVisible(false);
     bar.setForeground(Color.WHITE);
     bar.setLegendFont(TWaverUtil.getFont(2, 9.0F));
     bar.setYAxisOutlineColor(Color.WHITE);
     bar.setXAxisOutlineColor(Color.WHITE);
     bar.setOpaque(false);
   }
 }
