package com.toolkit2.client.component2ex.button;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class OKWithoutCloseButton extends JButton {
	public OKWithoutCloseButton() {
		setText(Translator.getString("OKButton.OK"));
		setIcon(FreeIconStore.OK_ICON);
		setMnemonic('O');
	}

	public void onClick() {
	}
}
