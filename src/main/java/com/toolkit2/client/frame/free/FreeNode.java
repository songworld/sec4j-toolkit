package com.toolkit2.client.frame.free;
 import java.awt.event.ActionListener;
 import twaver.Node;
 import twaver.TDataBox;
 
 public class FreeNode extends Node
 {
   private int labelYOffsetSingleLine = -30;
   private int labelYOffsetMultiLine = -38;
   private int labelXOffset = -12;
   private String defaultImage = FreeUtil.getImageURL("module_node.png");
   private String moduleIcon = null;
   private String buttonIcon1 = null;
   private String buttonIcon2 = null;
   private String buttonIcon3 = null;
   private String buttonTooltip1 = null;
   private String buttonTooltip2 = null;
   private String buttonTooltip3 = null;
   private ActionListener action = null;
   private ActionListener buttonAction1 = null;
   private ActionListener buttonAction2 = null;
   private ActionListener buttonAction3 = null;
   private String actionCommand1 = null;
   private String actionCommand2 = null;
   private String actionCommand3 = null;
   private FreeNodeButtonAttachment buttonAttachment = new FreeNodeButtonAttachment(this);
   private String networkName = null;
   private TDataBox shortcutData = null;
 
   public FreeNode() {
     init();
   }
 
   public FreeNode(Object id) {
     super(id);
     init();
   }
 
   public FreeNodeButtonAttachment getButtonAttachment() {
     return this.buttonAttachment;
   }
 
   private void init() {
     setImage(this.defaultImage);
     putBorderVisible(false);
     putLabelFont(FreeUtil.FONT_12_BOLD);
     putLabelColor(FreeUtil.DEFAULT_TEXT_COLOR);
     putLabelHighlightable(false);
     putLabelYOffset(this.labelYOffsetSingleLine);
     putLabelXOffset(this.labelXOffset);
   }
 
   public String getUIClassID()
   {
     return FreeNodeUI.class.getName();
   }
 
   public String getModuleIcon() {
     return this.moduleIcon;
   }
 
   public void setModuleIcon(String moduleIcon) {
     this.moduleIcon = moduleIcon;
     updateUI();
     updateComponentAttachment();
   }
 
   public String getButtonIcon1() {
     return this.buttonIcon1;
   }
 
   public void setButtonIcon1(String buttonIcon1) {
     this.buttonIcon1 = buttonIcon1;
     updateUI();
     updateComponentAttachment();
   }
 
   public String getButtonIcon3() {
     return this.buttonIcon3;
   }
 
   public void setButtonIcon3(String buttonIcon3) {
     this.buttonIcon3 = buttonIcon3;
     updateUI();
     updateComponentAttachment();
   }
 
   public String getButtonIcon2() {
     return this.buttonIcon2;
   }
 
   public void setButtonIcon2(String buttonIcon2) {
     this.buttonIcon2 = buttonIcon2;
     updateUI();
     updateComponentAttachment();
   }
 
   private void updateComponentAttachment() {
     this.buttonAttachment.updateProperties();
   }
 
   public String getButtonTooltip1() {
     return this.buttonTooltip1;
   }
 
   public void setButtonTooltip1(String buttonTooltip1) {
     this.buttonTooltip1 = buttonTooltip1;
     updateComponentAttachment();
   }
 
   public String getButtonTooltip2() {
     return this.buttonTooltip2;
   }
 
   public void setButtonTooltip2(String buttonTooltip2) {
     this.buttonTooltip2 = buttonTooltip2;
     updateComponentAttachment();
   }
 
   public String getButtonTooltip3() {
     return this.buttonTooltip3;
   }
 
   public void setButtonTooltip3(String buttonTooltip3) {
     this.buttonTooltip3 = buttonTooltip3;
     updateComponentAttachment();
   }
 
   public ActionListener getButtonAction1() {
     return this.buttonAction1;
   }
 
   public void setButtonAction1(ActionListener buttonAction1) {
     this.buttonAction1 = buttonAction1;
   }
 
   public ActionListener getButtonAction2() {
     return this.buttonAction2;
   }
 
   public void setButtonAction2(ActionListener buttonAction2) {
     this.buttonAction2 = buttonAction2;
   }
 
   public ActionListener getButtonAction3() {
     return this.buttonAction3;
   }
 
   public void setButtonAction3(ActionListener buttonAction3) {
     this.buttonAction3 = buttonAction3;
   }
 
   public String getNetworkName() {
     return this.networkName;
   }
 
   public void setNetworkName(String networkName) {
     this.networkName = networkName;
     if ((networkName != null) && (networkName.contains("<br>")))
       putLabelYOffset(this.labelYOffsetMultiLine);
     else
       putLabelYOffset(this.labelYOffsetSingleLine);
   }
 
   public String getActionCommand1()
   {
     return this.actionCommand1;
   }
 
   public void setActionCommand1(String actionCommand1) {
     this.actionCommand1 = actionCommand1;
   }
 
   public String getActionCommand2() {
     return this.actionCommand2;
   }
 
   public void setActionCommand2(String actionCommand2) {
     this.actionCommand2 = actionCommand2;
   }
 
   public String getActionCommand3() {
     return this.actionCommand3;
   }
 
   public void setActionCommand3(String actionCommand3) {
     this.actionCommand3 = actionCommand3;
   }
 
   public ActionListener getAction() {
     return this.action;
   }
 
   public void setAction(ActionListener action) {
     this.action = action;
   }
 
   public boolean isShortcutLoaded() {
     return this.shortcutData == null;
   }
 
   public TDataBox getShortcuts() {
     return this.shortcutData;
   }
 
   public void setShortcuts(TDataBox shortcutData) {
     this.shortcutData = shortcutData;
   }
 }
