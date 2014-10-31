package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;
public class DeleteButton extends JButton
{
  public DeleteButton()
  {
    setToolTipText(Translator.getString("DeleteButton.Delete"));
    setText(Translator.getString("DeleteButton.Delete"));
    setIcon(FreeIconStore.DELETE_ICON);
    setMnemonic('R');
    Insets oldInsets = getMargin();
    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
  }

  public void onClick()
  {
  }
}
