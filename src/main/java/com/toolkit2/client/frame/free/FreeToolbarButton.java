package com.toolkit2.client.frame.free;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class FreeToolbarButton extends JButton {
	private int buttonSize = 20;
	private Color roverBorderColor = FreeUtil.BUTTON_ROVER_COLOR;
	private Border roverBorder = new Border() {
		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			g.setColor(FreeToolbarButton.this.roverBorderColor);
			g.drawRect(x, y, width - 1, height - 1);
		}

		public Insets getBorderInsets(Component c) {
			return new Insets(1, 1, 1, 1);
		}

		public boolean isBorderOpaque() {
			return true;
		}
	};

	private Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

	public FreeToolbarButton() {
		init();
	}

	public FreeToolbarButton(String text) {
		this(text, null);
	}

	public FreeToolbarButton(Icon icon) {
		this(null, icon);
	}

	public FreeToolbarButton(String text, Icon icon) {
		this(text, icon, null);
	}

	public FreeToolbarButton(String text, Icon icon, String tooltip) {
		super(text, icon);
		init();
		if (tooltip != null)
			setToolTipText(tooltip);
	}

	private void init() {
		setVerticalAlignment(0);
		setFont(FreeUtil.FONT_12_BOLD);
		setOpaque(false);
		setBorder(this.emptyBorder);
		setContentAreaFilled(false);
		setFocusPainted(false);

		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				FreeToolbarButton.this
						.setBorder(FreeToolbarButton.this.roverBorder);
			}

			public void mouseExited(MouseEvent e) {
				FreeToolbarButton.this
						.setBorder(FreeToolbarButton.this.emptyBorder);
			}
		});
	}

	public void setBtnIcon(Icon icon) {
		super.setIcon(icon);
		// System.out.println("FreeToolbarButton.java icon == null==>"+icon ==
		// null);
		if (icon == null) {
			setPressedIcon(null);
			setRolloverIcon(null);

		} else {
			// System.out.println(icon.hashCode()+"$$$"+icon.getClass().getName()+"==>FreeToolbarButton.java 这里出现了递归调用");
			Icon pressedIcon = FreeUtil.createMovedIcon(icon);

			setPressedIcon(pressedIcon);
			pressedIcon = null;
		}

	}

	/*
	 * 设置按下时的图标 从源代码setIcon中独立处理，修复bug：递归调用内存溢出问题 暂时废弃没有解决问题
	 */
	public void setPressedIcon(Icon pressedIcon) {
		super.setPressedIcon(pressedIcon);
	}

	public Dimension getPreferredSize() {
		int width = super.getPreferredSize().width;
		width = Math.max(width, this.buttonSize);
		int height = this.buttonSize;
		return new Dimension(width, height);
	}

	public void setRoverBorder(Border roverBorder) {
		this.roverBorder = roverBorder;
		repaint();
	}
}
