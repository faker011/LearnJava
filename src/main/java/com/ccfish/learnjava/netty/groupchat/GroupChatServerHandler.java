package com.ccfish.learnjava.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * @Author: Ciaos
 * @Date: 2020/5/11 22:38
 */

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channel组 管理所有的channel
    // GlobalEventExecutor.INSTANCE 是一个全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    // handlerAdded 表示连接建立，一旦链接，第一个被执行
    // 将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 该方法会将 channelGroup 中所有的channel遍历，并发送消息
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
    }

    // 表示channel处于活跃状态，提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了 ");
    }

    // 表示channel处于活跃状态，提示xx离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了 ");
    }

    // 断开连接, 将客户离开信息推送给当前在线的所有客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush(ctx.channel().remoteAddress() + " 离开了 ");
    }

    /**
     * 接收多个客户端消息 并转发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取到当前channel
        Channel channel = ctx.channel();
        // 遍历channelGroup，根据不同的情况发送不同的消息（去除自己）
        channelGroup.forEach(ch -> {
            if(channel != ch) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + ":" + msg + "\n") ;
            }else {
                ch.writeAndFlush("自己发送给自己" + msg);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
