package com.github.cm360.pixadv.graphics.picasso;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.Deflater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.cm360.pixadv.environment.Universe;
import com.github.cm360.pixadv.graphics.gui.GuiManager;
import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.registry.Registry;
import com.github.cm360.pixadv.util.Logger;

public class Picasso {
	
	private Registry registry;
	private GuiManager guiManager;
	
	private int viewportWidth;
	private int viewportHeight;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public boolean showUI;
	public boolean showDebug;
	
	private File screenshotsDir;
	private DateTimeFormatter screenshotNameFormatter;
	
	private BitmapFont font;

	public Picasso(Registry registry, GuiManager guiManager) {
		font = new BitmapFont();
		// Important objects
		this.registry = registry;
		this.guiManager = guiManager;
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		// Debug toggles
		showUI = true;
		showDebug = true;
		// Screenshot directories
		screenshotsDir = new File(Gdx.files.getLocalStoragePath(), "screenshots");
		screenshotNameFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss.SSS");	
	}
	
	public void render(Universe universe) {
		// Prepare for drawing
		ScreenUtils.clear(0f, 0f, 0f, 1f);
		camera.setToOrtho(false, viewportWidth, viewportHeight);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Draw
		if (registry.isInitialized()) {
			batch.draw(registry.getTexture(new Identifier("pixadv", "textures/tiles/missing.png")), 0, 0, 200, 200);
			renderWorld(universe);
			renderGui();
		} else {
			// TODO draw loading registry message
		}
		// Finalize
		batch.end();
	}
	
	private void renderWorld(Universe universe) {
		if (universe != null) {

		}
	}
	
	private void renderGui() {
		if (showUI) {
			// Draw HUD
			for (Layer hud : guiManager.getHudLayers()) {
				hud.paint(batch, registry);
			}
			// Draw menu
			Menu menu = guiManager.getCurrentMenu();
			if (menu != null) {
				menu.paint(batch, registry);
			}
			// Draw overlays
			for (Layer overlay : guiManager.getOverlays()) {
				overlay.paint(batch, registry);
			}
			// Draw debug info
			if (showDebug)
				renderDebugInfo();
		}
		
	}
	
	private void renderDebugInfo() {
		// Draw debug info
		font.setColor(Color.WHITE);
		font.draw(batch, String.format("%s FPS", Gdx.graphics.getFramesPerSecond()), 5, viewportHeight - 5);
	}
	
	public void resize(int width, int height) {
		this.viewportWidth = width;
		this.viewportHeight = height;
        Gdx.gl.glViewport(0, 0, width, height);
	}
	
	public int getViewportWidth() {
		return viewportWidth;
	}
	
	public int getViewportHeight() {
		return viewportHeight;
	}
	
	public void setFullscreen(boolean fullscreen) {
		if (fullscreen) {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		} else {
			Gdx.graphics.setWindowedMode(800, 500);
		}
	}
	
	public boolean isFullscreen() {
		return Gdx.graphics.isFullscreen();
	}
	
	public void takeScreenshot() {
		Gdx.app.postRunnable(() -> {
			try {
				Pixmap screenshotPixmap = Pixmap.createFromFrameBuffer(0, 0, 
						Gdx.graphics.getBackBufferWidth(),
						Gdx.graphics.getBackBufferHeight());
				String screenshotFilePath = screenshotsDir.getPath() + File.separator
						+ LocalDateTime.now().format(screenshotNameFormatter) + ".png";
				PixmapIO.writePNG(
						Gdx.files.external(screenshotFilePath),
						screenshotPixmap, Deflater.DEFAULT_COMPRESSION, true);
				screenshotPixmap.dispose();
				Logger.logMessage(Logger.INFO, "Saved screenshot as %s", Gdx.files.external(screenshotFilePath).file().getCanonicalFile());
			} catch (Exception e) {
				Logger.logException("Exception while saving screenshot!", e);
			}
		});
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
