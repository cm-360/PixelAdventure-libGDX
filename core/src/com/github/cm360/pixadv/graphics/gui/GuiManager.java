package com.github.cm360.pixadv.graphics.gui;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.events.EventHandler;
import com.github.cm360.pixadv.events.EventListener;
import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.modules.builtin.events.gui.OpenMenuEvent;

public class GuiManager implements EventListener {

	private Set<Layer> guiHuds;
	private Stack<Menu> guiMenus;
	private Set<Layer> guiOverlays;
	
	public GuiManager() {
		ClientApplication.getEventManager().registerHandlers(this);
		// GUI collections
		guiHuds = new TreeSet<Layer>();
		guiMenus = new Stack<Menu>();
		guiOverlays = new TreeSet<Layer>();
	}
	
	@EventHandler
	public void openMenu(OpenMenuEvent event) {
		System.out.println("opened menu");
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
