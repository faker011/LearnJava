package com.ccfish.learnjava.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Ciaos
 * @Date: 2019/10/25 22:15
 * i = 10;i
 * i=i++;
 * sout i->10
 *
 * 底层分为三部
 * i = temp;
 * i = i+1;
 * i = temp
 *
 * 二。原子变量， java.util.concurrent.atomic 包下提供了常用的原子变量：
 *      1. volatile保证了内存的可见性
 *      2. CAS算法保证了数据的原子性 （Compare-And-Swap)
 *         CAS算法是硬件对于并发操作共享数据的支持
 *         CAS包含了三个操作数：
 *             内存值V
 *             预估值A
 *             更新值B
 *             当且仅当V==A时，V==B，否则不进行操作
 */

public class TestAtomicDemo{
    public static void main(String[] args) {
        AtomicDemo at = new AtomicDemo();
        for(int i = 0;i < 10;i ++){
            Thread td = new Thread(at);
            td.start();
        }
    }

}
class AtomicDemo implements Runnable{

    // 使用volatile修饰，原子性问题依然存在，使用原子变量
    // private int serialNumber = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    public int getSerialNumber() {
        return atomicInteger.incrementAndGet();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }
}