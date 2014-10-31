package com.toolkit2.client.tools;

public class ConfigurationItem
{
  private String name;
  private String key;
  private String resID;
  private boolean visible = true;
  private boolean autoResize = false;
  private boolean isPrint = true;
  private boolean isExtend = false;
  private String sortBy;
  private String rendererClass = null;
  private String readMethod;
  private String alignment;
  private int printedWidth = 10;
  private boolean printNewLine = false;
  private boolean rowPackParticipable = false;
  private String editorClass;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getResID() {
    return this.resID;
  }

  public void setResID(String resID) {
    this.resID = resID;
  }

  public boolean isVisible() {
    return this.visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean isAutoResize() {
    return this.autoResize;
  }

  public void setAutoResize(boolean autoResize) {
    this.autoResize = autoResize;
  }

  public String getRendererClass() {
    return this.rendererClass;
  }

  public void setRendererClass(String rendererClass) {
    this.rendererClass = rendererClass;
  }

  public String getReadMethod() {
    return this.readMethod;
  }

  public void setReadMethod(String readMethod) {
    this.readMethod = readMethod;
  }

  public void setAlignment(String alignment) {
    this.alignment = alignment;
  }

  public void setPrintedWidth(int printedWidth) {
    this.printedWidth = printedWidth;
  }

  public String getAlignment() {
    return this.alignment;
  }

  public int getPrintedWidth() {
    return this.printedWidth;
  }

  public boolean isPrint() {
    return this.isPrint;
  }

  public void setPrint(boolean isPrint) {
    this.isPrint = isPrint;
  }

  public String getSortBy() {
    return this.sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public boolean isExtend() {
    return this.isExtend;
  }

  public void setExtend(boolean isExtend) {
    this.isExtend = isExtend;
  }

  public boolean isPrintNewLine() {
    return this.printNewLine;
  }

  public void setPrintNewLine(boolean printNewLine) {
    this.printNewLine = printNewLine;
  }

  public boolean isRowPackParticipable() {
    return this.rowPackParticipable;
  }

  public void setRowPackParticipable(boolean rowPackParticipable) {
    this.rowPackParticipable = rowPackParticipable;
  }

  public String getEditorClass() {
    return this.editorClass;
  }

  public void setEditorClass(String editorClass) {
    this.editorClass = editorClass;
  }
}