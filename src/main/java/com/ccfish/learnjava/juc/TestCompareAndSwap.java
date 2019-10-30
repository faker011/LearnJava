package com.ccfish.learnjava.juc;

/**
 * @Author: Ciaos
 * @Date: 2019/10/27 21:15
 */

public class TestCompareAndSwap {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0;i< 100; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectVale = cas.getValue();
                    cas.compareAndSet(expectVale, (int)(Math.random()*101));
                }
            }).start();
        }
    }

}
class CompareAndSwap{
    private int value;

    public synchronized int getValue(){
        return this.value;
    }

    public synchronized int compareAndSwap(int expectValue, int newValue){
        int oldValue = value;
        if (expectValue==oldValue){
            this.value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectValue, int newValue){
        return expectValue==compareAndSwap(expectValue, newValue);
    }
}
