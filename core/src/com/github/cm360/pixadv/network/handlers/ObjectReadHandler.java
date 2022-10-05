package com.github.cm360.pixadv.network.handlers;

import com.github.cm360.pixadv.util.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ObjectReadHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws InterruptedException {
		System.out.println(msg);
		ctx.channel().writeAndFlush("reply! " + msg).sync();
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO real error message
		Logger.logThrowable(Logger.ERROR, "", cause);
		ctx.close();
	}

}
