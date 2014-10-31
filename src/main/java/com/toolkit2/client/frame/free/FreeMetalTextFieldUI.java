package com.toolkit2.client.frame.free;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.plaf.metal.MetalTextFieldUI;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.PasswordView;
import javax.swing.text.View;
import twaver.TWaverUtil;

public class FreeMetalTextFieldUI extends MetalTextFieldUI
{
  private String backgroundImageURL = FreeUtil.getImageURL("textfield_background.png");
  private Image backgroundLeftImage = FreeUtil.getImage("textfield_background_left.png");
  private Image backgroundRightImage = FreeUtil.getImage("textfield_background_right.png");
  private ImageIcon backgroundImageIcon = TWaverUtil.getImageIcon(this.backgroundImageURL);
  private TexturePaint paint = FreeUtil.createTexturePaint(this.backgroundImageURL);

  public View create(Element elem)
  {
    if ((getComponent() instanceof JPasswordField)) {
      return new PasswordView(elem);
    }
    return super.create(elem);
  }

  protected void paintBackground(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    if (getComponent().isEditable()) {
      if (this.backgroundImageIcon.getIconHeight() < getComponent().getHeight() - 1) {
        BufferedImage bi = new BufferedImage(this.backgroundImageIcon.getIconWidth(), this.backgroundImageIcon.getIconHeight(), 2);
        Graphics biGraphics = bi.createGraphics();
        biGraphics.drawImage(this.backgroundImageIcon.getImage(), 0, 0, null);
        biGraphics.dispose();
        TexturePaint paint = new TexturePaint(bi, new Rectangle(0, 0, this.backgroundImageIcon.getIconWidth(), getComponent().getHeight() - 1));
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, getComponent().getWidth(), getComponent().getHeight());

        g2d.drawImage(this.backgroundLeftImage, 0, 0, this.backgroundLeftImage.getWidth(null), getComponent().getHeight() - 1, null);

        g2d.drawImage(this.backgroundRightImage, getComponent().getWidth() - this.backgroundRightImage.getWidth(null), 0, this.backgroundRightImage.getWidth(null), getComponent().getHeight() - 1, null);
      } else {
        g2d.setPaint(this.paint);
        g2d.fillRect(0, 0, getComponent().getWidth(), getComponent().getHeight());

        g2d.drawImage(this.backgroundLeftImage, 0, 0, null);

        g2d.drawImage(this.backgroundRightImage, getComponent().getWidth() - this.backgroundRightImage.getWidth(null), 0, null);
      }
    }
    else super.paintBackground(g);
  }

  public Dimension getPreferredSize(JComponent c)
  {
    return new Dimension(getComponent().getWidth(), this.backgroundImageIcon.getIconHeight());
  }
}