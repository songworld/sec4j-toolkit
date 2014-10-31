package com.toolkit2.client.component2ex;

import java.awt.Color;

import javax.swing.JTable;

import com.toolkit2.client.frame.free.FreeUtil;

public abstract class QuickLinkable {
	public static final Color FOCUS_COLOR = FreeUtil.HighLighter.darker();

	  public abstract boolean isQuickLinkable(JTable paramJTable, int paramInt1, int paramInt2);
}
