package com.github.cm360.pixadv.input;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.Component;
import com.github.cm360.pixadv.graphics.gui.components.Menu;

public class GuiInputProcessor extends AbstractInputProcessor {

	private ClientApplication client;
	
	private Set<Integer> heldModifiers;
	
	public GuiInputProcessor(ClientApplication client) {
		super();
		this.client = client;
		heldModifiers = new HashSet<Integer>();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (Character.isISOControl(keycode)) {
			heldModifiers.add(keycode);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Character.isISOControl(keycode)) {
			heldModifiers.remove(keycode);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		client.getGuiManager().getCurrentMenu();
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Menu menu = client.getGuiManager().getCurrentMenu();
		if (menu != null) {
			Vector2 clickPos = new Vector2(screenX, client.getRenderingEngine().getViewportHeight() - screenY);
			Component component = menu.attemptFocus(clickPos);
//			if (component != null)
//				component.interactClick(clickPos, 100, new KeyCombo(button, Set.copyOf(heldModifiers)));
			return true;
		} else {
			
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

}
