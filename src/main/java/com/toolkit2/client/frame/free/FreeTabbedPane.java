package com.toolkit2.client.frame.free;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toolkit2.client.component2ex.MainTabHeader;

public class FreeTabbedPane extends JTabbedPane {
	private int preferredUnselectedTabWidth = 80;
	private int preferredTabHeight = FreeUtil.getImageIcon(
			"tab_header_background.png").getIconHeight();
//默认导航事件
	private FreeMainTabNavigator navigator = new FreeMainTabNavigator(this);
	public FreeTabbedPane() {
		init();
	}

	private void init() {
		setFont(FreeUtil.FONT_12_BOLD);
		setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
		setBorder(null);
		setFocusable(false);
		setTabLayoutPolicy(1);
		setOpaque(false);
		setUI(new FreeTabbedPaneUI(this));
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				FreeTabbedPane.this.updateTabComponents();
			}
		});
	}

	public void addTab(String title, Component component) {
		super.addTab(title, component);
		int index = getTabCount() - 1;
		FreeTabComponent tabComponent = new FreeTabComponent(this);
		tabComponent.setTitle(title);
		setTabComponentAt(index, tabComponent);
		setToolTipTextAt(index, title);
		updateTabComponents();
	}

	public void addTab(MainTabHeader tabHeader, JComponent component) {
		add(component);
		int index = super.indexOfComponent(component);
		setTabComponentAt(index, tabHeader);
	}
	public void addTab(String title, Component component, Component old)
	{
		int index = indexOfComponent(old);
		if (index == -1)
		{
			addTab(title, component);
			updateTabComponents();
		} else
		{
			FreeTabComponent tabComponent = new FreeTabComponent(this);
			setComponentAt(index, component);
			tabComponent.setTitle(title);
			setTabComponentAt(index, tabComponent);
			setToolTipTextAt(index, title);
			updateTabComponents();
		}
	}
	public void paint(Graphics g)
	{
		if (getTabCount() != 0)
			super.paint(g);
	}
	public int getPreferredTabHeight() {
		return this.preferredTabHeight;
	}

	private void updateTabComponents() {
		int selectedIndex = getSelectedIndex();
		for (int i = 0; i < getTabCount(); i++) {
			Component c = getTabComponentAt(i);
			if ((c instanceof FreeTabComponent)) {
				FreeTabComponent component = (FreeTabComponent) c;
				boolean selected = selectedIndex == i;
				component.updateSelection(selected);
			}
		}
	}

	public int getPreferredUnselectedTabWidth() {
		return this.preferredUnselectedTabWidth;
	}
	
	 public FreeMainTabNavigator getNavigator()
	  {
	    return navigator;
	  }
}