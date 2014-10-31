package com.toolkit2.client.frame.free;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.toolkit2.client.GraphicsUtilities;
import com.toolkit2.client.Shell;

import twaver.DataBoxAdapter;
import twaver.DataBoxEvent;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.Generator;
import twaver.MovableFilter;
import twaver.Node;
import twaver.SelectableFilter;
import twaver.TDataBox;
import twaver.TWaverUtil;
import twaver.network.TNetwork;
import twaver.network.background.ColorBackground;

public class FreeNetwork extends TNetwork {
	private Color canvasColor = FreeUtil.NETWORK_BACKGROUND;
	private int shadowSize = 6;
	private float shadowOpacity = 0.3F;

	public FreeNetwork() {
		init();
	}

	public FreeNetwork(TDataBox box) {
		super(box);
		init();
	}

	private void init() {
		setToolbar(null);
		setBorder(null);
		getCanvasScrollPane().setBorder(null);
		setNetworkBackground(new ColorBackground(this.canvasColor));
		addMovableFilter(new MovableFilter() {
			public boolean isMovable(Element elmnt) {
				return false;
			}
		});
		getDataBox().getSelectionModel().addDataBoxSelectionListener(
				new DataBoxSelectionListener() {
					private boolean adjusting = false;

					public void selectionChanged(DataBoxSelectionEvent e) {
						if (this.adjusting) {
							return;
						}
						if (FreeNetwork.this.getDataBox().getSelectionModel()
								.size() > 1)
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (FreeNetwork.this.getDataBox()
											.getSelectionModel().size() > 1) {
										adjusting = true;

										FreeNetwork.this
												.getDataBox()
												.getSelectionModel()
												.setSelection(
														FreeNetwork.this
																.getDataBox()
																.getSelectionModel()
																.lastElement());
										adjusting = false;

									}
								}
							});
					}
				});
		addSelectableFilter(new SelectableFilter() {
			public boolean isSelectable(Element element) {
				return element instanceof FreeNode;
			}
		});
		getDataBox().addDataBoxListener(new DataBoxAdapter() {
			public void elementAdded(DataBoxEvent e) {
				Element element = e.getElement();
				if ((element instanceof Node)) {
					Node node = (Node) element;
					FreeNetwork.this.updateShadow(node);

					if ((element instanceof FreeNode)) {
						FreeNode freeNode = (FreeNode) element;
						FreeNodeButtonAttachment attachment = freeNode
								.getButtonAttachment();
						if (attachment != null) {
							FreeNetwork.this.getCanvas().add(attachment);
							attachment.updateBounds();
						}
					}
				}
			}

			public void elementRemoved(DataBoxEvent e) {
				Element element = e.getElement();
				if ((element instanceof FreeNode)) {
					FreeNode freeNode = (FreeNode) element;
					JComponent attachment = freeNode.getButtonAttachment();
					if (attachment != null)
						FreeNetwork.this.getCanvas().remove(attachment);
				}
			}
		});
		getDataBox().addElementPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						Object source = evt.getSource();
						if (((source instanceof FreeNode))
								&& (TWaverUtil.getPropertyName(evt)
										.equalsIgnoreCase("location"))) {
							FreeNode node = (FreeNode) source;
							FreeNodeButtonAttachment attachment = node
									.getButtonAttachment();
							attachment.updateBounds();
						}
					}
				});
		addCanvasCushion(new FreeNetworkShadowCushion(this));

		setElementLabelGenerator(new Generator() {
			public Object generate(Object o) {
				if ((o instanceof FreeNode)) {
					FreeNode node = (FreeNode) o;
					return node.getNetworkName();
				}
				return ((Element) o).getName();
			}
		});
		getDataBox().getSelectionModel().addDataBoxSelectionListener(
				new DataBoxSelectionListener() {
					public void selectionChanged(DataBoxSelectionEvent e) {
						if (e.getBoxSelectionModel().size() == 1) {
							Element element = e.getBoxSelectionModel()
									.lastElement();
							if ((element instanceof FreeNode)) {
								FreeNode node = (FreeNode) element;
								ActionListener nodeAction = node.getAction();
								if (nodeAction != null) {
									ActionEvent event = new ActionEvent(
											FreeNetwork.this, 0, null);
									nodeAction.actionPerformed(event);
								}
							}
						}
					}
				});


		 addComponentListener(new ComponentAdapter()
		 {
		 public void componentResized(ComponentEvent e) {
		 FreeNetwork.this.computeNodeLocation();
		 }
		 });
		 addHierarchyListener(new HierarchyListener()
		 {
		 public void hierarchyChanged(HierarchyEvent e) {
		 FreeNetwork.this.setOriginalPercent();
		 FreeNetwork.this.computeNodeLocation();
		 }
		 });
		 getCanvas().addMouseMotionListener(new MouseMotionAdapter()
		 {
		 public void mouseMoved(MouseEvent e) {
		 Element o = FreeNetwork.this.getElementPhysicalAt(e.getPoint());
		 FreeNetwork.this.getDataBox().getSelectionModel().clearSelection();
		 if ((o instanceof FreeModuleNode)) {
		 FreeModuleNode node = (FreeModuleNode)o;
		 node.setSelected(true);
		 FreeNetwork.this.getCanvas().setCursor(Cursor.getPredefinedCursor(12));
		 } else {
		 FreeNetwork.this.getCanvas().setCursor(Cursor.getDefaultCursor());
		 }
		 }
		 });
		 getCanvas().addMouseListener(new MouseAdapter()
		 {
		 public void mouseReleased(MouseEvent e) {
		 Element o = FreeNetwork.this.getElementPhysicalAt(e.getPoint());
		 FreeNetwork.this.getDataBox().getSelectionModel().clearSelection();
		 if ((o instanceof FreeModuleNode)) {
		 FreeModuleNode node = (FreeModuleNode)o;
		 String treeNodeName = node.getTreeNodeName();
		  TDataBox treeBox = Shell.getInstance().getMainTreePane().getMainTree().getDataBox();
		  Element element = treeBox.getElementByID(treeNodeName);
		  if (element != null) {
		  treeBox.getSelectionModel().clearSelection();
		  element.setSelected(true);
		  }
		 node.setSelected(true);
		 }
		 }
		 });
	}

	private void setOriginalPercent() {
		double maxX = 0.0D;
		double maxY = 0.0D;
		double minX = 0.0D;
		double minY = 0.0D;
		Iterator it = getDataBox().getAllElementsReverse().iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			if ((e instanceof FreeModuleNode)) {
				FreeModuleNode node = (FreeModuleNode) e;
				double x = node.getCenterLocation().getX();
				double y = node.getCenterLocation().getY();
				maxX = maxX < x ? x : maxX;
				maxY = maxY < y ? y : maxY;
				if (minX == 0.0D)
					minX = x;
				else {
					minX = minX > x ? x : minX;
				}
				if (minY == 0.0D)
					minY = y;
				else {
					minY = minY > x ? x : minY;
				}
			}
		}
		maxX += minX;
		maxY += minY;
		it = getDataBox().getAllElementsReverse().iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			if ((e instanceof Node)) {
				Node node = (Node) e;
				if (node.getClientProperty("origin_location") == null) {
					double xLocationPercent = node.getCenterLocation().getX()
							/ maxX;
					double yLocationPercent = node.getCenterLocation().getY()
							/ maxY;
					Point2D.Double originalLocation = new Point2D.Double(
							xLocationPercent, yLocationPercent);
					node.putClientProperty("origin_location", originalLocation);
				}
			}
		}
	}

	private void computeNodeLocation() {
		Iterator it = getDataBox().getAllElementsReverse().iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			if ((e instanceof Node)) {
				Node node = (Node) e;
				Point2D.Double originalLocation = (Point2D.Double) node
						.getClientProperty("origin_location");
				if (originalLocation != null) {
					int width = getWidth();
					int height = getHeight();
					double xLocationPercent = originalLocation.x;
					double yLocationPercent = originalLocation.y;
					int x = (int) (width * xLocationPercent);
					int y = (int) (height * yLocationPercent);
					node.setCenterLocation(x, y);
				}
			}
		}
	}

	private void updateShadow(Node node) {
		if (node != null) {
			ImageIcon imageIcon = node.getImage();
			if (imageIcon != null) {
				String urlString = node.getImageURL();
				URL url = getClass().getResource(urlString);
				try {
					BufferedImage imageSource = GraphicsUtilities
							.loadCompatibleImage(url);
					BufferedImage imageDest = FreeUtil.createDropShadow(
							imageSource, this.shadowSize);

					BufferedImage imageShadow = new BufferedImage(
							imageDest.getWidth(), imageDest.getHeight(), 2);

					Graphics2D g2d = imageShadow.createGraphics();
					g2d.setComposite(AlphaComposite.SrcOver
							.derive(this.shadowOpacity));
					g2d.drawImage(imageDest, 0, 0, null);
					g2d.dispose();

					FreeUtil.setNodeShadowImage(node, imageShadow);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getShadowSize() {
		return this.shadowSize;
	}
}
