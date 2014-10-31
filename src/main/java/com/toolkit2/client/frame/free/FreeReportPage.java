package com.toolkit2.client.frame.free;
 import java.awt.Color;
 import java.awt.Dimension;
 import javax.swing.BorderFactory;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JScrollPane;
 import javax.swing.JTable;
 import javax.swing.JTextField;
 import javax.swing.border.Border;
 
 public class FreeReportPage extends FreePagePane
 {
   private FreeTable table = new FreeTable();
   private JScrollPane scroll = new JScrollPane(this.table);
   private int descriptionHeight = 25;
   private JTextField lbDescription = new JTextField()
   {
     public Dimension getPreferredSize()
     {
       Dimension size = super.getPreferredSize();
       return new Dimension(size.width, FreeReportPage.this.descriptionHeight);
     }
   };
 
   private Border descriptionBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
   private Color descriptionTextColor = new Color(120, 123, 154);
   private Color descriptionBackgroundColor = new Color(226, 228, 229);
 
   public FreeReportPage() {
     init();
   }
 
   private void init() {
     this.scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
     getCenterPane().add(this.scroll, "Center");
     getCenterPane().add(this.lbDescription, "North");
 
     JLabel lbCorner = new JLabel();
     lbCorner.setOpaque(true);
     lbCorner.setBackground(this.descriptionBackgroundColor);
     this.scroll.setCorner("UPPER_RIGHT_CORNER", lbCorner);
 
     this.lbDescription.setForeground(this.descriptionTextColor);
     this.lbDescription.setBackground(this.descriptionBackgroundColor);
     this.lbDescription.setOpaque(true);
     this.lbDescription.setBorder(this.descriptionBorder);
     this.lbDescription.setEditable(false);
     this.lbDescription.setFont(FreeUtil.FONT_12_BOLD);
   }
 
   public void setDescription(String description) {
	  // System.out.println("FreeReportPage.java Println:"+description);
     this.lbDescription.setText(description);
   }
 
   public JTable getTable() {
     return this.table;
   }
 }
