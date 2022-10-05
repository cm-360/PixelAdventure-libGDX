package com.github.cm360.pixadv.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageReadHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    Object m = (Object) msg;
	    System.out.println(m);
	    ctx.close();
	}

}
