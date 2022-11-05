package com.github.cm360.pixadv.modules.builtin.tiles.types.terra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.registry.Identifier;

public class Dirt implements Tile {

	public boolean mud, grass, snow;
	
	@Override
	public String getID() {
		return "terra/dirt";
	}
	
	@Override
	public void setData(String data) {
		try {
			JsonValue dataJson = new JsonReader().parse(data);
			mud = dataJson.getBoolean("mud", false);
			grass = dataJson.getBoolean("grass", false);
			snow = dataJson.getBoolean("snow", false);
		} catch (Exception e) {
			// Do nothing
		}
	}
	
	@Override
	public String getData() {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("mud", Boolean.toString(mud));
		dataMap.put("grass", Boolean.toString(grass));
		dataMap.put("snow", Boolean.toString(snow));
		return new Json().toJson(dataMap);
	}
	
	@Override
	public List<Identifier> getTextures() {
		List<Identifier> textures = new ArrayList<Identifier>();
		textures.add(Identifier.parse("pixadv:textures/tiles/terra/dirt/" + (mud ? "mud" : "dirt") + "/basic"));
		if (grass)
			textures.add(Identifier.parse("pixadv:textures/tiles/terra/dirt/" + (mud ? "mud" : "dirt") + "/grass"));
		if (snow)
			textures.add(Identifier.parse("pixadv:textures/tiles/terra/dirt/" + (mud ? "mud" : "dirt") + "/snow"));
		return textures;
	}
	
	@Override
	public double getFriction() {
		return 0.5;
	}

}
