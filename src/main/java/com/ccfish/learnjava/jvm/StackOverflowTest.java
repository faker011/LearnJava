package com.ccfish.learnjava.jvm;

/**
 * 递归操作和栈帧；
 *      栈满之后抛出 java.lang.StackOverflowError
 */
public class StackOverflowTest {

    public static void main(String[] args) {
        fun();
    }
    public static void fun(){
        fun();
    }
}
