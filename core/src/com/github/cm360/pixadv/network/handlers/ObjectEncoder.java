package com.github.cm360.pixadv.network.handlers;

import java.io.IOException;

import com.github.cm360.pixadv.network.packets.ObjectPacket;
import com.github.cm360.pixadv.network.packets.ObjectPacketSerializer;
import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

// ChannelOutboundHandlerAdapter
public class ObjectEncoder extends MessageToByteEncoder<Packet> {

	protected ObjectPacketSerializer objSerializer;
	
	public ObjectEncoder() {
		// TODO make a serializer list and use some kind of priority ordering
		objSerializer = new ObjectPacketSerializer();
	}
	
	@Override
	public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws IOException {
		if (ObjectPacket.class.isAssignableFrom(packet.getClass())) {
			objSerializer.serialize(out, (ObjectPacket) packet);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
