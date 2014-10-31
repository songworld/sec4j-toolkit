package com.toolkit2.client.frame.free;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;

public class FreeTreePaneHolder extends JPanel {
	private FreeOutlookHeader header = new FreeOutlookHeader() {
		protected void updateDock(boolean dock) {
			System.out.println("FreeTreePaneHolder.java Info:update dock!"); 
			if (dock)
				FreeTreePaneHolder.this.dock();
			else
				FreeTreePaneHolder.this.undock();
		}
	};

	private JPanel headerPane = new JPanel(new BorderLayout());
	private FreeOutlookSplitListener splitListener = new FreeOutlookSplitListener(
			this.header);

	private FreeMainUITreePane mainTree = null;
	private FreeSplitButton split = new FreeSplitButton();
	private JPanel centerPane = new JPanel(new BorderLayout());
	private boolean splitMouseOver = false;
	private long splitLastMoveTime = 0L;
	private JPanel popupPane = new JPanel();
	private int mouseDelay = 50;
	private boolean needPopup = ClientUtil.getPopupMainTreeOnMouseOver();

	public FreeTreePaneHolder(String xml_name) {
		this.mainTree = new FreeMainUITreePane(xml_name);
		init();
	}
	private void init() {
		this.split.setCursor(Cursor.getPredefinedCursor(10));
		this.split.addMouseListener(this.splitListener);
		this.split.addMouseMotionListener(this.splitListener);
		this.split.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			//	System.out.println("FreeTreePaneHolder.java 设置split按钮 单机次数："+e.getClickCount());
				if (e.getClickCount() > 1){
					FreeTreePaneHolder.this.header.changeShrink();

				}
			}
		});
		this.split.getHandlerButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FreeTreePaneHolder.this.header.changeShrink();
				
			}
		});
		// this.noticeCenterUI.getTreePane().setBackground(FreeUtil.ALL_UI_BACKGROUD_COLOR);
		this.mainTree.getTabbedPane().setTabPlacement(3);
		this.mainTree.setBackground(FreeUtil.ALL_UI_BACKGROUD_COLOR);

		// this.centerPane.add(this.noticeCenterUI.getTreePane(), "South");
		this.centerPane.add(this.mainTree, "Center");
		setLayout(new BorderLayout());
		add(this.centerPane, "Center");
		add(this.split, "East");
		this.headerPane.setOpaque(true);
		this.headerPane.add(this.header, "Center");
		this.centerPane.add(this.headerPane, "North");
		this.split.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FreeTreePaneHolder.this.header.isShrinked()){
					FreeTreePaneHolder.this.header.changeShrink();
				}
			}
		});
		MouseAdapter splitMouseHandler = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				FreeTreePaneHolder.this.splitMouseOver = true;
			}

			public void mouseExited(MouseEvent e) {
				FreeTreePaneHolder.this.splitMouseOver = false;
			}

			public void mouseMoved(MouseEvent e) {
				FreeTreePaneHolder.this.splitLastMoveTime = System
						.currentTimeMillis();
			}

			public void mouseClicked(MouseEvent e) {
				FreeTreePaneHolder.this.splitLastMoveTime = System
						.currentTimeMillis();
			}

			public void mousePressed(MouseEvent e) {
				FreeTreePaneHolder.this.splitLastMoveTime = System
						.currentTimeMillis();
			}

			public void mouseReleased(MouseEvent e) {
				FreeTreePaneHolder.this.splitLastMoveTime = System
						.currentTimeMillis();
			}
		};
		this.split.addMouseListener(splitMouseHandler);
		this.split.addMouseMotionListener(splitMouseHandler);
		ActionListener popupTreeAction = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if ((FreeTreePaneHolder.this.needPopup)
						&& (FreeTreePaneHolder.this.splitMouseOver)
						&& (!FreeTreePaneHolder.this.centerPane.isShowing())) {
					long now = System.currentTimeMillis();
					long time = now - FreeTreePaneHolder.this.splitLastMoveTime;
					if (time > FreeTreePaneHolder.this.mouseDelay)
						FreeTreePaneHolder.this.popupTree();
				}
			}
		};
		new Timer(this.mouseDelay, popupTreeAction).start();
		MouseAdapter popupMouseHandler = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				FreeTreePaneHolder.this.hideTree();
			}

			public void mouseMoved(MouseEvent e) {
				FreeTreePaneHolder.this.hideTree();
			}
		};
		this.popupPane.addMouseListener(popupMouseHandler);
		this.popupPane.addMouseMotionListener(popupMouseHandler);
		this.popupPane.setVisible(false);
		this.popupPane.setOpaque(false);

		this.popupPane.setBorder(BorderFactory
				.createEmptyBorder(42, 0, 25, 200));
		this.popupPane.setLayout(new BorderLayout());
		Shell.getInstance().getLayeredPane()
				.add(this.popupPane, JLayeredPane.DRAG_LAYER);
	}

	public FreeOutlookHeader getHeader() {
		return this.header;
	}

	public FreeMainUITreePane getTreePane() {
		return this.mainTree;
	}

	public void changeDock() {
		this.header.changeShrink();
	}

	public void dock() {
		this.splitMouseOver = false;
		this.splitLastMoveTime = System.currentTimeMillis();
		this.split.setCursor(Cursor.getPredefinedCursor(0));
		System.out.println("FreeTreePaneHolder.java Info:execute close!"); 
		this.split.setSplitClosed(true);
		remove(this.centerPane);
		revalidate();
		repaint();
	}

	public void undock() {
		this.splitMouseOver = false;
		this.splitLastMoveTime = System.currentTimeMillis();
		add(this.centerPane, "Center");
		this.split.setCursor(Cursor.getPredefinedCursor(10));
		System.out.println("FreeTreePaneHolder.java Info:execute open!"); 
		this.split.setSplitClosed(false);
		this.centerPane.add(this.mainTree, "Center");
		revalidate();
		repaint();
	}

	private void popupTree() {
		if (this.needPopup) {
			int x = 0;
			int y = 0;
			int width = 500;
			int height = Shell.getInstance().getContentPane().getHeight();
			this.popupPane.setBounds(x, y, width, height);
			this.mainTree.setOpaque(false);
			this.popupPane.add(this.mainTree, "Center");
			this.popupPane.setVisible(true);
		}
	}

	private void hideTree() {
		this.mainTree.setOpaque(true);
		this.popupPane.setVisible(false);
		this.centerPane.add(this.mainTree, "Center");
	}
}