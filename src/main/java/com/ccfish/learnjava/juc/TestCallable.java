package com.ccfish.learnjava.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: Ciaos
 * @Date: 2019/10/28 21:22
 * 1.创建执行线程的方式3：实现callable接口，相较于实现runnable接口的方式，方法可以有返回值，并且可以抛出异常
 */

public class TestCallable {
    public static void main(String[] args) {
        ThreadCallableDemo threadCallableDemo = new ThreadCallableDemo();
        // 执行callable的方式需要一个 FutureTask 实现类的支持，用来接收运算结果
        FutureTask<Integer> futureTask = new FutureTask<>(threadCallableDemo);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            // futureTask.get() 得到结果
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
class ThreadCallableDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum+=i;
        }
        return sum;
    }
}