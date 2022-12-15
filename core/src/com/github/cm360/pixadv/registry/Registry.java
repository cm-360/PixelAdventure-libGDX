package com.github.cm360.pixadv.registry;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.registry.Asset.AssetType;
import com.github.cm360.pixadv.util.FileUtil;
import com.github.cm360.pixadv.util.Logger;

public class Registry {

	private boolean initialized;
	private Map<String, String> modules;
	private Map<Identifier, Texture> textures;
	private Map<Identifier, FreeTypeFontGenerator> fontGenerators;
	private Map<String, BitmapFont> fontCache;
	
	public void initialize() {
		initialized = false;
		modules = new HashMap<String, String>();
		textures = new HashMap<Identifier, Texture>();
		fontGenerators = new HashMap<Identifier, FreeTypeFontGenerator>();
		fontCache = new HashMap<String, BitmapFont>();
		// Load modules
		loadBuiltinModule();
		loadExternalModules();
		// Done
		initialized = true;
	}
	
	private void loadBuiltinModule() {
		String builtinModuleId = "pixadv";
		// Load assets from builtin module
		String[] assets = new String(Gdx.files.internal("assets.txt").readBytes()).split("\n");
		for (String assetFilename : assets)
			loadBuiltinAsset(builtinModuleId, assetFilename);
		// Record module version
		modules.put(builtinModuleId, ClientApplication.getVersionString());
	}
	
	private void loadExternalModules() {
		for (FileHandle moduleFilename : Gdx.files.local("modules").list())
			System.out.println(moduleFilename);
	}
	
	private void loadBuiltinAsset(String builtinModuleId, String assetFilename) {
		try {
			Identifier assetId = new Identifier(builtinModuleId, FileUtil.removeExtension(assetFilename));
			Asset asset = new Asset(
					Asset.getTypeByExtension(FileUtil.getExtension(assetFilename)),
					assetId,
					Gdx.files.internal(assetFilename));
			loadAsset(asset);
		} catch (Exception e) {
			Logger.logException("Failed to load asset! '%s'", e, assetFilename);
		}
	}
	
	private void loadAsset(Asset asset) throws RegistryException {
		try {
			AssetType type = asset.getType();
			// Verify known type
			if (type == null) {
				Logger.logMessage(Logger.WARNING, "Unknown type for asset '%s'!", asset.getId());
				return;
			}
			// Parse bytes by file type
			switch (type) {
			case TEXTURE:
				loadTexture(asset);
				break;
			case SOUND:
				loadSound(asset);
				break;
			case TRANSLATION:
				loadTranslations(asset);
				break;
			case FONT:
				loadFont(asset);
				break;
			}
			Logger.logMessage(Logger.DEBUG, "Loaded asset '%s'", asset.getId());
		} catch (Exception e) {
			throw new RegistryException(String.format("Failed to load asset '%s'!", asset.getId()), e);
		}
	}
	
	private void loadTexture(Asset asset) {
		Pixmap pixmap = new Pixmap(asset.getHandle());
		textures.put(asset.getId(), new Texture(pixmap));
		pixmap.dispose();
	}
	
	private void loadSound(Asset asset) {
		// TODO load sound
	}
	
	private void loadTranslations(Asset asset) {
		// TODO load translations
	}
	
	private void loadFont(Asset asset) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(asset.getHandle());
		fontGenerators.put(asset.getId(), generator);
	}
	
	public Texture getTexture(Identifier id) {
		return textures.get(id);
	}
	
	public BitmapFont getFont(Identifier id, int size) {
		String cacheId = String.format("%s#%d", id.toString(), size);
		BitmapFont font = fontCache.get(cacheId);
		if (font == null) {
			// Generate new font object and add to cache
			FreeTypeFontParameter fontParam = new FreeTypeFontParameter();
			fontParam.size = size;
			font = fontGenerators.get(id).generateFont(fontParam);
			fontCache.put(cacheId, font);
		}
		return font;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	public int getModuleCount() {
		return modules.size();
	}
	
	public void dispose() {
		textures.forEach((id, texture) -> texture.dispose());
		fontGenerators.forEach((id, fontGen) -> fontGen.dispose());
		fontCache.forEach((id, font) -> font.dispose());
	}

}
