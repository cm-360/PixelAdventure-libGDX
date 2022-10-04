package com.github.cm360.pixadv.input;

import com.badlogic.gdx.Input.Keys;
import com.github.cm360.pixadv.ClientApplication;

public class FunctionInputProcessor extends AbstractInputProcessor {

	public FunctionInputProcessor() {
		super();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		// Toggle UI
		case Keys.F1:
			ClientApplication.getRenderingEngine().showUI = !ClientApplication.getRenderingEngine().showUI;
			break;
		// Take screenshot
		case Keys.F2:
			ClientApplication.getRenderingEngine().takeScreenshot();
			break;
		// Garbage collect
		case Keys.F3:
			System.gc();
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
