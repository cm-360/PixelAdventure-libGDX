package com.github.cm360.pixadv.network.packets;

import io.netty.buffer.ByteBuf;

public abstract class PacketSerializer<T extends Packet> {

	public abstract void serialize(ByteBuf dest, T packet) throws Exception;
	
	public abstract T deserialize(ByteBuf source) throws Exception;

}
