package com.toolkit2.client.component2ex.button;

import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;

public class AddUICancelButton extends CancelButton {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onClick()
	  {
	    if (this.needDefaultAction) {
	      SubForm parent = ClientUtil.getClientUIForComponent(this);
	      Window win = SwingUtilities.getWindowAncestor(this);
	      if (parent != null) {
	        int choice = JOptionPane.showConfirmDialog(parent, Translator.getString("AddUICancelButton.WarningMessage"), Translator.getString("AddUICancelButton.Confirm"), 0, 3);

	        if (choice == 0) {
	          parent.dispose();
	        }
	      }
	      else if (win != null) {
	        int choice = JOptionPane.showConfirmDialog(win, Translator.getString("AddUICancelButton.WarningMessage"), Translator.getString("AddUICancelButton.Confirm"), 0, 3);

	        if (choice == 0)
	          win.dispose();
	      }
	    }
	  }
}
