package com.github.cm360.pixadv.graphics.gui;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;

public class GuiManager {

	private Set<Layer> guiHuds;
	private Stack<Menu> guiMenus;
	private Set<Layer> guiOverlays;
	
	public GuiManager() {
		// GUI collections
		guiHuds = new TreeSet<Layer>();
		guiMenus = new Stack<Menu>();
		guiOverlays = new TreeSet<Layer>();
	}
	
	public Set<Layer> getHudLayers() {
		return guiHuds;
	}

	public Menu getCurrentMenu() {
		if (guiMenus.isEmpty())
			return null;
		else
			return guiMenus.peek();
	}
	
	public Set<Layer> getOverlays() {
		return guiOverlays;
	}

}
