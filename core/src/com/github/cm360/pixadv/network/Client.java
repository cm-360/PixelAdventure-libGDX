package com.github.cm360.pixadv.network;

import java.net.InetAddress;

import com.github.cm360.pixadv.network.handlers.ObjectDecoder;
import com.github.cm360.pixadv.network.handlers.ObjectEncoder;
import com.github.cm360.pixadv.network.handlers.ObjectReadHandler;
import com.github.cm360.pixadv.util.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	private EventLoopGroup workerGroup;
	private Channel channel;
	
	public void connect(InetAddress address, int port) {
		workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
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
					.option(ChannelOption.SO_KEEPALIVE, true);
			Logger.logMessage(Logger.INFO, "Connecting to %s:%d...", address.getHostAddress(), port);
			channel = b.connect(address, port).sync().channel();
			// Send hello
			channel.writeAndFlush("testing testing uwu").sync();
			Logger.logMessage(Logger.INFO, "Sent hello");
		} catch (Exception e) {
			Logger.logException("Failed to connect!", e);
		}
	}
	
	public void close() {
		try {
			channel.close().sync();
		}  catch (Exception e) {
			Logger.logException("Error during disconnect!", e);
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

}