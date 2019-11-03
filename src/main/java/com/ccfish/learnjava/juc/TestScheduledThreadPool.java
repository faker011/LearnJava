package com.ccfish.learnjava.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: Ciaos
 * @Date: 2019/11/3 21:19
 *
 * 线程调度 可进行延迟或定时执行
 */

public class TestScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        // 分配任务
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.schedule(() -> {
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() +":"+ num);
                return num;
            }, 3, TimeUnit.SECONDS);

            System.out.println(future.get());
        }
        // 关闭
        pool.shutdown();
    }
}
