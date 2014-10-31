package com.toolkit2.client.component2ex.button;

import javax.swing.JButton;

import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class NoticeButton extends JButton {
	public NoticeButton()
	  {
	    setText(Translator.getString("NoticeButton.Notice"));
	    setIcon(FreeIconStore.NOTICE_ICON);
	    setToolTipText(Translator.getString("NoticeButton.Tooltip"));
	    setMnemonic('N');

	    setText("");
	  }

	  public void onClick()
	  {
//	    ClientUI ui = ClientUtil.getClientUIForComponent(this);
//	    NoticeAndEmailSendUI dlg = new NoticeAndEmailSendUI(ui);
//	    if ((ui instanceof NoticeableSetupUI)) {
//	      ((NoticeableSetupUI)ui).setupUI(dlg);
//	    }
//	    dlg.setVisible();
	  }

	  public static byte[] getPdfAttachment(SubForm parent)
	    throws Exception
	  {
//	    if ((parent instanceof PrintableDetailUI)) {
//	      PrintableDetailUI ui = (PrintableDetailUI)parent;
//	      PrintSettingPane settingPane = ui.getConcretSettingPane();
//	      PrintDetailSettingUI settingUI = new PrintDetailSettingUI(parent, true);
//	      settingUI.setPrintSettingPane(settingPane);
//	      settingUI.setVisible(true);
//	      if (settingUI.isPrintButtonClicked()) {
//	        TemplateGenerator generator = ui.getConcretGenerator();
//	        generator.generate(parent, true);
//	        return generator.getBytePdf();
//	      }
//	      return null;
//	    }

	    return null;
	  }
}
