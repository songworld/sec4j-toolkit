package com.toolkit2.client.frame.free;
 
 import java.awt.Dimension;
 import java.awt.GridLayout;
 import java.awt.Point;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseListener;
 import javax.swing.JPanel;
 import twaver.TWaverUtil;
 
 public class FreeNodeButtonAttachment extends JPanel
 {
   private FreeToolbarButton button1 = createButton();
   private FreeToolbarButton button2 = createButton();
   private FreeToolbarButton button3 = createButton();
   private FreeNode node = null;
 
   public FreeNodeButtonAttachment(FreeNode node) {
     this.node = node;
     init();
   }
 
   private void init() {
     setLayout(new GridLayout(3, 1, 0, 12));
     setOpaque(false);
     add(this.button1);
     add(this.button2);
     add(this.button3);
 
     this.button1.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ActionListener listener = FreeNodeButtonAttachment.this.node.getButtonAction1();
         if (listener != null) {
           ActionEvent event = FreeNodeButtonAttachment.this.createActionEvent(e, FreeNodeButtonAttachment.this.node.getActionCommand1());
           listener.actionPerformed(event);
         }
       }
     });
     this.button2.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ActionListener listener = FreeNodeButtonAttachment.this.node.getButtonAction2();
         if (listener != null) {
           ActionEvent event = FreeNodeButtonAttachment.this.createActionEvent(e, FreeNodeButtonAttachment.this.node.getActionCommand2());
           listener.actionPerformed(event);
         }
       }
     });
     this.button3.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ActionListener listener = FreeNodeButtonAttachment.this.node.getButtonAction3();
         if (listener != null) {
           ActionEvent event = FreeNodeButtonAttachment.this.createActionEvent(e, FreeNodeButtonAttachment.this.node.getActionCommand3());
           listener.actionPerformed(event);
         }
       }
     });
     MouseListener mouseListener = new MouseAdapter()
     {
       public void mousePressed(MouseEvent e)
       {
         FreeNodeButtonAttachment.this.node.setSelected(true);
       }
     };
     addMouseListener(mouseListener);
     this.button1.addMouseListener(mouseListener);
     this.button2.addMouseListener(mouseListener);
     this.button3.addMouseListener(mouseListener);
   }
 
   private FreeToolbarButton createButton() {
     FreeToolbarButton button = new FreeToolbarButton();
     button.setOpaque(false);
     return button;
   }
 
   public void updateBounds() {
     int width = getPreferredSize().width;
     int height = getPreferredSize().height;
     int x = this.node.getLocation().x + this.node.getWidth() - width - 3;
     int y = this.node.getLocation().y + 6;
     setBounds(x, y, width, height);
   }
 
   public void updateProperties() {
     this.button1.setIcon(TWaverUtil.getImageIcon(this.node.getButtonIcon1()));
     this.button1.setToolTipText(this.node.getButtonTooltip1());
     this.button2.setIcon(TWaverUtil.getImageIcon(this.node.getButtonIcon2()));
     this.button2.setToolTipText(this.node.getButtonTooltip2());
     this.button3.setIcon(TWaverUtil.getImageIcon(this.node.getButtonIcon3()));
     this.button3.setToolTipText(this.node.getButtonTooltip3());
   }
 
   private ActionEvent createActionEvent(ActionEvent e, String command) {
     return new ActionEvent(e.getSource(), e.getID(), command, e.getWhen(), e.getModifiers());
   }
 }
