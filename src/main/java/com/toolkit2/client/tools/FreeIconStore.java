package com.toolkit2.client.tools;

import java.awt.Image;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.toolkit2.client.frame.free.FreeUtil;

import twaver.TWaverUtil;

/**********************************************************************************
 * 图标仓库，为应用提供全局统一的图标资源
 * 
 * ****/
public class FreeIconStore {
	public static final Icon ADD_ICON = getIcon("add.png");
	public static final Icon BATCH_ADD_ICON = getIcon("batch-add.png");
	public static final Icon UPDATE_ICON = getIcon("update.png");
	public static final Icon DELETE_ICON = getIcon("delete.png");
	public static final Icon SIGNATURE_ICON = getIcon("signature.png");
	public static final Icon REFRESH_ICON = getIcon("refresh.png");
	public static final Icon PRINT_ICON = getIcon("print.png");
	public static final Icon CANCEL_ICON = getIcon("cancel.png");
	public static final Image TOOLKIT_FRAME_ICON = getImage("xcode.png");
	public static final Icon OK_ICON = getIcon("ok.png");
	public static final Icon BROWSE_ICON = getIcon("browse.png");
	public static final Icon UPLOAD_ICON = getIcon("upload.png");
	public static final Icon COPY_ICON = getIcon("copy.png");
	public static final Icon PICK_ICON = getIcon("pick.png");
	public static final Icon DOWNLOAD_ICON = getIcon("download.png");
	public static final Icon OPEN_ICON = getIcon("tree_open.png");
	public static final ImageIcon DOWN_ARROW_ICON = getImageIcon("down_arrow.png");
	public static final Icon GOTO_ICON = getIcon("goto.png");
	public static final Icon APPROVE_ICON = getIcon("approval.png");
	public static final Icon SAVE_ICON = getIcon("save.png");
	public static final Icon DELETEALL_ICON = getIcon("deleteAll.png");
	public static final Icon ACTIVE_ICON = getIcon("activation.png");
	public static final Icon LOCK_ICON = getIcon("lock.png");
	public static final Icon UNLOCK_ICON = getIcon("unlock.png");
	public static final Icon ADD_MOUSE_OVER_ICON = getIcon("add_mouse_over.png");
	public static final Icon UPDATE_MOUSE_OVER_ICON = getIcon("update_mouse_over.png");
	public static final Icon REFRESH_MOUSE_OVER_ICON = getIcon("refresh_mouse_over.png");
	public static final Icon PRINT_MOUSE_OVER_ICON = getIcon("print_mouse_over.png");
	public static final Icon NEXT_ICON = getIcon("next.png");
	public static final Icon NEXT_MOUSE_OVER_ICON = getIcon("next_mouse_over.png");
	public static final Icon NEXT_MOUSE_CLICK_ICON = getIcon("next_mouse_click.png");
	public static final Icon PREVIOUS_ICON = getIcon("previous.png");
	public static final Icon PREVIOUS_MOUSE_OVER_ICON = getIcon("previous_mouse_over.png");
	public static final Icon PREVIOUS_MOUSE_CLICK_ICON = getIcon("previous_mouse_click.png");
	public static final Icon FIRST_ICON = getIcon("first.png");
	public static final Icon LAST_ICON = getIcon("last.png");
	public static final Icon SEARCH_ICON = getIcon("search.png");
	public static final Icon HOME_ICON = getIcon("home.png");
	public static final Icon EMAIL_ICON = getIcon("notice.png");
	public static final Icon NOTICE_CENTER_ICON = getIcon("notice_center.png");
	public static final ImageIcon TREE_OPEN_ICON = getImageIcon("tree_open.png");
	public static final ImageIcon TREE_CLOSE_ICON = getImageIcon("tree_close.png");
	public static final ImageIcon TREE_LEAF_ICON = getImageIcon("tree_leaf.png");
	public static final Icon EXPAND_ICON = getIcon("expand.png");
	public static final Icon HIDE_ICON = getIcon("hide.png");
	public static final Icon XLS_ICON = getIcon("xls.png");
	public static final Icon PDF_ICON = getIcon("pdf.png");
	public static final Icon DOCUMENT_ICON = getIcon("document.png");
	public static final Icon LEFT_ICON = getIcon("left.png");
	public static final Icon RIGHT_ICON = getIcon("right.png");
	public static final Icon MODULE_DIAGRAM_ICON = getIcon("module-diagram.png");
	public static final Icon QUICK_ACCESS_ICON = getIcon("quick-access.png");
	public static final Icon ERROR_STATUS_ICON = getIcon("error_status.png");
	public static final Icon OK_STATUS_ICON = getIcon("ok_status.png");
	public static final Icon TEXT_ICON = getIcon("text.png");
	public static final Icon PICTURE_ICON = getIcon("picture.png");
	public static final Icon GROUP_ICON = getIcon("group.png");
	public static final Icon SUB_PAGE_ICON = getIcon("sub-homepage.png");
	public static final Icon NOTICE_RE_ICON = getIcon("notice_re.png");
	public static final Icon NOTICE_FW_ICON = getIcon("notice_fw.png");
	public static final Icon PREV_ICON = getIcon("prev.gif");
	public static final Icon HELP_ICON = getIcon("help_button.png");
	public static final Icon NOTICE_ICON = FreeIconStore.EMAIL_ICON;
	public static final Icon UP_ICON = getIcon("up.gif");
	public static final Icon DOWN_ICON = getIcon("down.gif");
	public static final Icon TOP_ICON = getIcon("top.png");
	public static final Icon BOTTOM_ICON = getIcon("bottom.png");
	public static final Icon ASCENDING_SORT_ICON = getIcon("sort1.png");
	public static final Icon DESCENDING_SORT_ICON = getIcon("sort2.png");
	public static final Icon DEFAULT_ICON = getIcon("default.gif");
	public static final Icon CUSTOM_ORDER_ICON = getIcon("custom_order.gif");
	public static final Icon CONFIG_ICON = getIcon("config.png");

	
	
	public static Icon getIcon(String name) {
		String path = AppConfiguration.getImageURLPrefix() + name;
		URL url = FreeIconStore.class.getResource(path);
		if (url != null) {
			return TWaverUtil.getIcon(path);
		}
		return TWaverUtil.getIcon("/com/toolkit2/client/images/" + name);
		// return TWaverUtil.getIcon(name);
	}

	public static Icon getIcon(String name, String imageURLPrefix) {
		return TWaverUtil.getIcon(imageURLPrefix + name);
	}

	private static Image getImage(String name) {
		String path = AppConfiguration.getImageURLPrefix() + name;
		URL url = FreeIconStore.class.getResource(path);
		if (url != null) {
			return FreeUtil.getImageIconByCache(path).getImage();
		}
		// return FreeUtil.getImageIcon("/com/toolkit2/client/images/" +
		// name).getImage();
		return FreeUtil.getImageIconByCache(name).getImage();
	}

	private static ImageIcon getImageIcon(String name) {
		String path = AppConfiguration.getImageURLPrefix() + name;
		URL url = FreeIconStore.class.getResource(path);
		if (url != null) {
			return FreeUtil.getImageIconByCache(path);
		}
		return FreeUtil.getImageIconByCache("/com/toolkit2/client/images/"
				+ name);
	}
}
