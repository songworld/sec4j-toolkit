package com.toolkit2.client.frame.free;

import twaver.Node;

public class FreeModuleNode extends Node
{
	  private int labelYOffsetSingleLine = -30;
	  private int labelYOffsetMultiLine = -38;
	  private String defaultImage = FreeUtil.getImageURL("module_node.png");
	  private String moduleIcon = null;
	  private String networkName = null;
	  private String treeNodeName = null;

	  public FreeModuleNode() {
	    init();
	  }

	  public FreeModuleNode(Object id) {
	    super(id);
	    init();
	  }

	  private void init() {
	    setImage(this.defaultImage);
	    putBorderVisible(false);
	    //设置字体
	    putLabelFont(FreeUtil.FONT_12_PLAIN);
	    putLabelColor(FreeUtil.DEFAULT_TEXT_COLOR);
	    putLabelHighlightable(false);
	    putLabelYOffset(this.labelYOffsetSingleLine);
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

	  public String getTreeNodeName()
	  {
	    return this.treeNodeName;
	  }

	  public void setTreeNodeName(String treeNodeName) {
	    this.treeNodeName = treeNodeName;
	  }
	}