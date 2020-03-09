package com.ccfish.learnjava.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: Ciaos
 * @Date: 2020/3/9 21:53
 */

public class NettyServer {
    public static void main(String[] args) throws Exception{
        // 创建bossGroup和workerGroup
        // 说明
        // 1 创建两个线程组 boss Group和 workerGroup
        // 2 bossGroup只负责进行链接处理
        // 3 workerGroup负责业务处理
        // 4 两个线程组都是无限循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            // 创建服务器端的启动
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128) // 设置线程队列等待链接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保证保持活动链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象（匿名对象）
                        // 给pipline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给workerGroup 的 EventLoop对应的管道设置处理器

            System.out.println("server is ready");
            // 绑定一个端口并且同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = server.bind(6668).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
