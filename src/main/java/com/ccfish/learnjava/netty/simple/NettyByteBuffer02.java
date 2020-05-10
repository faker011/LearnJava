package com.ccfish.learnjava.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;


/**
 * @Author: Ciaos
 * @Date: 2020/5/10 21:58
 */

public class NettyByteBuffer02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);

        if(byteBuf.hasArray()){   // 判断是否有数据
            byte[] content = byteBuf.array();
            // 将content转成字符串
            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println("byteBuf " + byteBuf);
            System.out.println("arrayOffset " +byteBuf.arrayOffset());
            System.out.println("readerIndex " +byteBuf.readerIndex());
            System.out.println("writerIndex " +byteBuf.writerIndex());
            System.out.println("capacity " +byteBuf.capacity());
            System.out.println("readableBytes " +byteBuf.readableBytes());
        }

        // 使用for取出各个字节
        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            System.out.println((char) byteBuf.getByte(i));
        }

        System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));
        System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));
    }
}
