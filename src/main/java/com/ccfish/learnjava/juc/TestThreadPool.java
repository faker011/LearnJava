package com.ccfish.learnjava.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Ciaos
 * @Date: 2019/11/3 20:29
 * 1.线程池：
 *       提供了一个线程队列，队列中保存着所有等待状态的线程。
 *       避免了创建与销毁的额外开销，提高了响应的速度
 * 2.线程池的体系结构：
 *       java.util.concurrent.Executor: 负责线程的使用与调度的跟接口
 *          |-- ** ExecutorService 子接口： 线程池的主要接口
 *              |--ThreadPoolExecutor 线程池实现类。
 *              |--ScheduledExecutorService 子接口：负责线程的调度
 *                  |--ScheduledThreadPoolExecutor 实现类 ：继承了ThreadPoolExecutor 同时也实现了ScheduledExecutorService
 * 3. 工具类 ：Executors
 *       ExecutorService newFixedThreadPool() 创建固定大小的线程池
 *       ExecutorService newCachedThreadPool() 缓存线程池：线程池的数量不固定，可以根据需求自动的更改数量。
 *       ExecutorService newSingleThreadExecutor() 创建单个线程池，线程池中只有一个线程
 *
 *       ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务
 *
 */

public class TestThreadPool {
    public static void main(String[] args) {
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        // 为线程池中的线程分配任务
        ThreadPoolDemo tpd = new ThreadPoolDemo();
        for (int i = 0; i <100 ; i++) {
            pool.submit(tpd);
        }
        // 关闭线程池
        pool.shutdown();
    }
}
class ThreadPoolDemo implements Runnable{

    private int i;
    @Override
    public void run() {
        while (i < 100){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() +":"+ i++);
        }
    }
}
