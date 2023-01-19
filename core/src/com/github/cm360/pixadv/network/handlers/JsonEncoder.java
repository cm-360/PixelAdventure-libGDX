package com.github.cm360.pixadv.network.handlers;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.utils.Json;
import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JsonEncoder extends MessageToByteEncoder<Packet> {

	protected Json json;
	
	public JsonEncoder() {
		json = new Json();
	}
	
	@Override
	public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		out.writeBytes(Packet.magicNumber);
		// Packet class
		byte[] packetClass = packet.getClass().getCanonicalName().getBytes(StandardCharsets.UTF_8);
		out.writeInt(packetClass.length);
		out.writeBytes(packetClass);
		// Packet data
		byte[] packetData = json.toJson(packet).getBytes(StandardCharsets.UTF_8);
		out.writeInt(packetData.length);
		out.writeBytes(packetData);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
