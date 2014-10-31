package com.toolkit2.client.frame.free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.TDataBox;
import twaver.TWaverUtil;

public class FreeOutlookBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String backgroundImageURL = FreeUtil
			.getImageURL("outlook_bar_background.png");
	private Image backgroundSelectedLeft = FreeUtil
			.getImage("outlook_bar_background_selected_left.png");
	private Image backgroundSelectedRight = FreeUtil
			.getImage("outlook_bar_background_selected_right.png");
	private String backgroundSelectedImageURL = FreeUtil
			.getImageURL("outlook_bar_background_selected.png");
	private Image backgroundImage = TWaverUtil
			.getImage(this.backgroundImageURL);
	private ImageIcon handlerIcon = FreeUtil
			.getImageIcon("outlook_bar_handler.png");
	private ImageIcon handlerSelectedIcon = FreeUtil
			.getImageIcon("outlook_bar_handler_selected.png");
	private TexturePaint paint = FreeUtil
			.createTexturePaint(this.backgroundImageURL);
	private TexturePaint selectedPaint = FreeUtil
			.createTexturePaint(this.backgroundSelectedImageURL);
	private JLabel lbHandler = new JLabel();
	private Border handlerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 16);
	private Border handlerShrinkedBorder = BorderFactory.createEmptyBorder(0,
			0, 0, 22);
	private JLabel lbIcon = new JLabel();
	private JLabel lbTitle = new JLabel();
	private boolean selected = false;
	private Color titleColor = FreeUtil.OUTLOOK_TEXT_COLOR;
	private Color selectedTitleColor = Color.white;
	private MouseListener mouseListener = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			if (((JComponent) e.getSource()).contains(e.getPoint()))
				FreeOutlookBar.this.changeStatus();
		}
	};

	private FreeOutlookPane pane = null;
	private TDataBox box = new TDataBox();
	private FreeOutlookList list = new FreeOutlookList(this, this.box);
	private FreeNetwork network = new FreeNetwork(this.box);
	private JScrollPane scroll = new JScrollPane(this.list);
	private Color scrollBorderColor = new Color(233, 223, 207);
	private Border scrollBorder = new Border() {
		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			g.setColor(FreeOutlookBar.this.scrollBorderColor);
			g.drawLine(0, height, x, height);
		}

		public Insets getBorderInsets(Component c) {
			return new Insets(0, 0, 1, 0);
		}

		public boolean isBorderOpaque() {
			return true;
		}
	};

	private Icon icon = null;
	private Icon selectedIcon = null;

	public FreeOutlookBar(FreeOutlookPane pane) {
		this.pane = pane;
		init();
	}

	public JComponent getContentComponent() {
		return this.scroll;
	}

	private void init() {
		setLayout(new BorderLayout());
		this.lbHandler.setVerticalAlignment(0);
		this.lbHandler.setIcon(this.handlerIcon);
		this.lbHandler.setBorder(this.handlerBorder);
		add(this.lbHandler, "East");

		this.lbIcon.setVerticalAlignment(0);
		this.lbIcon.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
		add(this.lbIcon, "West");

		this.lbTitle.setVerticalAlignment(0);
		this.lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		this.lbTitle.setFont(FreeUtil.FONT_14_BOLD);
		this.lbTitle.setForeground(this.titleColor);
		add(this.lbTitle, "Center");

		this.lbHandler.addMouseListener(this.mouseListener);
		this.lbTitle.addMouseListener(this.mouseListener);
		this.lbIcon.addMouseListener(this.mouseListener);

		this.scroll.setMinimumSize(new Dimension(0, 0));
		this.scroll.setBorder(this.scrollBorder);

		this.box.getSelectionModel().addDataBoxSelectionListener(
				new DataBoxSelectionListener() {
					public void selectionChanged(DataBoxSelectionEvent e) {
						FreeListPane shortcutPane = FreeOutlookBar.this.pane
								.getShortcutPane();
						if (shortcutPane != null) {
							DataBoxSelectionModel model = e
									.getBoxSelectionModel();
							if (model.size() == 1) {
								Element element = model.lastElement();
								if ((element instanceof FreeNode)) {
									FreeNode node = (FreeNode) element;
									TDataBox shortcutBox = node.getShortcuts();
									shortcutPane.getList().setDataBox(
											shortcutBox);
								}
							}
						}
					}
				});
	}

	// 绘制outlookbar
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		if (isSelected()) {
			g2d.setPaint(this.selectedPaint);
			if (getSelectedIcon() != null)
				this.lbIcon.setIcon(getSelectedIcon());
			else
				this.lbIcon.setIcon(getIcon());
		} else {
			g2d.setPaint(this.paint);
			this.lbIcon.setIcon(getIcon());
		}
		g2d.fillRect(0, 0, getWidth(), getHeight());

		if (isSelected()) {
			g2d.drawImage(this.backgroundSelectedLeft, 0, 0, null);

			g2d.drawImage(this.backgroundSelectedRight, getWidth()
					- this.backgroundSelectedRight.getWidth(null), 0, null);
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(super.getPreferredSize().width,
				this.backgroundImage.getHeight(null));
	}

	// 设置选中状态
	public void setSelected(boolean selected) {
		if (selected != this.selected) {
			if (!isSelected()) {
				this.pane.closeAllBars();
			}
			this.selected = selected;
			if (selected) {
				this.lbHandler.setIcon(this.handlerSelectedIcon);
				this.lbTitle.setForeground(this.selectedTitleColor);
			} else {
				this.lbHandler.setIcon(this.handlerIcon);
				this.lbTitle.setForeground(this.titleColor);
			}
			this.pane.updateLayoutConstraint(getContentComponent(), selected);
			this.pane.setAdditionalPaneVisible(!selected);
			this.pane.revalidate();
		}
	}

	public void changeStatus() {
		setSelected(!isSelected());
	}

	public boolean isSelected() {
		return this.selected;
	}

	public Icon getIcon() {
		return this.icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
		updateIcon();
	}

	public Icon getSelectedIcon() {
		return this.selectedIcon;
	}

	public void setSelectedIcon(Icon selectedIcon) {
		this.selectedIcon = selectedIcon;
		updateIcon();
	}

	private void updateIcon() {
		if (this.selected)
			this.lbIcon.setIcon(this.selectedIcon);
		else
			this.lbIcon.setIcon(this.icon);
	}

	public void setTitle(String title) {
		this.lbTitle.setText(title);
		this.lbTitle.setToolTipText(title);
		this.lbHandler.setToolTipText(title);
		this.lbIcon.setToolTipText(title);
	}

	public String getTitle() {
		return this.lbTitle.getText();
	}

	public FreeOutlookPane getFreeOutlookPane() {
		return this.pane;
	}

	public FreeOutlookList getList() {
		return this.list;
	}

	public void headerShrinkChanged(boolean headShrinked) {
		if (headShrinked)
			this.lbHandler.setBorder(this.handlerShrinkedBorder);
		else
			this.lbHandler.setBorder(this.handlerBorder);
	}

	public FreeNetwork getNetwork() {
		return this.network;
	}
}
