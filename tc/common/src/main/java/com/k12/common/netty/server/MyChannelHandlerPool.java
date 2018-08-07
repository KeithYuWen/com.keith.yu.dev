package com.k12.common.netty.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 * 这里讲ChannelGroup单独放到一个类里，并有多个客户端使用 同时ChannelGroup是static的 说明：这不是唯一的处理方式
 * 
 */
public class MyChannelHandlerPool {

	public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}