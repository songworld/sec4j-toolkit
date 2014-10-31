package com.toolkit2.client.frame.free;
 
 import twaver.RotatableNode;
 
 public class FreeLink extends RotatableNode
 {
   private int direction = 3;
 
   public FreeLink() {
     init();
   }
 
   public FreeLink(Object id) {
     super(id);
     init();
   }
 
   private void init() {
     putBorderVisible(false);
     putLabelFont(FreeUtil.FONT_12_BOLD);
     putLabelColor(FreeUtil.DEFAULT_TEXT_COLOR);
     putLabelHighlightable(false);
     setDirection(this.direction);
   }
 
   public void setDirection(int direction) {
     if (direction == 3) {
       setImage(FreeUtil.getImageURL("arrow_right.png"));
     }
     if (direction == 7) {
       setImage(FreeUtil.getImageURL("arrow_left.png"));
     }
     if (direction == 1) {
       setImage(FreeUtil.getImageURL("arrow_up.png"));
     }
     if (direction == 5) {
       setImage(FreeUtil.getImageURL("arrow_down.png"));
     }
 
     if (direction == 2) {
       setImage(FreeUtil.getImageURL("arrow_right_up.png"));
     }
     if (direction == 4) {
       setImage(FreeUtil.getImageURL("arrow_right_down.png"));
     }
     if (direction == 8) {
       setImage(FreeUtil.getImageURL("arrow_left_up.png"));
     }
     if (direction == 6) {
       setImage(FreeUtil.getImageURL("arrow_left_down.png"));
     }
 
     this.direction = direction;
     updateUI();
   }
 }
