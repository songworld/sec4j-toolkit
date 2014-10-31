package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class DeleteAllButton extends JButton
{
  public DeleteAllButton()
  {
	//设置提示信息
    setToolTipText(Translator.getString("DeleteAllButton.Delete"));
    //设置显示信息
    setText(Translator.getString("DeleteAllButton.Delete"));
    //设置图标
    setIcon(FreeIconStore.DELETEALL_ICON);
    //设置快捷键
    setMnemonic('L');
    //设置Margin
    Insets oldInsets = getMargin();
    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
  }

  public void onClick()
  {
  }
}