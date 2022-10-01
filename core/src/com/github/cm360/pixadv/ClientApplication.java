package com.github.cm360.pixadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.github.cm360.pixadv.environment.Universe;
import com.github.cm360.pixadv.graphics.gui.GuiManager;
import com.github.cm360.pixadv.graphics.picasso.Picasso;
import com.github.cm360.pixadv.registry.Registry;

public class ClientApplication extends ApplicationAdapter {

	private Registry registry;
	private Picasso picasso;
	private GuiManager guiManager;

	private static Universe universe;

	@Override
	public void create() {
		registry = new Registry();
		registry.initialize();
		picasso = new Picasso(registry);
	}

	@Override
	public void render() {
		picasso.render(universe);
	}
	
	@Override
	public void resize(int width, int height) {
		picasso.resize(width, height);
	}

	@Override
	public void dispose() {
		picasso.dispose();
		registry.dispose();
	}
	
	public Picasso getRenderingEngine() {
		return picasso;
	}
	
	public GuiManager getGuiManager() {
		return guiManager;
	}

}
