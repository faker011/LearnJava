package com.ccfish.learnjava.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Ciaos
 * @Date: 2019/10/28 21:06
 */

public class TestCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5 ; i++) {
            Thread thread = new Thread(latchDemo);
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("TIME:" + (end-start));
    }
}

class LatchDemo implements Runnable {

    private CountDownLatch countDownLatch;

    public LatchDemo(CountDownLatch latch) {
        this.countDownLatch = latch;
    }

    @Override
    public void run() {
        synchronized (this){
            try{
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
