package com.toolkit2.client.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class VO
  implements Serializable
{
  private Map customProperties;
  private Map clientProperties = new HashMap();

  public Map getCustomProperties() {
    if (this.customProperties == null)
      this.customProperties = new HashMap();
    return this.customProperties;
  }

  public void setCustomProperties(Map customProperties) {
    this.customProperties = customProperties;
  }

  public Object getValueOfCustomField(String name) {
    return getCustomProperties().get(name);
  }

  public void setValueOfCustomField(String name, Object value) {
    getCustomProperties().put(name, value);
  }

  public String uniqueStr() {
    String className = getClass().getName();
    return className.substring(className.lastIndexOf('.') + 1);
  }

  public Map getClientProperties() {
    return this.clientProperties;
  }

  public void setClientProperties(Map clientProperties) {
    this.clientProperties = clientProperties;
  }
}