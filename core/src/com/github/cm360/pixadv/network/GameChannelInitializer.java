package com.github.cm360.pixadv.network;

import java.util.function.Consumer;

import com.github.cm360.pixadv.network.handlers.ObjectDecoder;
import com.github.cm360.pixadv.network.handlers.ObjectEncoder;
import com.github.cm360.pixadv.network.handlers.ObjectReadHandler;
import com.github.cm360.pixadv.network.packets.Packet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class GameChannelInitializer extends ChannelInitializer<SocketChannel> {

	protected Consumer<Packet> packetHandler;
	
	public GameChannelInitializer(Consumer<Packet> packetHandler) {
		this.packetHandler = packetHandler;
	}
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// Decoding/receiving
		pipeline.addLast(new ObjectDecoder());
		pipeline.addLast(new ObjectReadHandler(packetHandler));
		// Encoding/sending
		pipeline.addLast(new ObjectEncoder());
	}

}
