package com.toolkit2.client.component2ex;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.CacheManager;
import com.toolkit2.client.i18n.Translator;

public class TextComponentPopuMenu extends JPopupMenu
{
	 static
	  {
	    CacheManager.registerCache(TextComponentPopuMenu.class, "clearCache");
	  }
	 
  private static JTextComponent parent = null;
  private JMenuItem cutButton = new JMenuItem(Translator.getString("TextComponentPopuMenu.Cut"));
  private JMenuItem copyButton = new JMenuItem(Translator.getString("TextComponentPopuMenu.Copy"));

  private JMenuItem pastButton = new JMenuItem(Translator.getString("TextComponentPopuMenu.Paste"));
  private JMenuItem selectAllButton = new JMenuItem(Translator.getString("TextComponentPopuMenu.SelectAll"));
  private static TextComponentPopuMenu instance = null;
  private static String cliptext = "";

  public static void clearCache()
  {
    instance = null;
  }

  private TextComponentPopuMenu() {
    init();
  }

  private void setParent(JTextComponent parent) {
    if (parent == null) {
      return;
    }
    parent = parent;
    setItemStatus();
  }

  public static TextComponentPopuMenu getInstance(JTextComponent parent) {
    if (instance == null) {
      instance = new TextComponentPopuMenu();
    }
    instance.setParent(parent);
    return instance;
  }

  private void init() {
    this.cutButton.setEnabled(false);
    this.copyButton.setEnabled(false);
    this.pastButton.setEnabled(false);
    this.selectAllButton.setEnabled(false);
    this.cutButton.setMnemonic('t');
    this.copyButton.setMnemonic('C');
    this.pastButton.setMnemonic('P');
    this.selectAllButton.setMnemonic('A');
    add(this.cutButton);
    add(this.copyButton);
    add(this.pastButton);
    add(this.selectAllButton);
    this.cutButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        StringSelection selection = new StringSelection(TextComponentPopuMenu.parent.getSelectedText());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        TextComponentPopuMenu.parent.replaceSelection("");
      }
    });
    this.copyButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        String text;
        if (ClientUtil.isNullObject(TextComponentPopuMenu.parent.getSelectedText()))
          text = TextComponentPopuMenu.parent.getText();
        else {
          text = TextComponentPopuMenu.parent.getSelectedText();
        }
        StringSelection ss = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
      }
    });
    this.pastButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        TextComponentPopuMenu.parent.replaceSelection(TextComponentPopuMenu.cliptext);
      }
    });
    this.selectAllButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        TextComponentPopuMenu.parent.selectAll();
      }
    });
  }

  private void setItemStatus() {
    if (parent == null) {
      return;
    }
    Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(instance);
    cliptext = "";
    if (contents == null)
      cliptext = "";
    else {
      try {
        cliptext = (String)contents.getTransferData(DataFlavor.stringFlavor);
      } catch (Exception exception) {
        exception.getStackTrace();
      }
    }
    this.cutButton.setEnabled(false);
    this.copyButton.setEnabled(false);
    this.pastButton.setEnabled(false);
    this.selectAllButton.setEnabled(false);
    if (parent.isEnabled()) {
      if (parent.isEditable()) {
        if (!ClientUtil.isNullObject(parent.getSelectedText())) {
          this.cutButton.setEnabled(true);
        }
        if (!ClientUtil.isNullObject(cliptext)) {
          this.pastButton.setEnabled(true);
        }
      }
      if (!ClientUtil.isNullObject(parent.getText())) {
        this.copyButton.setEnabled(true);
      }
      if (!ClientUtil.isNullObject(parent.getText()))
        this.selectAllButton.setEnabled(true);
    }
  }

 
}