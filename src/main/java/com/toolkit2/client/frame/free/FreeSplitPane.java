package com.toolkit2.client.frame.free;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class FreeSplitPane extends JPanel
{
  public static final int DEFAULT_SPLIT_SIZE = 7;
  private Color shadowColor = Color.darkGray;
  private JComponent leftComponent = null;
  private JComponent rightComponent = null;
  private JButton divider = new JButton()
  {
    public void paint(Graphics g) {
      super.paint(g);
      Color fillColor = null;
      if (getModel().isRollover())
        fillColor = UIManager.getColor("SplitPane.shadow");
      else {
        fillColor = UIManager.getColor("SplitPane.background");
      }
      int w = getWidth();
      int h = getHeight();
      g.setColor(fillColor);
      g.fillRect(0, 0, w, h);
      for (int x = 0; x < w - 2; x += 4)
        for (int y = 0; y < h - 2; y += 4) {
          g.setColor(Color.WHITE);
          g.fillRect(x + 1, y, 1, 1);
          g.fillRect(x + 3, y + 2, 1, 1);
          g.setColor(FreeSplitPane.this.shadowColor);
          g.fillRect(x + 2, y + 1, 1, 1);
          g.fillRect(x + 4, y + 3, 1, 1);
        }
    }
  };

  private JPanel leftContainer = new JPanel(new BorderLayout());
  private boolean pressedAndNoDragged = false;
  private int lastLocation = 300;
  private boolean leftRightDirection = true;
  private boolean dockLeft = true;

  public FreeSplitPane(boolean leftRightDirection, boolean dockLeft, JComponent leftComponent, JComponent rightComponent) {
    this.leftRightDirection = leftRightDirection;
    this.dockLeft = dockLeft;
    setLayout(new BorderLayout());
    if (leftRightDirection)
      add(this.leftContainer, "West");
    else {
      add(this.leftContainer, "North");
    }
    setLeftComponent(leftComponent);
    setRightComponent(rightComponent);
    initDivider();
  }

  private void initDivider() {
    if (this.leftRightDirection)
      this.leftContainer.add(this.divider, "East");
    else {
      this.leftContainer.add(this.divider, "South");
    }
    this.divider.setRolloverEnabled(true);
    this.divider.setCursor(getResizeCursor());
    this.divider.setPreferredSize(new Dimension(7, 7));
    MouseAdapter listener = new MouseAdapter()
    {
      private Point lastPoint = null;

      public void mousePressed(MouseEvent e)
      {
        FreeSplitPane.this.pressedAndNoDragged = true;
        this.lastPoint = e.getPoint();
      }

      public void mouseReleased(MouseEvent e)
      {
        this.lastPoint = null;
        if (FreeSplitPane.this.pressedAndNoDragged) {
          int location = FreeSplitPane.this.getDividLocation();
          //如果停靠在左边
          if (FreeSplitPane.this.dockLeft) {
            if (location == 7) {
              location = FreeSplitPane.this.lastLocation;
              if (FreeSplitPane.this.lastLocation <= 7) {
                location = 300;
              }
              FreeSplitPane.this.divider.setCursor(FreeSplitPane.this.getResizeCursor());
              
            } else {
              location = 7;
              FreeSplitPane.this.divider.setCursor(Cursor.getDefaultCursor());
            }
          }
          else if (FreeSplitPane.this.leftRightDirection) {
            if (location == FreeSplitPane.this.getWidth()) {
              location = FreeSplitPane.this.lastLocation;
              if (FreeSplitPane.this.lastLocation >= FreeSplitPane.this.getWidth()) {
                location = 300;
              }
              FreeSplitPane.this.divider.setCursor(FreeSplitPane.this.getResizeCursor());
            } else {
              location = FreeSplitPane.this.getWidth();
              FreeSplitPane.this.divider.setCursor(Cursor.getDefaultCursor());
            }
          }
          else if (location == FreeSplitPane.this.getHeight()) {
            location = FreeSplitPane.this.lastLocation;
            if (FreeSplitPane.this.lastLocation >= FreeSplitPane.this.getHeight()) {
              location = 300;
            }
            FreeSplitPane.this.divider.setCursor(FreeSplitPane.this.getResizeCursor());
          } else {
            location = FreeSplitPane.this.getHeight();
            FreeSplitPane.this.divider.setCursor(Cursor.getDefaultCursor());
          }

          FreeSplitPane.this.setDividLocation(location);
        }
      }

      public void mouseDragged(MouseEvent e)
      {
        FreeSplitPane.this.pressedAndNoDragged = false;
        if ((FreeSplitPane.this.leftComponent != null) && (FreeSplitPane.this.leftComponent.isVisible()) && 
          (this.lastPoint != null)) {
          int location = FreeSplitPane.this.getDividLocation();
          Point thisPoint = e.getPoint();
          int movement = 0;
          if (FreeSplitPane.this.leftRightDirection)
            movement = thisPoint.x - this.lastPoint.x;
          else {
            movement = thisPoint.y - this.lastPoint.y;
          }
          location += movement;
          FreeSplitPane.this.setDividLocation(location);
          FreeSplitPane.this.revalidate();
        }
      }
    };
    this.divider.addMouseListener(listener);
    this.divider.addMouseMotionListener(listener);
  }

  public int getDividLocation() {
    if (this.leftRightDirection) {
      return this.leftContainer.getPreferredSize().width;
    }
    return this.leftContainer.getPreferredSize().height;
  }

  public void setDividLocation(int location)
  {
    Dimension size = this.leftContainer.getPreferredSize();
    if (this.leftRightDirection) {
      size.width = location;
      size.width = (size.width < 7 ? 7 : size.width);
      size.width = (size.width > getWidth() ? getWidth() : size.width);
    } else {
      size.height = location;
      size.height = (size.height < 7 ? 7 : size.height);
      size.height = (size.height > getHeight() ? getHeight() : size.height);
    }
    if (this.leftRightDirection) {
      if (size.width != 7) {
        this.lastLocation = this.leftContainer.getPreferredSize().width;
        this.divider.setCursor(getResizeCursor());
      } else {
        this.divider.setCursor(Cursor.getDefaultCursor());
      }
    }
    else if (size.height != 7) {
      this.lastLocation = this.leftContainer.getPreferredSize().height;
      this.divider.setCursor(getResizeCursor());
    } else {
      this.divider.setCursor(Cursor.getDefaultCursor());
    }

    this.leftContainer.setPreferredSize(size);
    revalidate();
  }

  private void setLeftComponent(JComponent leftComponent) {
    if (this.leftComponent != null) {
      this.leftContainer.remove(this.leftComponent);
    }
    this.leftComponent = leftComponent;
    if (leftComponent != null) {
      this.leftContainer.add(leftComponent, "Center");
    }
    this.leftContainer.revalidate();
    if (this.leftRightDirection)
      this.lastLocation = this.leftContainer.getPreferredSize().width;
    else
      this.lastLocation = this.leftContainer.getPreferredSize().height;
  }

  private void setRightComponent(JComponent rightComponent)
  {
    if (rightComponent != null) {
      remove(rightComponent);
    }
    this.rightComponent = rightComponent;
    if (rightComponent != null) {
      add(rightComponent, "Center");
    }
    if (this.leftRightDirection)
      this.lastLocation = this.leftContainer.getPreferredSize().width;
    else
      this.lastLocation = this.leftContainer.getPreferredSize().height;
  }

  private Cursor getResizeCursor()
  {
    if (this.leftRightDirection) {
      return Cursor.getPredefinedCursor(11);
    }
    return Cursor.getPredefinedCursor(8);
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(3);
    frame.setSize(800, 600);
    FreeSplitPane split = new FreeSplitPane(true, false, new JButton("Left"), new JButton("Right"));
    frame.add(split, "Center");
    frame.setVisible(true);
  }
}