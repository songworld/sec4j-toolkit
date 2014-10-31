 package com.toolkit2.client.test;
 
 import java.awt.Color;
 import java.awt.Graphics2D;
 import java.awt.Point;
 import java.awt.Rectangle;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseEvent;
 import javax.swing.JComponent;
 import javax.swing.JMenuItem;
 import javax.swing.JPanel;
 import javax.swing.JPopupMenu;
 import javax.swing.SwingUtilities;
 import twaver.PopupMenuGenerator;
 import twaver.TView;
 import twaver.chart.LineChart;
 
 public class HistoryChart extends LineChart
 {
   private int valuesCount = 0;
   private LineChart rangeChart = null;
   private LineChart volumeChart = null;
   private Point startPoint = null;
   private Point endPoint = null;
   private Point lastPoint = null;
 
   public void paintChart(Graphics2D g2d, int width, int height) {
     super.paintChart(g2d, width, height);
     if ((this.startPoint != null) && (this.endPoint != null)) {
       Rectangle bounds = getBackgroundBounds();
       g2d.setColor(new Color(0, 255, 0, 128));
       int x = Math.min(this.startPoint.x, this.endPoint.x);
       int y = bounds.y;
       int w = Math.abs(this.endPoint.x - this.startPoint.x);
       int h = bounds.height;
       g2d.fillRect(x, y, w, h);
     }
   }
 
   private boolean isValidEvent(MouseEvent e) {
     if (SwingUtilities.isLeftMouseButton(e)) {
       Rectangle bounds = getBackgroundBounds();
       bounds.grow(1, 1);
       if (bounds.contains(e.getPoint())) {
         return true;
       }
     }
     return false;
   }
 
   private boolean isInsideEvent(MouseEvent e) {
     if ((this.startPoint != null) && (this.endPoint != null)) {
       if ((e.getX() >= this.startPoint.x) && (e.getX() <= this.endPoint.getX())) {
         return true;
       }
       if ((e.getX() >= this.endPoint.x) && (e.getX() <= this.startPoint.getX())) {
         return true;
       }
     }
     return false;
   }
 
   public void mousePressed(MouseEvent e) {
     this.lastPoint = null;
     if (isValidEvent(e)) {
       if (isInsideEvent(e)) {
         this.lastPoint = e.getPoint();
       } else {
         this.startPoint = e.getPoint();
         this.endPoint = e.getPoint();
         this.lastPoint = null;
       }
       changeRange();
     }
   }
 
   public void mouseDragged(MouseEvent e) {
     if ((isValidEvent(e)) && (this.startPoint != null)) {
       if (this.lastPoint != null) {
         int offset = this.lastPoint.x - e.getX();
         this.startPoint.x -= offset;
         this.endPoint.x -= offset;
         this.lastPoint = e.getPoint();
       } else {
         this.endPoint = e.getPoint();
       }
       changeRange();
     }
   }
 
   private void changeRange() {
     getChartPane().repaint();
 
     if (this.startPoint.x == this.endPoint.x) {
       this.rangeChart.setStartIndex(0);
       this.rangeChart.setEndIndex(2147483647);
       this.volumeChart.setStartIndex(0);
       this.volumeChart.setEndIndex(2147483647);
       this.rangeChart.setXScaleTextSpanCount(30);
       this.volumeChart.setXScaleTextSpanCount(30);
     } else {
       double x1 = getStartX();
       double x2 = getEndX();
       double w = (x2 - x1) / (this.valuesCount - 1);
       int s = (int)((this.startPoint.x - x1) / w);
       int e = (int)((this.endPoint.x - x1) / w);
       if (s > e) {
         int tmp = e;
         e = s;
         s = tmp;
       }
       if (s < 0) {
         s = 0;
       }
       if (e > this.valuesCount) {
         e = this.valuesCount;
       }
 
       this.rangeChart.setStartIndex(s);
       this.rangeChart.setEndIndex(e);
       this.volumeChart.setStartIndex(s);
       this.volumeChart.setEndIndex(e);
 
       int span = Math.max(1, 15 * (e - s) / this.rangeChart.getBackgroundBounds().width - 1);
       this.rangeChart.setXScaleTextSpanCount(span);
       this.volumeChart.setXScaleTextSpanCount(span);
     }
   }
 
   public HistoryChart(LineChart rangeChart, LineChart volumeChart, int valuesCount)
   {
     this.rangeChart = rangeChart;
     this.volumeChart = volumeChart;
     this.valuesCount = valuesCount;
     setEnableXTranslate(false);
     setEnableXZoom(false);
     setEnableYTranslate(false);
     setEnableYZoom(false);
     setLineType(2);
     setValueSpanCount(7);
     setXScaleTextSpanCount(35);
     setXScaleTextOrientation(4);
     setLowerLimit(0.0D);
     setYScaleTextVisible(true);
     setYScaleValueGap(100.0D);
     setLegendLayout(2);
     getLegendPane().setVisible(false);
     setEnableToolTipText(false);
 
     setPopupMenuGenerator(new PopupMenuGenerator()
     {
       public JPopupMenu generate(TView tview, MouseEvent mouseEvent) {
         JPopupMenu popup = new JPopupMenu();
         popup.add(HistoryChart.this.createMenuItem(1, 15));
         popup.add(HistoryChart.this.createMenuItem(3, 30));
         popup.add(HistoryChart.this.createMenuItem(7, 35));
         popup.add(HistoryChart.this.createMenuItem(15, 45));
         popup.add(HistoryChart.this.createMenuItem(30, 60));
         popup.add(HistoryChart.this.createMenuItem(60, 60));
         popup.add(HistoryChart.this.createMenuItem(120, 120));
         return popup;
       }
     });
   }
 
   private JMenuItem createMenuItem(final int valueSpanCount, final int textSpanCount) {
     JMenuItem item = new JMenuItem("Value Span:" + valueSpanCount + "|Text Span:" + textSpanCount);
     item.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         HistoryChart.this.setValueSpanCount(valueSpanCount);
         HistoryChart.this.setXScaleTextSpanCount(textSpanCount);
       }
     });
     return item;
   }
 }