package com.toolkit2.client.frame.free;
 import twaver.Element;
 import twaver.TDataBox;
 import twaver.VisibleFilter;
 import twaver.list.TList;
 
 public class FreeOutlookList extends TList
 {
   private FreeOutlookBar bar = null;
 
   public FreeOutlookList(FreeOutlookBar bar, TDataBox box) {
     super(box);
     this.bar = bar;
     init();
   }
 
   private void init() {
     setCellRenderer(new FreeOutlookListRenderer(this));
     setFont(FreeUtil.FONT_12_BOLD);
     setForeground(FreeUtil.OUTLOOK_TEXT_COLOR);
     setSelectionMode(0);
     addVisibleFilter(new VisibleFilter()
     {
       public boolean isVisible(Element element) {
         return element instanceof FreeNode;
       }
     });
   }
 
   public FreeOutlookBar getFreeOutlookBar() {
     return this.bar;
   }
 }
