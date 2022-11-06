package com.github.cm360.pixadv.graphics.picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.environment.storage.Chunk;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.environment.storage.World;
import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.graphics.gui.components.Layer;
import com.github.cm360.pixadv.graphics.gui.components.Menu;
import com.github.cm360.pixadv.graphics.gui.jarvis.Jarvis;
import com.github.cm360.pixadv.modules.builtin.tiles.capabilities.LightEmitter;
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
	private float worldCamX;
	private float worldCamY;
	private float worldCamXDelta;
	private float worldCamYDelta;
	private float worldCamXTarget;
	private float worldCamYTarget;
	// Tile sizes
	private int tileSize;
	private float tileScale;
	private float tileSizeScaled;
	// Screen center
	private int centerX;
	private int centerY;
	// Visible chunks
	private int minCX;
	private int minCY;
	private int maxCX;
	private int maxCY;
	
	// UI rendering flags
	private boolean showUI;
	private boolean showDebug;
	private List<String> debugLinesLeft;
	private List<String> debugLinesRight;
	
	// Lighting things
	private Pixmap lightPixmap;
	private Texture lightTexture;
	
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
		setTileScale(2f);
		// Debug variables
		showUI = true;
		showDebug = true;
		debugLinesLeft = new ArrayList<String>();
		debugLinesRight = new ArrayList<String>();
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
			if (universe != null) {
				World world = universe.getWorld("GENTEST");
				if (world != null) {
					renderWorld(world);
				}
			}
			renderGui(universe);
		} else {
			// TODO draw loading registry message
		}
		// Finalize
		batch.end();
		if (lightTexture != null)
			lightTexture.dispose();
	}
	
	private void renderWorld(World world) {
		int chunkSize = world.getChunkSize();
		// Viewport center
		centerX = (int) ((viewportWidth / 2) - tileSizeScaled / 2);
		centerY = (int) ((viewportHeight / 2) - tileSizeScaled / 2);
		// Calculate world camera bounds
		float overscan = 2f;
		minCX = (int) Math.round(((worldCamX * tileSizeScaled - viewportWidth / 2.0)) / (chunkSize * tileSizeScaled) - overscan);
		minCY = (int) Math.round(((worldCamY * tileSizeScaled - viewportHeight / 2.0)) / (chunkSize * tileSizeScaled) - overscan);
		maxCX = minCX + (int) Math.round(viewportWidth / (chunkSize * tileSizeScaled) + (2 * overscan));
		maxCY = minCY + (int) Math.round(viewportHeight / (chunkSize * tileSizeScaled) + (2 * overscan));
		// Get camera movement inputs
		float speed = 100f;
		worldCamXDelta = (
				(Gdx.input.isKeyPressed(Keys.LEFT) ? -speed : 0) + 
				(Gdx.input.isKeyPressed(Keys.RIGHT) ? speed : 0));
		worldCamYDelta = (
				(Gdx.input.isKeyPressed(Keys.UP) ? speed : 0) + 
				(Gdx.input.isKeyPressed(Keys.DOWN) ? -speed : 0));
		// Update camera position
		worldCamXTarget += worldCamXDelta / targetFPS;
		worldCamYTarget += worldCamYDelta / targetFPS;
		worldCamX += (worldCamXTarget - worldCamX) / (targetFPS / 7);
		worldCamY += (worldCamYTarget - worldCamY) / (targetFPS / 7);
		// Render world parts
		renderSky(world);
		renderTileGrid(world);
		renderEntities(world);
		renderLightmap(world);
	}
	
	private void renderSky(World world) {
		Texture skyTex = registry.getTexture(world.getSkyTexture());
		if (skyTex != null) {
			skyTex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
			// Overscan sky texture to let linear filtering make a better gradient
			batch.draw(skyTex, 0, (-viewportHeight / 2), viewportWidth, viewportHeight * 2);
		}
	}
	
	private void renderTileGrid(World world) {
		for (int l = 0; l < 3; l++) {
			// Darken background layer
			if (l == 0) {
				batch.setColor(0.5F, 0.5F, 0.5F, 1F);
			} else {
				batch.setColor(1F, 1F, 1F, 1F);
			}
			// Draw tiles
			for (int cx = minCX; cx < maxCX; cx++) {
				for (int cy = Math.max(0, minCY); cy < Math.min(world.getHeight(), maxCY); cy++) {
					Chunk chunk = world.getChunk(cx, cy);
					int chunkSize = world.getChunkSize();
					for (int xc = 0; xc < chunkSize; xc++) {
						for (int yc = 0; yc < chunkSize; yc++) {
							int x = (cx * chunkSize) + xc;
							int y = (cy * chunkSize) + yc;
							Tile tile = chunk.getTile(xc, yc, l);
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
	
	private void renderEntities(World world) {
		
	}
	
	/**
	 * Calculates lighting values and renders the resulting texture to the screen.
	 * This is the function I would say I am most proud of, it was a challenge for
	 * me to get it working properly and not make a memory hog :)
	 * 
	 * Lighting is calculated in three stages:
	 * 1. Draw all light sources to the texture and propagate in the forward XY
	 *    directions
	 * 2. Propagate in the backward XY directions
	 * 3. Increase contrast to avoid darkness bleeding
	 * 
	 * The propagation flood fill algorithm is based entirely off this YouTube
	 * video: https://www.youtube.com/watch?v=0knk78UYlvc
	 * 
	 * @param world The world to render lighting for
	 */
	private void renderLightmap(World world) {
		// Create new light texture if needed
		if (lightPixmap == null) {
			createLightmap(world);
		}
		// Draw light sources and propagate forwards
		int chunkSize = world.getChunkSize();
		lightPixmap.setColor(Color.BLACK);
		lightPixmap.fillRectangle(0, 0, lightPixmap.getWidth(), lightPixmap.getHeight());
		Color thisColor = Color.BLACK.cpy();
		Color prevColor1 = Color.BLACK.cpy();
		Color prevColor2 = Color.BLACK.cpy();
		for (int xp = 0; xp < lightPixmap.getWidth(); xp++) {
			for (int yp = 0; yp < lightPixmap.getHeight(); yp++) {
				// Tile coordinates
				int x = (minCX * chunkSize) + xp;
				int y = (minCY * chunkSize) + (lightPixmap.getHeight() - yp - 1);
				// Set color for light emitters
				if (y > 0 && y < world.getHeight() * chunkSize) {
					Tile tile = world.getTile(x, y, 2);
					if (tile instanceof LightEmitter) {
						lightPixmap.setColor(Color.CLEAR);
						lightPixmap.drawPixel(xp, yp);
					}
					tile = world.getTile((minCX * chunkSize) + xp, y, 0);
					if (tile == null) {
						lightPixmap.setColor(Color.CLEAR);
						lightPixmap.drawPixel(xp, yp);
					}
				}
				// Propagate forwards
				Color.rgba8888ToColor(thisColor, lightPixmap.getPixel(xp, yp));
				Color.rgba8888ToColor(prevColor1, lightPixmap.getPixel(xp, yp - 1));
				Color.rgba8888ToColor(prevColor2, lightPixmap.getPixel(xp - 1, yp));
				thisColor.a = Math.min(thisColor.a, Math.min(prevColor1.a, prevColor2.a));
				thisColor.a += 0.05f;
				lightPixmap.setColor(thisColor.clamp());
				lightPixmap.drawPixel(xp, yp);
			}
		}
		// Propagate backwards
		for (int x = lightPixmap.getWidth(); x >= 0; x--) {
			for (int y = lightPixmap.getHeight(); y >= 0; y--) {
				Color.rgba8888ToColor(thisColor, lightPixmap.getPixel(x, y));
				Color.rgba8888ToColor(prevColor1, lightPixmap.getPixel(x, y + 1));
				Color.rgba8888ToColor(prevColor2, lightPixmap.getPixel(x + 1, y));
				thisColor.a = Math.min(thisColor.a, Math.min(prevColor1.a, prevColor2.a));
				thisColor.a += 0.05f;
				lightPixmap.setColor(thisColor.clamp());
				lightPixmap.drawPixel(x, y);
			}
		}
		// Adjust
		for (int x = 0; x < lightPixmap.getWidth(); x++) {
			for (int y = 0; y < lightPixmap.getHeight(); y++) {
				Color.rgba8888ToColor(thisColor, lightPixmap.getPixel(x, y));
				thisColor.a = (thisColor.a - 0.5f) * 2f;
				lightPixmap.setColor(thisColor.clamp());
				lightPixmap.drawPixel(x, y);
			}
		}
		// Draw light texture
		lightTexture = new Texture(lightPixmap);
		lightTexture.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		batch.draw(
				lightTexture,
				centerX - ((worldCamX - (minCX * chunkSize)) * tileSizeScaled),
				centerY - ((worldCamY - (minCY * chunkSize)) * tileSizeScaled),
				lightTexture.getWidth() * tileSizeScaled,
				lightTexture.getHeight() * tileSizeScaled);
	}
	
	private void createLightmap(World world) {
		int chunkSize = world.getChunkSize();
		lightPixmap = new Pixmap(
				(maxCX - minCX) * chunkSize,
				(maxCY - minCY) * chunkSize,
				Format.RGBA8888);
		lightPixmap.setBlending(Blending.None);
	}
	
	private void disposeLightmap() {
		if (lightPixmap != null) {
			lightPixmap.dispose();
			lightPixmap = null;
		}
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
		debugLinesLeft.clear();
		debugLinesRight.clear();
		// Rendering info
		debugLinesLeft.add(String.format("%d/%d FPS%s",
				Gdx.graphics.getFramesPerSecond(),
				targetFPS,
				(useVSync ? " (VSync)" : "")));
		debugLinesLeft.add(String.format("%dx%d %dHz",
				viewportWidth,
				viewportHeight,
				Gdx.graphics.getDisplayMode().refreshRate));
		GLVersion glVersion = Gdx.graphics.getGLVersion();
		debugLinesLeft.add(String.format("%s %d.%d.%d (%s)",
				glVersion.getType(),
				glVersion.getMajorVersion(),
				glVersion.getMinorVersion(),
				glVersion.getReleaseVersion(),
				glVersion.getVendorString()));
		debugLinesLeft.add(glVersion.getRendererString());
		debugLinesLeft.add(null);
		// CPU info
		debugLinesLeft.add(String.format("%s %s %s",
				System.getProperty("os.name"),
				System.getProperty("os.version"),
				System.getProperty("os.arch")));
		debugLinesLeft.add(String.format("x%d %s",
				Runtime.getRuntime().availableProcessors(),
				"Epic CPU Brand @ 29 GigaLOLs/MegaBruh"));
		debugLinesLeft.add(null);
		// Runtime info
		debugLinesLeft.add(String.format("Java %s", Runtime.version()));
		int mibSize = 1024 * 1024;
		long totalMem = Runtime.getRuntime().totalMemory() / mibSize;
		long freeMem = Runtime.getRuntime().freeMemory() / mibSize;
		long usedMem = totalMem - freeMem;
		debugLinesLeft.add(String.format("%.0f%% %d/%dMiB",
				((float) usedMem / (float) totalMem) * 100,
				usedMem,
				totalMem));
		// Game info
		debugLinesRight.add(String.format("%s v%s",
				ClientApplication.name,
				ClientApplication.getVersionString()));
		debugLinesRight.add(null);
		// World info
		if (universe != null) {
			debugLinesRight.add(String.format("U: '%s' (%s)",
					universe.getName(),
					universe.getClass().getSimpleName()));
			debugLinesRight.add(String.format("W: '%s' (%s)",
					null,
					null));
			debugLinesRight.add(null);
		}
		// Camera info
		debugLinesRight.add(String.format("Camera: x%.4f y%.4f", worldCamX, worldCamY));
		debugLinesRight.add(String.format("Chunks: (%d,%d)-(%d,%d) %dx%d",
				minCX, minCY,
				maxCX, maxCY,
				maxCX - minCX, maxCY - minCY));
		if (lightPixmap != null) {
			debugLinesRight.add(String.format("Lightmap: %dx%d",
					lightPixmap.getWidth(),
					lightPixmap.getHeight()));
		}
		debugLinesRight.add(null);
		// Draw text
		int spacers;
		defaultFont.setColor(Color.WHITE);
		// Left
		spacers = 0;
		for (int i = 0; i < debugLinesLeft.size(); i++) {
			String line = debugLinesLeft.get(i);
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
		for (int i = 0; i < debugLinesRight.size(); i++) {
			String line = debugLinesRight.get(i);
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
	
	public float getTileScale() {
		return tileScale;
	}
	
	public void setTileScale(float newScale) {
		tileScale = newScale;
		tileSizeScaled = tileSize * tileScale;
		disposeLightmap();
	}
	
	public boolean isUIShown() {
		return showUI;
	}

	public void setUIShown(boolean showUI) {
		this.showUI = showUI;
	}

	public boolean isDebugShown() {
		return showDebug;
	}

	public void setDebugShown(boolean showDebug) {
		this.showDebug = showDebug;
	}

	public void resize(int width, int height) {
		this.viewportWidth = width;
		this.viewportHeight = height;
        Gdx.gl.glViewport(0, 0, width, height);
        disposeLightmap();
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
		disposeLightmap();
	}

}
