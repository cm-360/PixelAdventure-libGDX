package com.github.cm360.pixadv.environment.storage;

import java.util.Map;
import java.util.UUID;

import com.github.cm360.pixadv.environment.types.Entity;
import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.registry.Identifier;

public class World {

	protected String name;
	protected Identifier type;
	protected Identifier skyTextureId;
	
	protected Chunk[][] chunks;
	protected int width;
	protected int height;
	protected int chunkSize;
	protected Map<UUID, Entity> entities;
	
	public String getName() {
		return name;
	}
	
	public Identifier getType() {
		return type;
	}
	
	public Identifier getSkyTexture() {
		return skyTextureId;
	}
	
	public int fixCoordinate(int coord, int max) {
		if (coord < 0)
			return ((coord + 1) % max) + (max - 1);
		else
			return coord % max;
	}
	
	public boolean isValid(int x, int y, int z) {
		return ((y >= 0) && (y < (height * chunkSize)) && (z >= 0) && (z < Chunk.layers));
	}
	
	public Tile getTile(int x, int y, int z) {
		int xFixed = fixCoordinate(x, width * chunkSize);
		return chunks[xFixed / chunkSize][y / chunkSize].getTile(xFixed % chunkSize, y % chunkSize, z);
	}
	
	public void setTile(Tile tile, int x, int y, int z) {
		int xFixed = fixCoordinate(x, width * chunkSize);
		chunks[xFixed / chunkSize][y / chunkSize].setTile(tile, xFixed % chunkSize, y % chunkSize, z);
	}
	
	public Chunk getChunk(int cx, int cy) {
		int cxFixed = fixCoordinate(cx, width);
		return chunks[cxFixed][cy];
	}
	
	public void setChunk(Chunk chunk, int cx, int cy) {
		chunks[cx][cy] = chunk;
	}
	
	public Entity getEntity(UUID uuid) {
		return entities.get(uuid);
	}
	
	public void addEntity(UUID uuid, Entity entity) {
		entities.put(uuid, entity);
	}
	
	public void removeEntity(UUID uuid) {
		entities.remove(uuid);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getChunkSize() {
		return chunkSize;
	}
	
}
