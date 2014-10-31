package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Component;
 import java.awt.Cursor;
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Insets;
 import java.util.Hashtable;
 import javax.swing.Icon;
 import javax.swing.JComponent;
 import javax.swing.JPanel;
 import javax.swing.border.Border;
 import twaver.swing.TableLayout;
 
 public class FreeOutlookPane extends JPanel
 {
   private FreeOutlookHeader header = new FreeOutlookHeader()
   {
     public void setShrink(boolean shrinked)
     {
       super.setShrink(shrinked);
 
       FreeOutlookPane.this.shrinkChanged(shrinked);
     }
   };
 
   private TableLayout barPaneLayout = new TableLayout();
   private JPanel barPane = new JPanel(this.barPaneLayout);
   private JPanel split = new JPanel();
   private int splitWidth = 1;
   private Color splitColor = new Color(166, 172, 174);
   private JPanel additionalPane = new JPanel(new BorderLayout());
   private Hashtable<Component, Integer> componentLayoutRows = new Hashtable();
   private JPanel centerPane = new JPanel(new BorderLayout());
   private FreeOutlookSplitListener splitListener = new FreeOutlookSplitListener(this.header);
   private Color additionalPaneBackground = new Color(217, 218, 219);
   private FreeListPane shortcutPane = null;
 
   public FreeOutlookPane(FreeListPane shortcutPane) {
     this.shortcutPane = shortcutPane;
     init();
   }
 
   private void init()
   {
     this.split.setPreferredSize(new Dimension(this.splitWidth, 0));
     this.split.setOpaque(true);
     this.split.setBackground(this.splitColor);
     this.split.setCursor(Cursor.getPredefinedCursor(10));
     this.split.addMouseListener(this.splitListener);
     this.split.addMouseMotionListener(this.splitListener);
 
     this.additionalPane.setBackground(this.additionalPaneBackground);
     this.additionalPane.setPreferredSize(new Dimension(0, 0));
     this.additionalPane.setBorder(new Border()
     {
       public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
       {
         g.setColor(FreeUtil.OUTLOOK_SPLIT_COLOR);
         g.drawLine(0, 0, width, 0);
       }
 
       public Insets getBorderInsets(Component c) {
         return new Insets(1, 0, 0, 0);
       }
 
       public boolean isBorderOpaque() {
         return true;
       }
     });
     this.centerPane.add(this.barPane, "North");
     this.centerPane.add(this.additionalPane, "Center");
 
     this.barPaneLayout.insertColumn(0, -1.0D);
 
     setLayout(new BorderLayout());
     add(this.header, "North");
     add(this.centerPane, "Center");
     add(this.split, "East");
   }
 
   public FreeOutlookBar addBar(String title, Icon icon, Icon selectedIcon) {
     FreeOutlookBar bar = new FreeOutlookBar(this);
     bar.setSelected(false);
     bar.setTitle(title);
     bar.setIcon(icon);
     bar.setSelectedIcon(selectedIcon);
     int rowCount = this.barPaneLayout.getRow().length;
 
     this.barPaneLayout.insertRow(rowCount, -2.0D);
     this.barPane.add(bar, "0," + rowCount);
     this.componentLayoutRows.put(bar, Integer.valueOf(rowCount));
     rowCount++;
 
     this.barPaneLayout.insertRow(rowCount, -3.0D);
     this.barPane.add(bar.getContentComponent(), "0," + rowCount);
     this.componentLayoutRows.put(bar.getContentComponent(), Integer.valueOf(rowCount));
 
     return bar;
   }
 
   public void updateLayoutConstraint(Component component, boolean selected) {
     int rowIndex = ((Integer)this.componentLayoutRows.get(component)).intValue();
     double constraint = -1.0D;
     if (!selected) {
       constraint = -3.0D;
     }
     this.barPaneLayout.setRow(rowIndex, constraint);
   }
 
   public JComponent getAdditionalPane() {
     return this.additionalPane;
   }
 
   public void setAdditionalPaneVisible(boolean visible) {
     this.centerPane.remove(this.barPane);
     this.centerPane.remove(this.additionalPane);
     if (visible) {
       this.centerPane.add(this.barPane, "North");
       this.centerPane.add(this.additionalPane, "Center");
     } else {
       this.centerPane.add(this.barPane, "Center");
     }
   }
 
   public void closeAllBars() {
     for (int i = 0; i < this.barPane.getComponentCount(); i++) {
       Component c = this.barPane.getComponent(i);
       if ((c instanceof FreeOutlookBar)) {
         FreeOutlookBar bar = (FreeOutlookBar)c;
         if (bar.isSelected())
           bar.setSelected(false);
       }
     }
   }
 
   public FreeOutlookBar getSelectedBar()
   {
     for (int i = 0; i < this.barPane.getComponentCount(); i++) {
       Component c = this.barPane.getComponent(i);
       if ((c instanceof FreeOutlookBar)) {
         FreeOutlookBar bar = (FreeOutlookBar)c;
         if (bar.isSelected()) {
           return bar;
         }
       }
     }
     return null;
   }
 
   public FreeOutlookBar getBarByNetwork(FreeNetwork network) {
     for (int i = 0; i < this.barPane.getComponentCount(); i++) {
       Component c = this.barPane.getComponent(i);
       if ((c instanceof FreeOutlookBar)) {
         FreeOutlookBar bar = (FreeOutlookBar)c;
         if (bar.getNetwork() == network) {
           return bar;
         }
       }
     }
     return null;
   }
 
   public void setShrink(boolean shrinked) {
     this.header.setShrink(shrinked);
   }
 
   public boolean isShrinked() {
     return this.header.isShrinked();
   }
 
   private void shrinkChanged(boolean shrinked) {
     if (shrinked)
       this.split.setCursor(Cursor.getDefaultCursor());
     else {
       this.split.setCursor(Cursor.getPredefinedCursor(10));
     }
 
     for (int i = 0; i < this.barPane.getComponentCount(); i++) {
       Component c = this.barPane.getComponent(i);
       if ((c instanceof FreeOutlookBar)) {
         FreeOutlookBar bar = (FreeOutlookBar)c;
         bar.headerShrinkChanged(shrinked);
         FreeOutlookList list = bar.getList();
 
         list.firePropertyChange("layoutOrientation", true, false);
       }
     }
   }
 
   public FreeOutlookHeader getHeader() {
     return this.header;
   }
 
   public FreeListPane getShortcutPane() {
     return this.shortcutPane;
   }
 }
