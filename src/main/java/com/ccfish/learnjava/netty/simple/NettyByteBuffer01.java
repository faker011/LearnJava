package com.ccfish.learnjava.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Ciaos
 * @Date: 2020/5/10 21:45
 */

public class NettyByteBuffer01 {
    public static void main(String[] args) {
        // 1. 创建对象，该对象包含一个数组arr，是一个byte[10]
        // 2. 在netty buffer中 不需要使用flip进行反转
        // ，因为netty维护了readerIndex 和 writerIndex
        // 通过readerIndex writerIndex capacity 将 buffer 分成三个区域
        // 0-readerIndex -> discardUnreadable 已经读取的区域
        // readerIndex -> writerIndex 可读区域
        // writerIndex -> capacity 可写区域

        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }

        // 输出
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }

    }
}
