package com.toolkit2.client.frame.free;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Collator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import com.toolkit2.Util.ClientUtil;
import com.toolkit2.Util.GeneralUtil;
import com.toolkit2.client.Shell;
import com.toolkit2.client.tools.FreeIconStore;

import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.TDataBox;
import twaver.tree.TTree;
/*
 * 	Tree ToolBar
 * */
public class FreeTreeToolbar extends JPanel {
	private static String TREE_SORT_ORDER = "TREE_SORT_ORDER";
	private static String ASCENDING_SORT = "ASCENDING_SORT";
	private static String DESCENDING_SORT = "DESCENDING_SORT";
	private static String DEFAULT_SORT = "DEFAULT_SORT";
	private static String CUSTOMIZED_SORT = "CUSTOMIZED_SORT";
	private TTree tree = null;
	private FreeToolbarButton btnUp = new FreeToolbarButton(null,
			FreeIconStore.UP_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Moveup"));
	private FreeToolbarButton btnDown = new FreeToolbarButton(null,
			FreeIconStore.DOWN_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Movedown"));
	private FreeToolbarButton btnTop = new FreeToolbarButton(null,
			FreeIconStore.TOP_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Movetotop"));
	private FreeToolbarButton btnBottom = new FreeToolbarButton(null,
			FreeIconStore.BOTTOM_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Movetobottom"));
	private FreeToolbarToggleButton btnAscendingSort = new FreeToolbarToggleButton(
			null, FreeIconStore.ASCENDING_SORT_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Sortinascendingorder"));
	private FreeToolbarToggleButton btnDescendingSort = new FreeToolbarToggleButton(
			null, FreeIconStore.DESCENDING_SORT_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Sortindescendingorder"));
	private FreeToolbarToggleButton btnDefault = new FreeToolbarToggleButton(
			null, FreeIconStore.DEFAULT_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Sortindefaultorder"));
	private FreeToolbarToggleButton btnCustomizedSort = new FreeToolbarToggleButton(
			null, FreeIconStore.CUSTOM_ORDER_ICON,
			GeneralUtil.getString("TreeToolbarFactory.Sortincustomizedorder"));

	

	public FreeTreeToolbar(final TTree tree) {
		setOpaque(true);
		setBackground(FreeUtil.ALL_UI_BACKGROUD_COLOR);
		this.tree = tree;
		setLayout(new FlowLayout(0, 0, 0));
		this.btnUp.setEnabled(false);
		this.btnDown.setEnabled(false);
		this.btnTop.setEnabled(false);
		this.btnBottom.setEnabled(false);
		this.btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector elements = new Vector();

				boolean startToIterate = false;
				Iterator it = ((Element) tree.getDataBox().getRootElements()
						.get(0)).children();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					if (element.isSelected()) {
						if (startToIterate)
							elements.addElement(element);
					} else {
						startToIterate = true;
					}
				}

				for (int i = 0; i < elements.size(); i++) {
					Element element = (Element) elements.get(i);
					tree.getDataBox().moveToUp(element);
				}
			}
		});
		this.btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector elements = new Vector();

				boolean startToIterate = false;
				List list = ((Element) tree.getDataBox().getRootElements()
						.get(0)).getChildren();
				for (int i = list.size(); i > 0; i--) {
					Element element = (Element) list.get(i - 1);
					if (element.isSelected()) {
						if (startToIterate)
							elements.addElement(element);
					} else {
						startToIterate = true;
					}
				}

				for (int i = 0; i < elements.size(); i++) {
					Element element = (Element) elements.get(i);
					tree.getDataBox().moveToDown(element);
				}
			}
		});
		this.btnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector elements = new Vector();
				Iterator it = ((Element) tree.getDataBox().getRootElements()
						.get(0)).children();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					if (element.isSelected()) {
						elements.addElement(element);
					}
				}
				for (int i = elements.size(); i > 0; i--) {
					Element element = (Element) elements.get(i - 1);
					tree.getDataBox().moveToTop(element);
				}
			}
		});
		this.btnBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector elements = new Vector();
				Iterator it = ((Element) tree.getDataBox().getRootElements()
						.get(0)).children();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					if (element.isSelected()) {
						elements.addElement(element);
					}
				}
				for (int i = 0; i < elements.size(); i++) {
					Element element = (Element) elements.get(i);
					tree.getDataBox().moveToBottom(element);
				}
			}
		});
		this.btnAscendingSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FreeTreeToolbar.sortTreeNode(tree.getDataBox(), true);
				FreeTreeToolbar.this.resetMoveButtons();
				ClientUtil.saveClientProperty(FreeTreeToolbar.TREE_SORT_ORDER,
						FreeTreeToolbar.ASCENDING_SORT);
			}
		});
		this.btnDescendingSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FreeTreeToolbar.sortTreeNode(tree.getDataBox(), false);
				FreeTreeToolbar.this.resetMoveButtons();
				ClientUtil.saveClientProperty(FreeTreeToolbar.TREE_SORT_ORDER,
						FreeTreeToolbar.DESCENDING_SORT);
			}
		});
		this.btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreeMap elements = new TreeMap();
				Iterator it = ((Element) tree.getDataBox().getRootElements()
						.get(0)).children();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					elements.put(element.getClientProperty("defaultIndex"),
							element);
				}
				it = elements.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					Element element = (Element) elements.get(key);
					tree.getDataBox().moveToBottom(element);
				}
				FreeTreeToolbar.this.resetMoveButtons();
				ClientUtil.saveClientProperty(FreeTreeToolbar.TREE_SORT_ORDER,
						FreeTreeToolbar.DEFAULT_SORT);
			}
		});
		this.btnCustomizedSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FreeTreeToolbar.this.resetMoveButtons();
				ClientUtil.saveClientProperty(FreeTreeToolbar.TREE_SORT_ORDER,
						FreeTreeToolbar.CUSTOMIZED_SORT);
			}
		});
		
		tree.getDataBox().getSelectionModel()
				.addDataBoxSelectionListener(new DataBoxSelectionListener() {
					public void selectionChanged(DataBoxSelectionEvent e) {
						FreeTreeToolbar.this.resetMoveButtons();
					}
				});
		initSortButton();
		add(this.btnTop);
		add(this.btnUp);
		add(this.btnDown);
		add(this.btnBottom);
		add(this.btnAscendingSort);
		add(this.btnDescendingSort);
		add(this.btnDefault);
		add(this.btnCustomizedSort);
	}

	private void initSortButton() {
		ButtonGroup orderByGroup = new ButtonGroup();
		orderByGroup.add(this.btnAscendingSort);
		orderByGroup.add(this.btnDescendingSort);
		orderByGroup.add(this.btnDefault);
		orderByGroup.add(this.btnCustomizedSort);
		String treeSortOrder = ClientUtil.readClientProperty(TREE_SORT_ORDER);
		if (DESCENDING_SORT.equalsIgnoreCase(treeSortOrder))
			this.btnDescendingSort.setSelected(true);
		else if (DEFAULT_SORT.equalsIgnoreCase(treeSortOrder))
			this.btnDefault.setSelected(true);
		else if (CUSTOMIZED_SORT.equalsIgnoreCase(treeSortOrder))
			this.btnCustomizedSort.setSelected(true);
		else
			this.btnAscendingSort.setSelected(true);
	}

	private void resetMoveButtons() {
		boolean buttonEnableStatus = this.btnCustomizedSort.isSelected();
		if (!this.tree.getDataBox().getSelectionModel().isEmpty()) {
			boolean allSelectionAreBoxModules = false;
			Iterator it = this.tree.getDataBox().getSelectionModel()
					.selection();
			while (it.hasNext()) {
				Element element = (Element) it.next();

				if (((element.getParent() != null) && (element.getParent()
						.getParent() != null)) || (element.getParent() == null)) {
					allSelectionAreBoxModules = false;
					break;
				}
				allSelectionAreBoxModules = true;
			}
			buttonEnableStatus = (buttonEnableStatus)
					&& (allSelectionAreBoxModules);
		}
		this.btnUp.setEnabled(buttonEnableStatus);
		this.btnDown.setEnabled(buttonEnableStatus);
		this.btnTop.setEnabled(buttonEnableStatus);
		this.btnBottom.setEnabled(buttonEnableStatus);
	}

	/********************************************************************************
	 * 树节点排序
	 * 
	 * *******/
	public static void sortTreeNode(TDataBox box, boolean ascendingSort) {
		Comparator com = Collator.getInstance(Locale.CHINA);
		TreeMap elements = new TreeMap(com);
		Iterator it = ((Element) box.getRootElements().get(0)).children();
		Integer lastNodeIndex = Integer.valueOf(0);
		Element lastNode = null;
		while (it.hasNext()) {
			Element element = (Element) it.next();
			elements.put(element.getName(), element);
			Object index = element.getClientProperty("defaultIndex");
			if (((index instanceof Integer))
					&& (((Integer) index).intValue() > lastNodeIndex.intValue())) {
				lastNodeIndex = (Integer) index;
				lastNode = element;
			}
		}
		it = elements.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Element element = (Element) elements.get(key);
			if (ascendingSort)
				box.moveToBottom(element);
			else {
				box.moveToTop(element);
			}
		}

		if (lastNode != null)
			box.moveToBottom(lastNode);
	}
}