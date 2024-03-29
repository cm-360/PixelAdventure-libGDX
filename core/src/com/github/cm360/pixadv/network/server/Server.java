package com.github.cm360.pixadv.network.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;

import com.github.cm360.pixadv.commands.CommandProcessor;
import com.github.cm360.pixadv.environment.storage.LocalUniverse;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.network.handlers.ChannelManager;
import com.github.cm360.pixadv.network.handlers.GameChannelInitializer;
import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.util.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel serverChannel;
	private ChannelManager channelManager;
	
	private Universe universe;
	private CommandProcessor commands;
	
	public void load(File universeDirectory) {
		Logger.logMessage(Logger.INFO, "Loading universe from '%s'", universeDirectory);
		try {
			universe = new LocalUniverse(universeDirectory);
			commands = new CommandProcessor(universe);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(InetAddress address, int port) {
		Server thisServer = this;
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		channelManager = new ChannelManager();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new GameChannelInitializer(thisServer::processClientPacket, channelManager));
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
	
	public void processClientPacket(Packet packet) {
		String contents = packet.toString();
		System.out.println("packet: " + contents);
		if (contents.startsWith("/"))
			commands.processCommand(contents.substring(1));
	}
	
	public Universe getUniverse() {
		return universe;
	}

}
