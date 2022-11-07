package com.github.cm360.pixadv.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.picasso.Picasso;

public class FunctionInputProcessor extends AbstractInputProcessor {

	public FunctionInputProcessor() {
		super();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		Picasso p = ClientApplication.getRenderingEngine();
		switch(keycode) {
		// Toggle UI
		case Keys.F1:
			p.setUIShown(!p.isUIShown());
			break;
		// Take screenshot
		case Keys.F2:
			p.takeScreenshot();
			break;
		// Garbage collect
		case Keys.F3:
			System.gc();
			break;
		// Toggle fullscreen
		case Keys.F11:
			p.setFullscreen(!p.isFullscreen());
			break;
		// Toggle debug UI
		case Keys.F12:
			p.setDebugShown(!p.isDebugShown());
			break;
		// Quit
		case Keys.ESCAPE:
			Gdx.app.exit();
			break;
		default:
			return false;
		}
		return true;
	}
	
}
