package com.xx.job.day01;


import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RunnableTest01
 * @Description
 * @Author xuxiang
 * @Date 14:55 2022/12/29
 **/
@Slf4j(topic = "c.RunnableTest01")
public class RunnableTest01 {

    public static void main(String[] args) {
        RunnableTest runnableTest = new RunnableTest();
        Thread thread = new Thread(runnableTest);
        thread.start();
        log.info("dddddddddd");
    }
}

@Slf4j(topic = "c.RunnableTest")
class RunnableTest implements Runnable{

    @Override
    public void run() {
        log.info("aaaaa");
    }
}
