package com.ccfish.learnjava.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Ciaos
 * @Date: 2019/11/1 21:26
 * 读写锁
 * 写写/读写 需要互斥
 * 读读 不需要互斥
 */

public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockDemo rw = new ReadWriteLockDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                rw.set((int)(Math.random()*101));
            }
        }, "Write:").start();

        for (int i = 0; i <100 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rw.get();
                }
            },"Read "+ i).start();
        }
    }
}
class ReadWriteLockDemo{

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();


    // 读
    public void get(){
        // 读锁
        Lock lock = this.lock.readLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 写
    public void set(int number){
        // 写锁
        Lock lock = this.lock.writeLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}