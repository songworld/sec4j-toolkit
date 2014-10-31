package com.toolkit2.client.frame.mian;

import java.awt.BorderLayout;

import com.toolkit2.client.component2ex.SubForm;

import twaver.TDataBox;
import twaver.network.TNetwork;

public class CustomerHomePage extends SubForm
{
  private static CustomerHomePage instance = null;
  private boolean initialed = false;

  private CustomerHomePage() {
    setTitle(getTabTitle());
    setLayout(new BorderLayout());
  }

  public static synchronized CustomerHomePage getInstance() {
    if (instance == null) {
      instance = new CustomerHomePage();
    }
    return instance;
  }

  public void setData(TDataBox box) {
    this.initialed = true;
  }


  public static String getTabTitle() {
    return "ToolKit2";
  }

  public boolean isInitialed() {
    return this.initialed;
  }

	
  public TNetwork getNetWork() {
		// TODO Auto-generated method stub
		return null;
	}
}