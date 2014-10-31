package com.toolkit2.client.component2ex;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class OrderBy
  implements Serializable
{
  public static final String ASC = "asc";
  public static final String DESC = "desc";
  private LinkedHashMap orderBy = null;

  public void setOrderBy(LinkedHashMap orderBy)
  {
    this.orderBy = orderBy;
  }

  public LinkedHashMap getOrderBy() {
    return this.orderBy;
  }
}