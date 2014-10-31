package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

 import javax.swing.BorderFactory;
import javax.swing.JComponent;
 import javax.swing.JMenuItem;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
 
 public class FreeMenuItem extends JMenuItem
 {
   private Color backgroundColor = FreeUtil.MENUITEM_BACKGROUND;
   private Color foregroundColor = FreeUtil.DEFAULT_TEXT_COLOR;
   private int borderThickness = 1;
   private Border border = BorderFactory.createLineBorder(this.backgroundColor, this.borderThickness);
   private int preferredHeight = 23;
 
   public FreeMenuItem() {
     init();
   }
 
   public FreeMenuItem(String text) {
     super(text);
     init();
   }
 
   private void init() {
     setForeground(this.foregroundColor);
     setFont(FreeUtil.FONT_14_BOLD);
     setBackground(this.backgroundColor);
     setBorder(this.border);
    addMouseListener(new MouseAdapter()
    {
      public void mouseExited(MouseEvent e)
      {
      }

      public void mouseDragged(MouseEvent e)
      {
        JComponent c = (JComponent)e.getSource();
        TransferHandler handler = c.getTransferHandler();
        handler.exportAsDrag(c, e, 1);
      }
    });
  }
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.preferredHeight);
   }
 }
