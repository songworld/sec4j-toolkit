 package com.toolkit2.client.test;
 
 import twaver.chart.LineChart;
 
 public class RangeChart extends LineChart
 {
   public RangeChart()
   {
     setEnableXTranslate(false);
     setEnableXZoom(false);
     setXScaleTextSpanCount(30);
     setXScaleTextOrientation(4);
   }
 }