package com.github.cm360.pixadv.network;

import java.util.function.Consumer;

import com.github.cm360.pixadv.network.handlers.PacketReadHandler;
import com.github.cm360.pixadv.network.handlers.PacketUnwrapper;
import com.github.cm360.pixadv.network.packets.Packet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class GameChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

	protected Consumer<Packet> packetHandler;
	
	public GameChannelInitializer(Consumer<Packet> packetHandler) {
		this.packetHandler = packetHandler;
	}
	
	@Override
	public void initChannel(NioDatagramChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// Decoding/receiving
		pipeline.addLast(new PacketUnwrapper());
		pipeline.addLast(new PacketReadHandler(packetHandler));
		// Encoding/sending
//		pipeline.addLast(new ObjectEncoder());
	}

}
