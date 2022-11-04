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
	protected Map<UUID, Entity> entities;
	
	public String getName() {
		return name;
	}
	
	public Identifier getType() {
		return type;
	}
	
	public Tile getTile(int x, int y) {
		return null;
	}
	
	public Chunk getChunk(int cx, int cy) {
		return chunks[cx][cy];
	}
	
	public void setChunk(int cx, int cy, Chunk chunk) {
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
	
}
