package com.toolkit2.client.images;

public class ResourceAgent
{
  private static final ResourceAgent instance = new ResourceAgent();

  public static ResourceAgent getInstance()
  {
    return instance;
  }
}