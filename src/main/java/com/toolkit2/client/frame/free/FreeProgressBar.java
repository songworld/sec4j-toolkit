package com.toolkit2.client.frame.free;
 
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Font;
 import java.awt.FontMetrics;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.Paint;
 import java.awt.Rectangle;
 import java.awt.RenderingHints;
 import java.awt.Shape;
 import java.awt.TexturePaint;
 import java.awt.geom.Area;
 import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
 import java.awt.geom.RoundRectangle2D.Float;
 import javax.swing.ImageIcon;
 import javax.swing.JProgressBar;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ChangeListener;
 import twaver.TWaverConst;
import twaver.TWaverUtil;
 
 public class FreeProgressBar extends JProgressBar
 {
   private String selectedBackgroundImageURL = FreeUtil.getImageURL("progress_select_background.png");
   private String unselectedBackgroundImageURL = FreeUtil.getImageURL("progress_unselect_background.png");
   private ImageIcon selectedBackgroundImageIcon = TWaverUtil.getImageIcon(this.selectedBackgroundImageURL);
  private Image unselectedBackgroundImage = TWaverUtil.getImage(this.unselectedBackgroundImageURL);
   private Image selectedBackgroundLeft = FreeUtil.getImage("progress_selected_left.png");
   private Image selectedBackgroundRight = FreeUtil.getImage("progress_selected_right.png");
   private TexturePaint selectedPaint = FreeUtil.createTexturePaint(this.selectedBackgroundImageURL);
   private TexturePaint unselectedPaint = FreeUtil.createTexturePaint(this.unselectedBackgroundImageURL);
   private Color selectedBorderColor = new Color(233, 145, 17);
   private Color unselectedBorderColor = new Color(105, 166, 44);
   private int roundArc = 6;
   private Font font = FreeUtil.FONT_12_BOLD;
 
   public FreeProgressBar() {
     this(0, 0, 100);
   }
 
   public FreeProgressBar(int min, int max) {
     this(0, min, max);
   }
 
   public FreeProgressBar(int value, int min, int max) {
     super(min, max);
     setValue(value);
     init();
   }
 
   private void init() {
     setOpaque(false);
     setBackground(Color.white);
     setBorder(null);
     setStringPainted(true);
     setFont(this.font);
     setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
     addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
         FreeProgressBar.this.updateString();
       }
     });
     updateString();
   }
 
   private void updateString() {
     int percent = getValue() * 100 / getMaximum();
     setString(percent + "%, " + getValue() + "M/" + getMaximum() + "M");
   }
 
   protected void paintComponent(Graphics g)
   {
     Graphics2D g2d = (Graphics2D)g;
     Object oldHint = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
     Shape allShape = getAllShape();
     paintShape(g2d, allShape, allShape, this.unselectedPaint, this.unselectedBorderColor, null, null);
 
     Shape selectedShape = getSelectedShape();
     paintShape(g2d, allShape, selectedShape, this.selectedPaint, this.selectedBorderColor, this.selectedBackgroundLeft, this.selectedBackgroundRight);
 
     g2d.setFont(getFont());
     g2d.setColor(FreeUtil.DEFAULT_TEXT_COLOR);
     Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(getString(), g);
     int x = (int)((getWidth() - bounds.getWidth()) / 2.0D);
     int y = (int)((getHeight() - bounds.getHeight()) / 2.0D) + g2d.getFontMetrics().getAscent() + 1;
 
     g2d.drawString(getString(), x, y);
 
     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldHint);
   }
 
   private void paintShape(Graphics2D g2d, Shape shape, Shape clip, Paint fillPaint, Color borderColor, Image leftImage, Image rightImage)
   {
     Shape oldClip = g2d.getClip();
     if (clip != null) {
       g2d.setClip(clip);
     }
 
     g2d.setPaint(fillPaint);
     g2d.fill(shape);
     if (leftImage != null) {
       g2d.drawImage(leftImage, 0, 0, null);
     }
     if (rightImage != null) {
       int x = getWidth() - rightImage.getWidth(null);
       int y = 0;
       g2d.drawImage(rightImage, x, y, this);
     }
 
     g2d.setClip(oldClip);
 
     g2d.setColor(borderColor);
     g2d.setStroke(TWaverConst.BASIC_STROKE);
     g2d.draw(clip);
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.selectedBackgroundImageIcon.getIconHeight());
   }
 
   private Shape getSelectedShape() {
     Shape round = getAllShape();
     double percent = getValue() / getMaximum();
     int x = (int)(getWidth() * percent);
     int y = 0;
     int width = getWidth() - x;
     int height = getHeight() - 1;
     Rectangle unselectedRect = new Rectangle(x, y, width, height);
     Area selectedShape = new Area(round);
     selectedShape.subtract(new Area(unselectedRect));
     return selectedShape;
   }
 
   private Shape getAllShape() {
     int width = getWidth() - 1;
     int height = getHeight() - 1;
     return new RoundRectangle2D.Float(0.0F, 0.0F, width, height, this.roundArc, this.roundArc);
   }
 }
