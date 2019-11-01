package com.ccfish.learnjava.juc;

/**
 * @Author: Ciaos
 * @Date: 2019/11/1 21:42
 *
 * 线程8锁
 *
 * 题目：判断打印的 one 还是 two
 * 1. 两个普通同步方法，两个线程，标准打印 // one two
 * 2. 新增Thread.sleep() 给getOne() // one two
 * 3. 新增普通方法 getThree() 打印 // three one two
 * 4. 两个普通的同步方法，两个number对象，打印 //two one
 * 5. 修改getOne()为静态同步方法，打印 // two one
 * 6. 修改两个方法均为静态同步方法，一个number对象 // one two
 * 7. 一个是静态同步方法，一个是非静态同步方法，两个number对象 // two one
 * 8. 两个均为静态同步方法，两个number对象 // one two
 *
 * 线程八锁的关键：
 * 1.非静态方法的锁默认为this，静态方法的锁为 对应的Class实例
 * 2.某一个时刻内，只能有一个线程持有锁，无论几个方法
 */

public class TestThread8Monitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();
        new Thread(new Runnable(){

            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable(){

            @Override
            public void run() {
                //number.getTwo();
                number2.getTwo();
            }
        }).start();
        /*new Thread(new Runnable(){

            @Override
            public void run() {
                number.getThree();
            }
        }).start();*/
    }
}
class Number{
    public static synchronized void getOne(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }
    public static synchronized void getTwo(){
        System.out.println("two");
    }
    public void getThree(){
        System.out.println("Three");
    }
}
