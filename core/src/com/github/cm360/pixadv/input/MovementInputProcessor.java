package com.github.cm360.pixadv.input;

import com.badlogic.gdx.Input.Keys;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.picasso.Picasso;

public class MovementInputProcessor extends AbstractInputProcessor {
	
	@Override
	public boolean keyDown(int keycode) {
		return processKey(keycode, false);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		return processKey(keycode, true);
	}
	
	public boolean processKey(int keycode, boolean release) {
		Picasso p = ClientApplication.getRenderingEngine();
		float speed = 50f;
		switch(keycode) {
		// Camera controls
		case Keys.UP:
			p.worldCamYDelta = (release ? 0f : speed);
			break;
		case Keys.DOWN:
			p.worldCamYDelta = (release ? 0f : -speed);
			break;
		case Keys.LEFT:
			p.worldCamXDelta = (release ? 0f : -speed);
			break;
		case Keys.RIGHT:
			p.worldCamXDelta = (release ? 0f : speed);
			break;
		default:
			return false;
		}
		return true;
	}
	
	@Override
	public boolean scrolled(float x, float y) {
		Picasso p = ClientApplication.getRenderingEngine();
		p.tileScale = Math.max(1, Math.min(16, p.tileScale - y));
		return true;
	}

}
