package com.github.cm360.pixadv.network.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class JsonDecoder extends ByteToMessageDecoder {

	protected Json json;
	
	public JsonDecoder() {
		json = new Json();
	}

	/**
	 * Decodes a packet from.
	 *
	 * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
	 * @param in the {@link ByteBuf} from which to read data
	 * @param out the {@link List} to which decoded messages should be added
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= Packet.magicNumber.length) {
			in.resetReaderIndex();
			// Header
			if (!validateHeader(in))
				throw new IOException("Invalid packet header!");
			// Class
			Class<? extends Packet> packetClass = readPacketClass(in);
			if (packetClass == null)
				return;
			// Data
			Packet packet = readPacketData(packetClass, in);
			if (packet == null)
				return;
			out.add(packet);
		}
	}
	
	protected boolean validateHeader(ByteBuf data) {
		// TODO validate magic number
		byte[] magicNumber = new byte[Packet.magicNumber.length];
		data.readBytes(magicNumber);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<? extends Packet> readPacketClass(ByteBuf data) throws Exception {
		if (data.readableBytes() >= Integer.BYTES) {
			int length = data.readInt();
			if (data.readableBytes() >= length) {
				Class<?> packetClassUncast = Class.forName(data.readBytes(length).toString(StandardCharsets.UTF_8));
				if (Packet.class.isAssignableFrom(packetClassUncast)) {
					return (Class<? extends Packet>) packetClassUncast;
				} else {
					throw new IOException("Attempted to deserialize into a non-packet class!");
				}
			}
		}
		return null;
	}
	
	protected Packet readPacketData(Class<? extends Packet> packetClass, ByteBuf data) {
		if (data.readableBytes() >= Integer.BYTES) {
			int length = data.readInt();
			if (data.readableBytes() >= length) {
				return json.fromJson(packetClass, data.readBytes(length).toString(StandardCharsets.UTF_8));
			}
		}
		return null;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
