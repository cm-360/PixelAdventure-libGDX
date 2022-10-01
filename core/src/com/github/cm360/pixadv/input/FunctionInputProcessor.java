package com.github.cm360.pixadv.input;

import com.badlogic.gdx.Input.Keys;
import com.github.cm360.pixadv.ClientApplication;

public class FunctionInputProcessor extends AbstractInputProcessor {

	private ClientApplication client;
	
	public FunctionInputProcessor(ClientApplication client) {
		super();
		this.client = client;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		// Toggle UI
		case Keys.F1:
			client.getRenderingEngine().showUI = !client.getRenderingEngine().showUI;
			break;
		// Take screenshot
		case Keys.F2:
			client.getRenderingEngine().takeScreenshot();
			break;
		// Toggle fullscreen
		case Keys.F11:
			client.getRenderingEngine().setFullscreen(!client.getRenderingEngine().isFullscreen());
			break;
		// Toggle debug UI
		case Keys.F12:
			client.getRenderingEngine().showDebug = !client.getRenderingEngine().showDebug;
			break;
		default:
			return false;
		}
		return false;
	}
	
}
