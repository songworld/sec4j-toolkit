package com.toolkit2.client.frame.free;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

public class FreeListSeparator extends JPanel
{
  private String imageName = "list_separator.png";
  private Image backgroundImage = FreeUtil.getImage(this.imageName);
  private int imageHeight = this.backgroundImage.getHeight(null);
  private int preferredWidth = -1;

  public FreeListSeparator() {
    this(-1);
  }

  public FreeListSeparator(int preferredWidth) {
    this.preferredWidth = preferredWidth;
  }

  public Dimension getPreferredSize()
  {
    if (this.preferredWidth == -1) {
      return super.getPreferredSize();
    }
    return new Dimension(this.preferredWidth, super.getPreferredSize().height);
  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2d = (Graphics2D)g;
    int x = 1;
    int y = getHeight() / 2;
    g2d.drawImage(this.backgroundImage, x, y, getWidth(), this.imageHeight, null);
  }
}