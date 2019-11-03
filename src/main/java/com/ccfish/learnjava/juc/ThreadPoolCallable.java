package com.ccfish.learnjava.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: Ciaos
 * @Date: 2019/11/3 21:13
 */

public class ThreadPoolCallable {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>(){
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i = 0; i <= 100; i++) {
                        sum=sum+i;
                    }
                    return sum;
                }
            });
            futureList.add(future);
        }
        pool.shutdown();
        for (Future<Integer> future : futureList) {
            System.out.println(future.get());
        }
    }
}
