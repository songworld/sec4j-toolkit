package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Component;
 import javax.swing.BorderFactory;
 import javax.swing.JList;
 import javax.swing.border.Border;
 import twaver.list.TListCellRenderer;
 
 public class FreeOutlookListRenderer extends TListCellRenderer
 {
   private Color selectedColor = new Color(253, 192, 47);
   private Border normalBorder = BorderFactory.createEmptyBorder(3, 19, 3, 2);
   private Border shrinkedBorder = BorderFactory.createEmptyBorder(3, 2, 3, 2);
 
   public FreeOutlookListRenderer(FreeOutlookList list) {
     super(list);
   }
 
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
   {
     super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
 
     setToolTipText(getText());
     FreeOutlookList outlookList = (FreeOutlookList)list;
     if (outlookList.getFreeOutlookBar().getFreeOutlookPane().isShrinked()) {
       setBorder(this.shrinkedBorder);
       setText(null);
       setHorizontalAlignment(0);
       setIconTextGap(0);
     } else {
       setBorder(this.normalBorder);
       setHorizontalAlignment(10);
       setIconTextGap(5);
     }
 
     if (isSelected) {
       setBackground(this.selectedColor);
     }
     return this;
   }
 }
