package com.toolkit2.client.frame.mian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.TreePath;

import com.toolkit2.Util.ClientUtil;

import twaver.Element;
import twaver.SubNetwork;
import twaver.TDataBox;
import twaver.VisibleFilter;
import twaver.network.TNetwork;
import twaver.tree.DataBoxNode;
import twaver.tree.ElementNode;
import twaver.tree.TTree;

public class CustomerMainTree extends TTree
{
  private static CustomerMainTree instance = null;

  private CustomerMainTree() {
    addVisibleFilter(new VisibleFilter()
    {
      public boolean isVisible(Element e) {
        if ((e instanceof SubNetwork)) {
          return true;
        }
        return false;
      }
    });
    getDataBox().setName("ToolKit2");
    addTreeNodeClickedActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if ((source instanceof DataBoxNode)) {
          CustomerMainTree.this.showHomepage();
        } else if ((source instanceof ElementNode)) {
          ElementNode node = (ElementNode)source;
          CustomerHomePage homePage = CustomerMainTree.this.showHomepage();
          homePage.getNetWork().setCurrentSubNetwork((SubNetwork)node.getElement());
          homePage.setTitle(((SubNetwork)node.getElement()).getName());
        }
      }
    });
  }

  public CustomerHomePage showHomepage() {
    return showHomepage(null);
  }

  public CustomerHomePage showHomepage(SubNetwork network) {
    CustomerHomePage homePage = CustomerHomePage.getInstance();
    if (!homePage.isInitialed()) {
      homePage.setData(getDataBox());
    }
    homePage.getNetWork().setCurrentSubNetwork(network);
    if (network == null)
      homePage.setTitle(CustomerHomePage.getTabTitle());
    else {
      homePage.setTitle(network.getName());
    }
    homePage.setVisible();
    return homePage;
  }

  public static synchronized CustomerMainTree getInstance() {
    if (instance == null) {
      instance = new CustomerMainTree();
    }
    return instance;
  }

  public void setData(byte[] b) {
    if ((b == null) || (b.length == 0))
      return;
    try
    {
      ClientUtil.setDataBoxByJarData(this.box, b);
    } catch (IOException e) {
      ClientUtil.showException(e);
    }
    Iterator it = getDataBox().getAllElementsReverse().iterator();
    while (it.hasNext()) {
      Element e = (Element)it.next();
      if (e.childrenSize() > 0) {
        Iterator iter = e.children();
        boolean module = false;
        while (iter.hasNext()) {
          Element element = (Element)iter.next();
          if ((element instanceof SubNetwork)) {
            e.setIcon("/com/toolkit2/client/images/module.gif");
            module = true;
            break;
          }
        }
        if (!module)
        	 e.setIcon("/com/toolkit2/client/images/leaf.png");
      }
      else {
    	  e.setIcon("/com/toolkit2/client/images/leaf.png");
      }
    }
  }

  protected boolean removeDescendantSelectedPaths(TreePath path, boolean includePath) {
    return false;
  }

  public boolean isCustom() {
    return getDataBox().getAllElements().size() != 0;
  }
}