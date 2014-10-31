package com.toolkit2.client.frame.free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FreeTabComponent extends JPanel {
	// 载入当前资源
	private String backgroundSelectedImageURL = FreeUtil
			.getImageURL("tab_header_background.png");
	private String backgroundUnselectedImageURL = FreeUtil
			.getImageURL("tab_header_unselected_background.png");
	private TexturePaint selectedPaint = FreeUtil
			.createTexturePaint(this.backgroundSelectedImageURL);
	private TexturePaint unselectedPaint = FreeUtil
			.createTexturePaint(this.backgroundUnselectedImageURL);
	private ImageIcon icon = FreeUtil.getImageIcon("tab_close.png");
	private ImageIcon pressedIcon = FreeUtil
			.getImageIcon("tab_close_pressed.png");
	private Image unselectedLeftImage = FreeUtil
			.getImage("tab_header_unselected_background_left.png");
	private Image unselectedRightImage = FreeUtil
			.getImage("tab_header_unselected_background_right.png");
	private Image selectedLeftImage = FreeUtil
			.getImage("tab_header_selected_background_left.png");
	private Image selectedRightImage = FreeUtil
			.getImage("tab_header_selected_background_right.png");
	private JButton btnClose = new JButton();
	private JLabel lbTitle = new JLabel();
	private FreeTabbedPane tab = null;
	private Color selectedTitleColor = new Color(120, 120, 125);
	private Color unselectedTitleColor = Color.white;
	private Border border = BorderFactory.createEmptyBorder(0, 5, 0, 5);
	private boolean showCloseButton = true;

	// 构造函数
	public FreeTabComponent(FreeTabbedPane tab) {
		this(tab, true);
	}

	public FreeTabComponent(FreeTabbedPane tab, boolean showCloseButton) {
		this.tab = tab;
		this.showCloseButton = showCloseButton;
		init();
	}

	private void init() {
		this.btnClose.setIcon(this.icon);
		this.btnClose.setPressedIcon(this.pressedIcon);
		this.btnClose.setToolTipText("Close this tab");
		this.btnClose.setMargin(FreeUtil.ZERO_INSETS);
		this.btnClose.setFocusPainted(false);
		this.btnClose.setBorder(BorderFactory.createEmptyBorder(0, 3, 1, 3));
		this.btnClose.setContentAreaFilled(false);
		this.btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FreeTabComponent.this.closeTab();
			}
		});
		this.lbTitle.setOpaque(false);
		this.lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		this.lbTitle.setVerticalAlignment(0);
		this.lbTitle.setFont(FreeUtil.FONT_12_BOLD);

		setLayout(new BorderLayout());
		add(this.btnClose, "East");
		add(this.lbTitle, "Center");
		setBorder(this.border);
		setOpaque(false);
	}

	/*
	 * 绘制组件
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (isTabSelected()) {
			g2d.drawImage(this.selectedLeftImage, 0, 0, null);
			g2d.setPaint(this.selectedPaint);
			int x = this.selectedLeftImage.getWidth(null);
			int y = 0;
			int width = getWidth() - x - this.selectedRightImage.getWidth(null);
			int height = getHeight();
			g2d.fillRect(x, y, width, height);
			g2d.drawImage(this.selectedRightImage, x + width, 0, null);
		} else {
			g2d.drawImage(this.unselectedLeftImage, 0, 0, null);
			g2d.setPaint(this.unselectedPaint);
			int x = this.unselectedLeftImage.getWidth(null);
			int y = 0;
			int width = getWidth() - x - this.selectedRightImage.getWidth(null);
			int height = getHeight();
			g2d.fillRect(x, y, width, height);
			g2d.drawImage(this.unselectedRightImage, x + width, 0, null);

			g2d.setColor(FreeUtil.TAB_BOTTOM_LINE_COLOR);
			int lineY = getHeight() - 1;
			g2d.drawLine(0, lineY, getWidth(), lineY);
		}
	}

	public Dimension getPreferredSize() {
		int width = super.getPreferredSize().width;
		if (!isTabSelected()) {
			width = Math.min(width, this.tab.getPreferredUnselectedTabWidth());
		}
		int height = this.tab.getPreferredTabHeight();
		return new Dimension(width, height);
	}

	public boolean isTabSelected() {
		int index = this.tab.indexOfTabComponent(this);
		int selectedIndex = this.tab.getSelectedIndex();
		return selectedIndex == index;
	}

	public void setTitle(String title) {
		this.lbTitle.setText(title);
	}

	public void updateSelection(boolean selected) {
		if (selected)
			this.lbTitle.setForeground(this.selectedTitleColor);
		else {
			this.lbTitle.setForeground(this.unselectedTitleColor);
		}
		this.btnClose.setVisible(selected);
	}

	private void closeTab() {
		int index = this.tab.indexOfTabComponent(this);
		this.tab.removeTabAt(index);
	}

	/**************************************************
	 * 设置图标
	 * **/
	public void setIcon(Icon icon) {
		this.lbTitle.setIcon(icon);
		revalidate();
		repaint();
	}
}
