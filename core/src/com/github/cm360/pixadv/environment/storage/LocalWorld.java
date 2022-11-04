package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

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
			}
		}
		//
		
	}

}
