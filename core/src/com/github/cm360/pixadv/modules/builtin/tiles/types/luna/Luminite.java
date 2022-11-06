package com.github.cm360.pixadv.modules.builtin.tiles.types.luna;

import java.awt.Color;

import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.modules.builtin.tiles.capabilities.LightEmitter;
import com.github.cm360.pixadv.registry.Identifier;

public class Luminite implements Tile, LightEmitter {

	protected Identifier[] textures;
	
	public Luminite() {
		textures = new Identifier[] {
				Identifier.parse("pixadv:textures/tiles/luna/luminite")
		};
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
	public Identifier[] getTextures() {
		return textures;
	}
	
	@Override
	public float getTransmittance() {
		return 0.2f;
	}

	@Override
	public float getFriction() {
		return 0.8f;
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
