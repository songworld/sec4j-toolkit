package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class EditWithoutCloseButton extends JButton {
	  public EditWithoutCloseButton()
	  {
	    setToolTipText(Translator.getString("EditButton.Update"));
	    setText(Translator.getString("EditButton.Update"));
	    setIcon(FreeIconStore.UPDATE_ICON);
	    setMnemonic('U');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
	  }

	  public void onClick()
	  {
	  }
}
