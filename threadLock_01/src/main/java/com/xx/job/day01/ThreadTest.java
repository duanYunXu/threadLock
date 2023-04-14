package com.xx.job.day01;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ThreadTest
 * @Description
 * @Author xuxiang
 * @Date 14:26 2022/12/7
 **/
@Slf4j(topic = "c.test")
public class ThreadTest {

    public static void main(String[] args) {
        MyThread myThread = new MyThread("t0");
        myThread.start();
        for (int i = 0; i < 10; i++) {
            log.info("main"+i);
        }
    }
}
@Slf4j(topic = "c.test")
class MyThread extends Thread{

    MyThread(String name){
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            log.info("mythread"+i);
        }
    }
}
