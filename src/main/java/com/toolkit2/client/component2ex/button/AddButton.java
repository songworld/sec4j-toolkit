package com.toolkit2.client.component2ex.button;

import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JButton;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class AddButton extends JButton {
	boolean needDefaultAction = false;

	  public AddButton() {
	    this(false);
	  }

	  public AddButton(boolean needDefaultAction) {
	    this.needDefaultAction = needDefaultAction;
	    setToolTipText(Translator.getString("AddButton.Add"));
	    setText(Translator.getString("AddButton.Add"));
	    setIcon(FreeIconStore.ADD_ICON);
	    setMnemonic('A');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));

	    addHierarchyListener(new HierarchyListener()
	    {
	      public void hierarchyChanged(HierarchyEvent e) {
//	        SubForm ui = ClientUtil.getClientUIForComponent(AddButton.this);
//	        if (((ui instanceof DetailUI)) && ((AddButton.this.getParent() instanceof ButtonPane)))
//	          AddButton.this.setText("");
	      }
	    });
	  }

	  public void onClick()
	  {
	    if (this.needDefaultAction) {
//	      SubForm ui = ClientUtil.getClientUIForComponent(this);
//	      if ((ui instanceof UpdateableUI)) {
//	        SubForm addUI = ((UpdateableUI)ui).getAddUI();
//	        if (addUI != null)
//	          addUI.setVisible();
//	      }
	    }
	  }
	}
