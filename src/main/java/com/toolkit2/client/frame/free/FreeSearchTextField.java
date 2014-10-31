package com.toolkit2.client.frame.free;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import com.toolkit2.client.tools.FreeIconStore;

public class FreeSearchTextField extends JTextField {
	private ImageIcon icon = (ImageIcon) FreeIconStore.SEARCH_ICON;
	private FreeToolbarRoverButton button = new FreeToolbarRoverButton(
			this.icon);

	private LayoutManager layout = new LayoutManager() {
		public void addLayoutComponent(String name, Component comp) {
		}

		public void removeLayoutComponent(Component comp) {
		}

		public Dimension preferredLayoutSize(Container parent) {
			return new Dimension();
		}

		public Dimension minimumLayoutSize(Container parent) {
			return new Dimension();
		}

		public void layoutContainer(Container parent) {
			synchronized (parent.getTreeLock()) {
				Rectangle bound = FreeSearchTextField.this.getBounds();
				int gap = (bound.height - FreeSearchTextField.this.icon
						.getIconHeight()) / 2;
				int x = bound.width
						- FreeSearchTextField.this.icon.getIconWidth() - gap;

				int y = gap / 2;
				int width = FreeSearchTextField.this.icon.getIconWidth() + gap;
				int height = FreeSearchTextField.this.icon.getIconHeight()
						+ gap;
				FreeSearchTextField.this.button.setBounds(x, y, width, height);
			}
		}
	};

	public FreeSearchTextField() {
		init();
	}

	public FreeToolbarRoverButton getButton() {
		return this.button;
	}

	private void init() {
		setLayout(this.layout);
		add(this.button);
		setColumns(10);
		this.button.setCursor(Cursor.getDefaultCursor());
		this.button.setRoverBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FreeSearchTextField.this.fireActionPerformed();
			}
		});
	}
}