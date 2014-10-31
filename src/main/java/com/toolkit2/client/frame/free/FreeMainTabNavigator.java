package com.toolkit2.client.frame.free;

import java.awt.Component;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.component2ex.SubForm;
import com.toolkit2.client.component2ex.WaitingUI;

public class FreeMainTabNavigator {
	private Vector<Component> pages = new Vector();
	private int index = -1;
	//Adjusting==>调整
	private boolean isAdjusting = false;

	public FreeMainTabNavigator(final FreeTabbedPane tab) {
	    tab.addChangeListener(new ChangeListener()
	    {
	      public void stateChanged(ChangeEvent e) {
	        if (!FreeMainTabNavigator.this.isAdjusting) {
	        	SubForm ui = (SubForm)tab.getSelectedComponent();
	          if (ui != null) {
	        	  FreeMainTabNavigator.this.addPage(ui);
	          }
	          else
	        	  FreeMainTabNavigator.this.clear();
	        }
	      }
	    });
	    tab.addContainerListener(new ContainerAdapter()
	    {
	      public void componentAdded(ContainerEvent e) {
	    	  FreeMainTabNavigator.this.checkAndRemove();
	      }

	      public void componentRemoved(ContainerEvent e) {
	    	  FreeMainTabNavigator.this.checkAndRemove();
	      }
	    });
	  }

	  private void clear() {
	    this.pages.clear();
	    this.index = -1;
	  }

	  private void addPage(SubForm ui)
	  {
	    while (this.index < this.pages.size() - 1) {
	      this.pages.remove(this.pages.size() - 1);
	    }

	    if ((this.pages.size() == 0) || (ui != this.pages.lastElement())) {
	      this.pages.addElement(ui);
	    }
	    checkAndRemove();
	    this.index = (this.pages.size() - 1);
	  }

	  private void checkAndRemove()
	  {
	    if (Shell.isInstanced()) {
	    	FreeTabbedPane tab = Shell.getInstance().getTabbedPane();
	      Iterator it = this.pages.iterator();
	      SubForm lastUI = null;
	      while (it.hasNext()) {
	    	  SubForm ui = (SubForm)it.next();

	        if (tab.indexOfComponent(ui) == -1) {
	          it.remove();
	        }
	        else if ((lastUI != null) && (ui == lastUI))
	          it.remove();
	        else
	          lastUI = ui;
	      }
	    }
	  }
	
	public void back() {
//		System.out.println("FreeMainTabNavigator.java back event execute！");
//		 WaitingUI.getInstance().setHeadTitle(GeneralUtil.getString("WaitingUI.Header"));
//		 WaitingUI.getInstance().setVisible(true);
		if (this.index > 0) {
			this.index -= 1;
			this.isAdjusting = true;
			if (this.index < this.pages.size() - 1) {
				Shell.getInstance().showTab(
						(SubForm) this.pages.get(this.index));
			}
			this.isAdjusting = false;
		}
//		WaitingUI.getInstance().setVisible(false);
	}

	public void next() {
//		System.out.println("FreeMainTabNavigator.java next event execute！");
//		WaitingUI.getInstance().setHeadTitle(GeneralUtil.getString("WaitingUI.Header"));
//		 WaitingUI.getInstance().setVisible(true);
		if (this.index < this.pages.size() - 1) {
			this.index += 1;
			this.isAdjusting = true;
			Shell.getInstance().showTab((SubForm) this.pages.get(this.index));
			this.isAdjusting = false;
		}
//		WaitingUI.getInstance().setVisible(false);
	}
}
