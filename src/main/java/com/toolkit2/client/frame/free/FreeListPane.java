package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
 import java.awt.Component;
 import java.awt.Cursor;
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Insets;
 import javax.swing.JPanel;
 import javax.swing.border.Border;
 
 public class FreeListPane extends JPanel
 {
   private FreeList list = new FreeList();
   private JPanel split = new JPanel(new BorderLayout());
   private FreeHeader header = new FreeHeader()
   {
     public void setShrink(boolean shrinked)
     {
       super.setShrink(shrinked);
 
       if (shrinked)
         FreeListPane.this.split.setCursor(Cursor.getDefaultCursor());
       else
         FreeListPane.this.split.setCursor(Cursor.getPredefinedCursor(10));
     }
   };
 
   private FreeListSplitListener splitListener = new FreeListSplitListener(this.header);
 
   public FreeListPane() {
     init();
   }
 
   private void init() {
     setLayout(new BorderLayout());
 
     JPanel rightInsetPane = new JPanel();
     rightInsetPane.setPreferredSize(new Dimension(2, 0));
     rightInsetPane.setBackground(FreeUtil.LIST_BACKGROUND);
     add(rightInsetPane, "East");
     add(this.header, "North");
 
     this.split.setBorder(new Border()
     {
       public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
         g.setColor(FreeUtil.LIST_SPLIT_COLOR);
         g.drawLine(x, y, x, y + height);
       }
 
       public Insets getBorderInsets(Component c) {
         return new Insets(0, 1, 0, 0);
       }
 
       public boolean isBorderOpaque() {
         return true;
       }
     });
     this.split.setOpaque(true);
     this.split.setPreferredSize(new Dimension(4, 0));
     this.split.setBackground(FreeUtil.LIST_BACKGROUND);
     this.split.setCursor(Cursor.getPredefinedCursor(10));
     this.split.addMouseListener(this.splitListener);
     this.split.addMouseMotionListener(this.splitListener);
 
     add(this.split, "West");
     add(this.list, "Center");
   }
 
   public FreeList getList() {
     return this.list;
   }
 
   public void setTitle(String title) {
     this.header.setTitle(title);
   }
 
   public String getTitle() {
     return this.header.getTitle();
   }
 
   public void setShrink(boolean shrinked) {
     this.header.setShrink(shrinked);
   }
 
   public boolean isShrinked() {
     return this.header.isShrinked();
   }
 }