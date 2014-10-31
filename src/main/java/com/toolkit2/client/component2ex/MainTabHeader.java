package com.toolkit2.client.component2ex;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import com.toolkit2.Util.ClientUtil;

public class MainTabHeader extends JComponent
{
  private String title = null;
  private SubForm clientUI = null;
  private JTabbedPane tab = null;
  private JLabel label = null;
  private JButton button = new JButton(ClientUtil.getIcon("close.png"));

  public MainTabHeader(JTabbedPane tab, SubForm clientUI, String title) {
    this.tab = tab;
    this.clientUI = clientUI;
    this.title = title;
    init();
  }

  private void init() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    this.button.setOpaque(false);
    this.button.setMargin(new Insets(0, 0, 0, 0));
    this.button.setFocusable(false);
    this.button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        MainTabHeader.this.clientUI.dispose();
      }
    });
    add(this.button, "East");
    this.label = new JLabel(this.title);
    this.label.setOpaque(false);
    add(this.label, "Center");
  }

  public String getTitle() {
    return this.title;
  }

  public JTabbedPane getTabbedPane() {
    return this.tab;
  }

  public void setLabelText(String text) {
    this.label.setText(text);
  }

  public void setIcon(Icon icon) {
    this.label.setIcon(icon);
    revalidate();
    repaint();
  }
}
