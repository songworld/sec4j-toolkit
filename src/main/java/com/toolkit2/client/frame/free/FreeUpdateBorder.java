package com.toolkit2.client.frame.free;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.border.Border;

public class FreeUpdateBorder
  implements Border
{
  private Image imageLeftTop = FreeUtil.getImage("update_border_left_top.png");
  private Image imageTopMiddle = FreeUtil.getImage("update_border_top_middle.png");
  private Image imageLeftMiddle = FreeUtil.getImage("update_border_left_middle.png");
  private Image imageRightTop = FreeUtil.getImage("update_border_right_top.png");
  private Image imageRightMiddle = FreeUtil.getImage("update_border_right_middle.png");
  public static final FreeUpdateBorder INSTANCE = new FreeUpdateBorder();

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.drawImage(this.imageLeftTop, x, y, null);
    g.drawImage(this.imageTopMiddle, x + this.imageLeftTop.getWidth(null), y, width - this.imageRightTop.getWidth(null) - this.imageLeftTop.getWidth(null), this.imageTopMiddle.getHeight(null), null);
    g.drawImage(this.imageRightTop, x + width - this.imageRightTop.getWidth(null), y, null);
    g.drawImage(this.imageLeftMiddle, x, y + this.imageLeftTop.getHeight(null), this.imageLeftMiddle.getWidth(null), height - this.imageLeftTop.getHeight(null), null);
    g.drawImage(this.imageRightMiddle, x + width - this.imageRightMiddle.getWidth(null), y + this.imageRightTop.getHeight(null), this.imageRightMiddle.getWidth(null), height - this.imageRightTop.getHeight(null), null);
  }

  public Insets getBorderInsets(Component c) {
    return new Insets(this.imageLeftTop.getHeight(null), this.imageLeftTop.getWidth(null), 0, this.imageRightTop.getWidth(null));
  }

  public boolean isBorderOpaque() {
    return true;
  }
}