package com.xx.job.day01;

/**
 * @ClassName ThreadTest01
 * @Description
 * @Author xuxiang
 * @Date 14:43 2022/12/29
 **/
public class ThreadTest01 {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("0000");
        },"t1").start();
        System.out.println("11111");
    }
}
