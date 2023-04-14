package com.xx.job.day02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolTest
 * @Description
 * @Author xuxiang
 * @Date 11:46 2023/3/28
 **/
@Slf4j(topic = "c.ThreadPoolTest")
public class ThreadPoolTest {

    private static BlockingQueue blockingQueue = new ArrayBlockingQueue(100);

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 3000, TimeUnit.MICROSECONDS, blockingQueue, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 1000; i++) {
            log.debug("第{}个任务", i);
            try {
                test(i);
            } catch (RejectedExecutionException e) {
                e.printStackTrace();;
                log.error("拒绝执行{}", i);
                Thread.sleep(2000);
                log.debug("继续执行{}", i);
                test(i);
            }
        }
    }

    public static void test(int index){
        threadPoolExecutor.submit( () -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                }
                log.debug("test run {}", index);
            });
            log.debug("队列剩余空间:{}", blockingQueue.remainingCapacity());
    }
}
