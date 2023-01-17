package com.github.cm360.pixadv.network.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.netty.buffer.ByteBuf;

public class ObjectPacketSerializer extends PacketSerializer<ObjectPacket> {

	@Override
	public void serialize(ByteBuf dest, ObjectPacket packet) throws IOException {
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutStream = new ObjectOutputStream(byteOutStream);
		objOutStream.writeObject(packet);
		dest.writeBytes(byteOutStream.toByteArray());
	}

	@Override
	public ObjectPacket deserialize(ByteBuf source) throws Exception {
		int size = source.readableBytes();
		byte[] bytes = new byte[size];
		source.readBytes(bytes);
		ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objInStream = new ObjectInputStream(byteInStream);
		return (ObjectPacket) objInStream.readObject();
	}

}
