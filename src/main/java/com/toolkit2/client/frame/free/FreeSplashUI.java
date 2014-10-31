package com.toolkit2.client.frame.free;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Date;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.toolkit2.Util.ClientContext;
import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.client.frame.common.VersionInformation;

import twaver.TWaverUtil;
/*****************************************************************************************************
 * 系统闪屏
 * 		每个应用程序启动是加载程序时显示的界面
 * 
 * ****/
public class FreeSplashUI extends JWindow
{
  private static final FreeSplashUI instance = new FreeSplashUI();
  private SplashLabel lbImage = new SplashLabel();
  private ImageIcon icon = FreeUtil.getImageIcon("FreeSplash_" + ClientContext.getLocale() + ".png");

  public static FreeSplashUI getInstance() {
    return instance;
  }

  private FreeSplashUI() {
    add(this.lbImage, "Center");
    pack();
    TWaverUtil.centerWindow(this);
  }

  private String getVersion() {
    return VersionInformation.getVersionString();
  }

  public void setMessage(String message)
  {
    this.lbImage.setText(message);
  }

  public static void main(String[] s) {
    getInstance().setVisible(true);
  }

  public void setIcon(ImageIcon icon) {
    this.icon = icon;
  }

  public class SplashLabel extends JLabel
  {
    private Map desktopHints = null;
  

    public SplashLabel()
    {
      Toolkit tk = Toolkit.getDefaultToolkit();
      this.desktopHints = ((Map)tk.getDesktopProperty("awt.font.desktophints"));
    }

    public void paint(Graphics g) {
      super.paint(g);
      FreeSplashUI.this.icon.paintIcon(this, g, 0, 0);
      Graphics2D g2d = (Graphics2D)g;
      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      if (this.desktopHints != null) {
        g2d.addRenderingHints(this.desktopHints);
      }
      g.setColor(Color.white);
      try {
        g.setFont(FreeUtil.getFont14Plain());
      } catch (Exception e) {
        e.printStackTrace();
      }
      g.drawString(GeneralUtil.getString("PingTask.v") + FreeSplashUI.this.getVersion(), 30, FreeSplashUI.this.icon.getIconHeight() / 2 - 15);
      g.drawString(getText(), 30, FreeSplashUI.this.icon.getIconHeight() / 2);
      g.setFont(new Font("微软雅黑", 0, 12));
      String[] texts = "基于插件模式的应用系统框架\n实现前端统计UI设计\n此工具设计为BS开发辅助工具".split("\n");
      for (int i = 0; i < texts.length; i++) {
        g.drawString(texts[i], 30, FreeSplashUI.this.icon.getIconHeight() / 2 + 14 * i + 20);
      }

      g.setFont(new Font("Calibri", 0, 17));
      g.drawString("© ", FreeSplashUI.this.icon.getIconWidth() - 108, FreeSplashUI.this.icon.getIconHeight() - 12);
      g.setFont(new Font("Calibri", 0, 13));
      g.drawString(ClientUtil.getYearLocalString(new Date()) + " ToolKit2", FreeSplashUI.this.icon.getIconWidth() - 90, FreeSplashUI.this.icon.getIconHeight() - 15);
    }

    public Dimension getPreferredSize() {
      return new Dimension(FreeSplashUI.this.icon.getIconWidth(), FreeSplashUI.this.icon.getIconHeight());
    }
  }
}