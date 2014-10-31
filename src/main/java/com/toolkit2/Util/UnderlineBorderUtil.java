package com.toolkit2.Util;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.toolkit2.client.frame.free.FreeUnderlineBorder;

public class UnderlineBorderUtil
  implements Border, Serializable
{
  public static final Border INSTANCE = new FreeUnderlineBorder();
  public static final UnderlineBorderUtil INSTANCE_BLUE = new UnderlineBorderUtil(Color.BLUE);
  public static final Border INSTANCE_NORMAL = BorderFactory.createLineBorder(new Color(158, 159, 160));
  private static final int DEFAULT_BOTTOM = 2;
  private Color lineColor = null;
  private int bottom = 2;

  public UnderlineBorderUtil() {
    this(Color.gray, 2);
  }

  public UnderlineBorderUtil(Color color) {
    this(color, 2);
  }

  public UnderlineBorderUtil(Color color, int bottom) {
    this.lineColor = color;
    this.bottom = bottom;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.setColor(this.lineColor);
    g.drawLine(x, y + height - this.bottom, x + width, y + height - this.bottom);
  }

  public Insets getBorderInsets(Component c) {
    return new Insets(0, 0, this.bottom, 0);
  }

  public boolean isBorderOpaque() {
    return true;
  }
}