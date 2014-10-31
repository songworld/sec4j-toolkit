package com.toolkit2.client.component2ex;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.CacheManager;
import com.toolkit2.client.frame.free.FreeProgressBar;
import com.toolkit2.client.component2ex.button.CancelButton;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class WaitingUI extends JDialog
{
	 static
	  {
	    CacheManager.registerCache(WaitingUI.class, "clearCache");
	  }
  JPanel pane = new JPanel();
  BorderLayout layout = new BorderLayout();
  JLabel headerLabel = new JLabel();
  JProgressBar bar = new JProgressBar();
  JPanel mainPane = new JPanel();
  BorderLayout mainPaneLayout = new BorderLayout();
  JPanel buttonPane = new JPanel();
  FlowLayout buttonPaneLayout = new FlowLayout();
  JButton btnCancel = new CancelButton();
  JLabel image = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
  private static WaitingUI instance = null;
  private boolean packOnMessage = true;

  public static void clearCache()
  {
    instance = null;
  }

  public static synchronized WaitingUI getInstance() {
    if (instance == null) {
      instance = new WaitingUI();
    }
    instance.setHeadTitle(Translator.getString("WaitingUI.Header"));
    instance.setTitle(Translator.getString("WaitingUI.Title"));
    instance.btnCancel.setText(Translator.getString("CancelButton.Cancel"));
    instance.headerLabel.setText(Translator.getString("WaitingUI.Header"));
    return instance;
  }

  public JButton getCancelButton() {
    return this.btnCancel;
  }

  private WaitingUI()
  {
    init();
  }

  private void init() {
    this.btnCancel.setIcon(FreeIconStore.CANCEL_ICON);
    this.btnCancel.setMnemonic('C');
    initComponent();
    pack();
    ClientUtil.setupWindow((JPanel)getContentPane());
    ClientUtil.setupCancelKey(this, this.btnCancel);
    setModal(true);
  }

  private void initComponent() {
    this.pane.setLayout(this.layout);
    this.mainPane.setLayout(this.mainPaneLayout);
    this.mainPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.buttonPane.setLayout(this.buttonPaneLayout);
    this.buttonPaneLayout.setAlignment(1);
    this.buttonPaneLayout.setHgap(0);
    this.buttonPaneLayout.setVgap(0);
    this.layout.setHgap(10);
    this.layout.setVgap(10);
    this.buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    this.bar = new FreeProgressBar()
    {
      protected void updateString() {
        setString("");
      }
    };
    setResizable(false);
    setModal(true);
    setModalityType(ModalityType.DOCUMENT_MODAL);
    this.image.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    this.image.setText("");
    this.pane.add(this.headerLabel, "North");
    this.pane.add(this.bar, "Center");
    this.bar.setIndeterminate(true);
    ClientUtil.setPreferredWidth(this.bar, 200);
    add(this.mainPane, "Center");
    this.mainPane.add(this.pane, "Center");
    this.mainPane.add(this.buttonPane, "South");
    this.buttonPane.add(this.btnCancel, null);
    this.mainPane.add(this.image, "West");
  }

  public JLabel getHeaderLabel() {
    return this.headerLabel;
  }

  public JProgressBar getBar() {
    return this.bar;
  }

  public synchronized boolean isPackOnMessage() {
    return this.packOnMessage;
  }

  public void setHeadTitle(String headTitle) {
    this.headerLabel.setText(headTitle);
    if (this.packOnMessage)
      pack();
  }

  public synchronized void setPackOnMessage(boolean packOnMessage)
  {
    this.packOnMessage = packOnMessage;
  }

 
}
