package com.github.cm360.pixadv.network.handlers;

import java.util.function.Consumer;

import com.github.cm360.pixadv.network.packets.Packet;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class GameChannelInitializer extends ChannelInitializer<SocketChannel> {

	protected Consumer<Packet> packetHandler;
	protected ChannelHandler[] preHandlers;
	
	public GameChannelInitializer(Consumer<Packet> packetHandler, ChannelHandler... preHandlers) {
		this.packetHandler = packetHandler;
		this.preHandlers = preHandlers;
	}
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// Insert pre-handlers first
		pipeline.addLast(preHandlers);
		// Decoding/receiving
		pipeline.addLast(new JsonDecoder());
		pipeline.addLast(new PacketReadHandler(packetHandler));
		// Encoding/sending
		pipeline.addLast(new JsonEncoder());
	}

}
