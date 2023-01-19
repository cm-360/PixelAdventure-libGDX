package com.github.cm360.pixadv.network.packets;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public abstract class Packet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final byte[] magicNumber;
	public static final int headerSize;

	/**
	 * The bytes of a packet header are split up as follows:
	 *  0x0-0x5:	Packet magic number ("pixadv" by default)
	 *  0x6-0xB:	Unused (Might use later for version/registry info)
	 *  0xC-0xF:	Payload size in bytes
	 */
	static {
		magicNumber = "pixadv".getBytes(StandardCharsets.UTF_8);
		headerSize = magicNumber.length + Integer.BYTES;
	}

}
