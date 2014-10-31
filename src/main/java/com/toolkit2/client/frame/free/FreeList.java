package com.toolkit2.client.frame.free;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseEvent;
 import javax.swing.event.MouseInputAdapter;
 import javax.swing.event.MouseInputListener;
 import twaver.DataBoxSelectionModel;
 import twaver.Element;
 import twaver.Group;
 import twaver.TDataBox;
 import twaver.list.TList;
 
 public class FreeList extends TList
 {
   public FreeList()
   {
     init();
   }
 
   private void init() {
 setOpaque(false);
     setFont(FreeUtil.FONT_12_BOLD);
     setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
     setBackground(FreeUtil.LIST_BACKGROUND);
     setCellRenderer(new FreeListRenderer(this));
     setSelectionMode(0);
 
     MouseInputListener mouseListener = new MouseInputAdapter()
     {
       public void mouseMoved(MouseEvent e)
       {
         Element element = FreeList.this.getElementByPoint(e.getPoint());
         FreeList.this.getDataBox().getSelectionModel().clearSelection();
         if (element != null)
         {
           if (!(element instanceof Group))
             element.setSelected(true);
         }
       }
 
       public void mouseExited(MouseEvent e)
       {
         FreeList.this.getDataBox().getSelectionModel().clearSelection();
       }
 
       public void mouseClicked(MouseEvent e)
       {
         Element element = FreeList.this.getDataBox().getSelectionModel().lastElement();
         if ((element != null) && 
           (!(element instanceof Group))) {
           ActionListener action = (ActionListener)element.getUserObject();
           String command = (String)element.getBusinessObject();
 
           if (action != null) {
             ActionEvent event = new ActionEvent(element, 0, command);
             action.actionPerformed(event);
           }
         }
       }
     };
     addMouseMotionListener(mouseListener);
     addMouseListener(mouseListener);
 setDragEnabled(true);
   }
 }
