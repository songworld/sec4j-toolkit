package com.toolkit2.client.frame.mian;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import com.toolkit2.Util.ClientUtil;

import twaver.Element;
import twaver.Node;
import twaver.chart.PercentChart;

public class MemoryBarChart extends PercentChart
  implements MemoryBar
{
  private static final int ONE_K = 1024;
  private Element element = new Node();
  private long usedMemory = 0L;

  public MemoryBarChart() {
    setPercentLabelCenter(true);
    setGradient(true);
    this.element.putChartPercentStyle(2);
    this.element.putChartPercentSpareFill(true);
    setSpareColor(Color.lightGray);
    this.element.putChartPercentSpareCoverColor(Color.lightGray);
    this.box.addElement(this.element);
    setXGap(0);
    setYGap(0);
    setPreferredSize(new Dimension(150, 0));
    setOpaque(false);
    setEnableXZoom(false);
    setEnableYZoom(false);
  }

  public void updateMemoryUsage(long usedMemory, long totalMemory) {
    this.usedMemory = usedMemory;
    int used = (int)(usedMemory / 1024L);
    int total = (int)(totalMemory / 1024L);
    int free = total - used;

    int percent = (int)((1.0F - free / total) * 100.0F);
    this.element.putChartValue(percent);

    String text = percent + "%, " + ClientUtil.getNumberString(Integer.valueOf(used / 1024)) + "M/" + ClientUtil.getNumberString(Integer.valueOf(total / 1024)) + "M";
    this.element.setName(text);
  }

  public Color getColor(Element element) {
    int used = (int)(this.usedMemory / 1024L);
    int max = (int)(Runtime.getRuntime().maxMemory() / 1024L);
    int percentOfMax = (int)(used / max * 100.0F);
    int red = (int)(255.0F * percentOfMax / 100.0F);
    int green = (int)(255.0F * (100 - percentOfMax) / 100.0F);
    return new Color(red, green, 0);
  }

  public void mousePressed(MouseEvent e)
  {
  }
}
