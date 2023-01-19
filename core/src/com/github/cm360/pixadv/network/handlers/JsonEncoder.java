package com.github.cm360.pixadv.network.handlers;

import java.nio.charset.StandardCharsets;

import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JsonEncoder extends MessageToByteEncoder<Packet> {

	@Override
	public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		out.writeBytes(Packet.magicNumber);
		byte[] payload = packet.toString().getBytes(StandardCharsets.UTF_8);
		out.writeInt(payload.length);
		out.writeBytes(payload);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
