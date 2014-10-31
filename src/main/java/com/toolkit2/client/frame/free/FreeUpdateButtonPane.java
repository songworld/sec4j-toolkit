package com.toolkit2.client.frame.free;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FreeUpdateButtonPane extends JPanel
{
  ImageIcon backgroudIcon = FreeUtil.getImageIcon("update_border_bottom_middle.png");
  ImageIcon backgroudLeftIcon = FreeUtil.getImageIcon("update_border_left_bottom.png");
  ImageIcon backgroudRightIcon = FreeUtil.getImageIcon("update_border_right_bottom.png");

  public FreeUpdateButtonPane() {
    setLayout(new BorderLayout());
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D)g;
    g.drawImage(this.backgroudIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
    g2d.drawImage(this.backgroudLeftIcon.getImage(), 0, 0, null);
    g2d.drawImage(this.backgroudRightIcon.getImage(), getWidth() - this.backgroudRightIcon.getImage().getWidth(null), 0, null);
  }

  public Dimension getPreferredSize()
  {
    Dimension size = super.getPreferredSize();
    size.height = this.backgroudIcon.getIconHeight();
    return size;
  }
}
