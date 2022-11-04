package com.github.cm360.pixadv.environment.storage;

import java.util.Map;
import java.util.UUID;

import com.github.cm360.pixadv.environment.types.Entity;
import com.github.cm360.pixadv.environment.types.Tile;
import com.github.cm360.pixadv.registry.Identifier;

public class World {

	protected String name;
	protected Identifier type;
	
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
	
	public int fixCoordinate(int coord, int max) {
		int coordNew = coord;
		if (coord < 0)
			while (coordNew < 0)
				coordNew += max;
		else if (coord >= max)
			while (coordNew >= max)
				coordNew -= max;
		return coordNew;
	}
	
	public Tile getTile(int x, int y, int l) {
		int xFixed = fixCoordinate(x, width * chunkSize);
		return chunks[xFixed / chunkSize][y / chunkSize].getTile(xFixed % chunkSize, y % chunkSize, l);
	}
	
	public void setTile(Tile tile, int x, int y, int l) {
		int xFixed = fixCoordinate(x, width * chunkSize);
		chunks[xFixed / chunkSize][y / chunkSize].setTile(tile, xFixed % chunkSize, y % chunkSize, l);
	}
	
	public Chunk getChunk(int cx, int cy) {
		return chunks[cx][cy];
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
