package com.toolkit2.client.frame.free;

	import java.awt.BorderLayout;
	import javax.swing.Icon;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toolkit2.client.component2ex.textfield.QuickActionTextField;
import com.toolkit2.client.tools.FreeIconStore;

	public class FreeQuickSearchBox extends JPanel
	{
	  private QuickActionTextField txtSearch = new QuickActionTextField();
	  private JLabel label = new JLabel(FreeIconStore.SEARCH_ICON);

	  public FreeQuickSearchBox() {
	    setLayout(new BorderLayout(3, 0));
	    setBorder(new JTextField().getBorder());
	    setOpaque(true);
	    setBackground(UIManager.getColor("TextField.background"));
	    this.label.setOpaque(true);
	    this.label.setBackground(UIManager.getColor("TextField.background"));
	    add(this.label, "West");
	    this.txtSearch.setBorder(null);
	    add(this.txtSearch, "Center");
	  }

	  public QuickActionTextField getTextField() {
	    return this.txtSearch;
	  }

	  public void setIcon(Icon icon) {
	    this.label.setIcon(icon);
	  }

	  public void setToolTipText(String text) {
	    super.setToolTipText(text);
	    this.label.setToolTipText(text);
	    this.txtSearch.setToolTipText(text);
	  }
	}
