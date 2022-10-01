package com.github.cm360.pixadv.graphics.gui.components;

public abstract class Menu extends Layer {

	public Menu(Component parent) {
		super(parent);
	}

	public abstract void onClose();

}
