package com.github.cm360.pixadv.modules.builtin.tiles.types.terra;

import java.util.HashMap;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.cm360.pixadv.environment.types.tile.Tile;
import com.github.cm360.pixadv.registry.Identifier;

public class Dirt implements Tile {

	public boolean mud, grass, snow;

	protected Identifier[] textures;
	
	public Dirt() {
		textures = new Identifier[3];
		updateTextures();
	}
	
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
			updateTextures();
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
	
	public void updateTextures() {
		// Update textures
		textures[0] = Identifier.parse("pixadv:textures/tiles/terra/dirt/" + (mud ? "mud" : "dirt") + "/basic");
		if (grass)
			textures[1] = Identifier.parse("pixadv:textures/tiles/terra/dirt/" + (mud ? "mud" : "dirt") + "/grass");
		if (snow)
			textures[2] = Identifier.parse("pixadv:textures/tiles/terra/dirt/" + (mud ? "mud" : "dirt") + "/snow");
	}
	
	@Override
	public Identifier[] getTextures() {
		return textures;
	}
	
	@Override
	public float getTransmittance() {
		return 0.2f;
	}
	
	@Override
	public float getFriction() {
		return 0.5f;
	}

}
