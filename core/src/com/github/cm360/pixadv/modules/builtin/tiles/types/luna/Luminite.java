package com.github.cm360.pixadv.modules.builtin.tiles.types.luna;

import java.awt.Color;
import java.util.List;

import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.modules.builtin.tiles.capabilities.LightEmitter;
import com.github.cm360.pixadv.registry.Identifier;

public class Luminite implements Tile, LightEmitter {

	protected Identifier textureId;
	
	public Luminite() {
		textureId = Identifier.parse("pixadv:textures/tiles/luna/luminite");
	}
	
	@Override
	public String getID() {
		return "luna/luminite";
	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Identifier> getTextures() {
		return List.of(textureId);
	}

	@Override
	public double getFriction() {
		return 0.8;
	}
	
	@Override
	public double getLightIntensity() {
		return 1;
	}

	@Override
	public Color getLightColor() {
		return Color.CYAN;
	}

}
