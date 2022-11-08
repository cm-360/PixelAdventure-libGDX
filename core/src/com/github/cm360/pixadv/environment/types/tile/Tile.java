package com.github.cm360.pixadv.environment.types.tile;

import com.github.cm360.pixadv.registry.Identifier;

public interface Tile {

	public String getID();
	
	public void setData(String data);
	
	public String getData();
	
	public Identifier[] getTextures();
	
	public float getTransmittance();
	
	public float getFriction();

}
