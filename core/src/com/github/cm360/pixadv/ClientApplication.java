package com.github.cm360.pixadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.github.cm360.pixadv.environment.Universe;
import com.github.cm360.pixadv.events.EventManager;
import com.github.cm360.pixadv.graphics.gui.GuiManager;
import com.github.cm360.pixadv.graphics.picasso.Picasso;
import com.github.cm360.pixadv.input.FunctionInputProcessor;
import com.github.cm360.pixadv.input.GuiInputProcessor;
import com.github.cm360.pixadv.registry.Registry;

public class ClientApplication extends ApplicationAdapter {

	private static Registry registry;
	private static Picasso picasso;
	private static EventManager eventManager;
	private static GuiManager guiManager;

	private Universe universe;

	@Override
	public void create() {
		// Create managers
		registry = new Registry();
		eventManager = new EventManager();
		guiManager = new GuiManager();
		picasso = new Picasso(registry, guiManager);
		// Create input handlers
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new FunctionInputProcessor());
		inputMultiplexer.addProcessor(new GuiInputProcessor());
		Gdx.input.setInputProcessor(inputMultiplexer);
		// Initialize registry
		registry.initialize();
	}

	@Override
	public void render() {
		eventManager.tick();
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
	
	public static Registry getRegistry() {
		return registry;
	}
	
	public static Picasso getRenderingEngine() {
		return picasso;
	}
	
	public static EventManager getEventManager() {
		return eventManager;
	}
	
	public static GuiManager getGuiManager() {
		return guiManager;
	}

}
