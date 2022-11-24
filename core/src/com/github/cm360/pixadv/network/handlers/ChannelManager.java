package com.github.cm360.pixadv.network.handlers;

import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChannelManager extends ChannelInboundHandlerAdapter {

	private Map<String, Channel> clientChannels;
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) {
		Channel ch = ctx.channel();
		clientChannels.put(ch.remoteAddress().toString(), ch);
    }
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) {
		clientChannels.remove(ctx.channel().remoteAddress().toString());
    }
	
	public Channel getChannel(String address) {
		return clientChannels.get(address);
	}

}
