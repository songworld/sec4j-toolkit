 package com.toolkit2.client.test;
 
 import twaver.chart.LineChart;
 
 public class VolumeChart extends LineChart
 {
   public VolumeChart()
   {
     setEnableYTranslate(false);
     setEnableYZoom(false);
     setLineType(3);
     setLowerLimit(0.0D);
     setXScaleTextSpanCount(30);
     setXScaleTextOrientation(4);
   }
 }