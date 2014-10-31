package com.toolkit2.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.frame.free.FreeMetalComboBoxUI;
import com.toolkit2.client.frame.free.FreeUpdateButtonPane;
import com.toolkit2.client.component2ex.ButtonPane;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.component2ex.TextComponentPopuMenu;

public class WindowUtil {
	public static void addStringPopupMenu(JTextComponent text) {
		if ((text instanceof JPasswordField)) {
			return;
		}
		text.addMouseListener(TextPopuMenuListener);
	}

	private static ComponentAdapter packTableListener = new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			Component component = e.getComponent();
			if ((component instanceof Container))
				WindowUtil.packTable((Container) component);
		}
	};
	private static MouseListener TextPopuMenuListener = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			if ((e.getButton() == 3)
					&& ((e.getSource() instanceof JTextComponent))) {
				JTextComponent text = (JTextComponent) e.getSource();
				TextComponentPopuMenu.getInstance(text).show(text, e.getX(),
						e.getY());
			}
		}
	};

	private static final FocusListener SelectAllOnFocusListener = new FocusAdapter() {
		public void focusGained(FocusEvent e) {
			Object source = e.getSource();
			if (((source instanceof JTextField)) && (!e.isTemporary()))
				((JTextField) source).selectAll();
		}
	};

	public static void packTable(Container container) {
		Component[] components = container.getComponents();
		if ((components == null) || (components.length == 0)) {
			return;
		}
		for (int i = 0; i < components.length; i++) {
			// if ((components[i] instanceof HyperTable)) {
			// ((HyperTable) components[i]).packColumns();
			// ((HyperTable) components[i]).repaint();
			// break;
			// }
			// if ((components[i] instanceof HyperElementTable)) {
			// HyperElementTable table = (HyperElementTable) components[i];
			// if (!table.isWidthChanged())
			// break;
			// table.packColumns();
			// table.repaint();
			// break;
			// }
			//
			// if ((components[i] instanceof HyperTTreeTable)) {
			// ((HyperTTreeTable) components[i]).packColumns();
			// ((HyperTTreeTable) components[i]).repaint();
			// break;
			// }
			if ((components[i] instanceof Container))
				packTable((Container) components[i]);
		}
	}

	public static void setupWindow(Component component) {
		Window window = ClientUtil.getWindowForComponent(component);
		if ((window != null) && (!(window instanceof Shell))) {
			Toolkit kit = Toolkit.getDefaultToolkit();
			GraphicsConfiguration config = window.getGraphicsConfiguration();
			Insets insets = kit.getScreenInsets(config);
			Dimension screenSize = kit.getScreenSize();
			Dimension size = window.getSize();

			int screenX = screenSize.width - insets.left - insets.right;
			int screenY = screenSize.height - insets.top - insets.bottom;
			size.width = (size.width > screenX ? screenX : size.width);
			size.height = (size.height > screenY ? screenY : size.height);
			window.setSize(size);
			int x = (screenX - size.width) / 2;
			int y = (screenY - size.height) / 2;
			x += insets.left;
			y += insets.top;
			window.setLocation(x, y);
		}
		JComponent jcomponent;
		if ((component instanceof JComponent)) {
			jcomponent = (JComponent) component;
		} else {
			if (((component instanceof JFrame))
					&& ((((JFrame) component).getContentPane() instanceof JComponent))) {
				jcomponent = (JComponent) ((JFrame) component).getContentPane();
			} else {
				if (((component instanceof JDialog))
						&& ((((JDialog) component).getContentPane() instanceof JComponent)))
					jcomponent = (JComponent) ((JDialog) component)
							.getContentPane();
				else
					jcomponent = null;
			}
		}
		if (jcomponent != null) {
			setupComponentUI(component);
			setBackground(jcomponent);
//			installTabDoubleClickMaxmum(jcomponent);
//			setTextFieldBorder(jcomponent);
//			setTextFocuseAction(jcomponent);
			if ((jcomponent instanceof Container)) {
				jcomponent.addComponentListener(packTableListener);
			}
			jcomponent.putClientProperty("Toolkit.SetUp", Boolean.TRUE);
		}
	}

	/***************************************************************************************************
	 * 设置组件背景色
	 * 
	 * *****/
	public static void setBackground(JComponent c) {
		if ((c.getClientProperty("Color") instanceof Color)) {
			c.setBackground((Color) c.getClientProperty("Color"));
		}
		if (c.getComponentCount() > 0)
			for (int i = 0; i < c.getComponentCount(); i++) {
				Component child = c.getComponent(i);
				if (((child instanceof JPanel))
						&& ((c.getClientProperty("Color") instanceof Color))) {
					((JPanel) child).setOpaque(true);
				}
				if (((child instanceof JPanel))
						|| ((child instanceof JLabel))
						|| ((child instanceof JScrollPane))
						|| ((child instanceof JViewport))
						|| ((child instanceof JTree))
						|| ((child instanceof JTabbedPane))
						// || ((child instanceof BooleanTextField))
						// || ((child instanceof JTextAreaLabel))
						// || ((child instanceof BooleanTextField))
						|| ((child instanceof JRadioButton))
						|| ((child instanceof JSplitPane))
						|| ((child instanceof JComboBox))
						|| ((child instanceof AbstractButton))
						|| ((child instanceof JTextComponent))
						|| ((child instanceof JPasswordField))
						// || ((child instanceof NumberDisplayTextField))
						|| ((child instanceof JCheckBox))) {
					JComponent component = (JComponent) child;
					if ((component.getClientProperty("Color") instanceof Color)) {
						component.setBackground((Color) component
								.getClientProperty("Color"));
					}

					if ((child instanceof JTextComponent)) {
						JTextComponent txtComponent = (JTextComponent) child;
						if (!txtComponent.isEditable()) {
							txtComponent.setOpaque(false);
						}
						if (component.getClientProperty("Color") != null) {
							txtComponent.setOpaque(true);
						}
					}

					if ((child instanceof JComboBox)) {
						JComboBox box = (JComboBox) child;
						if (!box.isEnabled()) {
							box.setUI(new FreeMetalComboBoxUI());
						}
					}
					setBackground(component);
				}
			}
	}

	public static void setupComponentUI(Component component) {
		if (((component instanceof JComponent))
				&& ((((JComponent) component).getClientProperty("NotSetup") instanceof Boolean))) {
			Boolean b = (Boolean) ((JComponent) component)
					.getClientProperty("NotSetup");
			if (b == Boolean.TRUE) {
				return;
			}
		}

		// if ((component instanceof JDatePicker))
		// return;
		JComboBox cb;
		if ((component instanceof JComboBox)) {
			cb = (JComboBox) component;
		}

		// if (((component instanceof JTextField)) && (!(component instanceof
		// HtmlStyleTextField))) {
		if (((component instanceof JTextField))) {
			JTextField txt = (JTextField) component;
			if ((txt.getPreferredSize() == null)
					|| (txt.getPreferredSize().width != 0))
				;
		}
		JSpinner spinner;
		if ((component instanceof JSpinner)) {
			spinner = (JSpinner) component;
		}

		if ((component instanceof JSplitPane)) {
			JSplitPane split = (JSplitPane) component;
			split.setDividerSize(2);
		}

		if ((component instanceof JScrollPane)) {
			JScrollPane scroll = (JScrollPane) component;
			JLabel lbCorner = new JLabel();
			lbCorner.setOpaque(true);
			lbCorner.setBackground(new Color(226, 228, 229));
			scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			scroll.setCorner("UPPER_RIGHT_CORNER", lbCorner);
		}
		if ((component instanceof Container)) {
			Container jc = (Container) component;
			int count = jc.getComponentCount();
			for (int i = 0; i < count; i++) {
				Component c = jc.getComponent(i);
				setupComponentUI(c);
			}
		}
	}

//	public static void installTabDoubleClickMaxmum(JComponent c) {
//		if ((c.getClientProperty("NotSetup") instanceof Boolean)) {
//			Boolean b = (Boolean) c.getClientProperty("NotSetup");
//			if (b == Boolean.TRUE) {
//				return;
//			}
//		}
//		if ((c instanceof JTabbedPane)) {
//			final JTabbedPane thisTab = (JTabbedPane) c;
//			thisTab.addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					if ((e.getClickCount() > 1) && (thisTab.getTabCount() > 0)) {
//						final ClientUI clientUI = ClientUtil.getClientUIForComponent(thisTab);
//						if (clientUI == null) {
//							return;
//						}
//						LayoutManager oldLayoutManager = clientUI.getLayout();
//						if ((oldLayoutManager instanceof BorderLayout)) {
//							final Component southComponent = ((BorderLayout) oldLayoutManager)
//									.getLayoutComponent(clientUI, "South");
//							final Component centerComponent = ((BorderLayout) oldLayoutManager)
//									.getLayoutComponent(clientUI, "Center");
//							final Component northComponent = ((BorderLayout) oldLayoutManager)
//									.getLayoutComponent(clientUI, "North");
//
//							final int selectIndex = thisTab.getSelectedIndex();
//							final JTabbedPane maximizedTab = new JTabbedPane();
//
//							maximizedTab
//									.addComponentListener(WindowUtil.packTableListener);
//							while (thisTab.getTabCount() > 0) {
//								String title = thisTab.getTitleAt(0);
//								Icon icon = thisTab.getIconAt(0);
//								Component component = thisTab.getComponentAt(0);
//								String tip = thisTab.getToolTipTextAt(0);
//								thisTab.removeTabAt(0);
//								maximizedTab
//										.addTab(title, icon, component, tip);
//							}
//							maximizedTab.setSelectedIndex(selectIndex);
//							maximizedTab.setTabLayoutPolicy(thisTab
//									.getTabLayoutPolicy());
//							maximizedTab.setTabPlacement(thisTab
//									.getTabPlacement());
//							ButtonPane buttonPane = WindowUtil
//									.findButtonPane(clientUI);
//							maximizedTab.setBorder(BorderFactory
//									.createEmptyBorder(10, 10,
//											buttonPane == null ? 10 : 0, 10));
//							clientUI.removeAll();
//							clientUI.add(maximizedTab);
//							if (buttonPane != null) {
//								if ((buttonPane.getParent() instanceof FreeUpdateButtonPane))
//									clientUI.add(buttonPane.getParent(),
//											"South");
//								else {
//									clientUI.add(buttonPane, "South");
//								}
//
//							}
//
//							maximizedTab.revalidate();
//
//							maximizedTab.addMouseListener(new MouseAdapter() {
//								public void mouseClicked(MouseEvent e) {
//									if (e.getClickCount() > 1) {
//										clientUI.removeAll();
//										if (southComponent != null) {
//											clientUI.add(southComponent,
//													"South");
//										}
//										if (centerComponent != null) {
//											clientUI.add(centerComponent);
//										}
//										if (northComponent != null) {
//											clientUI.add(northComponent,
//													"North");
//										}
//										clientUI.repaint();
//
//										while (maximizedTab.getTabCount() > 0) {
//											String title = maximizedTab
//													.getTitleAt(0);
//											Icon icon = maximizedTab
//													.getIconAt(0);
//											Component component = maximizedTab
//													.getComponentAt(0);
//											String tip = maximizedTab
//													.getToolTipTextAt(0);
//											maximizedTab.removeTabAt(0);
//											thisTab.addTab(title, icon,
//													component, tip);
//										}
//										thisTab.setSelectedIndex(selectIndex);
//									}
//								}
//							});
//						}
//					}
//				}
//
//			});
//		} else if ((c instanceof Container)) {
//			for (int i = 0; i < c.getComponentCount(); i++) {
//				Component child = c.getComponent(i);
//				if ((child instanceof JComponent))
//					installTabDoubleClickMaxmum((JComponent) child);
//			}
//		}
//	}

//	public static void setTextFieldBorder(JComponent c) {
//		if ((c.getClientProperty("NotSetup") instanceof Boolean)) {
//			Boolean b = (Boolean) c.getClientProperty("NotSetup");
//			if (b == Boolean.TRUE) {
//				return;
//			}
//		}
//		if ((c != null) && (c.getComponentCount() > 0))
//			for (int i = 0; i < c.getComponentCount(); i++) {
//				final Component child = c.getComponent(i);
//
//				if ((!(child instanceof PagingNavigatorPane))
//						&& (!(child instanceof JSpinner))) {
//					if ((child instanceof JComboBox)) {
//						JComboBox comboBox = (JComboBox) child;
//						for (int j = 0; j < comboBox.getComponentCount(); j++) {
//							if ((comboBox.getComponent(j) instanceof JTextComponent)) {
//								addStringPopupMenu((JTextComponent) ((JComboBox) child)
//										.getComponent(j));
//							}
//						}
//
//					} else if ((child instanceof JComponent)) {
//						JComponent component = (JComponent) child;
//
//						if (ClientUtil.isHighlightComponent(component)) {
//							setupHighlightComponent(component);
//						} else if ((child instanceof JTextField)) {
//							JTextField text = (JTextField) child;
//
//							if (text.getClientProperty("Font") == null) {
//								text.setFont(FreeUtil.getFont12Plain());
//							}
//							if (!text.isEditable()) {
//								if ((text.getBorder() != FreeUnderlineBorder.INSTANCE)
//										&& (!(text instanceof HtmlStyleTextField)))
//									text.setBorder(FreeUnderlineBorder.INSTANCE);
//								else if ((text instanceof HtmlStyleTextField))
//									text.setBorder(null);
//							} else {
//								text.setBorder(UnderlineBorder.INSTANCE_NORMAL);
//							}
//
//							((JTextComponent) child)
//									.addPropertyChangeListener(new PropertyChangeListener() {
//										public void propertyChange(
//												PropertyChangeEvent evt) {
//											if ("editable".equalsIgnoreCase(evt
//													.getPropertyName()))
//												if (!((JTextComponent) child)
//														.isEditable()) {
//													((JTextComponent) child)
//															.setBackground(ClientUtil
//																	.darker(FreeUtil.ALL_UI_BACKGROUD_COLOR,
//																			0.99D));
//												} else {
//													((JTextComponent) child)
//															.setBackground(Color.white);
//													((JTextComponent) child)
//															.setOpaque(true);
//												}
//										}
//									});
//							addStringPopupMenu(text);
//						} else if ((child instanceof JTextComponent)) {
//							addStringPopupMenu((JTextComponent) child);
//
//							if ((!(child instanceof JTextAreaLabel))
//									&& (!(child instanceof JEditorPane))) {
//								((JTextComponent) child).setFont(FreeUtil
//										.getFont12Plain());
//								if (!((JTextComponent) child).isEditable())
//									((JTextComponent) child)
//											.setBackground(ClientUtil
//													.darker(FreeUtil.ALL_UI_BACKGROUD_COLOR,
//															0.99D));
//								else {
//									((JTextComponent) child)
//											.setBackground(Color.white);
//								}
//								if (!(ClientUtil.getClientUIForComponent(child) instanceof AbstractListUI)) {
//									if (!UnderlineBorder.INSTANCE
//											.equals(((JTextComponent) child)
//													.getBorder()))
//										((JTextComponent) child)
//												.setBorder(BorderFactory
//														.createLineBorder(Color.gray));
//									else {
//										((JTextComponent) child)
//												.setBackground(FreeUtil.ALL_UI_BACKGROUD_COLOR);
//									}
//								}
//								((JTextComponent) child)
//										.addPropertyChangeListener(new PropertyChangeListener() {
//											public void propertyChange(
//													PropertyChangeEvent evt) {
//												if ("editable".equalsIgnoreCase(evt
//														.getPropertyName()))
//													if (!((JTextComponent) child)
//															.isEditable()) {
//														((JTextComponent) child)
//																.setBackground(ClientUtil
//																		.darker(FreeUtil.ALL_UI_BACKGROUD_COLOR,
//																				0.99D));
//													} else {
//														((JTextComponent) child)
//																.setBackground(Color.white);
//														((JTextComponent) child)
//																.setOpaque(true);
//													}
//											}
//										});
//							}
//						} else {
//							setTextFieldBorder((JComponent) child);
//						}
//					}
//				}
//			}
//	}

//	public static void setTextFocuseAction(JComponent pane) {
//		if ((pane != null) && (pane.getComponentCount() > 0))
//			for (int i = 0; i < pane.getComponentCount(); i++) {
//				Component child = pane.getComponent(i);
//				if (!(child instanceof SearchActionPane)) {
//					if ((child instanceof JTextComponent)) {
//						JTextComponent text = (JTextComponent) child;
//						if (text.isEditable())
//							addSelectAllOnFocusListener(text);
//					} else if ((child instanceof JComponent)) {
//						setTextFocuseAction((JComponent) child);
//					}
//				}
//			}
//	}

	public static void addSelectAllOnFocusListener(JTextComponent textField) {
		if (textField == null) {
			return;
		}
		if (!(textField.getParent() instanceof JComboBox)) {
			textField.addFocusListener(SelectAllOnFocusListener);
		}
	}

	public static void setComponentVisableInScrollPane(Component component) {
	    setComponentVisableInScrollPane(null, component);
	  }

	  public static void setComponentVisableInScrollPane(JScrollPane scroll, Component component) {
	    if ((component == null) || ((component instanceof JScrollPane))) {
	      return;
	    }
	    Component parent = component;
	    int y = component.getHeight() / 2 + component.getY();
	    if (scroll == null) {
	      while (parent != null) {
	        if ((parent instanceof JScrollPane)) {
	          JScrollPane scrollPane = (JScrollPane)parent;
	          scrollPane.getViewport().scrollRectToVisible(new Rectangle(0, y, component.getWidth(), component.getHeight()));
	          break;
	        }
	        y += parent.getY();

	        parent = parent.getParent();
	      }
	    }
	    while (parent != null) {
	      if (parent == scroll) {
	        scroll.getViewport().scrollRectToVisible(new Rectangle(0, y, 0, 0));
	        break;
	      }
	      y += parent.getY();

	      parent = parent.getParent();
	    }
	  }


}
