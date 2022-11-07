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
		// Load assets from builtin module
		String builtinModuleId = "pixadv";
		String[] assets = new String(Gdx.files.internal("assets.txt").readBytes()).split("\n");
		for (String assetFilename : assets) {
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
		modules.put(builtinModuleId, ClientApplication.getVersionString());
		// Load external modules
		for (FileHandle moduleFilename : Gdx.files.local("modules").list())
			System.out.println(moduleFilename);
		// Done
		initialized = true;
	}
	
	private void loadAsset(Asset asset) throws RegistryException {
		try {
			AssetType type = asset.getType();
			if (type == null) {
				Logger.logMessage(Logger.WARNING, "Unknown type for asset '%s'!", asset.getId());
			} else {
				// Parse bytes by file type
				switch (type) {
				case TEXTURE:
					// Texture
					Pixmap pixmap = new Pixmap(asset.getHandle());
					textures.put(asset.getId(), new Texture(pixmap));
					pixmap.dispose();
					break;
				case SOUND:
					// Sound
					
					break;
				case TRANSLATION:
					// Translations file

					break;
				case FONT:
					// Font file
					FreeTypeFontGenerator generator = new FreeTypeFontGenerator(asset.getHandle());
					fontGenerators.put(asset.getId(), generator);
					break;
				}
				Logger.logMessage(Logger.DEBUG, "Loaded asset '%s'", asset.getId());
			}
		} catch (Exception e) {
			throw new RegistryException(String.format("Failed to load asset '%s'!", asset.getId()), e);
		}
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
