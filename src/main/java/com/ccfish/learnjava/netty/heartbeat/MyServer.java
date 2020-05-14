package com.ccfish.learnjava.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 心跳检测
 * @Author: Ciaos
 * @Date: 2020/5/13 22:32
 */

public class MyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 加入一个netty提供的IdleStateHandler
                            // IdleStateHandler 是netty提供的处理空闲状态的处理器
                            // 1. long readerIdleTime ： 表示多长时间没有读
                            // 2. long writerIdleTime :  表示多长时间没有写
                            // 3. long allIdleTime    :  表示没有读写
                            // 4. Triggers an IdleStateEvent when a Channel has not performed read, write,
                            //    or both operation for a while
                            // 5. 当IdleStateEvent触发后，就会传递给管道的下一个handler去处理
                            //    通过调用（触发）下一个handler的userEventTriggered，在该方法中去处理IdleStateEvent（读空闲，写空闲，读写空闲）
                            // 6.
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的自定义handler
                            pipeline.addLast(new MyServerHandler());

                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
