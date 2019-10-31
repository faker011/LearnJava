package com.ccfish.learnjava.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Ciaos
 * @Date: 2019/10/31 20:55
 * @Description 生产者消费者案例
 *
 * -->等待唤醒机制
 * 避免虚假唤醒问题，应该总是使用在循环中（if->while）
 *
 * 使用锁的方式
 *      Condition
 *      Condition接口描述了可能会与锁有关联的条件变量。
 *      这些变量在用法上使用Object.wait访问的隐式监视器类似，
 *      但提供了更强大的功能。需要特别指出的是，单个
 *      Lock可能与多个Condition对象关联。为了避免兼容性问题，
 *      Condition方法的名称与对应的Object版本中的不同。
 *
 *      在Condition对象中，与wait、nitify、notifyAll方法对应的分别是
 *      await、signal、和signalAll
 *
 *      Condition实例实质上被绑定到一个锁上。要为特定Lock实例
 *      获得Condition实例，请使用其newCondition()方法
 */

public class TestProductorAndConsumerForLock {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor,"生产者A").start();
        new Thread(consumer, "消费者B").start();
    }

}

/**
 * 店员
 */
class Clerk{

    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // 进货
    public  void get(){

        lock.lock();
        try{
            while(product >= 10){
                System.out.println("产品已满，无法添加");
                // 等待
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + ":" + ++product);
            // 通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 卖货
    public void sell(){
        lock.lock();
        try{
            while(product <= 0){
                System.out.println("缺货");
                // 等待
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":" + --product);
            // 通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

// 生产者
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        // 生产
        for (int i = 0; i <20 ; i++) {
            clerk.get();
        }
    }
}

// 消费者
class Consumer implements Runnable{

    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i <20 ; i++) {
            clerk.sell();
        }
    }
}