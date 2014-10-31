package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class NextButton extends JButton {
	boolean needDefaultAction = false;

	  public NextButton() {
	    this(false);
	  }

	  public NextButton(boolean needDefaultAction) {
	    this.needDefaultAction = needDefaultAction;
	    setToolTipText(Translator.getString("NextButton.next"));
	    setText(Translator.getString("NextButton.next"));
	    setIcon(FreeIconStore.NEXT_ICON);
	    setMnemonic('N');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
	  }

	  public void onClick() {
	    if (this.needDefaultAction) {
	      SubForm ui = ClientUtil.getClientUIForComponent(this);
	      if (ui != null)
	        ui.dispose();
	    }
	  }
}
