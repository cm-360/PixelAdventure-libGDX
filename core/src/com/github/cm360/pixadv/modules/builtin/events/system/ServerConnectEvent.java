package com.github.cm360.pixadv.modules.builtin.events.system;

import java.net.InetAddress;

import com.github.cm360.pixadv.events.Event;

public class ServerConnectEvent extends Event {

	protected InetAddress address;
	protected int port;
	
	public ServerConnectEvent(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}

}
