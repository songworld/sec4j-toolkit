package com.toolkit2.client.frame.free;
 
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Container;
 import java.awt.Cursor;
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.TexturePaint;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseListener;
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JComponent;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.border.Border;
 import twaver.TWaverUtil;
 
 public class FreeHeader extends JPanel
 {
   public static final ImageIcon RIGHT_ARROW_ICON = FreeUtil.getImageIcon("shrink_handler_right.png");
   public static final ImageIcon LEFT_ARROW_ICON = FreeUtil.getImageIcon("shrink_handler_left.png");
   private Color titleColor = new Color(215, 215, 216);
   private boolean shrinked = false;
   private String selectedBackgroundImageURL = FreeUtil.getImageURL("header_background.png");
   private ImageIcon backgroundImageIcon = TWaverUtil.getImageIcon(this.selectedBackgroundImageURL);
   private Image backgroundLeftImage = FreeUtil.getImage("header_background_left.png");
   private Image backgroundRightImage = FreeUtil.getImage("header_background_right.png");
   private TexturePaint paint = FreeUtil.createTexturePaint(this.selectedBackgroundImageURL);
   private int preferredHeight = this.backgroundImageIcon.getIconHeight();
   private JLabel lbResizeHandler = new JLabel(FreeUtil.getImageIcon("resize_handler.png"));
   private JLabel lbShrinkHandler = new JLabel(getShrinkIcon(this.shrinked));
   private JLabel lbTitle = new JLabel();
   private int normalPreferredWidth = 0;
   private FreeListSplitListener splitListener = createSplitListener();
   private MouseListener shrinkListener = new MouseAdapter()
   {
     public void mouseClicked(MouseEvent e)
     {
       FreeHeader.this.changeShrink();
     }
   };
 
   public FreeHeader()
   {
     init();
   }
 
   protected FreeListSplitListener createSplitListener() {
     return new FreeListSplitListener(this);
   }
 
   protected Border createBorder() {
     return BorderFactory.createEmptyBorder(4, 7, 0, 0);
   }
 
   private void init() {
     setBorder(createBorder());
     setOpaque(false);
     setLayout(new BorderLayout());
     add(this.lbResizeHandler, getResizeHandlerLayoutConstraint());
     add(this.lbShrinkHandler, getShrinkHandlerLayoutConstraint());
     JComponent centerComponent = getCenterComponent();
     if (centerComponent != null) {
       add(centerComponent, "Center");
     }
 
     this.lbResizeHandler.addMouseMotionListener(this.splitListener);
     this.lbResizeHandler.addMouseListener(this.splitListener);
     this.lbShrinkHandler.addMouseListener(this.shrinkListener);
     this.lbTitle.setFont(FreeUtil.FONT_14_BOLD);
     this.lbTitle.setForeground(this.titleColor);
     this.lbTitle.setBorder(BorderFactory.createEmptyBorder(2, 8, 0, 0));
 
     updateCursor();
 
     this.lbShrinkHandler.setBorder(BorderFactory.createEmptyBorder(0, 8, 2, 5));
   }
 
   protected JComponent getCenterComponent() {
     return this.lbTitle;
   }
 
   protected Object getResizeHandlerLayoutConstraint() {
     return "West";
   }
 
   protected Object getShrinkHandlerLayoutConstraint() {
     return "East";
   }
 
   protected void paintComponent(Graphics g)
   {
     Graphics2D g2d = (Graphics2D)g;
 
     g2d.setPaint(this.paint);
     g2d.fillRect(0, 0, getWidth(), getHeight());
 
     g2d.drawImage(this.backgroundLeftImage, 0, 0, null);
 
     int x = getWidth() - this.backgroundRightImage.getWidth(null);
     int y = 0;
     g2d.drawImage(this.backgroundRightImage, x, y, null);
   }
 
   public Dimension getPreferredSize()
   {
     return new Dimension(super.getPreferredSize().width, this.preferredHeight);
   }
 
   public void revalidateParent() {
     if ((getParent() instanceof JComponent))
       ((JComponent)getParent()).revalidate();
   }
 
   public void changeShrink()
   {
     setShrink(!isShrinked());
   }
 
   public void setShrink(boolean shrinked) {
     if (shrinked != this.shrinked) {
     /*  Container parent = getParent();
       Dimension size = parent.getPreferredSize();
       if (shrinked) {
         this.normalPreferredWidth = size.width;
         size = new Dimension(getShrinkedWidth(), size.height);
       } else {
         int width = this.normalPreferredWidth;
         int height = parent.getPreferredSize().height;
         size = new Dimension(width, height);
       }
       parent.setPreferredSize(size);*/
	   updateDock(shrinked);
       this.lbShrinkHandler.setIcon(getShrinkIcon(shrinked));
       revalidateParent();
       this.shrinked = shrinked;
       updateCursor();
       this.lbTitle.setVisible(!shrinked);
       this.lbResizeHandler.setVisible(!shrinked);
     }
   }
  protected void updateDock(boolean dock)
  {
  }
   protected ImageIcon getShrinkIcon(boolean shrinked) {
     if (shrinked) {
       return LEFT_ARROW_ICON;
     }
     return RIGHT_ARROW_ICON;
   }
 
   private void updateCursor()
   {
     if (this.shrinked)
       this.lbResizeHandler.setCursor(Cursor.getDefaultCursor());
     else
       this.lbResizeHandler.setCursor(Cursor.getPredefinedCursor(10));
   }
 
   public boolean isShrinked()
   {
     return this.shrinked;
   }
 
   public void setTitle(String title) {
     this.lbTitle.setText(title);
   }
 
   public String getTitle() {
     return this.lbTitle.getText();
   }
 
   protected int getShrinkedWidth() {
     return 18;
   }
 }