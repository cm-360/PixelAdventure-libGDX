package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.badlogic.gdx.utils.Json;

public class LocalUniverse extends Universe {

	public LocalUniverse(File universeDirectory) throws FileNotFoundException {
		Json json = new Json();
		json.fromJson(Map.class, new FileReader(new File(universeDirectory, "info.json")));
	}
	
}
