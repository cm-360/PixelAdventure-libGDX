package com.github.cm360.pixadv.modules.builtin.gui.menus;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Rectangle;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.components.generic.Button;
import com.github.cm360.pixadv.graphics.gui.components.generic.Image;
import com.github.cm360.pixadv.input.KeyCombo;
import com.github.cm360.pixadv.registry.Identifier;

public class StartMenu extends Menu {

	public StartMenu() {
		super();
		// Main font
		Identifier menuFont = new Identifier("pixadv", "fonts/Style-7/PixelFont7");
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
//			client.addTask(() -> {
//				client.load(new File(".\\data\\saves\\Universe Zero"));
//				// pixadv:textures/entities/girl
//				HumanPlayer player = new HumanPlayer(client.getRegistry().getTexture(Identifier.parse("pixadv:mario")));
//				client.getCurrentUniverse().getCurrentWorld().addEntity(client.getPlayerId(), player);
//			});
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
			// ClientApplication.connect("127.0.0.1", 43234);
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
			// TODO quit
		});
		children.add(quitButton);
	}
	
	@Override
	public void onClose() {

	}

}
