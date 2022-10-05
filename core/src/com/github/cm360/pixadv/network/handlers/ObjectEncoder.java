package com.github.cm360.pixadv.network.handlers;

import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

// ChannelOutboundHandlerAdapter
public class ObjectEncoder extends MessageToByteEncoder<String> {

	@Override
	public void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) {
		out.writeBytes(msg.getBytes());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
