package com.ccfish.learnjava.jvm;

public class RuntimeTest {
    public static void main(String[] args) {
        System.out.println("MAX_MEMORY:" +byteToM(Runtime.getRuntime().maxMemory()));
        System.out.println("TOTAL_MEMORY:" + byteToM(Runtime.getRuntime().totalMemory()));
    }
    public static double round(double num, int scale)
    {
        return Math.round(Math.pow(10, scale)*num)/Math.pow(10, scale);
    }
    public static double byteToM(long num)
    {
        return round(num/1024/1024, 2);
    }
}
