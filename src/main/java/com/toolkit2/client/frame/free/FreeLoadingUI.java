package com.toolkit2.client.frame.free;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.lang.reflect.Method;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import twaver.TWaverUtil;

public class FreeLoadingUI extends JDialog
{
  private String imageURL = FreeUtil.getImageURL("loading.gif");
  private ImageIcon imageIcon = TWaverUtil.getImageIcon(this.imageURL);
  private int imageWidth = this.imageIcon.getIconWidth();
  private int imageHeight = this.imageIcon.getIconHeight();
  private static FreeLoadingUI instance = null;

  private FreeLoadingUI(Dialog parent) {
    super(parent);
    init();
  }

  private FreeLoadingUI(Frame parent) {
    super(parent);
    init();
  }

  private void init() {
    setModal(true);
    setUndecorated(true);
    try {
      Class clazz = Class.forName("com.sun.awt.AWTUtilities");
      if (clazz != null) {
        Method m = clazz.getDeclaredMethod("setWindowOpaque", new Class[] { Window.class, Boolean.TYPE });
        if (m != null)
          m.invoke(null, new Object[] { this, Boolean.valueOf(false) });
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    URL url = Class.class.getResource(this.imageURL);
    Icon icon = new ImageIcon(url);
    JLabel label = new JLabel(icon);
    getContentPane().add(label, "Center");
    setDefaultCloseOperation(2);
    setSize(this.imageWidth, this.imageHeight);
    TWaverUtil.centerWindow(this);
  }

  public static FreeLoadingUI showInstance(Dialog parent) {
    if ((instance != null) && (instance.isShowing())) {
      instance.dispose();
    }
    instance = new FreeLoadingUI(parent);
    instance.setVisible(true);
    return instance;
  }

  public static FreeLoadingUI createInstance(Frame parent) {
    if ((instance != null) && (instance.isShowing())) {
      instance.dispose();
    }
    instance = new FreeLoadingUI(parent);
    return instance;
  }
}