package com.github.cm360.pixadv.modules.builtin.gui.menus;

import com.badlogic.gdx.math.Rectangle;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.components.generic.TextField;
import com.github.cm360.pixadv.registry.Identifier;

public class ChatMenu extends Menu {

	protected Identifier chatFont;
	
	public ChatMenu() {
		super();
		chatFont = ClientApplication.getRenderingEngine().getGameFontId();
		TextField inputField = new TextField(this, parentBounds -> {
			return new Rectangle(10, 220, 100, 20);
		}, chatFont, 20);
		inputField.setText("<text would go here>");
		children.add(inputField);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
	}

}
