package com.github.cm360.pixadv.network.packets;

public class StringPacket extends Packet {

	private static final long serialVersionUID = 1L;

	protected String contents;
	
	public StringPacket(String contents) {
		this.contents = contents;
	}

}
