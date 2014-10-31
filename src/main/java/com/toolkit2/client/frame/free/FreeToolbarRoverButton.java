package com.toolkit2.client.frame.free;

import java.awt.Color;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class FreeToolbarRoverButton extends FreeToolbarButton {
	private Color roverDyeColor = new Color(86, 146, 61);

	public FreeToolbarRoverButton() {
		this(null, null);
	}

	public FreeToolbarRoverButton(String text) {
		this(text, null);
	}

	public FreeToolbarRoverButton(Icon icon) {
		this(null, icon);
	}

	public FreeToolbarRoverButton(String text, Icon icon) {
		setText(text);
		setIcon(icon);
	}

	public void setIcon(Icon icon) {
		super.setIcon(icon);

		if (icon == null) {
			setPressedIcon(null);
			setRolloverIcon(null);
		} else {
			Image image = FreeUtil.iconToImage(icon);
			Icon roverIcon = FreeUtil.createDyedIcon(new ImageIcon(image),
					this.roverDyeColor);
			Icon pressedIcon = FreeUtil.createMovedIcon(roverIcon);
			setRolloverIcon(roverIcon);
			setPressedIcon(pressedIcon);
		}
	}


}