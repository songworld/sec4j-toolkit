package com.toolkit2.client.component2ex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.button.AddButton;
import com.toolkit2.client.component2ex.button.AddUICancelButton;
import com.toolkit2.client.component2ex.button.CancelButton;
import com.toolkit2.client.component2ex.button.CloseButton;
import com.toolkit2.client.component2ex.button.CopyButton;
import com.toolkit2.client.component2ex.button.DeleteButton;
import com.toolkit2.client.component2ex.button.EditWithoutCloseButton;
import com.toolkit2.client.component2ex.button.HelpButton;
import com.toolkit2.client.component2ex.button.NoticeButton;
import com.toolkit2.client.component2ex.button.OKButton;
import com.toolkit2.client.component2ex.button.OKWithoutCloseButton;
import com.toolkit2.client.component2ex.button.RefreshButton;
import com.toolkit2.client.component2ex.button.UpdateButton;
import com.toolkit2.client.tools.FreeColorStore;

public class ButtonPane extends JPanel
{
  private JPanel rightRightPane = new JPanel(new FlowLayout(2, 0, 10));
  private JPanel rightLeftPane = new JPanel(new FlowLayout(2, 0, 10));
  private JPanel rightPane = new JPanel(new BorderLayout(10, 10));
  private JPanel leftPane = new JPanel(new FlowLayout(0, 0, 10));
  private JPanel buttonPane = new JPanel(new BorderLayout(0, 10));
  private JButton okButton = null;
  private JButton closeButton = null;
  private JButton cancelButton = null;
  private JButton helpButton = null;
  private JButton refreshButton = null;
  private JButton addButton = null;
  private JButton editButton = null;
  private JButton deleteButton = null;
  private JButton noticeButton = null;
  private LookupPane lookupPane = null;
  private JButton copyButton = null;
  private JButton defaultButton = null;
  private boolean needDefaultButton = true;

  public ButtonPane(String[] leftButtons, String[] rightButtons)
  {
    setLayout(new BorderLayout());
    add(this.rightPane, "Center");
    this.leftPane.setOpaque(false);
    this.rightPane.setOpaque(false);
    this.rightRightPane.setOpaque(false);
    this.buttonPane.setOpaque(false);
    this.rightLeftPane.setOpaque(false);
    this.rightPane.add(this.rightLeftPane, "Center");
    this.rightPane.add(this.rightRightPane, "East");
    add(this.buttonPane, "West");
    this.buttonPane.add(this.leftPane);
    setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    try {
      if ((rightButtons != null) && (rightButtons.length > 0)) {
        createButtons(rightButtons, this.rightRightPane);
      }
      if ((leftButtons != null) && (leftButtons.length > 0))
        createButtons(leftButtons, this.leftPane);
    }
    catch (Exception ex) {
      ClientUtil.showException(this, ex);
    }
    addAncestorListener(new AncestorListener()
    {
      public void ancestorAdded(AncestorEvent event) {
        Window win = ClientUtil.getWindowForComponent(ButtonPane.this);
        JButton button = null;
        if (ButtonPane.this.cancelButton != null)
          button = ButtonPane.this.cancelButton;
        else if (ButtonPane.this.closeButton != null)
          button = ButtonPane.this.closeButton;
        else if (ButtonPane.this.okButton != null) {
          button = ButtonPane.this.okButton;
        }
        if ((button != null) && (win != null) && (!(win instanceof Shell))) {
          ClientUtil.setupCancelKey(win, button);
          ClientUtil.setupRefreshKey(win, ButtonPane.this.refreshButton);
        }
        if (ButtonPane.this.needDefaultButton) {
          JRootPane rootPane = null;
          if ((win instanceof JFrame)) {
            rootPane = ((JFrame)win).getRootPane();
          }
          if ((win instanceof JDialog)) {
            rootPane = ((JDialog)win).getRootPane();
          }
          if (rootPane != null) {
            JButton defaultButton = ButtonPane.this.getDefaultButton();
            if ((defaultButton != null) && 
              (ButtonPane.this.isShowing()) && 
              (rootPane.getDefaultButton() != defaultButton))
              rootPane.setDefaultButton(defaultButton);
          }
        }
      }

      public void ancestorMoved(AncestorEvent event)
      {
      }

      public void ancestorRemoved(AncestorEvent event)
      {
        Window win = ClientUtil.getWindowForComponent(ButtonPane.this);
        JRootPane rootPane = null;
        if ((win instanceof JFrame)) {
          rootPane = ((JFrame)win).getRootPane();
        }
        if ((win instanceof JDialog)) {
          rootPane = ((JDialog)win).getRootPane();
        }
        if (rootPane != null) {
          JButton defaultButton = ButtonPane.this.getDefaultButton();
          if ((defaultButton != null) && 
            (rootPane.getDefaultButton() == defaultButton))
            rootPane.setDefaultButton(null);
        }
      }
    });
  }

  private void createButtons(String[] buttons, JPanel pane)
    throws Exception
  {
    if ((buttons != null) && (buttons.length > 0)) {
      JButton button = null;
      for (int i = 0; i < buttons.length; i++) {
        String buttonName = buttons[i];
        if (buttonName.equalsIgnoreCase("ok")) {
          button = new OKButton();
          this.okButton = button;
        } else if (buttonName.equalsIgnoreCase("close")) {
          button = new CloseButton();
          this.closeButton = button;
        } else if (buttonName.equalsIgnoreCase("cancel")) {
          button = new CancelButton();
          this.cancelButton = button;
        } else if (buttonName.equalsIgnoreCase("help")) {
          button = new HelpButton();
          this.helpButton = button;
        } else if (buttonName.toLowerCase().startsWith("refresh")) {
          int dot = buttonName.indexOf('@');
          if (dot >= 0)
            button = new RefreshButton(buttonName.substring(dot + 1));
          else {
            button = new RefreshButton();
          }
          this.refreshButton = button;
        } else if (buttonName.equalsIgnoreCase("add")) {
          button = new AddButton(true);
          this.addButton = button;
        } else if (buttonName.equalsIgnoreCase("copy")) {
          button = new CopyButton();
          this.copyButton = button;
        } else if (buttonName.equalsIgnoreCase("edit")) {
          button = new UpdateButton();
          this.editButton = button;
        } else if (buttonName.equalsIgnoreCase("addCancel")) {
          button = new AddUICancelButton();
          this.cancelButton = button;
        } else if (buttonName.equalsIgnoreCase("delete")) {
          button = new DeleteButton();
          this.deleteButton = button;
        } else if (buttonName.equalsIgnoreCase("okWithoutClose")) {
          button = new OKWithoutCloseButton();
          this.okButton = button;
        } else if (buttonName.equalsIgnoreCase("editWithoutClose")) {
          button = new EditWithoutCloseButton();
          this.editButton = button;
        } else if (buttonName.toLowerCase().startsWith("notice")) {
          button = new NoticeButton();
          this.noticeButton = button;
        } else if (buttonName.toLowerCase().startsWith("lookup")) {
          button = null;
          this.lookupPane = new LookupPane();
          this.buttonPane.add(this.lookupPane, "East");
        } else {
          button = new JButton(buttonName);
        }
        if (button != null)
          pane.add(button);
      }
    }
  }

  public JButton getOkButton()
  {
    return this.okButton;
  }

  public JButton getCloseButton() {
    return this.closeButton;
  }

  public JButton getCancelButton() {
    return this.cancelButton;
  }

  public JButton getHelpButton() {
    return this.helpButton;
  }

  public JButton getRefreshButton() {
    return this.refreshButton;
  }

  public JButton getAddButton() {
    return this.addButton;
  }

  public JButton getCopyButton() {
    return this.copyButton;
  }

  public JButton getUpdateButton() {
    return this.editButton;
  }

  public JButton getNoticeButton() {
    return this.noticeButton;
  }

  public JButton getDeleteButton() {
    return this.deleteButton;
  }

  public JPanel getRightPane() {
    return this.rightPane;
  }

  public JPanel getLeftButtonPane() {
    return this.leftPane;
  }

  public JPanel getRightButtonPane() {
    return this.rightRightPane;
  }

  public JPanel getRightLeftButtonPane() {
    return this.rightLeftPane;
  }

  public JButton getLeftPaneButton(int index) {
    return (JButton)this.leftPane.getComponent(index);
  }

  public LookupPane getLookUpPane() {
    return this.lookupPane;
  }

  public JButton getLookupButton() {
    return this.lookupPane.getButton();
  }

  private JButton getDefaultButton() {
    if (this.defaultButton != null) {
      return this.defaultButton;
    }
//    ClientUI clientUI = ClientUtil.getClientUIForComponent(this);
//    if ((clientUI != null) && (clientUI.getDefaultButton() != null)) {
//      return clientUI.getDefaultButton();
//    }
//    if ((clientUI instanceof DetailUI)) {
//      if (this.closeButton != null)
//        return this.closeButton;
//    }
//    else {
      if (this.okButton != null)
        return this.okButton;
      if (this.addButton != null)
        return this.addButton;
      if (this.closeButton != null)
        return this.closeButton;
      if (this.cancelButton != null) {
        return this.cancelButton;
      }
//    }

    if (this.rightRightPane.getComponentCount() > 0) {
      Component c = this.rightRightPane.getComponent(0);
      if ((c instanceof JButton)) {
        return (JButton)c;
      }
    }

    return null;
  }

  public void setHighlightColor(Color color)
  {
    setBackground(color);
    getRightButtonPane().setBackground(color);
    getLeftButtonPane().setBackground(color);
    getRightLeftButtonPane().setBackground(color);
    getRightButtonPane().setBackground(color);
    getRightPane().setBackground(color);
    if (getLookUpPane() != null)
      getLookUpPane().setBackground(color);
  }

  public void setUpdateBackground()
  {
    setHighlightColor(FreeColorStore.BACKGROUND_UPDATE_UI);
  }

  public void setAddBackground() {
    setHighlightColor(FreeColorStore.BACKGROUND_HOMEPAGE_ADD);
  }

  public boolean isNeedDefaultButton() {
    return this.needDefaultButton;
  }

  public void setNeedDefaultButton(boolean needDefaultButton) {
    this.needDefaultButton = needDefaultButton;
  }

  public void setDefaultButton(JButton defaultButton) {
    this.defaultButton = defaultButton;
  }
}