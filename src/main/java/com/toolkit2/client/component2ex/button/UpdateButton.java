package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class UpdateButton extends JButton {
	 boolean needDefaultAction = true;

	  public UpdateButton() { setToolTipText(Translator.getString("UpdateButton.Update"));
	    setText(Translator.getString("UpdateButton.Update"));
	    setIcon(FreeIconStore.UPDATE_ICON);
	    setMnemonic('U');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));

	    setText("");
	  }

	  public void onClick()
	  {
//	    if (this.needDefaultAction) {
//	      ClientUI ui = ClientUtil.getClientUIForComponent(this);
//	      if ((ui instanceof UpdateableUI)) {
//	        ClientUI updateUI = ((UpdateableUI)ui).getUpdateUI();
//	        if (updateUI != null) {
//	          updateUI.setVisible();
//	        }
//	      }
//	      if (ui != null) {
//	        ui.dispose();
//	      } else {
//	        Window win = ClientUtil.getWindowForComponent(this);
//	        if (!(win instanceof AbstractMainUI))
//	          win.dispose();
//	      }
//	    }
	  }

	  public void setNeedDefaultAction(boolean needDefaultAction)
	  {
	    this.needDefaultAction = needDefaultAction;
	  }

	  public boolean isNeedDefaultAction() {
	    return this.needDefaultAction;
	  }

	  public void setEnabled(boolean b) {
//	    if ((!b) && (!UserActionManager.canUpdateAnything()))
//	      super.setEnabled(false);
//	    else
//	      super.setEnabled(true);
	  }

	  public void setTrueEnable(boolean b)
	  {
	    super.setEnabled(b);
	  }
}
