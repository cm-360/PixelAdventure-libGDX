package com.github.cm360.pixadv.modules.builtin.gui.menus;

import java.util.Set;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.components.generic.TextField;
import com.github.cm360.pixadv.network.packets.StringPacket;
import com.github.cm360.pixadv.registry.Identifier;

public class ChatMenu extends Menu {

	protected Identifier chatFont;
	protected TextField inputField;
	
	public ChatMenu() {
		super();
		chatFont = ClientApplication.getRenderingEngine().getGameFontId();
		inputField = new TextField(this, parentBounds -> {
			return new Rectangle(10, 220, 100, 20);
		}, chatFont, 20);
		inputField.setText("");
		children.add(inputField);
	}
	
	@Override
	public void interactType(char character, Set<Integer> modifiers) {
		// Is control key held? (Command on Mac)
		boolean ctrl = modifiers.contains(Keys.CONTROL_LEFT) || modifiers.contains(Keys.CONTROL_RIGHT);		
		// Check for special characters
		if (character == '\b') { // Backspace
			if (ctrl) {
				inputField.setText("");
			} else {
				String text = inputField.getText();
				inputField.setText(text.substring(0, Math.max(0, text.length() - 1)));
			}
		} else if (character == '\n') { // Enter
			String text = inputField.getText();
			ClientApplication.getClient().send(StringPacket.create(text));
			inputField.setText("");
			
		} else {
			inputField.setText(inputField.getText() + character);
		}
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
	}

}
