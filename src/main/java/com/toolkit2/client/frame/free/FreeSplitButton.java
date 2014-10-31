package com.toolkit2.client.frame.free;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.toolkit2.client.Shell;

public class FreeSplitButton extends JButton
{
  private Color shadowColor = Color.darkGray;
  private int width = 6;
  
  private ContainerListener mainTabContainerListener = new ContainerListener()
  {
    public void componentAdded(ContainerEvent e) {
      tabChanged();
    }

    public void componentRemoved(ContainerEvent e) {
      tabChanged();
    }

    private void tabChanged() {
      Color fillColor = FreeSplitButton.this.getFillColor();
      FreeSplitButton.this.btnHandler.setBackground(fillColor);
      FreeSplitButton.this.repaint();
    }
  };

  private boolean listenerInstalled = false;
  private ImageIcon imageOpen = FreeUtil.getImageIcon("split-left.png");
  private ImageIcon imageClose = FreeUtil.getImageIcon("split-right.png");
  private boolean mouseOverHandler = false;
  private JLabel btnHandler = new JLabel(this.imageOpen)
  {
    protected void paintComponent(Graphics g) {
      boolean hasTab = Shell.getInstance().getTabbedPane().getTabCount() > 0;
      Color fillColor = null;
      if (FreeSplitButton.this.mouseOverHandler) {
        if (hasTab)
          fillColor = UIManager.getColor("SplitPane.shadow");
        else {
          fillColor = FreeUtil.CONTENT_PANE_BACKGROUND.darker();
        }
      }
      else if (hasTab)
        fillColor = UIManager.getColor("SplitPane.background");
      else {
        fillColor = FreeUtil.CONTENT_PANE_BACKGROUND;
      }

      g.setColor(fillColor);
      g.fillRect(0, 0, getWidth(), getHeight());
      super.paintComponent(g);
    }
  };

  public FreeSplitButton()
  {
    setRolloverEnabled(true);
    setBorder(null);
    setBorderPainted(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setPreferredSize(new Dimension(this.width, this.width));
    setLayout(new LayoutManager()
    {
      public void addLayoutComponent(String name, Component comp) {
      }

      public void removeLayoutComponent(Component comp) {
      }

      public Dimension preferredLayoutSize(Container parent) {
        return FreeSplitButton.this.getSize();
      }

      public Dimension minimumLayoutSize(Container parent) {
        return new Dimension();
      }

      public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
          int x = 0;
          int y = (FreeSplitButton.this.getHeight() - FreeSplitButton.this.btnHandler.getHeight()) / 2;
          int width = FreeSplitButton.this.btnHandler.getPreferredSize().width;
          int height = FreeSplitButton.this.btnHandler.getPreferredSize().height;
          FreeSplitButton.this.btnHandler.setBounds(x, y, width, height);
        }
      }
    });
    this.btnHandler.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
    this.btnHandler.setCursor(Cursor.getDefaultCursor());
    this.btnHandler.addMouseListener(new MouseAdapter()
    {
      public void mouseEntered(MouseEvent e) {
        FreeSplitButton.this.mouseOverHandler = true;
        FreeSplitButton.this.btnHandler.repaint();
      }

      public void mouseExited(MouseEvent e) {
        FreeSplitButton.this.mouseOverHandler = false;
        FreeSplitButton.this.btnHandler.repaint();
      }
    });
    add(this.btnHandler);
  }

  public JLabel getHandlerButton() {
    return this.btnHandler;
  }

  public void updateUI() {
    super.updateUI();
    setBorder(null);
  }

  public void setSplitClosed(boolean splitClosed) {
    if (splitClosed){
      this.btnHandler.setIcon(this.imageClose);
    System.out.println("FreeSplitButton.java Info:execute close!");  
    }
    else{
      this.btnHandler.setIcon(this.imageOpen);
      System.out.println("FreeSplitButton.java Info:execute open!");  
      }
  }
  private Color getFillColor()
  {
    Color fillColor = null;
    boolean hasTab = Shell.getInstance().getTabbedPane().getTabCount() > 0;
    if (getModel().isRollover()) {
      if (hasTab)
        fillColor = UIManager.getColor("SplitPane.shadow");
      else {
        fillColor = FreeUtil.CONTENT_PANE_BACKGROUND.darker();
      }
    }
    else if (hasTab)
      fillColor = UIManager.getColor("SplitPane.background");
    else {
      fillColor = FreeUtil.CONTENT_PANE_BACKGROUND;
    }

    return fillColor;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if ((!this.listenerInstalled) && 
      (Shell.isInstanced())) {
    	Shell.getInstance().getTabbedPane().addContainerListener(this.mainTabContainerListener);
      this.listenerInstalled = true;
    }

    Color fillColor = getFillColor();
    int w = getWidth();
    int h = getHeight();
    g.setColor(fillColor);
    g.fillRect(0, 0, w, h);
    boolean hasTab = Shell.getInstance().getTabbedPane().getTabCount() > 0;
    for (int y = 0; 
      y < h - 2; y += 4) {
      if (hasTab)
        g.setColor(Color.WHITE);
      else {
        g.setColor(FreeUtil.CONTENT_PANE_BACKGROUND.brighter());
      }
      g.fillRect(1, y, 1, 1);
      g.fillRect(3, y + 2, 1, 1);
      g.setColor(this.shadowColor);
      g.fillRect(2, y + 1, 1, 1);
      g.fillRect(4, y + 3, 1, 1);
    }
  }
}