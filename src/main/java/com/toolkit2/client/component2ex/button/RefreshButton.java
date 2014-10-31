package com.toolkit2.client.component2ex.button;

import java.awt.Insets;

import javax.swing.JButton;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.component2ex.CommonUI;
import com.toolkit2.client.i18n.Translator;
import com.toolkit2.client.tools.FreeIconStore;

public class RefreshButton extends JButton {
	private boolean createNewDetailUI = true;

	  public RefreshButton() {
	    this("true");
	  }

	  public RefreshButton(String createNewDetailUI) {
	    setToolTipText(Translator.getString("RefreshButton.Refresh"));

	    setIcon(FreeIconStore.REFRESH_ICON);
	    setMnemonic('R');
	    Insets oldInsets = getMargin();
	    setMargin(new Insets(oldInsets.top, oldInsets.top, oldInsets.bottom, oldInsets.bottom));
	    if ("false".equalsIgnoreCase(createNewDetailUI))
	      this.createNewDetailUI = false;
	  }

	  public void onClick()
	  {
//	    final ClientUI clientUI = ClientUtil.getClientUIForComponent(this);
//	    if ((clientUI != null) && ((clientUI instanceof DetailUI))) {
//	      setEnabled(false);
//	      final DetailUI oldUI = (DetailUI)clientUI;
//	      AbstractDetailThread thread = new AbstractDetailThread(clientUI)
//	      {
//	        public void actionPerformed(Object vo) throws Exception {
//	          setVO(clientUI, vo);
//	        }
//
//	        public Object queryFromServer() throws ServerActionException {
//	          return oldUI.retrieveVO(null);
//	        }
//
//	        protected void cancelPerformed() {
//	          RefreshButton.this.setEnabled(true);
//	        }
//
//	        private void setVO(ClientUI oldUI, Object vo) throws Exception {
//	          if (vo != null)
//	            try {
//	              if (RefreshButton.this.createNewDetailUI) {
//	                Class clazz = oldUI.getClass();
//	                ClientUI newUI = null;
//	                newUI = (ClientUI)clazz.newInstance();
//	                Method m = clazz.getMethod("setVO", new Class[] { vo.getClass() });
//	                m.invoke(newUI, new Object[] { vo });
//
//	                Window win = ClientUtil.getWindowForComponent(oldUI);
//	                if ((win instanceof DetachUI)) {
//	                  DetachUI detachUI = (DetachUI)win;
//	                  detachUI.setClientUI(newUI);
//	                } else {
//	                  AbstractMainUI.getInstance().showTab(newUI, oldUI);
//	                }
//	              } else {
//	                Method m = oldUI.getClass().getMethod("setVO", new Class[] { vo.getClass() });
//	                if (m != null)
//	                  m.invoke(oldUI, new Object[] { vo });
//	              }
//	            }
//	            catch (Exception e) {
//	              throw e;
//	            } finally {
//	              RefreshButton.this.setEnabled(true);
//	              synchronized (clientUI) {
//	                clientUI.notifyAll();
//	              }
//	            }
//	          else
//	            CommonUI.showNoData(clientUI);
//	        }
//	      };
//	      thread.execute();
//	    }
	  }
}
