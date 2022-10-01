package com.github.cm360.pixadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.github.cm360.pixadv.environment.Universe;
import com.github.cm360.pixadv.events.EventManager;
import com.github.cm360.pixadv.graphics.gui.GuiManager;
import com.github.cm360.pixadv.graphics.picasso.Picasso;
import com.github.cm360.pixadv.input.FunctionInputProcessor;
import com.github.cm360.pixadv.input.GuiInputProcessor;
import com.github.cm360.pixadv.registry.Registry;

public class ClientApplication extends ApplicationAdapter {

	private Registry registry;
	private Picasso picasso;
	private EventManager eventManager;
	private GuiManager guiManager;

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
		inputMultiplexer.addProcessor(new FunctionInputProcessor(this));
		inputMultiplexer.addProcessor(new GuiInputProcessor(this));
		Gdx.input.setInputProcessor(inputMultiplexer);
		// Initialize registry
		registry.initialize();
	}

	@Override
	public void render() {
		// TODO handle user input
		// TODO fire events
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
