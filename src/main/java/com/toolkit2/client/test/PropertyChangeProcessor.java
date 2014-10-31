 package com.toolkit2.client.test;
 
 import java.awt.Point;
 import java.beans.PropertyChangeEvent;
 import java.beans.PropertyChangeListener;
 import twaver.network.TNetwork;
 
 public class PropertyChangeProcessor
   implements PropertyChangeListener
 {
   TNetwork network;
 
   public PropertyChangeProcessor(TNetwork network)
   {
     this.network = network;
   }
 
   public void propertyChange(PropertyChangeEvent evt) {
     if (!(evt.getSource() instanceof StateNode)) {
       return;
     }
     StateNode node = (StateNode)evt.getSource();
     String name = evt.getPropertyName();
 
     if (name.equals("selected")) {
       if (node.containsAttachment("population"))
         node.removeAttachment("population");
       else {
         node.addAttachment("population");
       }
     }
 
     if ((name.equals("selected")) || (name.equals("location")))
     {
       Point location = node.getCenterLocation();
       Point center = this.network.getLogicalCenterPoint();
       if ((location.x > center.x) && (location.y < center.y)) {
         node.setAttachmentDirection(3);
       }
       else if ((location.x > center.x) && (location.y > center.y)) {
         node.setAttachmentDirection(1);
       }
       else if ((location.x < center.x) && (location.y < center.y)) {
         node.setAttachmentDirection(4);
       }
       else
         node.setAttachmentDirection(2);
     }
   }
 }