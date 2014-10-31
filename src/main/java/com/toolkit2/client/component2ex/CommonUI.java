package com.toolkit2.client.component2ex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.frame.free.FreeSplashUI;
import com.toolkit2.client.i18n.Translator;

/**************************************************************************************
 * 通用界面
 * 
 * **/
public class CommonUI {
	public static final int NO_OPTION = 0;
	public static final int YES_OPTION = 1;
	public static final int YES_ALL_OPTION = 2;
	private static boolean hasMessageDialog = false;

	public static String getInputText(Component c, String title, int row,
			int column) {
		return getInputText(c, title, row, column, "");
	}

	public static String getInputText(Component parentComponent, String title) {
		return getInputText(parentComponent, title, "");
	}

	public static String getInputText(Component c, String title,
			String defaultValue) {
		return getInputText(c, title, 0, 0, defaultValue);
	}

	public static String getInputText(JComponent inputCom, Component c,
			String title, int row, int column) {
		return getInputText(inputCom, c, title, row, column, "");
	}

	public static String getInputText(JComponent inputCom, Component c,
			String title) {
		return getInputText(inputCom, c, title, 0, 0, "");
	}

	public static String getInputText(JComponent inputCom, Component c,
			String title, String defaultValue) {
		return getInputText(inputCom, c, title, 0, 0, defaultValue);
	}

	public static int getInputValue(Component parentComponent,
			final String title, final Component component) {
		final Component parent = ClientUtil
				.getClientUIForComponent(parentComponent);
		if (SwingUtilities.isEventDispatchThread()) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(200L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							// if ((component instanceof JTextComponent) ||
							// (component instanceof JComboBox) || (component
							// instanceof JCheckBox) || (component instanceof
							// AddressBrowserPickPane))
							if ((component instanceof JTextComponent)
									|| (component instanceof JComboBox)
									|| (component instanceof JCheckBox))
								component.requestFocus();
							else if (component instanceof JComponent) {
								JComponent container = (JComponent) component;
								int i = 0;
								do {
									if (i >= container.getComponentCount())
										break;
									// if ((container.getComponent(i) instanceof
									// JTextComponent) ||
									// (container.getComponent(i) instanceof
									// JComboBox) || (container.getComponent(i)
									// instanceof JCheckBox) ||
									// (container.getComponent(i) instanceof
									// AddressBrowserPickPane))
									if ((container.getComponent(i) instanceof JTextComponent)
											|| (container.getComponent(i) instanceof JComboBox)
											|| (container.getComponent(i) instanceof JCheckBox)) {
										container.getComponent(i)
												.requestFocus();
										break;
									}
									i++;
								} while (true);
							}
						}
					});
				}
			}).start();

			return JOptionPane.showConfirmDialog(parent,
					new Object[] { component }, title, 2, 3);
		}

		Runnable run = new Runnable() {
			private int value = -1;

			public void run() {
				this.value = JOptionPane.showConfirmDialog(parent,
						new Object[] { component }, title, 2, 3);
			}

			public int getValue() {
				return this.value;
			}

		};
		try {
			SwingUtilities.invokeAndWait(run);
			return (Integer) ((JOptionPane) run).getValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	private static int getInputValue(Component c, final String title,
			final Object component) {
		final Component parent = ClientUtil.getClientUIForComponent(c);
		if (SwingUtilities.isEventDispatchThread()) {
			return JOptionPane.showConfirmDialog(c, component, title, 2, 3);
		}

		Runnable run = new Runnable() {
			private int value = -1;

			public void run() {
				this.value = JOptionPane.showConfirmDialog(parent, component,
						title, 2, 3);
			}

			public int getValue() {
				return this.value;
			}

		};
		try {
			SwingUtilities.invokeAndWait(run);
			return (Integer) ((JOptionPane) run).getValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static String getInputText(final JComponent inputCom,
			final Component c, final String title, final int row,
			final int column, final String defaultValue) {
		if (SwingUtilities.isEventDispatchThread()) {
			return getInputTextImpl(inputCom, c, title, row, column,
					defaultValue);
		}

		Runnable run = new Runnable() {
			private String value = null;

			public void run() {
				this.value = CommonUI.getInputTextImpl(inputCom, c, title, row,
						column, defaultValue);
			}

			public String getValue() {
				return this.value;
			}

		};
		try {
			SwingUtilities.invokeAndWait(run);
			return (String) ((JOptionPane) run).getValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getInputText(final Component c, final String title,
			final int row, final int column, final String defaultValue) {
		if (SwingUtilities.isEventDispatchThread()) {
			return getInputTextImpl(c, title, row, column, defaultValue);
		}

		Runnable run = new Runnable() {
			private String value = null;

			public void run() {
				this.value = CommonUI.getInputTextImpl(c, title, row, column,
						defaultValue);
			}

			public String getValue() {
				return this.value;
			}

		};
		try {
			SwingUtilities.invokeAndWait(run);
			return (String) ((JOptionPane) run).getValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getInputTextImpl(JComponent inputCom, Component c,
			String title, int row, int column, String defaultValue) {
		Object[] message = new Object[2];
		message[0] = title;
		if ((row == 0) && (column == 0))
			message[1] = inputCom;
		else {
			message[1] = new JScrollPane(inputCom);
		}
		JTextComponent inputComponent = null;
		int count = inputCom.getComponentCount();
		Component[] component = inputCom.getComponents();
		for (int i = 0; i < count; i++) {
			Component comp = component[i];
			if ((comp instanceof JTextComponent)) {
				inputComponent = (JTextComponent) comp;
				inputComponent.setText(defaultValue);
				break;
			}
		}
		if ((inputComponent == null) && ((inputCom instanceof JTextComponent))) {
			inputComponent = (JTextComponent) inputCom;
			inputComponent.setText(defaultValue);
		}

		if (inputComponent == null) {
			return null;
		}
		ClientUtil.addStringPopupMenu(inputComponent);
		c = ClientUtil.getClientUIForComponent(c);
		final JTextComponent finalInputComponet = inputComponent;
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(200L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						finalInputComponet.requestFocus();
					}
				});
			}

		}).start();

		int result = getInputValue(c, Translator.getString("CommonUI.Input"),
				message);
		if (result != 0) {
			return null;
		}
		return finalInputComponet.getText();
	}

	private static String getInputTextImpl(Component c, String title, int row,
			int column, String defaultValue) {
		Object[] message = new Object[2];
		message[0] = title;
		final JTextComponent inputComponent;
		if ((row == 0) && (column == 0)) {
			JTextField text = new JTextField();
			text.setColumns(column);
			text.setText(defaultValue);
			message[1] = text;
			inputComponent = text;
			ClientUtil.setupWindow(text);
		} else {
			JTextArea text = new JTextArea();
			text.setLineWrap(true);
			text.setWrapStyleWord(false);
			text.setRows(row);
			text.setColumns(column);
			text.setText(defaultValue);
			JScrollPane scroll = new JScrollPane(text);
			message[1] = scroll;
			inputComponent = text;
			ClientUtil.setupWindow(scroll);
		}
		ClientUtil.addStringPopupMenu(inputComponent);
		c = ClientUtil.getClientUIForComponent(c);
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(200L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						inputComponent.requestFocus();
					}
				});
			}
		}).start();

		int result = getInputValue(c, Translator.getString("CommonUI.Input"),
				message);
		if (result != 0) {
			return null;
		}
		return inputComponent.getText();
	}

	
	public static void showMessage(Component c, final String message)
	  {
	    c = ClientUtil.getWindowForComponent(c);
	    if (c == null) {
	      c = Shell.getInstance();
	    }
	    final Component component = c;
	    if (SwingUtilities.isEventDispatchThread()) {
	      if (!hasMessageDialog) {
	        hasMessageDialog = true;
	        JOptionPane.showMessageDialog(component, message, Translator.getString("CommonUI.Message"), 1);
	        hasMessageDialog = false;
	      }
	    }
	    else SwingUtilities.invokeLater(new Runnable()
	      {
	        public void run() {
	          if (!CommonUI.hasMessageDialog) {
	        	  CommonUI.hasMessageDialog = true;
					JOptionPane.showMessageDialog(component, message, Translator.getString("CommonUI.Message"), 1);
					CommonUI.hasMessageDialog = false;
	          }
	        }
	      });
	  }

	  public static void showMessage(Component c, final String message, boolean run)
	  {
	    c = ClientUtil.getWindowForComponent(c);
	    if (c == null) {
	      c = Shell.getInstance();
	    }
	    final Component component = c;
	    if ((SwingUtilities.isEventDispatchThread()) && (!run)) {
	      if (!hasMessageDialog) {
	        hasMessageDialog = true;
	        JOptionPane.showMessageDialog(component, message, Translator.getString("CommonUI.Message"), 1);
	        hasMessageDialog = false;
	      }
	    }
	    else SwingUtilities.invokeLater(new Runnable()
	      {
	        public void run() {
	          if (!CommonUI.hasMessageDialog) {
	        	  CommonUI.hasMessageDialog = true;
					JOptionPane.showMessageDialog(component, message, Translator.getString("CommonUI.Message"), 1);
					CommonUI.hasMessageDialog = false;
	          }
	        }
	      });
	  }

	/*********************************************************************************************
	 * 显示警告信息
	 * ****/
	public static void showWarning(Component c, String message) {
		final String msg = message;
		c = ClientUtil.getClientUIForComponent(c);
		final Component component = c;
		if (SwingUtilities.isEventDispatchThread())
			JOptionPane.showMessageDialog(component, msg,
					Translator.getString("CommonUI.Warning"), 2);
		else
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(component, msg,
							Translator.getString("CommonUI.Warning"), 2);
				}
			});
	}

	public static void showException(Component parent, String title,
			Exception ex) {
		if (SwingUtilities.isEventDispatchThread()) {
			// if (WaitingUI.getInstance() != null)
			// WaitingUI.getInstance().dispose();
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// if (WaitingUI.getInstance() != null) {
					// WaitingUI.getInstance().dispose();
					// }
				}
			});
		}
		// 原来是把异常信息做了转换，目前工具是内容部使用故在界面直接抛出详细异常信息
		// ExceptionWorker worker = new ExceptionWorker(ex);
		// if ((parent == null) && (AbstractMainUI.isInstanced())) {
		// parent = AbstractMainUI.getInstance();
		// }

		// boolean isIgnore = (worker != null) && (worker.getBB2Reason() !=
		// null) && (worker.getBB2Reason().getReason() != null) &&
		// (worker.getBB2Reason().getReason().equalsIgnoreCase(BIZReason.ALREADY_LOGOUT.getReason()));
		//
		// isIgnore = (isIgnore) && (!AbstractMainUI.isInstanced()) &&
		// ((LoginUI.getInstance().isShowing()) ||
		// (LoginUI.getInstance().isShowing()));

		boolean isIgnore = (ex != null);

		Object parameter = "RunTimeException";
		String exceptionHandle = "Ex";
		Window window = ClientUtil.getWindowForComponent(parent);
		if (isIgnore) {
			System.out.println("do nothing for this exception.");
		} else {
			ex.printStackTrace();

			if ((window != null) && (window.isShowing())) {
				showExceptionDialog(window, ex.getMessage(), parameter,
						exceptionHandle);
			}
			// else if ((AbstractMainUI.isInstanced()) &&
			// (AbstractMainUI.getInstance().isShowing())) {
			// showExceptionDialog(AbstractMainUI.getInstance(),
			// worker.getMessage(), parameter, exceptionHandle);
			// }
			// else if (LoginUI.getInstance().isShowing())
			// showExceptionDialog(LoginUI.getInstance(), worker.getMessage(),
			// parameter, exceptionHandle);
		}
	}
	/***************************************************************************
	 * 显示异常信息对话框
	 * 
	 * ****/
	public static void showExceptionDialog(Component parent, String message,
			final Object parameter, final String exceptionHandle) {
		if ((exceptionHandle == null) || ("".equals(exceptionHandle))
				|| ("?".equals(exceptionHandle))
				|| ("N".equals(exceptionHandle))
				|| ("C".equals(exceptionHandle))) {
			showWarning(parent, message);
		} else {
			final String msg = message;
			// parent = ClientUtil.getClientUIForComponent(parent);
			final Component component = parent;
			if (SwingUtilities.isEventDispatchThread()) {
				int option = JOptionPane.showConfirmDialog(component, msg,
						Translator.getString("CommonUI.Warning"), 0);
				// if (option == 0)
				// ExceptionActionManager.handleException(exceptionHandle,
				// parameter);
			} else {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						int option = JOptionPane.showConfirmDialog(component,
								msg, Translator.getString("CommonUI.Warning"),
								0);
						// if (option == 0)
						// ExceptionActionManager.handleException(exceptionHandle,
						// parameter);
					}
				});
			}
		}
	}
	/***************************************************************************
	 * 显示异常信息
	 * 
	 * ****/
	public static void showException(Component c, Exception ex) {
		showException(c, null, ex);
	}

	/***************************************************************************
	 * 公共方法显示闪屏
	 * 
	 * ****/
	public static void showSplashWindow() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					FreeSplashUI.getInstance().setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***************************************************************************
	 * 公共方法显示闪屏并添加显示信息
	 * 
	 * ****/
	public static void showSpalshWindow(final String message) {
		if (SwingUtilities.isEventDispatchThread()) {
			FreeSplashUI.getInstance().setVisible(true);
			FreeSplashUI.getInstance().setMessage(message);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					FreeSplashUI.getInstance().setVisible(true);
					FreeSplashUI.getInstance().setMessage(message);
				}
			});
		}
	}
	/***************************************************************************
	 * 公共方法关闭闪屏
	 * 
	 * ****/
	public static void closeSplashWindow() {
	    FreeSplashUI.getInstance().setVisible(false);
	  }

	
}
