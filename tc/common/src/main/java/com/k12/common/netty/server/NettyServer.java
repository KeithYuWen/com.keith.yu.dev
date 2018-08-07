package com.k12.common.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer{
	
	private Thread nettyServer;
	private final int port;
	private ServerBootstrap bootstrap;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workGroup;
	
	public NettyServer(int port) {
		this.port = port;
	}

	public void bing() throws Exception {
		nettyServer = new Thread(new Runnable(){  
            public void run(){ 
            	bossGroup = new NioEventLoopGroup();
            	workGroup = new NioEventLoopGroup();
            	try {
            		bootstrap = new ServerBootstrap();
            		bootstrap.group(bossGroup, workGroup);
            		bootstrap.channel(NioServerSocketChannel.class);
            		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            		bootstrap.childHandler(new ChildChannelHandler());
            		// 绑定端口
            		ChannelFuture f = bootstrap.bind(port).sync();
            		// 等待服务端监听端口关闭
            		f.channel().closeFuture().sync();
            	} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
            		// 优雅的退出
            		bossGroup.shutdownGracefully();
            		workGroup.shutdownGracefully();
            	}
            }});  
		nettyServer.start(); 
	}
	
	public void stop (){
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		nettyServer.interrupt();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("服务端开启等待客户端链接");
			new NettyServer(9696).bing();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}