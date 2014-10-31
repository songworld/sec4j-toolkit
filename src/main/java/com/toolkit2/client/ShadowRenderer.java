package com.toolkit2.client;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class ShadowRenderer
{
  public static final String SIZE_CHANGED_PROPERTY = "shadow_size";
  public static final String OPACITY_CHANGED_PROPERTY = "shadow_opacity";
  public static final String COLOR_CHANGED_PROPERTY = "shadow_color";
  private int size = 5;

  private float opacity = 0.5F;

  private Color color = Color.BLACK;
  private PropertyChangeSupport changeSupport;

  public ShadowRenderer()
  {
    this(5, 0.5F, Color.BLACK);
  }

  public ShadowRenderer(int size, float opacity, Color color)
  {
    this.changeSupport = new PropertyChangeSupport(this);

    setSize(size);
    setOpacity(opacity);
    setColor(color);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    this.changeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    this.changeSupport.removePropertyChangeListener(listener);
  }

  public Color getColor()
  {
    return this.color;
  }

  public void setColor(Color shadowColor)
  {
    if (shadowColor != null) {
      Color oldColor = this.color;
      this.color = shadowColor;
      this.changeSupport.firePropertyChange("shadow_color", oldColor, this.color);
    }
  }

  public float getOpacity()
  {
    return this.opacity;
  }

  public void setOpacity(float shadowOpacity)
  {
    float oldOpacity = this.opacity;

    if (shadowOpacity < 0.0D)
      this.opacity = 0.0F;
    else if (shadowOpacity > 1.0F)
      this.opacity = 1.0F;
    else {
      this.opacity = shadowOpacity;
    }

    this.changeSupport.firePropertyChange("shadow_opacity", Float.valueOf(oldOpacity), Float.valueOf(this.opacity));
  }

  public int getSize()
  {
    return this.size;
  }

  public void setSize(int shadowSize)
  {
    int oldSize = this.size;

    if (shadowSize < 0)
      this.size = 0;
    else {
      this.size = shadowSize;
    }

    this.changeSupport.firePropertyChange("shadow_size", new Integer(oldSize), new Integer(this.size));
  }

  public BufferedImage createShadow(BufferedImage image)
  {
    int shadowSize = this.size * 2;

    int srcWidth = image.getWidth();
    int srcHeight = image.getHeight();

    int dstWidth = srcWidth + shadowSize;
    int dstHeight = srcHeight + shadowSize;

    int left = this.size;
    int right = shadowSize - left;

    int yStop = dstHeight - right;

    int shadowRgb = this.color.getRGB() & 0xFFFFFF;
    int[] aHistory = new int[shadowSize];

    BufferedImage dst = new BufferedImage(dstWidth, dstHeight, 2);

    int[] dstBuffer = new int[dstWidth * dstHeight];
    int[] srcBuffer = new int[srcWidth * srcHeight];

    GraphicsUtilities.getPixels(image, 0, 0, srcWidth, srcHeight, srcBuffer);

    int lastPixelOffset = right * dstWidth;
    float hSumDivider = 1.0F / shadowSize;
    float vSumDivider = this.opacity / shadowSize;

    int[] hSumLookup = new int[256 * shadowSize];
    for (int i = 0; i < hSumLookup.length; i++) {
      hSumLookup[i] = ((int)(i * hSumDivider));
    }

    int[] vSumLookup = new int[256 * shadowSize];
    for (int i = 0; i < vSumLookup.length; i++) {
      vSumLookup[i] = ((int)(i * vSumDivider));
    }

    int srcY = 0; for (int dstOffset = left * dstWidth; srcY < srcHeight; srcY++)
    {
      for (int historyIdx = 0; historyIdx < shadowSize; ) {
        aHistory[(historyIdx++)] = 0;
      }

      int aSum = 0;
     int historyIdx = 0;
      int srcOffset = srcY * srcWidth;

      for (int srcX = 0; srcX < srcWidth; srcX++)
      {
        int a = hSumLookup[aSum];
        dstBuffer[(dstOffset++)] = (a << 24);

        aSum -= aHistory[historyIdx];

        a = srcBuffer[(srcOffset + srcX)] >>> 24;
        aHistory[historyIdx] = a;
        aSum += a;

        historyIdx++; if (historyIdx >= shadowSize) {
          historyIdx -= shadowSize;
        }

      }

      for (int i = 0; i < shadowSize; i++)
      {
        int a = hSumLookup[aSum];
        dstBuffer[(dstOffset++)] = (a << 24);

        aSum -= aHistory[historyIdx];

        historyIdx++; if (historyIdx >= shadowSize) {
          historyIdx -= shadowSize;
        }
      }

    }

    int x = 0; for (int bufferOffset = 0; x < dstWidth; bufferOffset = x)
    {
      int aSum = 0;
      int historyIdx = 0;
      for ( historyIdx = 0; historyIdx < left; ) {
        aHistory[(historyIdx++)] = 0;
      }

      for (int y = 0; y < right; bufferOffset += dstWidth) {
        int a = dstBuffer[bufferOffset] >>> 24;
        aHistory[(historyIdx++)] = a;
        aSum += a;

        y++;
      }

      bufferOffset = x;
      historyIdx = 0;

      for (int y = 0; y < yStop; bufferOffset += dstWidth)
      {
        int a = vSumLookup[aSum];
        dstBuffer[bufferOffset] = (a << 24 | shadowRgb);

        aSum -= aHistory[historyIdx];

        a = dstBuffer[(bufferOffset + lastPixelOffset)] >>> 24;
        aHistory[historyIdx] = a;
        aSum += a;

        historyIdx++; if (historyIdx >= shadowSize)
          historyIdx -= shadowSize;
        y++;
      }

      for (int y = yStop; y < dstHeight; bufferOffset += dstWidth)
      {
        int a = vSumLookup[aSum];
        dstBuffer[bufferOffset] = (a << 24 | shadowRgb);

        aSum -= aHistory[historyIdx];

        historyIdx++; if (historyIdx >= shadowSize)
          historyIdx -= shadowSize;
        y++;
      }
      x++;
    }

    GraphicsUtilities.setPixels(dst, 0, 0, dstWidth, dstHeight, dstBuffer);
    return dst;
  }
}