package com.github.cm360.pixadv.modules.builtin.tiles.types.terra;

import com.github.cm360.pixadv.environment.types.tiles.Tile;
import com.github.cm360.pixadv.registry.Identifier;

public class Stone implements Tile {

	protected Identifier[] textures;
	
	public Stone() {
		textures = new Identifier[] {
			Identifier.parse("pixadv:textures/tiles/terra/stone/basic")
		};
	}
	
	@Override
	public String getID() {
		return "terra/stone";
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
		return 0.5f;
	}

}
