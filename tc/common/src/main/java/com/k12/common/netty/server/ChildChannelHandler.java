package com.k12.common.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel e) throws Exception {

		System.out.println("报告");
		System.out.println("信息：有一客户端链接到本服务端");
		System.out.println("IP:" + e.localAddress().getHostName());
		System.out.println("Port:" + e.localAddress().getPort());
		System.out.println("报告完毕");
		
		
		e.pipeline().addLast("http-codec",new HttpServerCodec());
		e.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
		e.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
		e.pipeline().addLast("handler",new MyWebSocketServerHandler());
		
		// 解码器
		// 基于换行符号
//		e.pipeline().addLast(new LineBasedFrameDecoder(1024));
		// 基于指定字符串【换行符，这样功能等同于LineBasedFrameDecoder】
		// e.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, false,
		// Delimiters.lineDelimiter()));
		// 基于最大长度
		// e.pipeline().addLast(new FixedLengthFrameDecoder(4));
		// 解码转String
//		e.pipeline().addLast(new StringDecoder());

		// 编码器 String
//		e.pipeline().addLast(new StringEncoder());
		// 在管道中添加我们自己的接收数据实现方法
//		e.pipeline().addLast(new MyServerHanlder());

	}
	
	
	

}