package com.github.cm360.pixadv.modules.builtin.tiles.types.terra;

import java.util.List;

import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.registry.Identifier;

public class Stone implements Tile {

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
	public List<Identifier> getTextures() {
		return List.of(Identifier.parse("pixadv:textures/tiles/terra/stone/basic"));
	}
	
	@Override
	public double getFriction() {
		return 0.5;
	}

}
