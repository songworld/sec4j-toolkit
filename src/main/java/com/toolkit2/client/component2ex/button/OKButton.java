package com.toolkit2.client.component2ex.button;

import java.awt.Window;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class OKButton extends JButton {
	 public OKButton()
	  {
	    setText(Translator.getString("OKButton.OK"));
	    setIcon(FreeIconStore.OK_ICON);
	    setMnemonic('O');
	  }

	  public void onClick() {
	    SubForm ui = ClientUtil.getClientUIForComponent(this);
	    if (ui != null) {
	      ui.dispose();
	    } else {
	      Window parent = SwingUtilities.getWindowAncestor(this);
	      if ((parent != null) && (!(parent instanceof Shell)))
	        parent.dispose();
	    }
	  }
}
