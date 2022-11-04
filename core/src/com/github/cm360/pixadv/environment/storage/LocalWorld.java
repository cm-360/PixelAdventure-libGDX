package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class LocalWorld extends World {

	public LocalWorld(File worldDirectory) throws FileNotFoundException {
		name = worldDirectory.getName();
		// Parse universe info
		JsonReader jsonReader = new JsonReader();
		JsonValue json = jsonReader.parse(new FileReader(new File(worldDirectory, "info.json")));
	}

}
