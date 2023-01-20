package com.github.cm360.pixadv.network.client;

import java.net.InetAddress;

import com.github.cm360.pixadv.network.handlers.GameChannelInitializer;
import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.network.packets.StringPacket;
import com.github.cm360.pixadv.util.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientRemote extends AbstractClient {

	private EventLoopGroup workerGroup;
	private Channel channel;
	
	public void connect(InetAddress address, int port) {
		ClientRemote thisClient = this;
		workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new GameChannelInitializer(thisClient::processServerPacket));
			Logger.logMessage(Logger.INFO, "Connecting to %s:%d...", address.getHostAddress(), port);
			channel = b.connect(address, port).sync().channel();
			// Send hello
			StringPacket stringPacket = StringPacket.create("testing testing uwu");
			channel.writeAndFlush(stringPacket).sync();
			Logger.logMessage(Logger.INFO, "Sent hello");
		} catch (Exception e) {
			Logger.logException("Failed to connect!", e);
		}
	}
	
	@Override
	public void disconnect() {
		try {
			channel.close().sync();
		}  catch (Exception e) {
			Logger.logException("Error during disconnect!", e);
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	@Override
	public void send(Packet packet) {
		channel.writeAndFlush(packet);
	}
	
	public void processServerPacket(Packet packet) {
		System.out.println("packet: " + packet);
	}

}
