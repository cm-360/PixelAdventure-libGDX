package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.cm360.pixadv.modules.builtin.generators.world.BasicWorldGenerator;

public class LocalUniverse extends Universe {

	public LocalUniverse(File universeDir) throws FileNotFoundException {
		name = universeDir.getName();
		// Parse universe info
		JsonReader jsonReader = new JsonReader();
		JsonValue json = jsonReader.parse(new FileReader(new File(universeDir, "info.json")));
		// Load world folders
		File worldsDir = new File(universeDir, "worlds");
		worlds = new HashMap<String, World>();
		for (File worldDir : worldsDir.listFiles(File::isDirectory)) {
			LocalWorld world = new LocalWorld(worldDir);
			worlds.put(world.getName(), world);
		}
		// World gen debug world
		worlds.put("GENTEST", new BasicWorldGenerator(1337L).generate());
	}
	
}
