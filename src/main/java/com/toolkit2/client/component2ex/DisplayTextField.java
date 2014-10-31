package com.toolkit2.client.component2ex;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.text.Caret;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.UnderlineBorderUtil;
import com.toolkit2.client.frame.free.FreeUtil;

import twaver.TWaverUtil;

public class DisplayTextField extends JTextField
{
  private boolean drawStrikethrough = false;

  public DisplayTextField() {
    init(2);
  }

  public DisplayTextField(int align) {
    init(align);
  }

  public DisplayTextField(int align, boolean highlight) {
    init(align);
    if (highlight)
      ClientUtil.setHighlightComponent(this);
  }

  public DisplayTextField(boolean highlight)
  {
    this();
    if (highlight)
      ClientUtil.setHighlightComponent(this);
  }

  public DisplayTextField(int align, int columns)
  {
    init(align);
    setColumns(columns);
  }

  public DisplayTextField(String text) {
    init(2);
    setText(text);
  }

  private void init(int i) {
    setEditable(false);
    setHorizontalAlignment(i);
    setBorder(UnderlineBorderUtil.INSTANCE);
    setBackground(FreeUtil.ALL_UI_BACKGROUD_COLOR);
  }

  public void setText(String text) {
    setText(text, false);
  }

  public void setTextWithoutCheck(String text) {
    super.setText(text);
  }

  public void setText(String text, boolean drawStrikethrough) {
    this.drawStrikethrough = drawStrikethrough;
    super.setText(ClientUtil.getStringWithoutNull(text));
    setLocation();
  }

  public void setText(Number value) {
    setHorizontalAlignment(4);
    setText(ClientUtil.getNumberString(value));
    setLocation();
  }

  public void setText(Date date) {
    setText(ClientUtil.getDateLocalString(date));
    setLocation();
  }

  private void setLocation() {
    if (!"".equalsIgnoreCase(getText()))
      getCaret().setDot(0);
  }

  public BigDecimal getTextNumberZero()
  {
    String txt = super.getText();
    if ((txt == null) || ("".equalsIgnoreCase(txt))) {
      return BigDecimal.ZERO;
    }
    txt = ClientUtil.getStringNumber(txt);
    return new BigDecimal(txt);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    String text = getText();
    if ((this.drawStrikethrough) && (text != null) && (text.length() > 0)) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(Color.red);
      g2d.setStroke(TWaverUtil.createStroke(2));
      FontMetrics fm = getFontMetrics(getFont());
      int y = getHeight() / 2;
      int x1 = 0;
      int x2 = fm.stringWidth(text) + 3;
      if (x2 >= getWidth()) {
        x2 = getWidth();
      } else {
        int align = getHorizontalAlignment();
        if (getComponentOrientation().isLeftToRight()) {
          if (align == 10)
            align = 2;
          else if (align == 11) {
            align = 4;
          }
        }
        else if (align == 10)
          align = 4;
        else if (align == 11) {
          align = 2;
        }

        switch (align) {
        case 0:
          int gap = (getWidth() - x2) / 2;
          x1 += gap;
          x2 = x1 + x2;
          break;
        case 4:
          x1 = getWidth() - x2;
          x2 = getWidth();
        }
      }

      g.drawLine(x1, y, x2, y);
    }
  }

  public void setEditable(boolean b) {
    super.setEditable(b);
    setCaretColor(b ? Color.black : getBackground());
    if (b) {
      setBorder(UnderlineBorderUtil.INSTANCE_NORMAL);
      setBackground(Color.white);
    } else {
      setBorder(UnderlineBorderUtil.INSTANCE);
    }
  }
}