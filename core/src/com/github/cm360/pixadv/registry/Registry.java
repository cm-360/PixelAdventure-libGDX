package com.github.cm360.pixadv.registry;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;

public class Registry {

	private boolean initialized;
	private Map<Identifier, Texture> textures;
	
	public void initialize() {
		// 
		initialized = false;
		textures = new HashMap<Identifier, Texture>();
		// Load builtin module
		String[] assets = new String(Gdx.files.internal("assets.txt").readBytes()).split("\n");
		for (String assetFilename : assets) {
			try {
				loadInternalAsset(assetFilename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Load external modules
		for (FileHandle moduleFilename : Gdx.files.local("modules").list())
			System.out.println(moduleFilename);
		// Done
		initialized = true;
	}
	
	private void loadInternalAsset(String filename) throws IOException {
		Identifier assetId = new Identifier("pixadv", filename);
		Texture texture = new Texture(Gdx.files.internal(filename));
		textures.put(assetId, texture);
//		loadAsset(Gdx.files.internal(filename).read());
	}
	
	private void loadExternalAsset(String filename) {
		
	}
	
	private void loadAsset(InputStream stream) throws IOException {
		Gdx2DPixmap gdxPixmap = new Gdx2DPixmap(stream, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
		Pixmap pixmap = new Pixmap(gdxPixmap);
		new Texture(pixmap);
		gdxPixmap.dispose();
		pixmap.dispose();
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
