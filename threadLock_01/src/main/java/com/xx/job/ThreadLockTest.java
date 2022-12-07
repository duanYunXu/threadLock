package com.xx.job;

import java.util.concurrent.*;

/**
 * @ClassName ThreadLockTest
 * @Description
 * @Author xuxiang
 * @Date 15:25 2022/7/19
 **/
public class ThreadLockTest {

    public static void main(String[] args) throws InterruptedException {
         final int threadSize = 1000;
        ThreadUnsafeExample example = new ThreadUnsafeExample();
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = new ThreadPoolExecutor(5,1000,25,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() ->{
                example.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(example.get()+"-----------------------------------");
    }
}
