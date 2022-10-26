package com.github.cm360.pixadv.graphics.gui.jarvis;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.events.EventHandler;
import com.github.cm360.pixadv.events.EventListener;
import com.github.cm360.pixadv.events.EventManager.SyncType;
import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.modules.builtin.events.gui.OpenMenuEvent;

public class Jarvis implements EventListener {

	private Set<Layer> guiHuds;
	private Stack<Menu> guiMenus;
	private Set<Layer> guiOverlays;
	
	public Jarvis() {
		ClientApplication.getEventManager().registerHandlers(this);
		// GUI collections
		guiHuds = new TreeSet<Layer>();
		guiMenus = new Stack<Menu>();
		guiOverlays = new TreeSet<Layer>();
	}
	
	@EventHandler(sync = SyncType.RENDER)
	public void onOpenMenuEvent(OpenMenuEvent event) {
		System.out.println("opened menu");
	}
	
	public void openMenu(Menu menu) {
		guiMenus.add(menu);
	}
	
	public void closeMenu() {
		guiMenus.pop().onClose();
	}
	
	public void closeAllMenus() {
		while (!guiMenus.isEmpty())
			closeMenu();
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
