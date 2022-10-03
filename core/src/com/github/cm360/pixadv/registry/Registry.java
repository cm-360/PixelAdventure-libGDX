package com.github.cm360.pixadv.registry;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.github.cm360.pixadv.registry.Asset.AssetType;
import com.github.cm360.pixadv.util.FileUtil;
import com.github.cm360.pixadv.util.Logger;

public class Registry {

	private boolean initialized;
	private Map<Identifier, Texture> textures;
	
	public void initialize() {
		initialized = false;
		textures = new HashMap<Identifier, Texture>();
		// Load assets from builtin module
		String builtinModuleId = "pixadv";
		String[] assets = new String(Gdx.files.internal("assets.txt").readBytes()).split("\n");
		for (String assetFilename : assets) {
			try {
				Identifier assetId = new Identifier(builtinModuleId, FileUtil.removeExtension(assetFilename));
				Asset asset = new Asset(
						Asset.getTypeByExtension(FileUtil.getExtension(assetFilename)),
						assetId,
						Gdx.files.internal(assetFilename).read());;
				loadAsset(asset);
			} catch (Exception e) {
				Logger.logException("Failed to load asset! '%s'", e, assetFilename);
			}
		}
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
				byte[] assetBytes = asset.getBytes();
				// Parse bytes by file type
				switch (type) {
				case TEXTURE:
					// Texture
					Pixmap pixmap = new Pixmap(assetBytes, 0, assetBytes.length);
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
	
	public boolean isInitialized() {
		return initialized;
	}
	
	public void dispose() {
		textures.forEach((id, texture) -> texture.dispose());
	}

}
