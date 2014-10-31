 package com.toolkit2.client.test;
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Container;
 import java.awt.event.KeyAdapter;
 import java.awt.event.KeyEvent;
 import java.text.NumberFormat;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.BorderFactory;
 import javax.swing.JCheckBox;
 import javax.swing.JComponent;
 import javax.swing.JInternalFrame;
 import javax.swing.JLabel;
 import javax.swing.JLayeredPane;
 import javax.swing.JPanel;
 import javax.swing.JSlider;
 import javax.swing.JTextField;
 import javax.swing.JToolBar;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ChangeListener;
 import twaver.DataBoxQuickFinder;
 import twaver.DataBoxSelectionModel;
 import twaver.Element;
 import twaver.ElementCallbackHandler;
 import twaver.MovableFilter;
 import twaver.TDataBox;
 import twaver.TUIManager;
 import twaver.TWaverConst;
 import twaver.VisibleFilter;
 import twaver.network.TNetwork;
 import twaver.network.background.ImageBackground;
 import twaver.swing.TableLayout;
 
 public class PopulationDemo extends JPanel
 {
   TDataBox box = new TDataBox();
   TNetwork network = new TNetwork(this.box);
   DataBoxQuickFinder finder = this.box.createJavaBeanFinder("name");
   ImageBackground background = new ImageBackground("/com/toolkit2/client/test/usa.gif");
 
   public PopulationDemo() {
     setLayout(new BorderLayout());
     add(this.network, "Center");
     this.network.getToolbar().setBorder(null);
     try
     {
       this.box.parse("/com/toolkit2/client/test/population.xml");
     } catch (Exception ex) {
       ex.printStackTrace();
     }
 
     this.background.setTextureURL("/com/toolkit2/client/test/background.png");
     this.network.setBackground(this.background);
 
     createInternalFrame("ControlPane", this.network, createControlPane());
 
     this.box.addElementPropertyChangeListener(new PropertyChangeProcessor(this.network));
 
     init("Alabama", 4447100.0D, 4527166.0D, 4596330.0D, 4663111.0D, 4728915.0D, 4800092.0D, 4874243.0D);
     init("Alaska", 626932.0D, 661110.0D, 694109.0D, 732544.0D, 774421.0D, 820881.0D, 867674.0D);
     init("Arizona", 5130632.0D, 5868004.0D, 6637381.0D, 7495238.0D, 8456448.0D, 9531537.0D, 10712397.0D);
     init("Arkansas", 2673400.0D, 2777007.0D, 2875039.0D, 2968913.0D, 3060219.0D, 3151005.0D, 3240208.0D);
     init("California", 33871648.0D, 36038859.0D, 38067134.0D, 40123232.0D, 42206743.0D, 44305177.0D, 46444861.0D);
     init("Colorado", 4301261.0D, 4617962.0D, 4831554.0D, 5049493.0D, 5278867.0D, 5522803.0D, 5792357.0D);
     init("Connecticut", 3405565.0D, 3503185.0D, 3577490.0D, 3635414.0D, 3675650.0D, 3691016.0D, 3688630.0D);
     init("Delaware", 783600.0D, 836687.0D, 884342.0D, 927400.0D, 963209.0D, 990694.0D, 1012658.0D);
     init("Dist of Columbia", 572059.0D, 551136.0D, 529785.0D, 506323.0D, 480540.0D, 455108.0D, 433414.0D);
     init("Florida", 15982378.0D, 17509827.0D, 19251691.0D, 21204132.0D, 23406525.0D, 25912458.0D, 28685769.0D);
     init("Georgia", 8186453.0D, 8925796.0D, 9589080.0D, 10230578.0D, 10843753.0D, 11438622.0D, 12017838.0D);
     init("Hawaii", 1211537.0D, 1276552.0D, 1340674.0D, 1385952.0D, 1412373.0D, 1438720.0D, 1466046.0D);
     init("Idaho", 1293953.0D, 1407060.0D, 1517291.0D, 1630045.0D, 1741333.0D, 1852627.0D, 1969624.0D);
     init("Illinois", 12419293.0D, 12699336.0D, 12916894.0D, 13097218.0D, 13236720.0D, 13340507.0D, 13432892.0D);
     init("Indiana", 6080485.0D, 6249617.0D, 6392139.0D, 6517631.0D, 6627008.0D, 6721322.0D, 6810108.0D);
     init("Iowa", 2926324.0D, 2973700.0D, 3009907.0D, 3026380.0D, 3020496.0D, 2993222.0D, 2955172.0D);
     init("Kansas", 2688418.0D, 2751509.0D, 2805470.0D, 2852690.0D, 2890566.0D, 2919002.0D, 2940084.0D);
     init("Kentucky", 4041769.0D, 4163360.0D, 4265117.0D, 4351188.0D, 4424431.0D, 4489662.0D, 4554998.0D);
     init("Louisiana", 4468976.0D, 4534310.0D, 4612679.0D, 4673721.0D, 4719160.0D, 4762398.0D, 4802633.0D);
     init("Maine", 1274923.0D, 1318557.0D, 1357134.0D, 1388878.0D, 1408665.0D, 1414402.0D, 1411097.0D);
     init("Maryland", 5296486.0D, 5600563.0D, 5904970.0D, 6208392.0D, 6497626.0D, 6762732.0D, 7022251.0D);
     init("Mass", 6349097.0D, 6518868.0D, 6649441.0D, 6758580.0D, 6855546.0D, 6938636.0D, 7012009.0D);
     init("Michigan", 9938444.0D, 10207421.0D, 10428683.0D, 10599122.0D, 10695993.0D, 10713730.0D, 10694172.0D);
     init("Minnesota", 4919479.0D, 5174743.0D, 5420636.0D, 5668211.0D, 5900769.0D, 6108787.0D, 6306130.0D);
     init("Mississippi", 2844658.0D, 2915696.0D, 2971412.0D, 3014409.0D, 3044812.0D, 3069420.0D, 3092410.0D);
     init("Missouri", 5595211.0D, 5765166.0D, 5922078.0D, 6069556.0D, 6199882.0D, 6315366.0D, 6430173.0D);
     init("Montana", 902195.0D, 933005.0D, 968598.0D, 999489.0D, 1022735.0D, 1037387.0D, 1044898.0D);
     init("Nebraska", 1711263.0D, 1744370.0D, 1768997.0D, 1788508.0D, 1802678.0D, 1812787.0D, 1820247.0D);
     init("Nevada", 1998257.0D, 2352086.0D, 2690531.0D, 3058190.0D, 3452283.0D, 3863298.0D, 4282102.0D);
     init("New Hampshire", 1235786.0D, 1314821.0D, 1385560.0D, 1456679.0D, 1524751.0D, 1586348.0D, 1646471.0D);
     init("New Jersey", 8414350.0D, 8745279.0D, 9018231.0D, 9255769.0D, 9461635.0D, 9636644.0D, 9802440.0D);
     init("New Mexico", 1819046.0D, 1902057.0D, 1980225.0D, 2041539.0D, 2084341.0D, 2106584.0D, 2099708.0D);
     init("New York", 18976457.0D, 19258082.0D, 19443672.0D, 19546699.0D, 19576920.0D, 19540179.0D, 19477429.0D);
     init("North Carolina", 8049313.0D, 8702410.0D, 9345823.0D, 10010770.0D, 10709289.0D, 11449153.0D, 12227739.0D);
     init("North Dakota", 642200.0D, 635468.0D, 636623.0D, 635133.0D, 630112.0D, 620777.0D, 606566.0D);
     init("Ohio", 11353140.0D, 11477557.0D, 11576181.0D, 11635446.0D, 11644058.0D, 11605738.0D, 11550528.0D);
     init("Oklahoma", 3450654.0D, 3521379.0D, 3591516.0D, 3661694.0D, 3735690.0D, 3820994.0D, 3913251.0D);
     init("Oregon", 3421399.0D, 3596083.0D, 3790996.0D, 4012924.0D, 4260393.0D, 4536418.0D, 4833918.0D);
     init("Pennsylvania", 12281054.0D, 12426603.0D, 12584487.0D, 12710938.0D, 12787354.0D, 12801945.0D, 12768184.0D);
     init("Rhode Island", 1048319.0D, 1086575.0D, 1116652.0D, 1139543.0D, 1154230.0D, 1157855.0D, 1152941.0D);
     init("South Carolina", 4012012.0D, 4239310.0D, 4446704.0D, 4642137.0D, 4822577.0D, 4989550.0D, 5148569.0D);
     init("South Dakota", 754844.0D, 771803.0D, 786399.0D, 796954.0D, 801939.0D, 801845.0D, 800462.0D);
     init("Tennessee", 5689283.0D, 5965317.0D, 6230852.0D, 6502017.0D, 6780670.0D, 7073125.0D, 7380634.0D);
     init("Texas", 20851820.0D, 22775044.0D, 24648888.0D, 26585801.0D, 28634896.0D, 30865134.0D, 33317744.0D);
     init("Utah", 2233169.0D, 2417998.0D, 2595013.0D, 2783040.0D, 2990094.0D, 3225680.0D, 3485367.0D);
     init("Vermont", 608827.0D, 630979.0D, 652512.0D, 673169.0D, 690686.0D, 703288.0D, 711867.0D);
     init("Virginia", 7078515.0D, 7552581.0D, 8010245.0D, 8466864.0D, 8917395.0D, 9364304.0D, 9825019.0D);
     init("Washington", 5894121.0D, 6204632.0D, 6541963.0D, 6950610.0D, 7432136.0D, 7996400.0D, 8624801.0D);
     init("West Virginia", 1808344.0D, 1818887.0D, 1829141.0D, 1822758.0D, 1801112.0D, 1766435.0D, 1719959.0D);
     init("Wisconsin", 5363675.0D, 5554343.0D, 5727426.0D, 5882760.0D, 6004954.0D, 6088374.0D, 6150764.0D);
     init("Wyoming", 493782.0D, 507268.0D, 519886.0D, 528005.0D, 530948.0D, 529031.0D, 522979.0D);
   }
 
   private static JInternalFrame createInternalFrame(String title, TNetwork network, JComponent mainPane)
   {
     JInternalFrame frame = new JInternalFrame();
     frame.getContentPane().add(mainPane);
     frame.setTitle(title);
     frame.pack();
     frame.setLocation(30, 30);
     frame.setVisible(true);
     network.getLayeredPane().add(frame, 0);
     return frame;
   }
 
   private JPanel createControlPane() {
     final JTextField quickSearch = new JTextField();
     final JSlider pSlider = new JSlider(0, 35, 0);
     final JLabel pLabel = new JLabel(pSlider.getValue() + "  million");
     final JSlider bSlider = new JSlider(0, 100, 100);
     final JLabel bLabel = new JLabel("background 1");
     final JSlider eSlider = new JSlider(0, 100, 100);
     final JLabel eLabel = new JLabel("element 1");
     final JCheckBox movable = new JCheckBox("Enable Moving Node");
 
     pSlider.setPaintLabels(true);
     pSlider.setPaintTicks(true);
     pSlider.setMinorTickSpacing(1);
     pSlider.setMajorTickSpacing(5);
     pSlider.setSnapToTicks(true);
 
     double[] rows = { -2.0D, -2.0D, -2.0D, -2.0D, -2.0D };
     double[] columns = { -2.0D, -1.0D };
 
     TableLayout layout = new TableLayout(columns, rows);
     layout.setVGap(2);
     layout.setHGap(2);
     JPanel controlPane = new JPanel(layout);
     controlPane.add(new JLabel("Quick Search"), "0,0");
     controlPane.add(quickSearch, "1,0");
     controlPane.add(pLabel, "0,1");
     controlPane.add(pSlider, "1,1");
     controlPane.add(bLabel, "0,2");
     controlPane.add(bSlider, "1,2");
     controlPane.add(eLabel, "0,3");
     controlPane.add(eSlider, "1,3");
     controlPane.add(movable, "1,4");
     controlPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
     quickSearch.addKeyListener(new KeyAdapter()
     {
       public void keyReleased(KeyEvent e) {
         PopulationDemo.this.box.getSelectionModel().clearSelection();
         final String text = quickSearch.getText();
         if ((text != null) && (!text.trim().equals(""))) {
           final List elements = new ArrayList();
           PopulationDemo.this.box.iterator(new ElementCallbackHandler()
           {
             public boolean processElement(Element element) {
               String name = element.getName();
               if ((name != null) && (PopulationDemo.this.network.isVisible(element)) && (name.toLowerCase().indexOf(text.toLowerCase()) >= 0)) {
                 elements.add(element);
               }
               return true;
             }
           });
           PopulationDemo.this.box.getSelectionModel().setSelection(elements);
         }
       }
     });
     this.network.addVisibleFilter(new VisibleFilter()
     {
       public boolean isVisible(Element element) {
         if ((element instanceof StateNode)) {
           StateNode node = (StateNode)element;
           return node.getC2000() > pSlider.getValue() * 1000000;
         }
         return true;
       }
     });
     this.network.addMovableFilter(new MovableFilter()
     {
       public boolean isMovable(Element element) {
         return movable.isSelected();
       }
     });
     pSlider.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
         pLabel.setText(pSlider.getValue() + "  million");
         PopulationDemo.this.network.getCanvas().repaint();
       }
     });
     eSlider.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
         eLabel.setText("element " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(eSlider.getValue() / 100.0F));
         final Float alpha = new Float(eSlider.getValue() / 100.0F);
         PopulationDemo.this.box.iterator(new ElementCallbackHandler()
         {
           public boolean processElement(Element element) {
             element.putClientProperty("render.alpha", alpha);
             return true;
           }
         });
       }
     });
     bSlider.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
         bLabel.setText("background " + TWaverConst.DEFAULT_DOUBLE_FORMATER.format(bSlider.getValue() / 100.0F));
         PopulationDemo.this.background.setAlpha(bSlider.getValue() / 100.0F);
         PopulationDemo.this.network.getCanvas().repaint();
       }
     });
     return controlPane;
   }
 
   private void init(String stateName, double c2000, double p2005, double p2010, double p2015, double p2020, double p2025, double p2030) {
     StateNode node = (StateNode)this.finder.findFirst(stateName);
     node.putChartColor(Color.ORANGE);
     node.putChartInflexionStyle(1);
     node.init(c2000, p2005, p2010, p2015, p2020, p2025, p2030);
   }
 
   static
   {
     TUIManager.registerAttachment("population", PopulationAttachment.class);
   }
 }
