package com.github.cm360.pixadv.modules.builtin.gui.menus;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Rectangle;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.events.EventManager.SyncType;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.components.generic.Button;
import com.github.cm360.pixadv.graphics.gui.components.generic.Image;
import com.github.cm360.pixadv.input.KeyCombo;
import com.github.cm360.pixadv.modules.builtin.events.system.ServerConnectEvent;
import com.github.cm360.pixadv.modules.builtin.events.system.UniverseLoadEvent;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.util.Logger;

public class StartMenu extends Menu {

	public StartMenu() {
		super();
		// Main font
		Identifier menuFont = ClientApplication.getRenderingEngine().getGameFontId();
		// Logo
		Image logoImage = new Image(this, parentBounds -> {
			return new Rectangle(
					(parentBounds.getWidth() * 0.5f) - (155f * scale),
					(parentBounds.getHeight() * 0.5f) + (10f * scale),
					310f * scale,
					110f * scale
				);
		});
		logoImage.setTextures(new Identifier[] { Identifier.parse("pixadv:textures/gui/menu/title/logo") });
		children.add(logoImage);
		// Singleplayer button
		Button singleplayerButton = new Button(this, parentBounds -> {
			return new Rectangle(
					(parentBounds.getWidth() * 0.5f) - (120f * scale),
					(parentBounds.getHeight() * 0.5f) - (48f * scale),
					240f * scale,
					32f * scale
				);
		}, "Singleplayer", menuFont, 24);
		singleplayerButton.setTextures(new Identifier[] { Identifier.parse("pixadv:textures/gui/menu/title/singleplayer") });
		singleplayerButton.registerEvent(new KeyCombo(Buttons.LEFT), () -> {
			// TODO load singleplayer menu
			ClientApplication.getGuiManager().closeMenu();
			ClientApplication.getGuiManager().openMenu(new ChatMenu());
			ClientApplication.getEventManager().queue(
					new UniverseLoadEvent(new File("universes/Universe Zero")),
					SyncType.ASYNC);
//			HumanPlayer player = new HumanPlayer(client.getRegistry().getTexture(Identifier.parse("pixadv:mario")));
//			client.getCurrentUniverse().getCurrentWorld().addEntity(client.getPlayerId(), player);
		});
		children.add(singleplayerButton);
		// Multiplayer button
		Button multiplayerButton = new Button(this, parentBounds -> {
			return new Rectangle(
					(parentBounds.getWidth() * 0.5f) - (120f * scale),
					(parentBounds.getHeight() * 0.5f) - (84f * scale),
					240f * scale,
					32f * scale
				);
		}, "Multiplayer", menuFont, 24);
		multiplayerButton.setTextures(new Identifier[] { Identifier.parse("pixadv:textures/gui/menu/title/multiplayer") });
		multiplayerButton.registerEvent(new KeyCombo(Buttons.LEFT), () -> {
			// TODO load multiplayer menu
			ClientApplication.getGuiManager().closeMenu();
			try {
				ClientApplication.getEventManager().queue(
						new ServerConnectEvent(InetAddress.getByName("localhost"), 43234),
						SyncType.ASYNC);
			} catch (UnknownHostException e) {
				Logger.logException("Unknown host", e);
			}
		});
		children.add(multiplayerButton);
		// Options button
		Button optionsButton = new Button(this, parentBounds -> {
			return new Rectangle(
					(parentBounds.getWidth() * 0.5f) - (120f * scale),
					(parentBounds.getHeight() * 0.5f) - (120f * scale),
					118f * scale,
					32f * scale
				);
		}, "Options", menuFont, 24);
		optionsButton.setTextures(new Identifier[] { Identifier.parse("pixadv:textures/gui/menu/title/options") });
		optionsButton.registerEvent(new KeyCombo(Buttons.LEFT), () -> {
			// TODO load options menu
			System.out.println("options");
		});
		children.add(optionsButton);
		// Quit button
		Button quitButton = new Button(this, parentBounds -> {
			return new Rectangle(
					(parentBounds.getWidth() * 0.5f) + (2f * scale),
					(parentBounds.getHeight() * 0.5f) - (120f * scale),
					118f * scale,
					32f * scale
				);
		}, "Quit", menuFont, 24);
		quitButton.setTextures(new Identifier[] { Identifier.parse("pixadv:textures/gui/menu/title/quit") });
		quitButton.registerEvent(new KeyCombo(Buttons.LEFT), () -> {
			Gdx.app.exit();
		});
		children.add(quitButton);
	}
	
	@Override
	public void onClose() {

	}

}
