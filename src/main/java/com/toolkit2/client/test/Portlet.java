 package com.toolkit2.client.test;
 
 import java.awt.BorderLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JFileChooser;
 import javax.swing.JPanel;
 import javax.swing.JToolBar;

import twaver.TWaverConst;
 import twaver.TWaverUtil;
 import twaver.chart.AbstractChart;
import twaver.swing.TExpandPane;
 
public abstract class Portlet extends JPanel
   implements Runnable
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private boolean running = true;
   private static JFileChooser chooser;
   private AbstractChart chart;
 
   public void initialize(AbstractChart chart)
   {
     this.chart = chart;
     JToolBar toolbar = getControlPanel();
     TExpandPane searchPopup = new TExpandPane(toolbar, 2, true, false);
 
     setLayout(new BorderLayout());
     add(chart, "Center");
     add(searchPopup, "South");
   }
 
   public AbstractChart getChart() {
     return this.chart;
   }
 
   public JToolBar getControlPanel() {
     JToolBar toolbar = new JToolBar();
     toolbar.setRollover(true);
     toolbar.setFloatable(false);
 
     JButton export = createButton("/resource/image/network/exportToImage.png");
     export.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         if (Portlet.chooser == null) {
           Portlet.chooser=TWaverUtil.createImageFileChooser();
         }
         Portlet.this.chart.exportImage(Portlet.chooser, true);
       }
     });
     toolbar.add(export);
 
     JButton zoomIn = createButton("/resource/image/network/zoomIn.png");
     zoomIn.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         Portlet.this.chart.zoomIn();
       }
     });
     toolbar.add(zoomIn);
 
     JButton zoomOut = createButton("/resource/image/network/zoomOut.png");
     zoomOut.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         Portlet.this.chart.zoomOut();
       }
     });
     toolbar.add(zoomOut);
 
     JButton reset = createButton("/resource/image/network/zoomReset.png");
     reset.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         Portlet.this.chart.reset();
       }
     });
     toolbar.add(reset);
 
     JButton fullScreen = createButton("/resource/image/network/fullScreen.png");
     fullScreen.addActionListener(new ActionListener()
     {
    	 //执行动作
       public void actionPerformed(ActionEvent e) {
    	   //测试多了一级父容器故添加一个getParent();
         PortletPanel container = (PortletPanel)Portlet.this.getParent().getParent();
         container.fullScreen(Portlet.this);
       }
     });
     toolbar.add(fullScreen);
     return toolbar;
   }
 
   private JButton createButton(String icon) {
     JButton button = new JButton(TWaverUtil.getIcon(icon));
     button.setMargin(TWaverConst.NONE_INSETS);
     return button;
   }
 
   public JButton getRunButton() {
     final JButton button = createButton("/com/toolkit2/client/test/stop.gif");
     button.setMargin(TWaverConst.NONE_INSETS);
     button.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         if (Portlet.this.isRunning()) {
           Portlet.this.stopRun();
           button.setIcon(TWaverUtil.getIcon("/com/toolkit2/client/test/start.gif"));
         } else {
           Portlet.this.startRun();
           button.setIcon(TWaverUtil.getIcon("/com/toolkit2/client/test/stop.gif"));
         }
       }
     });
     return button;
   }
 
   public void startRun() {
     this.running = true;
   }
 
   public void stopRun() {
     this.running = false;
   }
 
   public boolean isRunning() {
     return this.running;
   }
 
   public void run()
   {
   }
 }
