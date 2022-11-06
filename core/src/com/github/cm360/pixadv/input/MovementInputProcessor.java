package com.github.cm360.pixadv.input;

import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.picasso.Picasso;

public class MovementInputProcessor extends AbstractInputProcessor {

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		ClientApplication.getRenderingEngine().setMouseScreenPos(screenX, screenY);
		return true;
	}
	
	@Override
	public boolean scrolled(float x, float y) {
		Picasso p = ClientApplication.getRenderingEngine();
		p.setTileScale(Math.max(1, Math.min(16, p.getTileScale() - y)));
		return true;
	}

}
