package com.toolkit2.client.frame.free;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

public class FreeToolbarToggleButton extends JToggleButton
{
  private int buttonSize = 20;
  private Color roverBorderColor = FreeUtil.BUTTON_ROVER_COLOR;
  private Border roverBorder = new Border()
  {
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      g.setColor(FreeToolbarToggleButton.this.roverBorderColor);
      g.drawRect(x, y, width - 1, height - 1);
    }

    public Insets getBorderInsets(Component c) {
      return new Insets(1, 1, 1, 1);
    }

    public boolean isBorderOpaque() {
      return true;
    }
  };

  private Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
  private Border selectedBorder = BorderFactory.createLineBorder(Color.green);

  public FreeToolbarToggleButton() {
    this(null, null);
  }

  public FreeToolbarToggleButton(String text) {
    this(text, null);
  }

  public FreeToolbarToggleButton(Icon icon) {
    this(null, icon);
  }

  public FreeToolbarToggleButton(String text, Icon icon) {
    this(text, icon, null);
  }

  public FreeToolbarToggleButton(String text, Icon icon, String tooltip) {
    super(text, icon);
    init();
    if (tooltip != null)
      setToolTipText(tooltip);
  }

  private void init()
  {
    setVerticalAlignment(0);
    setFont(FreeUtil.getFont12Plain());
    setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
    setOpaque(false);
    setBorder(this.emptyBorder);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setRolloverEnabled(true);
    addMouseListener(new MouseAdapter()
    {
      public void mouseEntered(MouseEvent e)
      {
        if ((FreeToolbarToggleButton.this.isEnabled()) && (FreeToolbarToggleButton.this.isRolloverEnabled()))
          FreeToolbarToggleButton.this.setBorder(FreeToolbarToggleButton.this.roverBorder);
      }

      public void mouseExited(MouseEvent e)
      {
        if ((FreeToolbarToggleButton.this.isEnabled()) && (FreeToolbarToggleButton.this.isRolloverEnabled()))
          FreeToolbarToggleButton.this.setBorder(FreeToolbarToggleButton.this.emptyBorder);
      }
    });
  }

  public void setIcon(Icon icon)
  {
    super.setIcon(icon);

    if (icon == null) {
      setPressedIcon(null);
      setRolloverIcon(null);
    } else {
      Icon pressedIcon = FreeUtil.createMovedIcon(icon);
      setPressedIcon(pressedIcon);
    }
  }

  public void setSelected(boolean selected)
  {
    super.setSelected(selected);
  }

  public Dimension getPreferredSize()
  {
    int width = super.getPreferredSize().width;
    width = Math.max(width, this.buttonSize);
    int height = this.buttonSize;
    return new Dimension(width, height);
  }

  public void setRoverBorder(Border roverBorder) {
    this.roverBorder = roverBorder;
    repaint();
  }
}