package com.github.cm360.pixadv.graphics.gui;

public abstract class Menu extends Layer {

	public Menu(Component parent) {
		super(parent);
	}

	public abstract void onClose();

}
