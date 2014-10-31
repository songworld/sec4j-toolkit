package com.toolkit2.client.component2ex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.frame.free.FreeSearchTextField;
import com.toolkit2.client.Shell;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class LookupPane extends JPanel {
	private JTextField txtField = new JTextField(10);
	private LookupButton btnLookup = new LookupButton();
	private JPanel pane = new JPanel();
	BoxLayout paneLayout = new BoxLayout(this.pane, 0);
	private JLabel lbButton = new JLabel();

	public LookupPane() {
		init();
	}

	private void init() {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		setLayout(new BorderLayout(2, 0));
		this.pane.setLayout(this.paneLayout);
		add(this.pane, "Center");
		add(this.btnLookup, "East");

		this.txtField = new FreeSearchTextField();
		((FreeSearchTextField) this.txtField).getButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						LookupPane.this.btnLookup.doClick();
					}
				});
		this.btnLookup.setVisible(false);

		int width = this.txtField.getPreferredSize().width;
		int height = this.btnLookup.getPreferredSize().height;
		Dimension size = new Dimension(width, height);
		this.txtField.setPreferredSize(size);
		this.pane.add(this.txtField);
		this.pane.add(this.lbButton);
		ClientUtil.setupEnterKey(this, this.btnLookup);
	}

	public void setLookupButtonLabel(String text) {
		this.lbButton.setText(text);
	}

	public void setLookupText(String text) {
		this.txtField.setText(text);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LookupPane.this.txtField.requestFocus();
			}
		});
	}

	public JButton getButton() {
		return this.btnLookup;
	}

	public JTextField getTextField() {
		return this.txtField;
	}

	class LookupButton extends JButton {
		public LookupButton() {
			setText(Translator.getString("LookUpButton.2LookUp"));
			setToolTipText(Translator.getString("LookUpButton.2LookUp"));
			setIcon(FreeIconStore.SEARCH_ICON);
			setMnemonic('L');
			Insets oldInsets = getMargin();
			setMargin(new Insets(oldInsets.top, oldInsets.top,
					oldInsets.bottom, oldInsets.bottom));
		}

		public void onClick() {
			LookupPane.this.btnLookup.setEnabled(false);
//			final ClientUI clientUI = ClientUtil.getClientUIForComponent(this);
//			final Window window = ClientUtil.getWindowForComponent(this);
//			if ((clientUI != null) && ((clientUI instanceof DetailUI))) {
//				final DetailUI detailUI = (DetailUI) clientUI;
//				Thread thread = new Thread() {
//					public void run() {
//						SwingUtilities.invokeLater(new Runnable() {
//							public void run() {
//								WaitingUI waiting = WaitingUI.getInstance();
//								waiting.setVisible(true);
//							}
//						});
//						try {
//							final Object vo = detailUI.retrieveVO(detailUI
//									.getLookUpPK(LookupPane.this.txtField
//											.getText().toUpperCase().trim()));
//							SwingUtilities.invokeLater(new Object() {
//								public void run() {
//									WaitingUI.getInstance().getHeaderLabel()
//											.setText("Loading data...");
//									this.setVO(clientUI, vo,
//											!(window instanceof Shell));
//									WaitingUI.getInstance().setVisible(false);
//								}
//							});
//						} catch (Exception e) {
//							ClientUtil.showException(e);
//						}
//					}
//
//					private void setVO(ClientUI win, Object vo, boolean detached) {
//						if (vo != null) {
//							if (!detached) {
//								Class clazz = win.getClass();
//								ClientUI newUI = null;
//								try {
//									newUI = (ClientUI) clazz.newInstance();
//									Method m = clazz.getMethod("setVO",
//											new Class[] { vo.getClass() });
//									m.invoke(newUI, new Object[] { vo });
//								} catch (Exception e) {
//									ClientUtil.showException(e);
//								}
//								AbstractMainUI.getInstance()
//										.showTab(newUI, win);
//							} else {
//								try {
//									Method m = detailUI.getClass().getMethod(
//											"setVO",
//											new Class[] { vo.getClass() });
//									m.invoke(detailUI, new Object[] { vo });
//								} catch (Exception e) {
//									ClientUtil.showException(e);
//								}
//							}
//						} else {
//							WaitingUI.getInstance().setVisible(false);
//							CommonUI.showNoData(win);
//						}
//					}
//				};
//				thread.start();
//			}
			LookupPane.this.btnLookup.setEnabled(true);
		}
	}
}