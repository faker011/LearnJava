package com.ccfish.learnjava.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个handler，需要继承netty规定好的某个HandlerAdapter
 * 这是我们自定义一个Handler 才能成为一个handler
 * @Author: Ciaos
 * @Date: 2020/3/9 22:13
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 重写方法，读取数据的事件（这里我们可以读取客户端发送的消息）

    /**
     * 1. ChannelHandlerContext ctx： 上下文对象，含有管道pipeline，通道channel, 地址
     * 2. Object msg : 客户端发送的数据 默认是object的格式
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        // 将msg转换成一个ByteBuf
        // ByteBuf 是 netty 提供的，不是nio的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());

        // super.channelRead(ctx, msg);
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 是 write + flush
        // 将数据写入到缓存 并刷新
        // 一般讲，我们对这个发送的数据进行一个编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, 客户端", CharsetUtil.UTF_8));
        // super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        ctx.close();
    }
}
