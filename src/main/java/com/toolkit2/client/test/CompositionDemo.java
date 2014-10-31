 package com.toolkit2.client.test;
 
 import java.awt.BorderLayout;
 import java.awt.Component;
 import java.awt.GridLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.text.NumberFormat;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.SwingUtilities;

import com.toolkit2.client.component2ex.SubForm;

 import twaver.Element;
 import twaver.MouseActionEvent;
 import twaver.TWaverConst;
import twaver.chart.AbstractChart;
 
 //public class CompositionDemo extends JPanel
 public class CompositionDemo extends SubForm
   implements PortletPanel
 {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//界面容器
	private JPanel container = new JPanel();
	 
   private List chartList = new ArrayList();
   private MarkerChart markerChart = new MarkerChart();
   private JapanChart japanChart = new JapanChart();
   private TravelChart travelChart = new TravelChart();
   private AdoptionChart adoptionChart = new AdoptionChart();
   private ShareChart shareChart = new ShareChart();
   private MobileChart mobileChart = new MobileChart();
   private HealthChart healthChart = new HealthChart();
   private WeatherChart weatherChart = new WeatherChart();
   private ClockChart clockChart = new ClockChart();
   private GridLayout layout = new GridLayout();
   private boolean isFullScreen = false;
   ActionListener listener = new ActionListener()
   {
     public void actionPerformed(ActionEvent e) {
       MouseActionEvent event = (MouseActionEvent)e;
       Element element = (Element)event.getSource();
       int index = event.getIndex();
       Component comp = (Component)event.getView();
 
       Double value = (Double)element.getClientProperty("chart.value");
       String message;
       if (value == null)
       {
         if (index < 0)
           message = element.getName();
         else
           message = element.getName() + "," + element.getChartValues().get(index);
       }
       else {
         message = element.getName() + "," + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(value.doubleValue());
       }
       JOptionPane.showMessageDialog(comp, message);
     }
   };
 
   public void initialize(JPanel container)
   {
     int size = this.chartList.size();
   //定义布局
   	 GridLayout mylayout = new GridLayout();     
   	 mylayout.setColumns(3);
   	 mylayout.setHgap(3);
   	 mylayout.setRows(3);
   	 mylayout.setVgap(3);
        container.setLayout(mylayout);
     for (int i = 0; i < size; i++){
    	 this.container.add((Portlet)this.chartList.get(i));
    	 }
     //添加到SubForm
     add(this.container, "Center");
     /*父类中的控件创建，被子类中的布局覆盖，想要显示必须重新加入布局代码
      *  getToolBar在FreePagePane中实现
      * */
     add(this.getToolBar(),"North");
   }
 
   //全屏化方法
   public void fullScreen(Portlet portlet)
   {
	 this.container.removeAll();
     this.isFullScreen = (!this.isFullScreen);
     if (this.isFullScreen) {
			this.container.setLayout(new BorderLayout());
			this.container.add(portlet, "Center");
			// 显示最大化的控件
			add(this.container, "Center");
     } else {
       initialize(this.container);
     }
     portlet.getChart().reset();
     repaint();
     revalidate();
   }
 
   public CompositionDemo() {
	 super.setTitle("仪表盘");
	//定义布局
	 GridLayout mylayout = new GridLayout();     
	 mylayout.setColumns(3);
	 mylayout.setHgap(3);
	 mylayout.setRows(3);
	 mylayout.setVgap(3);
     container.setLayout(mylayout);
     
     this.chartList.add(this.markerChart);
     this.chartList.add(this.japanChart);
     this.chartList.add(this.travelChart);
     this.chartList.add(this.adoptionChart);
     this.chartList.add(this.shareChart);
     this.chartList.add(this.mobileChart);
     this.chartList.add(this.healthChart);
     this.chartList.add(this.weatherChart);
     this.chartList.add(this.clockChart);
     int size = this.chartList.size();
     for (int i = 0; i < size; i++) {
       ((Portlet)this.chartList.get(i)).getChart().addElementDoubleClickedActionListener(this.listener);
     }
     initialize(this.container);
 
     Thread t = new Thread(new Runnable()
     {
       public void run() {
         try {
           while (true) {
             if (CompositionDemo.this.isShowing()) {
               SwingUtilities.invokeAndWait(new Runnable()
               {
                 public void run() {
                   int size = CompositionDemo.this.chartList.size();
                   for (int i = 0; i < size; i++) {
                     ((Portlet)CompositionDemo.this.chartList.get(i)).run();
                   }
                 }
               });
             }
             Thread.sleep(150L);
           }
         }
         catch (Exception e)
         {
         }
       }
     });
     t.start();
   }
 }