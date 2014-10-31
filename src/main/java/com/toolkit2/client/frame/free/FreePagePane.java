package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
import javax.swing.Icon;
 import javax.swing.JComponent;
 import javax.swing.JPanel;
 
 public class FreePagePane extends JPanel
 {
   private FreeToolBar toolbar = new FreeToolBar();
   private JPanel centerPane = new JPanel(new BorderLayout());
 
   public FreePagePane() {
     this(null);
   }
 
   public FreePagePane(JComponent contentComponent) {
     init(contentComponent);
   }
  public FreePagePane(JComponent contentComponent, Icon icon) {
    init(contentComponent, icon);
  }
  private void init(JComponent contentComponent, Icon icon)
  {
    setLayout(new BorderLayout());
    add(this.centerPane, "Center");
    this.centerPane.add(contentComponent, "Center");
  }
   private void init(JComponent contentComponent) {
     setLayout(new BorderLayout());
     add(this.toolbar, "North");
     add(this.centerPane, "Center");
     if (contentComponent != null)
       this.centerPane.add(contentComponent, "Center");
   }
 
   public JPanel getCenterPane()
   {
     return this.centerPane;
   }
 
   public FreeToolBar getToolBar() {
     return this.toolbar;
   }
 }
