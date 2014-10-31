package com.toolkit2.client.component2ex.button;

import java.util.StringTokenizer;
import javax.swing.JDialog;

import com.toolkit2.client.component2ex.ButtonPane;
import com.toolkit2.client.component2ex.SubForm;
public class ButtonPaneFactory {
	public static ButtonPane createStandardDetailUIButtonPane(SubForm parent)
	  {
	    ButtonPane pane = createButtonPane("help,print,refresh,edit,add,lookup|close");
	    return pane;
	  }

	  public static ButtonPane createStandardDetailUIButtonPaneWithNotice(SubForm parent) {
	    ButtonPane pane = createButtonPane("help,print,refresh,notice,edit,add,lookup|close");
	    return pane;
	  }

	  public static ButtonPane createStandardDetailUIButtonPaneWithoutAddButton(SubForm parent) {
	    return createButtonPane("help,print,refresh,edit,lookup|close");
	  }

	  public static ButtonPane createStandardDetailUIButtonPaneWithoutAddEditButton(SubForm parent) {
	    return createButtonPane("help,print,refresh,lookup|close");
	  }

	  public static ButtonPane createStandardDetailUIButtonPaneWithoutAddEditButtonNotLookup(SubForm parent) {
	    return createButtonPane("help,print,refresh|close");
	  }

	  public static ButtonPane createStandardDetailUIButtonPaneWithoutAddPrintButton(SubForm parent) {
	    return createButtonPane("help,refresh,edit,lookup|close");
	  }

	  public static ButtonPane createStandardDetailUIButtonPane(JDialog parent) {
	    return createButtonPane("help,print,refresh,edit,add|close");
	  }

	  public static ButtonPane createStandardDetailUIButtonPaneWithoutAddButton(JDialog parent) {
	    return createButtonPane("help,print,refresh,edit|close");
	  }

	  public static ButtonPane createStandardAddUIButtonPane() {
	    return createButtonPane("help|add,addCancel");
	  }

	  public static ButtonPane createStandardEditUIButtonPane() {
	    return createButtonPane("help|ok,addCancel");
	  }

	  public static ButtonPane createStandardEditWithoutCloseUIButtonPane() {
	    return createButtonPane("help|okWithoutClose,addCancel");
	  }

	  public static ButtonPane createButtonPane(String regex) {
	    StringTokenizer token = new StringTokenizer(regex, "|");
	    String leftStr = "";
	    String rightStr = "";
	    if (token.countTokens() > 0)
	    {
	      if (token.countTokens() == 1) {
	        rightStr = token.nextToken();
	      } else if (token.countTokens() == 2) {
	        if (token.hasMoreTokens()) {
	          leftStr = token.nextToken();
	        }
	        if (token.hasMoreTokens())
	          rightStr = token.nextToken();
	      }
	      else {
	        throw new IllegalArgumentException("bad button pane string:" + regex);
	      }
	    }

	    token = new StringTokenizer(leftStr, ",");
	    String[] leftButtonNames = new String[token.countTokens()];
	    int i = 0;
	    while (token.hasMoreTokens()) {
	      leftButtonNames[i] = token.nextToken();
	      i++;
	    }

	    token = new StringTokenizer(rightStr, ",");
	    String[] rightButtonNames = new String[token.countTokens()];
	    i = 0;
	    while (token.hasMoreTokens()) {
	      rightButtonNames[i] = token.nextToken();
	      i++;
	    }
	    return new ButtonPane(leftButtonNames, rightButtonNames);
	  }
}
