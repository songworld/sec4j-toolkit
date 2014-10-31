package com.toolkit2.client.frame.mian;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.ButtonPane;
import com.toolkit2.client.component2ex.button.ButtonPaneFactory;

public class MainNoticeUI extends JDialog
{
  private ButtonPane buttonPane = ButtonPaneFactory.createButtonPane("help|ok");
  private JTextArea txtMessage = new JTextArea();
  private JLabel txtTitle = new JLabel();

  public MainNoticeUI(Window parent) {
    super(parent);
    setModal(true);
    init();
    ClientUtil.setupWindow(this);
  }

  private void init() {
    setTitle("ToolKit2 System Notice");
    this.txtMessage.setWrapStyleWord(true);
    this.txtMessage.setLineWrap(true);
    this.txtMessage.setEditable(false);
    JPanel pane = new JPanel(new BorderLayout(0, 10));
    pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
    pane.add(new JScrollPane(this.txtMessage), "Center");
    pane.add(this.txtTitle, "North");
    add(pane, "Center");
    add(this.buttonPane, "South");
    setSize(800, 600);
  }

  public void setHeader(String title) {
    this.txtTitle.setText(title);
  }

  public void setMessage(String message) {
    this.txtMessage.setText(message);
  }
}
