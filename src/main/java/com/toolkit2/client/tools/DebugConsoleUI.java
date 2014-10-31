package com.toolkit2.client.tools;

import com.toolkit2.client.component2ex.TextAreaOutputStream;
import com.toolkit2.client.tools.FreeIconStore;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.toolkit2.client.CacheManager;

import twaver.TWaverUtil;

@SuppressWarnings("serial")
public class DebugConsoleUI extends JFrame
{
  private static DebugConsoleUI instance = null;
  @SuppressWarnings("unused")
private PrintStream originalOut = null;
  @SuppressWarnings("unused")
private PrintStream originalErr = null;
  JTextArea txtConsole = new JTextArea();
  PrintStream newOut = new PrintStream(new TextAreaOutputStream(this.txtConsole));
  JButton btnCopy = new JButton("复制", FreeIconStore.COPY_ICON);
  JButton btnClear = new JButton("清空", FreeIconStore.DELETE_ICON);
  JPanel buttonPane = new JPanel(new FlowLayout(2, 0, 1));
 
  public static DebugConsoleUI getInstance()
  {
    if (instance == null) {
      instance = new DebugConsoleUI();
    }
    return instance;
  }

  private DebugConsoleUI() {
    setDefaultCloseOperation(1);
    setSize(900, 600);
    setTitle("调试控制台");
    this.txtConsole.setLineWrap(false);
    this.txtConsole.setWrapStyleWord(false);
    getContentPane().add(new JScrollPane(this.txtConsole), "Center");
    getContentPane().add(this.buttonPane, "South");
    buttonPane.setSize(0, 80);
    
    setIconImage(FreeIconStore.TOOLKIT_FRAME_ICON);
    this.buttonPane.add(this.btnCopy);
    this.buttonPane.add(this.btnClear);
    this.txtConsole.setEditable(false);
    this.txtConsole.setFont(new Font("微软雅黑", 1, 12));
    this.btnCopy.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        DebugConsoleUI.this.copy(DebugConsoleUI.this.txtConsole.getText());
      }
    });
    this.btnClear.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        DebugConsoleUI.this.txtConsole.setText("");
      }
    });
    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        DebugConsoleUI.this.originalOut = System.out;
        DebugConsoleUI.this.originalErr = System.err;
        System.setOut(DebugConsoleUI.this.newOut);
        System.setErr(DebugConsoleUI.this.newOut);
      }
    });
    TWaverUtil.centerWindow(this);

    this.txtConsole.setBackground(Color.white);
  }

  public void copy(String str) {
    StringSelection stsel = new StringSelection(str);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
  }

  public static void clear() {
    instance = null;
  }

  static
  {
    CacheManager.registerCache(DebugConsoleUI.class, "clear");
  }
}