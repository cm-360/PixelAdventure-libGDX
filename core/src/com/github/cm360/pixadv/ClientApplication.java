package com.github.cm360.pixadv;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.events.EventHandler;
import com.github.cm360.pixadv.events.EventListener;
import com.github.cm360.pixadv.events.EventManager;
import com.github.cm360.pixadv.events.EventManager.SyncType;
import com.github.cm360.pixadv.graphics.gui.jarvis.Jarvis;
import com.github.cm360.pixadv.graphics.picasso.Picasso;
import com.github.cm360.pixadv.input.FunctionInputProcessor;
import com.github.cm360.pixadv.input.GuiInputProcessor;
import com.github.cm360.pixadv.modules.builtin.events.system.UniverseLoadEvent;
import com.github.cm360.pixadv.modules.builtin.gui.menus.StartMenu;
import com.github.cm360.pixadv.network.Client;
import com.github.cm360.pixadv.network.Server;
import com.github.cm360.pixadv.registry.Registry;

public class ClientApplication extends ApplicationAdapter implements EventListener {

	public static final String name = "PixelAdventure";
	public static final int versionMajor = 0;
	public static final int versionMinor = 0;
	public static final int versionPatch = 1;
	
	private static Registry registry;
	private static Picasso picasso;
	private static EventManager eventManager;
	private static Jarvis jarvis;

	private static Client client;
	private static Server internalServer;

	@Override
	public void create() {
		// Create managers
		registry = new Registry();
		eventManager = new EventManager();
		eventManager.registerHandlers(this);
		jarvis = new Jarvis();
		picasso = new Picasso(registry, jarvis);
		// Create input handlers
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new FunctionInputProcessor());
		inputMultiplexer.addProcessor(new GuiInputProcessor());
		Gdx.input.setInputProcessor(inputMultiplexer);
		// Initialize registry
		registry.initialize();
		// Create client
		client = new Client();
		// Open start menu
		jarvis.openMenu(new StartMenu());
	}

	@Override
	public void render() {
		eventManager.tick(SyncType.RENDER);
		picasso.render(getUniverse());
	}
	
	@Override
	public void resize(int width, int height) {
		picasso.resize(width, height);
	}

	@Override
	public void dispose() {
		eventManager.exit();
		picasso.dispose();
		registry.dispose();
	}
	
	@EventHandler(sync = SyncType.ASYNC)
	public void onUniverseLoadEvent(UniverseLoadEvent event) {
		loadUniverse(event.getDirectory());
	}
	
	public static void loadUniverse(File universeDirectory) {
		internalServer = new Server();
		internalServer.load(universeDirectory);
		client.link(internalServer);
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
	
	public static Jarvis getGuiManager() {
		return jarvis;
	}
	
	public static Client getClient() {
		return client;
	}
	
	public static Universe getUniverse() {
		if (client != null) {
			return client.getUniverse();
		} else {
			return null;
		}
	}
	
	public static String getVersionString() {
		return String.format("%d.%d.%d", versionMajor, versionMinor, versionPatch);
	}

}
