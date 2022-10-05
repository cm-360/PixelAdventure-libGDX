package com.github.cm360.pixadv.network.handlers;

import java.util.List;

import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ObjectDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		int size = in.readableBytes();
		byte[] msgBytes = new byte[size];
		in.readBytes(size).readBytes(msgBytes);
		String message = new String(msgBytes);
		out.add(message);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
