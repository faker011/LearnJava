package com.ccfish.learnjava.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Ciaos
 * @Date: 2019/10/30 21:57
 * 用于解决线程安全的方式
 * <p>
 * 1.同步代码块
 * 2.同步方法
 * 3.同步锁 1.5以后
 * 是一个显示锁，需要通过lock方法进行上锁
 * 通过unlock方法进行释放锁
 * 
 */

public class TestLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "一号窗口").start();
        new Thread(ticket, "二号窗口").start();
        new Thread(ticket, "三号窗口").start();
    }


}

class Ticket implements Runnable {

    private int tick = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock(); // 上锁
            try {
                Thread.sleep(200);
                if (tick > 0) {
                    System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --tick);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                // finally里释放
                lock.unlock();
            }

        }

    }
}