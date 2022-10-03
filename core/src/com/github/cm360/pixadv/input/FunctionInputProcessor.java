package com.github.cm360.pixadv.input;

import com.badlogic.gdx.Input.Keys;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.modules.builtin.events.gui.OpenMenuEvent;

public class FunctionInputProcessor extends AbstractInputProcessor {

	public FunctionInputProcessor() {
		super();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		// Main menu
		case Keys.ESCAPE:
			ClientApplication.getEventManager().queue(new OpenMenuEvent());
			break;
		// Toggle UI
		case Keys.F1:
			ClientApplication.getRenderingEngine().showUI = !ClientApplication.getRenderingEngine().showUI;
			break;
		// Take screenshot
		case Keys.F2:
			ClientApplication.getRenderingEngine().takeScreenshot();
			break;
		// Toggle fullscreen
		case Keys.F11:
			ClientApplication.getRenderingEngine().setFullscreen(!ClientApplication.getRenderingEngine().isFullscreen());
			break;
		// Toggle debug UI
		case Keys.F12:
			ClientApplication.getRenderingEngine().showDebug = !ClientApplication.getRenderingEngine().showDebug;
			break;
		default:
			return false;
		}
		return false;
	}
	
}
