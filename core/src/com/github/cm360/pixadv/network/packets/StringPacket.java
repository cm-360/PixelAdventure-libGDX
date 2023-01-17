package com.github.cm360.pixadv.network.packets;

public class StringPacket extends ObjectPacket {

	private static final long serialVersionUID = 1L;
	
	public String string;

	public StringPacket(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}

}
