package com.toolkit2.client.component2ex.button;

import java.awt.Window;

import javax.swing.JButton;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class CloseButton extends JButton
{
  public CloseButton()
  {
    setText(Translator.getString("CloseButton.Close"));
    setIcon(FreeIconStore.OK_ICON);
    setMnemonic('C');
  }

  public void onClick() {
    Window parent = ClientUtil.getWindowForComponent(this);
    SubForm ui = ClientUtil.getClientUIForComponent(this);
    if (ui != null)
      ui.dispose();
    else if ((!(parent instanceof Shell)) && (parent != null))
      parent.dispose();
  }
}