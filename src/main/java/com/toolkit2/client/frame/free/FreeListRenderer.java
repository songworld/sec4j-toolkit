package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Component;
 import java.awt.Dimension;
 import java.awt.Font;
 import javax.swing.BorderFactory;
 import javax.swing.JLabel;
 import javax.swing.JList;
 import javax.swing.JPanel;
 import javax.swing.OverlayLayout;
 import twaver.Element;
 import twaver.Group;
 import twaver.Node;
 import twaver.list.TListCellRenderer;
 
 public class FreeListRenderer extends TListCellRenderer
 {
   private JPanel itemRender = new JPanel(new BorderLayout());
   private int separatorHeight = 30;
   private JPanel separatorRender = new JPanel()
   {
     public Dimension getPreferredSize()
     {
       Dimension size = super.getPreferredSize();
       return new Dimension(size.width, FreeListRenderer.this.separatorHeight);
     }
   };
 
   private JLabel separatorLabel = new JLabel();
   private FreeSeparator separator = new FreeSeparator(0);
   private Color itemTextColor = FreeUtil.LIST_TEXT_COLOR;
   private Color separatorTextColor = Color.white;
   private Color itemSelectedBackground = new Color(199, 198, 200);
   private Color itemSelectedBorder = new Color(163, 163, 163);
   private Font separatorFont = FreeUtil.FONT_12_BOLD;
 
   public FreeListRenderer(FreeList list) {
     super(list);
 
     this.itemRender.setOpaque(false);
     this.itemRender.add(this, "Center");
     this.separatorRender.setLayout(new OverlayLayout(this.separatorRender));
 
     JPanel separatorHelpPane = new JPanel(new BorderLayout());
     separatorHelpPane.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
     separatorHelpPane.add(this.separator);
     separatorHelpPane.setOpaque(false);
     this.separatorRender.setOpaque(false);
     this.separatorLabel.setOpaque(true);
     this.separatorLabel.setBackground(FreeUtil.LIST_BACKGROUND);
     this.separatorLabel.setForeground(this.separatorTextColor);
     this.separatorLabel.setFont(this.separatorFont);
     this.separatorLabel.setVerticalAlignment(1);
     this.separatorLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
     JPanel labelHelpPane = new JPanel(new BorderLayout());
     labelHelpPane.setBorder(BorderFactory.createEmptyBorder(6, 15, 0, 0));
     labelHelpPane.add(this.separatorLabel, "West");
     labelHelpPane.setOpaque(false);
 
     this.separatorRender.add(labelHelpPane);
     this.separatorRender.add(separatorHelpPane);
   }
 
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
   {
     if ((value instanceof Group)) {
       String groupName = ((Group)value).getName();
       this.separatorLabel.setText(groupName);
       this.separatorRender.setToolTipText(groupName);
 
       if ((list.getParent() instanceof FreeListPane)) {
         FreeListPane pane = (FreeListPane)list.getParent();
         if (pane.isShrinked()) {
           this.separatorLabel.setText(" ");
           this.separatorLabel.setOpaque(false);
         } else {
           this.separatorLabel.setOpaque(true);
         }
       }
 
       return this.separatorRender;
     }
     super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
 
     setToolTipText(getText());
     if ((value instanceof Node)) {
       Node node = (Node)value;
       if (node.getToolTipText() != null) {
         String tooltip = "<html>" + getText() + "<br>" + node.getToolTipText() + "</html>";
         this.itemRender.setToolTipText(tooltip);
       }
     }
 
     if ((list.getParent() instanceof FreeListPane)) {
       FreeListPane pane = (FreeListPane)list.getParent();
       if (pane.isShrinked()) {
         setBorder(BorderFactory.createEmptyBorder(2, 7, 1, 2));
         setText("");
       } else {
         setBorder(BorderFactory.createEmptyBorder(2, 20, 1, 2));
       }
     }
     if (isSelected) {
       setBackground(this.itemSelectedBackground);
       this.itemRender.setBorder(BorderFactory.createLineBorder(this.itemSelectedBorder));
     } else {
       this.itemRender.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
     }
     setForeground(this.itemTextColor);
 
     if (((Element)value).getIconURL().equals("-")) {
       setIcon(FreeUtil.BLANK_ICON);
     }
     return this.itemRender;
   }
 }