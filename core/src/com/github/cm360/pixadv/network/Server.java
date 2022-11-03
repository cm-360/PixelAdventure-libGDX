package com.github.cm360.pixadv.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.Map;

import com.github.cm360.pixadv.environment.storage.LocalUniverse;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.network.handlers.ObjectDecoder;
import com.github.cm360.pixadv.network.handlers.ObjectEncoder;
import com.github.cm360.pixadv.network.handlers.ObjectReadHandler;
import com.github.cm360.pixadv.util.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel serverChannel;
	private Map<String, Channel> clientChannels;
	
	private Universe universe;
	
	public void load(File universeDirectory) {
		Logger.logMessage(Logger.INFO, "Loading universe from '%s'", universeDirectory);
		try {
			universe = new LocalUniverse(universeDirectory);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(InetAddress address, int port) {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// Decoding/receiving
							pipeline.addLast(new ObjectDecoder());
							pipeline.addLast(new ObjectReadHandler());
							// Encoding/sending
							pipeline.addLast(new ObjectEncoder());
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			serverChannel = b.bind(address, port).sync().channel();
			Logger.logMessage(Logger.INFO, "Server hosted on %s:%d", address.getHostAddress(), port);
		} catch (Exception e) {
			Logger.logException("Failed to start server!", e);
		}
	}
	
	public void close() {
		try {
			serverChannel.close().sync();
		} catch (Exception e) {
			Logger.logException("Error while stopping server!", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
