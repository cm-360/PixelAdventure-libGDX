package com.github.cm360.pixadv.network.handlers;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.util.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

// ChannelOutboundHandlerAdapter
public class ObjectEncoder extends MessageToByteEncoder<Packet> {

	@Override
	public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
//		for (Field packetField : packet.getClass().getDeclaredFields()) {
//			PacketSerializable annotation = packetField.getAnnotation(PacketSerializable.class);
//			if (annotation != null) {
//				packetField.get
//			}
//		}
		
//		// Determine size of packet and allocate byte buffer
//		int fieldsSize = 0;
//		for (String fieldName : fields.keySet())
//			fieldsSize += (maxFieldNameLength + 4) + fields.get(fieldName).capacity();
//		ByteBuffer packetData = ByteBuffer.allocate(headerSize + fieldsSize);
//		// Write header
//		packetData.put(magicNumber);
//		packetData.put(getClass().getCanonicalName().getBytes(StandardCharsets.UTF_8));
//		packetData.putInt(headerSize - 5, fieldsSize);
//		packetData.position(headerSize);
//		// Write field data
//		for (String fieldName : fields.keySet())
//			;
//		// Return
//		return packetData;
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutStream = new ObjectOutputStream(byteOutStream);
		objOutStream.writeObject(packet);
		out.writeBytes(byteOutStream.toByteArray());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
