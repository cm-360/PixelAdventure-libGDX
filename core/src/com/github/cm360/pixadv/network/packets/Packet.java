package com.github.cm360.pixadv.network.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

	public static final byte[] magicNumber;
	public static int maxClassNameLength;
	public static int maxFieldNameLength;
	public static int headerSize;

	protected Map<String, ByteBuffer> fields;

	static {
		magicNumber = "pixadv".getBytes(StandardCharsets.UTF_8);
		maxClassNameLength = 256;
		maxFieldNameLength = 64;
		headerSize = magicNumber.length + maxClassNameLength + 4;
	}
	
	public Packet() {
		fields = new HashMap<String, ByteBuffer>();
	}
	
	public void putField(String name, ByteBuffer data) {
		fields.put(name, data);
	}
	
	public ByteBuffer getField(String name) {
		return fields.get(name);
	}

	public Packet decode(byte[] bytes) {
		return null;
	}

	public ByteBuffer encode() {
		// Determine size of packet and allocate byte buffer
		int fieldsSize = 0;
		for (String fieldName : fields.keySet())
			fieldsSize += (maxFieldNameLength + 4) + fields.get(fieldName).capacity();
		ByteBuffer packetData = ByteBuffer.allocate(headerSize + fieldsSize);
		// Write header
		packetData.put(magicNumber);
		packetData.put(getClass().getCanonicalName().getBytes(StandardCharsets.UTF_8));
		packetData.putInt(headerSize - 5, fieldsSize);
		packetData.position(headerSize);
		// Write field data
		for (String fieldName : fields.keySet())
			;
		// Return
		return packetData;
	}

}
