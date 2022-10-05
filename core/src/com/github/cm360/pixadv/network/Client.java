package com.github.cm360.pixadv.network;

import java.net.InetAddress;

import com.github.cm360.pixadv.util.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
							ch.pipeline().addLast(new MessageDecoder());
						}
					})
					.option(ChannelOption.SO_KEEPALIVE, true);
			channel = b.connect(address, port).sync().channel();
		} catch (Exception e) {
			Logger.logException("Uncaught client exception!", e);
		}
	}
	
	public void close() {
		try {
			channel.close().sync();
		}  catch (Exception e) {
			Logger.logException("Uncaught client exception!", e);
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

}
