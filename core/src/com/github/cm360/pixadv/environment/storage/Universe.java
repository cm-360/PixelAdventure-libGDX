package com.github.cm360.pixadv.environment.storage;

import java.util.Map;

public class Universe {

	protected String name;
	
	protected Map<String, World> worlds;
	
	public String getName() {
		return name;
	}

	public World getWorld(String name) {
		return worlds.get(name);
	}
	
}
