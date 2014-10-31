package com.toolkit2.client.component2ex.base;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.frame.free.FreeUtil;
import com.toolkit2.client.frame.mian.MainTabCloseAdapter;

public class MessageGlassPane extends JPanel
{
  private SubForm clientUI = null;
  private static MessageGlassPane instance = null;
  private JPanel mainPane = new JPanel(new BorderLayout());
  private JPanel messagePane = new JPanel(new BorderLayout());
  private JLabel lbMessage = new JLabel();
  private ComponentAdapter adapter = null;
  private JPanel buttonPane = new JPanel(new FlowLayout(2, 0, 1));
  private HashMap<SubForm, Object[]> clientUIMessageCaches = new HashMap();
  private ImageIcon messageCenterIcon = FreeUtil.getImageIcon("message_center.png");
  private ImageIcon messageLeftIcon = FreeUtil.getImageIcon("message_left.png");
  private ImageIcon messageRightIcon = FreeUtil.getImageIcon("message_right.png");
  private ImageIcon messageWarningIcon = FreeUtil.getImageIcon("message_warning.png");
  private ImageIcon messageTitleIcon = FreeUtil.getImageIcon("message_title.png");
  private ImageIcon closeButtonIcon = FreeUtil.getImageIcon("message_close.png");
  private ImageIcon closeButtonRoverIcon = FreeUtil.getImageIcon("message_close_rover.png");
  private ImageIcon closeButtonPressedIcon = FreeUtil.getImageIcon("message_close_pressed.png");
  private JButton btnCloseMessagePane = createTransparentButton(this.closeButtonIcon, this.closeButtonRoverIcon, this.closeButtonPressedIcon, null);
  private JPanel northPane = new JPanel(new BorderLayout());
  private JLabel lbTitle = new JLabel();
  private Rectangle oldRect = null;
  private static final String MESSAGE_CLOSED = "MESSAGE_CLOSED";

  private MessageGlassPane()
  {
    setLayout(new BorderLayout(0, 0));
    add(this.mainPane);
    this.mainPane.add(this.northPane, "North");
    this.mainPane.add(this.messagePane);
    this.northPane.add(this.buttonPane, "East");
    this.northPane.add(this.lbTitle);
    this.northPane.setOpaque(false);
    this.messagePane.setOpaque(false);
    this.buttonPane.setOpaque(false);
    this.mainPane.setOpaque(false);
    this.messagePane.setBorder(ClientUtil.createEmptyBorder());
    this.lbTitle.setText("<html><font color = red size = 4> " + GeneralUtil.getString("MessageGlassPane.Message"));
    this.lbTitle.setIcon(this.messageWarningIcon);
    this.lbTitle.setHorizontalAlignment(0);
    this.buttonPane.add(this.btnCloseMessagePane);
    ClientUtil.setPreferredHeight(this.northPane, this.messageTitleIcon.getIconHeight());
    this.btnCloseMessagePane.setText("");
    this.btnCloseMessagePane.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MessageGlassPane.this.hideSelf();
      }
    });
    this.adapter = new ComponentAdapter()
    {
      public void componentResized(ComponentEvent e) {
        MessageGlassPane.this.computeMessagePostion();
      }
    };
    setBorder(new MessageBorder());
    ClientUtil.setupWindow(this);
  }

  private void hideSelf()
  {
    setVisible(false);
    if (this.clientUI != null)
      this.clientUI.putClientProperty("MESSAGE_CLOSED", Boolean.valueOf(true));
  }

  public static void clearCache()
  {
    instance = null;
  }

  public void setClientUI(SubForm clientUI) {
    if (clientUI == null) {
      return;
    }
    this.lbTitle.setText("<html><font color = red size = 4> " + GeneralUtil.getString("MessageGlassPane.Message"));
    setVisible(false);
    Boolean closedRef = (Boolean)clientUI.getClientProperty("MESSAGE_CLOSED");
    boolean closed = (closedRef != null) && (closedRef.booleanValue());
    if (clientUI != this.clientUI) {
      this.clientUI = clientUI;
      this.clientUI.addComponentListener(this.adapter);
      this.clientUI.addTabCloseListener(new MainTabCloseAdapter()
      {
        public void tabClosed() {
          MessageGlassPane.this.clientUIMessageCaches.remove(MessageGlassPane.this.clientUI);
        }
      });
      Component parent = clientUI.getParent();
      if ((parent instanceof JTabbedPane)) {
        final JTabbedPane tab = (JTabbedPane)parent;
        if (tab.getClientProperty("MessageGlassPaneChange") == null) {
          tab.addChangeListener(new ChangeListener()
          {
            public void stateChanged(ChangeEvent e) {
              MessageGlassPane.this.setClientUI((SubForm)tab.getSelectedComponent());
            }
          });
          tab.putClientProperty("MessageGlassPaneChange", new Object());
        }
      }
      if (!closed) {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run() {
            MessageGlassPane.this.addClientUIMessage();
          }
        });
      }
    }
    if (!closed)
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run() {
          MessageGlassPane.this.computeMessagePostion();
        }
      });
  }

  private void addClientUIMessage()
  {
    this.messagePane.removeAll();
    if (this.clientUI != null) {
      Object[] cacheMessages = (Object[])this.clientUIMessageCaches.get(this.clientUI);
      JComponent component = null;
      String message = null;
      if (cacheMessages != null) {
        component = (JComponent)cacheMessages[0];
        message = (String)cacheMessages[1];
      } else {
        component = this.clientUI.getMessageComponennt();
        message = this.clientUI.getMessage();
        Object[] newCacheMessages = { component, message };
        this.clientUIMessageCaches.put(this.clientUI, newCacheMessages);
      }
      setVisible(true);
      if (component != null) {
    	  //设置组件不透明
        ClientUtil.setOpaque(component, false);
        this.messagePane.add(component);
      } else if ((message != null) && (!"".equals(message))) {
        this.messagePane.add(this.lbMessage);
        this.lbMessage.setText(message);
      } else {
        setVisible(false);
      }
    }
  }

  private void computeMessagePostion() {
    if ((this.clientUI != null) && (this.clientUI.isShowing())) {
      Point p1 = Shell.getInstance().getContentPane().getLocationOnScreen();
      Component parent = this.clientUI.getParent();
      if ((parent.isShowing()) && 
        (parent != null)) {
        Point p2 = parent.getLocationOnScreen();
        int x_offset = 0;
        int y_offset = 0;
        int tab_width = 0;
        if ((parent instanceof JTabbedPane)) {
          JTabbedPane tab = (JTabbedPane)parent;
          int index = tab.indexOfComponent(this.clientUI);
          Rectangle rct = tab.getBoundsAt(index);
          x_offset = rct.x;
          y_offset = tab.getTabComponentAt(index) == null ? 0 : tab.getTabComponentAt(index).getHeight();
          tab_width = tab.getTabComponentAt(index) == null ? 0 : tab.getTabComponentAt(index).getWidth();
        }
        int pointX = p2.x - p1.x + x_offset - this.messageLeftIcon.getIconWidth() + 1;
        if (pointX + getPreferredSize().width > p1.x + Shell.getInstance().getContentPane().getBounds().width) {
          pointX = pointX - getPreferredSize().width + tab_width + this.messageLeftIcon.getIconWidth() * 2 - 4;
          setBounds(pointX, p2.y - p1.y + y_offset, getPreferredSize().width, getPreferredSize().height);
        } else {
          setBounds(pointX, p2.y - p1.y + y_offset, getPreferredSize().width, getPreferredSize().height);
        }
      }
    }
  }

  private JButton createTransparentButton(ImageIcon icon, ImageIcon roverIcon, ImageIcon pressedIcon, ImageIcon disableIcon)
  {
    JButton button = new JButton();
    button.setMargin(null);
    button.setOpaque(false);
    button.setIcon(icon);

    button.setRolloverIcon(roverIcon);
    button.setPressedIcon(pressedIcon);
    if (disableIcon != null) {
      button.setDisabledIcon(disableIcon);
    }
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setRequestFocusEnabled(false);
    button.setBorder(null);
    return button;
  }

  public static synchronized MessageGlassPane getInstance() {
    if (instance == null) {
      instance = new MessageGlassPane();
    }
    return instance;
  }

  public Dimension getPreferredSize() {
    Dimension d = super.getPreferredSize();
    d.width = (this.messageLeftIcon.getIconWidth() + this.messageTitleIcon.getIconWidth() + this.messageRightIcon.getIconWidth());
    return d;
  }

  private void paintBackgroundByICon(ImageIcon backgroundImageIcon, Graphics2D g2d, int x, int y, int width, int height) {
    BufferedImage bi = new BufferedImage(backgroundImageIcon.getIconWidth(), backgroundImageIcon.getIconHeight(), 2);
    Graphics biGraphics = bi.createGraphics();
    biGraphics.drawImage(backgroundImageIcon.getImage(), 0, 0, null);
    biGraphics.dispose();
    TexturePaint paint = new TexturePaint(bi, new Rectangle(0, 0, width, height));
    g2d.setPaint(paint);
    g2d.fillRect(x, y, width, height);
  }

  protected void paintBorder(Graphics g)
  {
    Graphics2D g2d = (Graphics2D)g;
    Composite oldComposit = g2d.getComposite();
    AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.85F);
    g2d.setComposite(alpha);
    super.paintBorder(g);
    g2d.setComposite(oldComposit);
  }

  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    Composite oldComposit = g2d.getComposite();
    AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.85F);
    g2d.setComposite(alpha);
    super.paintComponent(g);
    paintBackgroundByICon(this.messageTitleIcon, g2d, this.messageLeftIcon.getIconWidth(), 0, this.messageTitleIcon.getIconWidth(), this.messageTitleIcon.getIconHeight());
    paintBackgroundByICon(this.messageCenterIcon, g2d, this.messageLeftIcon.getIconWidth(), this.messageTitleIcon.getIconHeight(), getWidth() - this.messageRightIcon.getIconWidth() - this.messageLeftIcon.getIconWidth(), getHeight());

    g2d.setComposite(oldComposit);
  }

  public class MessageBorder
    implements Border
  {
    private Image messageBottom = FreeUtil.getImage("message_bottom.png");
    private Image messageLeft = FreeUtil.getImage("message_left.png");
    private Image messageLeftCorner = FreeUtil.getImage("message_left_corner.png");
    private Image messageRight = FreeUtil.getImage("message_right.png");
    private Image messageRightCorner = FreeUtil.getImage("message_right_corner.png");

    public MessageBorder() {  } 
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) { g.drawImage(this.messageLeft, x, y, this.messageLeft.getWidth(null), c.getHeight() - this.messageLeftCorner.getHeight(null), null);
      g.drawImage(this.messageLeftCorner, x, y + c.getHeight() - this.messageLeftCorner.getHeight(null), null);
      g.drawImage(this.messageRight, x + c.getWidth() - this.messageRight.getWidth(null), y, this.messageRight.getWidth(null), c.getHeight() - this.messageRightCorner.getHeight(null), null);
      g.drawImage(this.messageRightCorner, x + c.getWidth() - this.messageRight.getWidth(null), y + c.getHeight() - this.messageRightCorner.getHeight(null), null);
      g.drawImage(this.messageBottom, x + this.messageLeft.getWidth(null), y + c.getHeight() - this.messageBottom.getHeight(null), MessageGlassPane.this.messageTitleIcon.getIconWidth(), this.messageBottom.getHeight(null), null); }

    public Insets getBorderInsets(Component c)
    {
      return new Insets(0, this.messageLeft.getWidth(null), this.messageBottom.getHeight(null), this.messageRight.getWidth(null));
    }

    public boolean isBorderOpaque() {
      return true;
    }
  }
}