package com.xx.job.day01;

/**
 * @ClassName ThreadTest
 * @Description
 * @Author xuxiang
 * @Date 14:26 2022/12/7
 **/
public class ThreadTest {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("main"+i);

        }
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("mythread"+i);
        }
    }
}
