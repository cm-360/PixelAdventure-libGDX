package com.github.cm360.pixadv.environment.storage;

import java.util.Map;

public class Universe {

	protected Map<String, World> worlds;

	public World getWorld(String name) {
		return worlds.get(name);
	}
	
}
