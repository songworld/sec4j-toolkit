package com.toolkit2.Util;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;
/*****************************************************************************************
 * CS界面字体样式管理
 * 
 * ***/
public class FontStyle
{
  public static final String KEY_EN_FONT_NAME = "PREFERENCES_EN_FONT_NAME";
  public static final String KEY_EN_FONT_SIZE = "PREFERENCES_EN_FONT_SIZE";
  public static final String KEY_NATIVE_FONT_NAME = "PREFERENCES_NATIVE_FONT_NAME";
  public static final String KEY_NATIVE_FONT_SIZE = "PREFERENCES_NATIVE_FONT_SIZE";
  public static final String DEFAULT_EN_FONT_NAME = "Dialog";
  public static final String DEFAULT_NATIVE_FONT_NAME = "Dialog";
  public static final int DEFAULT_EN_FONT_SIZE = 11;
  public static final int DEFAULT_NATIVE_FONT_SIZE = 12;
  public static final int DEFAULT_LARGE_FONT_SIZE = 30;
  public static final int DEFAULT_POS_FONT_SIZE = 30;
  public static final int POS_CHECK_FONT_SIZE = 16;
  private String nativeFontName = "Dialog";
  private String englishFontName = "Dialog";
  private int englishFontSize = 11;
  private int nativeFontSize = 12;
  private int englishHighlightStyle = 1;
  private int nativeHighlightStyle = 0;
  private Font largeFont = new Font("Dialog", 1, 30);
  private static FontStyle instance = new FontStyle();

  public static FontStyle getInstance() {
    return instance;
  }

  private FontStyle()
  {
    String sEnFontName = ClientUtil.readClientProperty("PREFERENCES_EN_FONT_NAME");
    if (sEnFontName != null) {
      this.englishFontName = sEnFontName;
    }
    String sNativeFontName = ClientUtil.readClientProperty("PREFERENCES_NATIVE_FONT_NAME");
    if (sNativeFontName != null) {
      this.nativeFontName = sNativeFontName;
    }
    String sFontEnSize = ClientUtil.readClientProperty("PREFERENCES_EN_FONT_SIZE");
    if (sFontEnSize != null) {
      int size = Integer.valueOf(sFontEnSize).intValue();
      this.englishFontSize = size;
    }
    String sFontNativeSize = ClientUtil.readClientProperty("PREFERENCES_NATIVE_FONT_SIZE");
    if (sFontNativeSize != null) {
      int size = Integer.valueOf(sFontNativeSize).intValue();
      this.nativeFontSize = size;
    }
  }

  public void setNativeFontName(String nativeFontName) {
    this.nativeFontName = nativeFontName;
  }

  public void setEnglishFontName(String englishFontName) {
    this.englishFontName = englishFontName;
  }

  public void setEnglishFontSize(int englishFontSize) {
    this.englishFontSize = englishFontSize;
  }

  public void setNativeFontSize(int nativeFontSize) {
    this.nativeFontSize = nativeFontSize;
  }

  public void setEnglishHighlightStyle(int highlightStyle) {
    this.englishHighlightStyle = highlightStyle;
  }

  public void setNativeHighlightStyle(int nativeHighlightStyle) {
    this.nativeHighlightStyle = nativeHighlightStyle;
  }

  public String getNativeFontName() {
    return this.nativeFontName;
  }

  public String getEnglishFontName() {
    return this.englishFontName;
  }

  public int getEnglishFontSize() {
    return this.englishFontSize;
  }

  public int getNativeFontSize() {
    return this.nativeFontSize;
  }

  public int getEnglishHighlightStyle() {
    return this.englishHighlightStyle;
  }

  public int getNativeHighlightStyle() {
    return this.nativeHighlightStyle;
  }

  public Font getNativeFont() {
    return new FontUIResource(getNativeFontName(), 0, getNativeFontSize());
  }

  public Font getEnglishFont() {
    return new FontUIResource(getEnglishFontName(), 0, getEnglishFontSize());
  }

  public Font getNativeHighlightFont() {
    return new FontUIResource(getNativeFontName(), getNativeHighlightStyle(), getNativeFontSize());
  }

  public Font getEnglishHighlightFont() {
    return new FontUIResource(getEnglishFontName(), getEnglishHighlightStyle(), getEnglishFontSize());
  }

  public Font getCurrentFont() {
    if (ClientUtil.isChineseLocale()) {
      return getNativeFont();
    }
    return getEnglishFont();
  }

  public Font getCurrentLargeFont(int fontSize)
  {
    if (ClientUtil.isChineseLocale()) {
      return new FontUIResource(getNativeFontName(), 1, fontSize);
    }
    return new FontUIResource(getEnglishFontName(), 1, fontSize);
  }

  public Font getPosCheckLargeFont()
  {
    if (ClientUtil.isChineseLocale()) {
      return new FontUIResource(getNativeFontName(), 0, 16);
    }
    return new FontUIResource(getEnglishFontName(), 0, 16);
  }

  public Font getCurrentHighlightFont()
  {
    if (ClientUtil.isChineseLocale()) {
      return getNativeHighlightFont();
    }
    return getEnglishHighlightFont();
  }

  public void setCurrentFontName(String name)
  {
    if (ClientUtil.isChineseLocale())
      this.nativeFontName = name;
    else
      this.englishFontName = name;
  }

  public void setCurrentFontSize(int size)
  {
    if (ClientUtil.isChineseLocale())
      this.nativeFontSize = size;
    else
      this.englishFontSize = size;
  }

  public Font getLargeFont()
  {
    return this.largeFont;
  }

  public int getCurrentHighlightStyle() {
    if (ClientUtil.isChineseLocale()) {
      return getNativeHighlightStyle();
    }
    return getEnglishHighlightStyle();
  }
}