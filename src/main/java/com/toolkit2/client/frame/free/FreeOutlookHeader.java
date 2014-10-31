package com.toolkit2.client.frame.free;
 
 import java.awt.event.ActionListener;
 import javax.swing.BorderFactory;
 import javax.swing.Icon;
 import javax.swing.ImageIcon;
 import javax.swing.JComponent;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.border.Border;
import twaver.swing.SingleFiledLayout;
 
 public class FreeOutlookHeader extends FreeHeader
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private SingleFiledLayout toolbarLayout = new SingleFiledLayout(1, 0, 2);
 
   private JPanel toolbar = new JPanel(this.toolbarLayout);
   private ImageIcon separatorIcon = FreeUtil.getImageIcon("toolbar_separator.png");
 
   public FreeOutlookHeader() {
     init();
   }
 
   private void init() {
     this.toolbar.setOpaque(false);
     this.toolbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
 
     add(this.toolbar, "Center");
   }
 
   public FreeToolbarButton addButton(Icon icon, String tooltip, ActionListener action, String command) {
     FreeToolbarButton button = new FreeToolbarButton();
//     //按下时图标设置，添加功能 By letian 
//     Icon pressedIcon = FreeUtil.createMovedIcon(icon);
     button.setBtnIcon(icon);
   //  button.setPressedIcon(pressedIcon);
     button.setToolTipText(tooltip);
     if (action != null) {
       button.addActionListener(action);
     }
     button.setActionCommand(command);
     this.toolbar.add(button);
     return button;
   }
 
   public void addSeparator() {
     this.toolbar.add(new JLabel(this.separatorIcon));
   }
 
   protected Object getResizeHandlerLayoutConstraint()
   {
     return "East";
   }
 
   protected Object getShrinkHandlerLayoutConstraint()
   {
     return "West";
   }
 
   protected FreeListSplitListener createSplitListener()
   {
     return new FreeOutlookSplitListener(this);
   }
 
   protected Border createBorder()
   {
     return BorderFactory.createEmptyBorder(4, 0, 0, 7);
   }
 
   protected ImageIcon getShrinkIcon(boolean shrinked)
   {
     if (shrinked) {
       return RIGHT_ARROW_ICON;
     }
     return LEFT_ARROW_ICON;
   }
 
   protected JComponent getCenterComponent()
   {
     return null;
   }
 
   public void setShrink(boolean shrinked)
   {
     super.setShrink(shrinked);
     this.toolbar.setVisible(!shrinked);
   }
 
   protected int getShrinkedWidth()
   {
     return 0;
   }
 
   public JPanel getToolBar() {
     return this.toolbar;
   }
 }
