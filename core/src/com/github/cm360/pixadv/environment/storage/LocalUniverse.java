package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class LocalUniverse extends Universe {

	public LocalUniverse(File universeDirectory) throws FileNotFoundException {
		name = universeDirectory.getName();
		// Parse universe info
		JsonReader jsonReader = new JsonReader();
		JsonValue json = jsonReader.parse(new FileReader(new File(universeDirectory, "info.json")));
		// Load world folders
		worlds = new HashMap<String, World>();
		
	}
	
}
