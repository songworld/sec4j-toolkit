package com.toolkit2.client.frame.free;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ButtonModel;
import javax.swing.CellRendererPane;
import javax.swing.ComboBoxEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalComboBoxIcon;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class FreeMetalComboBoxUI extends MetalComboBoxUI
{
  public static Image BACKGROUND_IMAGE = FreeUtil.getImage("combo_background.png");
  private Image borderImage;
  private Image arrowImage;
  private Image underlineImage;
  private Image datePickImage;

  public FreeMetalComboBoxUI()
  {
    this.borderImage = FreeUtil.getImage("combo_border.png");
    this.arrowImage = FreeUtil.getImage("combo_arrow.png");
    this.underlineImage = FreeUtil.getImage("textfield_underline.png");
    this.datePickImage = FreeUtil.getImage("datePick.png");
  }
  protected ComboBoxEditor createEditor() {
    return new FreeMetalComboBoxEditor();
  }

  protected boolean getUseIcon() {
    return false;
  }

  protected JButton createArrowButton() {
    JButton button = new MetalComboBoxButton(this.comboBox, new MetalComboBoxIcon(), this.comboBox.isEditable(), this.currentValuePane, this.listBox)
    {
      public void paintComponent(Graphics g) {
        if (this.comboBox.isEnabled()) {
          g.drawImage(FreeMetalComboBoxUI.BACKGROUND_IMAGE, 0, 0, getWidth(), getHeight(), null);
          g.drawImage(FreeMetalComboBoxUI.this.borderImage, 0, 0, FreeMetalComboBoxUI.this.borderImage.getWidth(null), getHeight(), null);
          g.drawImage(FreeMetalComboBoxUI.this.borderImage, getWidth() - 1, 0, FreeMetalComboBoxUI.this.borderImage.getWidth(null), getHeight(), null);
        } else {
          g.drawImage(FreeMetalComboBoxUI.this.underlineImage, 0, getHeight() - 2, getWidth(), 2, null);
        }
        if (getModel().isPressed()) {
          g.setColor(Color.lightGray);
          g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        }
        int x = getWidth() - FreeMetalComboBoxUI.this.arrowImage.getWidth(null) - 3;
        int y = (getHeight() - FreeMetalComboBoxUI.this.arrowImage.getHeight(null)) / 2;
        if (getModel().isPressed()) {
          y++;
        }
        if (!FreeMetalComboBoxUI.this.getUseIcon())
          g.drawImage(FreeMetalComboBoxUI.this.arrowImage, x, y, null);
        else {
          g.drawImage(FreeMetalComboBoxUI.this.datePickImage, 0, 0, getWidth(), getHeight(), null);
        }
        if (!this.comboBox.isEditable()) {
          ListCellRenderer renderer = this.comboBox.getRenderer();
          Insets insets = getInsets();
          int width = getWidth() - (insets.left + insets.right);
          int height = getHeight() - (insets.top + insets.bottom);
          Component c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, false, false);
          c.setFont(this.rendererPane.getFont());
          int cWidth = width - insets.right - FreeMetalComboBoxUI.this.arrowImage.getWidth(null) - 3;

          boolean shouldValidate = false;
          if ((c instanceof JPanel)) {
            shouldValidate = true;
          }
          if ((c instanceof JComponent)) {
            ((JComponent)c).setOpaque(false);
          }
          this.rendererPane.paintComponent(g, c, this, insets.left, insets.top, cWidth, height, shouldValidate);
          if ((c instanceof JComponent))
            ((JComponent)c).setOpaque(true);
        }
      }

      public Insets getInsets()
      {
        return new Insets(0, 0, 0, 0);
      }
    };
    button.setBorder(null);
    button.setContentAreaFilled(false);
    return button;
  }

  public static class FreeMetalComboBoxEditor extends BasicComboBoxEditor
  {
    public FreeMetalComboBoxEditor()
    {
      this.editor = new FreeTextField()
      {
        public void setText(String s)
        {
          if (getText().equals(s)) {
            return;
          }
          super.setText(s);
        }

        public Dimension getPreferredSize()
        {
          Dimension pref = super.getPreferredSize();
          pref.height += 4;
          return pref;
        }

        public Dimension getMinimumSize() {
          Dimension min = super.getMinimumSize();
          min.height += 4;
          return min;
        }
      };
    }
  }
}