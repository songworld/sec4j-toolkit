package com.toolkit2.client.frame.free;
 
 import java.awt.Dimension;
 import java.awt.Point;
 import java.awt.event.MouseEvent;
 import javax.swing.JComponent;
 
 public class FreeOutlookSplitListener extends FreeListSplitListener
 {
   public FreeOutlookSplitListener(FreeOutlookHeader header)
   {
     super(header);
   }
 
   public void mouseDragged(MouseEvent e)
   {
     if ((!this.header.isShrinked()) && 
       (this.lastPoint != null)) {
       JComponent parent = (JComponent)this.header.getParent();
       Dimension size = parent.getPreferredSize();
       Point thisPoint = e.getPoint();
       int xMovement = thisPoint.x - this.lastPoint.x;
       size.width += xMovement;
     //  size.width = Math.max(size.width, 37);
       parent.setPreferredSize(size);
       this.header.revalidateParent();
     }
   }
 }
