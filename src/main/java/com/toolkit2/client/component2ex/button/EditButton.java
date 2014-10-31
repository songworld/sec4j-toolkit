package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;



public class EditButton extends JButton {
	public EditButton()
	  {
	    setToolTipText(Translator.getString("EditButton.Update"));
	    setText(Translator.getString("EditButton.Update"));
	    setIcon(FreeIconStore.UPDATE_ICON);
	    setMnemonic('E');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
	  }

	  public EditButton(String text) {
	    setText(text);
	    setToolTipText(text);
	    setIcon(FreeIconStore.UPDATE_ICON);
	    setMnemonic('E');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
	  }
}
