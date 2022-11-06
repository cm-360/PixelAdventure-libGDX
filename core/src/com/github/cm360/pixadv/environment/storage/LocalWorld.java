package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.cm360.pixadv.modules.builtin.tiles.types.terra.Dirt;
import com.github.cm360.pixadv.registry.Identifier;

public class LocalWorld extends World {

	public LocalWorld(File worldDirectory) throws FileNotFoundException {
		// Parse universe info
		JsonReader jsonReader = new JsonReader();
		JsonValue json = jsonReader.parse(new FileReader(new File(worldDirectory, "info.json")));
		// World name
		name = json.getString("name");
		// Create chunk grid
		width = json.getInt("width");
		height = json.getInt("height");
		chunkSize = json.getInt("chunkSize");
		chunks = new Chunk[width][height];
		for (int cx = 0; cx < width; cx++) {
			for (int cy = 0; cy < height; cy++) {
				chunks[cx][cy] = new Chunk(chunkSize);
				for (int i = 0; i < chunkSize; i++)
					chunks[cx][cy].setTile(new Dirt(), i, 0, 0);
			}
		}
		//
		skyTextureId = new Identifier("pixadv", "textures/environment/terra/sky");
	}

	public LocalWorld(int width, int height, int chunkSize, Map<String, String> worldInfo, Object object) {
		this.width = width;
		this.height = height;
		this.chunkSize = chunkSize;
		chunks = new Chunk[width][height];
		// TODO Auto-generated constructor stub
		skyTextureId = new Identifier("pixadv", "textures/environment/terra/sky");
	}

}
