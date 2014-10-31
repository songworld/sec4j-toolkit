package com.toolkit2.client.frame.free;
 
 import java.awt.Dimension;
 import java.awt.Point;
 import java.awt.event.MouseEvent;
 import javax.swing.JComponent;
 import javax.swing.event.MouseInputAdapter;
 
 public class FreeListSplitListener extends MouseInputAdapter
 {
   protected Point lastPoint = null;
   protected FreeHeader header = null;
 
   public FreeListSplitListener(FreeHeader header) {
     this.header = header;
   }
 
   public void mousePressed(MouseEvent e)
   {
	   //Shrinked==>收缩
     if (!this.header.isShrinked())
       this.lastPoint = e.getPoint();
   }
 
   public void mouseReleased(MouseEvent e)
   {
     this.lastPoint = null;
   }
 
   public void mouseDragged(MouseEvent e)
   {
     if ((!this.header.isShrinked()) && 
       (this.lastPoint != null)) {
       JComponent parent = (JComponent)this.header.getParent();
       Dimension size = parent.getPreferredSize();
       Point thisPoint = e.getPoint();
       int xMovement = thisPoint.x - this.lastPoint.x;
       size.width -= xMovement;
       size.width = Math.max(size.width, 37);
       parent.setPreferredSize(size);
       this.header.revalidateParent();
     }
   }
 }