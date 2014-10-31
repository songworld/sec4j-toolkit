 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Point;
 import java.util.ArrayList;
 import java.util.List;
 import twaver.ResizableNode;
 import twaver.TWaverUtil;
 
 public class StateNode extends ResizableNode
 {
   private double c2000;
   private double p2005;
   private double p2010;
   private double p2015;
   private double p2020;
   private double p2025;
   private double p2030;
   private List valueList = new ArrayList();
 
   private int attachmentDirection = 2;
 
   public void init(double c2000, double p2005, double p2010, double p2015, double p2020, double p2025, double p2030)
   {
     this.c2000 = c2000;
     this.p2005 = p2005;
     this.p2010 = p2010;
     this.p2015 = p2015;
     this.p2020 = p2020;
     this.p2025 = p2025;
     this.p2030 = p2030;
 
     double sum = c2000 + p2005 + p2010 + p2015 + p2020 + p2025 + p2030;
 
     this.valueList.add(new Double(c2000 / sum));
     this.valueList.add(new Double(p2005 / sum));
     this.valueList.add(new Double(p2010 / sum));
     this.valueList.add(new Double(p2015 / sum));
     this.valueList.add(new Double(p2020 / sum));
     this.valueList.add(new Double(p2025 / sum));
     this.valueList.add(new Double(p2030 / sum));
 
     putCustomDrawFillColor(Color.ORANGE);
     putBorderColor(Color.YELLOW);
     putBorderStroke("solid.middle");
     putCustomDrawOutline(false);
     putLabelFont(TWaverUtil.getFont(1, 10.0F));
     putLabelColor(Color.WHITE);
     putLabelHighlightForeground(Color.GREEN);
 
     int size = (int)(8.0D + Math.sqrt(c2000 / 300000000.0D) * 150.0D);
     Point point = getCenterLocation();
     setSize(size, size);
     setCenterLocation(point.x, point.y);
   }
 
   public double getC2000() {
     return this.c2000;
   }
 
   public double getP2005() {
     return this.p2005;
   }
 
   public double getP2010() {
     return this.p2010;
   }
 
   public double getP2015() {
     return this.p2015;
   }
 
   public double getP2020() {
     return this.p2020;
   }
 
   public double getP2025() {
     return this.p2025;
   }
 
   public double getP2030() {
     return this.p2030;
   }
 
   public int getAttachmentDirection() {
     return this.attachmentDirection;
   }
 
   public void setAttachmentDirection(int attachmentDirection) {
     if (this.attachmentDirection != attachmentDirection) {
       int oldValue = this.attachmentDirection;
       this.attachmentDirection = attachmentDirection;
       firePropertyChange("attachmentDirection", oldValue, attachmentDirection);
     }
   }
 
   public List getValueList() {
     return this.valueList;
   }
 }

