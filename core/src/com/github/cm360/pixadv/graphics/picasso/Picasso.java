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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.environment.storage.World;
import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.jarvis.Jarvis;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.registry.Registry;
import com.github.cm360.pixadv.util.Logger;

public class Picasso {
	
	// Important objects (not that the rest aren't important :P)
	private Registry registry;
	private Jarvis guiManager;
	
	// Graphics things
	public static final int defaultWindowWidth = 1280;
	public static final int defaultWindowHeight = 720;
	private int targetFPS;
	private boolean useVSync;
	private int viewportWidth;
	private int viewportHeight;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Camera variables
	public float worldCamX;
	public float worldCamY;
	private int tileSize;
	private float tileScale;
	private float tileSizeScaled;
	private int centerX;
	private int centerY;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	// UI rendering flags
	public boolean showUI;
	public boolean showDebug;
	
	// Screenshot things
	private FileHandle screenshotsDir;
	private DateTimeFormatter screenshotNameFormatter;
	
	// Default font
	private Identifier defaultFontId;
	private int defaultFontSize;
	private BitmapFont defaultFont;

	public Picasso(Registry registry, Jarvis guiManager) {
		// Rendering options
		setTargetFPS(60);
		setVSync(true);
		// Important objects
		this.registry = registry;
		this.guiManager = guiManager;
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		// World camera info
		worldCamX = 0;
		worldCamY = 200;
		tileSize = 8;
		tileScale = 4f;
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
			renderGui(universe);
		} else {
			// TODO draw loading registry message
		}
		// Finalize
		batch.end();
	}
	
	private void renderWorld(Universe universe) {
		tileScale = 1f;
		// Viewport center
		centerX = (int) ((viewportWidth / 2) - tileSizeScaled / 2);
		centerY = (int) ((viewportHeight / 2) - tileSizeScaled / 2);
		// Calculate world camera bounds
		tileSizeScaled = tileSize * tileScale;
		float overscan = 0.05f;
		minX = (int) Math.round(((worldCamX * tileSizeScaled - viewportWidth / 2)) / tileSizeScaled - overscan);
		minY = (int) Math.round(((worldCamY * tileSizeScaled - viewportHeight / 2)) / tileSizeScaled - overscan);
		maxX = (int) Math.round(((worldCamX * tileSizeScaled + viewportWidth / 2)) / tileSizeScaled + (1 + overscan));
		maxY = (int) Math.round(((worldCamY * tileSizeScaled + viewportHeight / 2)) / tileSizeScaled + (1 + overscan));
		// Render world parts
		if (universe != null) {
			renderSky(universe);
			renderTileGrid(universe);
			renderEntities(universe);
			renderLightmap(universe);
		}
	}
	
	private void renderSky(Universe universe) {
		Texture skyTex = registry.getTexture(new Identifier("pixadv", "textures/environment/terra/sky"));
		skyTex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		// Overscan sky texture to let linear filtering make a better gradient
		batch.draw(skyTex, 0, (-viewportHeight / 2), viewportWidth, viewportHeight * 2);
	}
	
	private void renderTileGrid(Universe universe) {
		World world = universe.getWorld("GENTEST");
		for (int l = 0; l < 3; l++) {
			// Darken background layer
			if (l == 0) {
				batch.setColor(0.5F, 0.5F, 0.5F, 1F);
			} else {
				batch.setColor(1F, 1F, 1F, 1F);
			}
			// Draw tiles
			for (int x = minX; x < maxX; x++) {
				for (int y = Math.max(0, minY); y < Math.min(world.getHeight() * world.getChunkSize(), maxY); y++) {
					Tile tile = world.getTile(x, y, l);
					if (tile != null) {
						for (Identifier textureId : tile.getTextures()) {
							if (textureId != null) {
								renderTile(registry.getTexture(textureId), x, y);
							}
						}
					}
				}
			}
		}
		renderTile(registry.getTexture(new Identifier("pixadv", "textures/gui/tile_hover")), 7, 7);
	}
	
	private void renderTile(Texture texture, int x, int y) {
		batch.draw(
				texture,
				centerX - ((worldCamX - x) * tileSizeScaled),
				centerY - ((worldCamY - y) * tileSizeScaled),
				tileSizeScaled, tileSizeScaled);
	}
	
	private void renderEntities(Universe universe) {
		
	}
	
	private void renderLightmap(Universe universe) {
//		batch.end();
//		com.badlogic.gdx.physics.box2d.World world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0),false);
//		RayHandler rayHandler = new RayHandler(world);
//		rayHandler.setCombinedMatrix(camera);   //<-- pass your camera combined matrix
//		new PointLight(rayHandler, 5, Color.WHITE, 1000, viewportWidth / 2, viewportHeight / 2);
//		rayHandler.updateAndRender();
//		batch.begin();
		
	}
	
	private void renderGui(Universe universe) {
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
			int overlayTextPadding = 5;
			if (showDebug) {
				renderDebugInfo(universe, overlayTextPadding);
			} else {
				// Show FPS counter
				defaultFont.setColor(Color.WHITE);
				defaultFont.draw(batch,
						String.format("%s FPS", Gdx.graphics.getFramesPerSecond()),
						overlayTextPadding,
						viewportHeight - overlayTextPadding);
			}
		}
	}
	
	private void renderDebugInfo(Universe universe, int padding) {
		// Generate debug text
		List<String> linesLeft = new ArrayList<String>();
		List<String> linesRight = new ArrayList<String>();
		// Rendering info
		linesLeft.add(String.format("%d/%d FPS%s",
				Gdx.graphics.getFramesPerSecond(),
				targetFPS,
				(useVSync ? " (VSync)" : "")));
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
		int mibSize = 1024 * 1024;
		long totalMem = Runtime.getRuntime().totalMemory() / mibSize;
		long freeMem = Runtime.getRuntime().freeMemory() / mibSize;
		long usedMem = totalMem - freeMem;
		linesLeft.add(String.format("%.0f%% %d/%dMiB",
				((float) usedMem / (float) totalMem) * 100,
				usedMem,
				totalMem));
		// Game info
		linesRight.add(String.format("%s v%s",
				ClientApplication.name,
				ClientApplication.getVersionString()));
		linesRight.add(null);
		// World info
		if (universe != null) {
			linesRight.add(String.format("U: '%s' (%s)",
					universe.getName(),
					universe.getClass().getSimpleName()));
			linesRight.add(String.format("W: '%s' (%s)",
					null,
					null));
			linesRight.add(null);
		}
		// Camera info
		linesRight.add(String.format("C: x%.4f y%.4f", worldCamX, worldCamY));
		linesRight.add(String.format("V: (%d,%d)-(%d,%d)",
				minX, minY,
				maxX, maxY));
		linesRight.add(null);
		// Draw text
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
	
	public void setTargetFPS(int fps) {
		Gdx.graphics.setForegroundFPS(targetFPS = fps);
	}
	
	public int getTargetFPS() {
		return targetFPS;
	}
	
	public void setVSync(boolean vsync) {
		Gdx.graphics.setVSync(useVSync = vsync);
	}
	
	public boolean getVSync() {
		return useVSync;
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
