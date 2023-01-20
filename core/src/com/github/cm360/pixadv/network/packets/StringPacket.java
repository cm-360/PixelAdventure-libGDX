package com.github.cm360.pixadv.network.packets;

public class StringPacket extends Packet {

	private static final long serialVersionUID = 1L;

	public String contents;
	
	public static StringPacket create(String contents) {
		StringPacket s = new StringPacket();
		s.contents = contents;
		return s;
	}
	
	@Override
	public String toString() {
		return contents;
	}

}
