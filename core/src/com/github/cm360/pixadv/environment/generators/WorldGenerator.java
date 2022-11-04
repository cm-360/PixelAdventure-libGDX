package com.github.cm360.pixadv.environment.generators;

import com.github.cm360.pixadv.environment.storage.World;

public interface WorldGenerator {

	public enum Phase { Waiting, Init, Heightmap, Surface, Caves, Decorate, Complete };
	
	public World generate();
	
	public Phase getGenerationPhase();
	
	public WorldGenerator setName(String name);
	
	public WorldGenerator setWidth(int width);
	
	public WorldGenerator setHeight(int height);
	
	public WorldGenerator setChunkSize(int chunkSize);

}
