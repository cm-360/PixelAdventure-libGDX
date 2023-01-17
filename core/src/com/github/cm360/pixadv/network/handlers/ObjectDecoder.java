package com.github.cm360.pixadv.network.handlers;

import java.io.IOException;
import java.util.List;

import com.github.cm360.pixadv.network.packets.ObjectPacketSerializer;
import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ObjectDecoder extends ByteToMessageDecoder {

	protected ObjectPacketSerializer objSerializer;
	
	public ObjectDecoder() {
		objSerializer = new ObjectPacketSerializer();
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
//		int bufSize = in.readableBytes();
//		// Check if packet header has been received yet
//		if (bufSize >= Packet.headerSize) {
//			in.resetReaderIndex();
//			// Read info from packet header
//			ByteBuf headerBytes = in.readBytes(bufSize);
//			
//			byte[] msgBytes = new byte[bufSize];
//			in.readBytes(bufSize).readBytes(msgBytes);
//			String message = new String(msgBytes);
//			out.add(message);
//		}
		
		out.add(objSerializer.deserialize(in));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
