package com.toolkit2.client.frame.free;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.border.Border;

public class FreeUnderlineBorder
  implements Border
{
  private Image underlineImage = FreeUtil.getImage("textfield_underline.png");
  public static final FreeUnderlineBorder INSTANCE = new FreeUnderlineBorder();

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.drawImage(this.underlineImage, x, y + height - this.underlineImage.getHeight(null), width, this.underlineImage.getHeight(null), null);
  }

  public Insets getBorderInsets(Component c) {
    return new Insets(0, 0, 2, 0);
  }

  public boolean isBorderOpaque() {
    return true;
  }
}