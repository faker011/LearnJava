package com.ccfish.learnjava.juc;

/**
 * @Author: Ciaos
 * @Date: 2019/10/24 22:07
 */

public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        // 从主存中读取flag，但是td还未进行修改，此时取到的flag是false，while执行的一直都是false
        while (true){
            /*synchronized (td){
                if(td.isFlag()){
                    System.out.println("while");
                    break;
                }
            }*/
            if(td.isFlag()){
                System.out.println("while");
                break;
            }
        }
    }
}
class ThreadDemo implements Runnable{

    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        // 当线程从主存中读取线程，再写回主存
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setFlag(true);
        System.out.println("flag:"+isFlag());
    }
}