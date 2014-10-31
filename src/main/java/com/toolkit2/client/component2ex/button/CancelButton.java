package com.toolkit2.client.component2ex.button;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class CancelButton extends JButton
{
  boolean needDefaultAction = true;

  public CancelButton() {
    setText(Translator.getString("CancelButton.Cancel"));
    setIcon(FreeIconStore.CANCEL_ICON);
    setMnemonic('C');
  }

  public void onClick() {
    if (this.needDefaultAction) {
      SubForm ui = ClientUtil.getClientUIForComponent(this);
      if (ui != null) {
        ui.dispose();
      } else {
        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent != null)
          parent.addWindowListener(new WindowAdapter()
          {
            public void windowClosed(WindowEvent e)
            {
            }

            public void windowOpened(WindowEvent e) {
            }
          });
        if ((!(parent instanceof Shell)) && 
          (parent != null))
          parent.dispose();
      }
    }
  }

  public void setNeedDefaultAction(boolean needDefaultAction)
  {
    this.needDefaultAction = needDefaultAction;
  }
}