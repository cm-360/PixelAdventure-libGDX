package com.github.cm360.pixadv.network.client;

import com.github.cm360.pixadv.network.Server;
import com.github.cm360.pixadv.network.packets.Packet;

public class ClientLocal extends AbstractClient {

	protected Server server;
	
	public void connect(Server internalServer) {
		// TODO link to internal server for singleplayer
		server = internalServer;
		universe = server.getUniverse();
		currentWorldName = "GENTEST";
	}
	
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void send(Packet packet) {
		server.processClientPacket(packet);
	}

}
