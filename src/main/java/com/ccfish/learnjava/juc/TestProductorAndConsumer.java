package com.ccfish.learnjava.juc;

/**
 * @Author: Ciaos
 * @Date: 2019/10/31 20:55
 * @Description 生产者消费者案例
 *
 * -->等待唤醒机制
 * 避免虚假唤醒问题，应该总是使用在循环中（if->while）
 *
 */

public class TestProductorAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor,"生产者A").start();
        new Thread(consumer, "消费者B").start();
    }

}
/*


// 店员
class Clerk{

    private int product = 0;

    // 进货
    public synchronized void get(){
        while(product >= 10){
            System.out.println("产品已满，无法添加");
            // 等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName() + ":" + ++product);
        // 通知
        this.notifyAll();
    }

    // 卖货
    public synchronized void sell(){
        while(product <= 0){
            System.out.println("缺货");
            // 等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + --product);
        // 通知
        this.notifyAll();
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
}*/
