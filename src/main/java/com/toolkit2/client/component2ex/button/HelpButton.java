package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class HelpButton extends JButton {
	  public HelpButton()
	  {
	    setToolTipText(Translator.getString("HelpButton.Help"));

	    setMnemonic('H');
	    setIcon(FreeIconStore.HELP_ICON);
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));

	    setVisible(false);
	  }

	  public void onClick()
	  {
	    //HelpUtil.showHelpForComponent(this);
	  }
}
