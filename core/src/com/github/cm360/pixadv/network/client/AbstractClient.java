package com.github.cm360.pixadv.network.client;

import java.util.UUID;

import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.environment.types.entity.Entity;
import com.github.cm360.pixadv.network.packets.Packet;

public abstract class AbstractClient {
	
	protected Universe universe;
	protected String currentWorldName;
	protected UUID playerId;
	protected Entity player;
	
	public abstract void disconnect();
	
	public abstract void send(Packet packet);
	
	public Universe getUniverse() {
		return universe;
	}
	
	public String getCurrentWorldName() {
		return currentWorldName;
	}
	
	public UUID getPlayerId() {
		return playerId;
	}
	
	public Entity getPlayer() {
		return player;
	}

}
