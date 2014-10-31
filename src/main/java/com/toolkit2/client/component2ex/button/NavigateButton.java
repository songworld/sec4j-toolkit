package com.toolkit2.client.component2ex.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class NavigateButton extends JButton {
	public static final int SIZE = 22;
	  private int buttonSize = 20;
	  private Color roverBorderColor = FreeUtil.BUTTON_ROVER_COLOR;
	  private Border roverBorder = new Border()
	  {
	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	      g.setColor(NavigateButton.this.roverBorderColor);
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

	  public NavigateButton(int function) {
	    setMargin(new Insets(0, 0, 0, 0));
	    Dimension size = new Dimension(22, 22);
	    setSize(size);
	    setMaximumSize(size);
	    setMinimumSize(size);
	    setPreferredSize(size);
	    switch (function) {
	    case 0:
	      setIcon(FreeIconStore.FIRST_ICON);
	      setToolTipText(Translator.getString("PagingNavigatorPane.First"));

	      setIcon(FreeIconStore.FIRST_ICON);

	      break;
	    case 1:
	      setIcon(FreeIconStore.LAST_ICON);
	      setToolTipText(Translator.getString("PagingNavigatorPane.Last"));

	      setIcon(FreeIconStore.LAST_ICON);

	      break;
	    case 2:
	      setIcon(FreeIconStore.NEXT_ICON);

	      setIcon(FreeIconStore.NEXT_ICON);
	      setRolloverIcon(FreeIconStore.NEXT_MOUSE_OVER_ICON);
	      setPressedIcon(FreeIconStore.NEXT_MOUSE_OVER_ICON);

	      setToolTipText(Translator.getString("PagingNavigatorPane.Next"));
	      break;
	    case 3:
	      setIcon(FreeIconStore.PREV_ICON);
	      setToolTipText(Translator.getString("PagingNavigatorPane.Prior"));

	      setIcon(FreeIconStore.PREVIOUS_ICON);
	      setRolloverIcon(FreeIconStore.PREVIOUS_MOUSE_OVER_ICON);
	      setPressedIcon(FreeIconStore.PREVIOUS_MOUSE_OVER_ICON);

	      break;
	    case 4:
	      setIcon(FreeIconStore.GOTO_ICON);
	      setToolTipText(Translator.getString("PagingNavigatorPane.Goto"));
	      break;
	    }

	    init();
	  }

	  private void init()
	  {
	    setVerticalAlignment(0);

	    setFont(FreeUtil.getFont13Bold());

	    setOpaque(false);
	    setBorder(this.emptyBorder);
	    setContentAreaFilled(false);
	    setFocusPainted(false);
	    setRolloverEnabled(true);
	    addMouseListener(new MouseAdapter()
	    {
	      public void mouseEntered(MouseEvent e) {
	        if (NavigateButton.this.isRolloverEnabled())
	          NavigateButton.this.setBorder(NavigateButton.this.roverBorder);
	      }

	      public void mouseExited(MouseEvent e)
	      {
	        if (NavigateButton.this.isRolloverEnabled())
	          NavigateButton.this.setBorder(NavigateButton.this.emptyBorder);
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

	  public void setRoverBorder(Border roverBorder)
	  {
	    this.roverBorder = roverBorder;
	    repaint();
	  }
}
