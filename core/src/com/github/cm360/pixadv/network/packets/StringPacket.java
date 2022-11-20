package com.github.cm360.pixadv.network.packets;

public class StringPacket extends Packet {

	public String string;

	public StringPacket(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}

}
