package com.github.cm360.pixadv.network.handlers;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

import com.github.cm360.pixadv.network.packets.Packet;
import com.github.cm360.pixadv.network.packets.StringPacket;
import com.github.cm360.pixadv.util.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketReadHandler extends SimpleChannelInboundHandler<Packet> {

	private Consumer<Packet> packetHandler;
	
	public PacketReadHandler(Consumer<Packet> packetHandler) {
		this.packetHandler = packetHandler;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
		Logger.logMessage(Logger.INFO, "Connected to %s:%d", address.getAddress().getHostAddress(), address.getPort());
	}
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Packet packet) throws InterruptedException {
		packetHandler.accept(packet);
		if (!packet.toString().contains("reply"))
			ctx.channel().writeAndFlush(new StringPacket("reply :D")).sync();
//		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
