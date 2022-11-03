package com.github.cm360.pixadv.graphics.picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.jarvis.Jarvis;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.registry.Registry;
import com.github.cm360.pixadv.util.Logger;

public class Picasso {
	
	private Registry registry;
	private Jarvis guiManager;
	
	public static final int defaultWindowWidth = 1280;
	public static final int defaultWindowHeight = 720;
	private int viewportWidth;
	private int viewportHeight;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public boolean showUI;
	public boolean showDebug;
	
	private FileHandle screenshotsDir;
	private DateTimeFormatter screenshotNameFormatter;
	
	private Identifier defaultFontId;
	private int defaultFontSize;
	private BitmapFont defaultFont;

	public Picasso(Registry registry, Jarvis guiManager) {
		// Important objects
		this.registry = registry;
		this.guiManager = guiManager;
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		// Debug toggles
		showUI = true;
		showDebug = true;
		// Screenshot directories
		screenshotsDir = Gdx.files.local("screenshots");
		screenshotNameFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss.SSS");
		// Default font
		defaultFontId = new Identifier("pixadv", "fonts/Style-7/PixelFont7");
		defaultFontSize = 16;
	}
	
	public void render(Universe universe) {
		// Prepare for drawing
		ScreenUtils.clear(0f, 0f, 0f, 1f);
		camera.setToOrtho(false, viewportWidth, viewportHeight);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Grab default font
		defaultFont = registry.getFont(defaultFontId, defaultFontSize);
		// Draw
		if (registry.isInitialized()) {
			renderWorld(universe);
			renderGui();
		} else {
			// TODO draw loading registry message
		}
		// Finalize
		batch.end();
	}
	
	private void renderWorld(Universe universe) {
		batch.draw(registry.getTexture(new Identifier("pixadv", "textures/tiles/missing")), 0, 0);
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
			if (showDebug) {
				renderDebugInfo();
			} else {
				// Show FPS counter
				defaultFont.setColor(Color.WHITE);
				defaultFont.draw(batch, String.format("%s FPS", Gdx.graphics.getFramesPerSecond()), 5, viewportHeight - 5);
			}
		}
		
	}
	
	private void renderDebugInfo() {
		// Generate debug text
		List<String> linesLeft = new ArrayList<String>();
		List<String> linesRight = new ArrayList<String>();
		// Rendering info
		linesLeft.add(String.format("%d FPS", Gdx.graphics.getFramesPerSecond()));
		linesLeft.add(String.format("%dx%d %dHz",
				viewportWidth,
				viewportHeight,
				Gdx.graphics.getDisplayMode().refreshRate));
		GLVersion glVersion = Gdx.graphics.getGLVersion();
		linesLeft.add(String.format("%s %d.%d.%d (%s)",
				glVersion.getType(),
				glVersion.getMajorVersion(),
				glVersion.getMinorVersion(),
				glVersion.getReleaseVersion(),
				glVersion.getVendorString()));
		linesLeft.add(glVersion.getRendererString());
		linesLeft.add(null);
		// CPU info
		linesLeft.add(String.format("%s %s %s",
				System.getProperty("os.name"),
				System.getProperty("os.version"),
				System.getProperty("os.arch")));
		linesLeft.add(String.format("x%d %s",
				Runtime.getRuntime().availableProcessors(),
				"Epic CPU Brand @ 29 GigaLOLs/MegaBruh"));
		linesLeft.add(null);
		// Runtime info
		linesLeft.add(String.format("Java %s", Runtime.version()));
		long totalMem = Runtime.getRuntime().totalMemory() / (1024 * 1024);
		long freeMem = Runtime.getRuntime().freeMemory() / (1024 * 1024);
		long usedMem = totalMem - freeMem;
		linesLeft.add(String.format("%.0f%% %d/%dMiB",
				((float) usedMem / (float) totalMem) * 100,
				usedMem,
				totalMem));
		// Game info
		linesRight.add(String.format("%s v%s",
				ClientApplication.name,
				ClientApplication.getVersionString()));
//		linesRight.add(String.format("", 
//				registry.));
		// Draw text
		int padding = 5;
		int spacers;
		defaultFont.setColor(Color.WHITE);
		// Left
		spacers = 0;
		for (int i = 0; i < linesLeft.size(); i++) {
			String line = linesLeft.get(i);
			if (line != null) {
				defaultFont.draw(batch, line,
						padding,
						(viewportHeight - padding) - (i * defaultFontSize) + (spacers * (defaultFontSize / 2)));
			} else {
				spacers++;
			}
		}
		// Right
		spacers = 0;
		for (int i = 0; i < linesRight.size(); i++) {
			String line = linesRight.get(i);
			if (line != null) {
				defaultFont.draw(batch, line,
						padding,
						(viewportHeight - padding) - (i * defaultFontSize) + (spacers * (defaultFontSize / 2)),
						viewportWidth - (2 * padding),
						Align.right, false);
			} else {
				spacers++;
			}
		}
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
			Gdx.graphics.setWindowedMode(defaultWindowWidth, defaultWindowHeight);
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
				FileHandle screenshotFile = screenshotsDir.child(
						LocalDateTime.now().format(screenshotNameFormatter) + ".png");
				PixmapIO.writePNG(screenshotFile, screenshotPixmap, Deflater.DEFAULT_COMPRESSION, true);
				screenshotPixmap.dispose();
				Logger.logMessage(Logger.INFO, "Saved screenshot as %s", screenshotFile.file().getCanonicalFile());
			} catch (Exception e) {
				Logger.logException("Exception while saving screenshot!", e);
			}
		});
	}
	
	public void dispose() {
		batch.dispose();
	}

}
