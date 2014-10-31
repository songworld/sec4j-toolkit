package com.toolkit2.client.component2ex.gantt;

import java.math.BigDecimal;

import com.toolkit2.client.i18n.Translator;

public final class WorkOrderUtil
{
  private static String module = "WorkOrderAccess";

  public static String getString(String key) {
    return Translator.getString(module, key);
  }

  public static String getString(String key, String arg) {
    return Translator.getString(module, key, arg);
  }

  public static String getString(String key, Object[] args) {
    return Translator.getString(module, key, args);
  }

  public static BigDecimal getHoursBySeq(BigDecimal amount) {
    return BigDecimal.ZERO.multiply(amount);
  }

  public static String getStringWithColon(String key) {
    return Translator.getStringWithColon(module, key);
  }
}