package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class CopyButton extends JButton {
	  public CopyButton()
	  {
	    setToolTipText(Translator.getString("CopyButton.Copy"));
	    setText(Translator.getString("CopyButton.Copy"));
	    setIcon(FreeIconStore.COPY_ICON);
	    setMnemonic('C');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
	  }

	  public void onClick()
	  {
	  }
}
