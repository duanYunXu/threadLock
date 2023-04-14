#JUC
***
##进程
***
###概述
进程：程序是静止的，进程实体的运行过程就是进程，是系统进行资源分配的基本单位  
进程的特征：并发性、异步性、动态性、独立性、结构性
##线程
***
###
###概述
线程：线程是属于进程的，是一个基本的CPU执行单元，是程序执行流的最小单元。线程是进程中的一个实体，是系统独立调用的基本单位，线程本身不
拥有系统资源，只拥有一点在运行中必不可少的资源，与同属一个进程 的其他线程共识进程所拥有的全部资源   
进程和线程的关系：一个进程可以包含多个线程，这就是多线程，比如看视频是进程，图画、声音、广告等就是多个线程   
线程的作用：使多道程序更好的并发执行，提高资源利用率和系统吞吐量，增强操作系统的并发性能    
###并发与并行
并发：在同一时刻，有多个指令在多个CPU上同时执行
并行：在同一时刻，有多个指令在单个CPU上交替运行
###同步异步
同步：需要等待结果返回，才能继续运行   
异步：不需要等待结果返回，就能继续运行    
##进程线程对比
+ 进程基本上是相互独立的，而线程存在于进程内，是进程的一个子集   
+ 进程拥有共同资源，如内存空间等，供其内部的线程共享   
+ 进程间的通信较为复杂   
同一台计算机的进程通信称为IPC（Inter-process communication）     
  + 信号量：信号量是一个计数器，用于多进程对共享数据的访问，解决同步相关的问题并避免竞争条件
  + 共享存储：多个进程可以访问同一块内存空间，需要使用信号量用来同步对共享存储的访问
  + 管道通信：管道是用于连接一个读进程和一个写进程以实现它们之间通信的一个共享文件pipe文件，该文件同一时间只允许一个进程访问，所以只支持半双工通信     
     + 匿名管道（Pipes）：用于具有亲缘关系的父子进程间或者兄弟进程之间的通信
     + 全名管道（Names Pipes）：以磁盘文件的方式存在，可以实现本机任意两个进程通信，遵循FIFO   
  + 消息队列：内核中存储消息的链表，由消息队列标识，能在不同进程之间提供全双工信，对比管道：   
    + 匿名管道存在于内存文件;全名管道存在于实际的磁盘介质或者文件系统;消息队列存放在内核中，只有在内核重启（操作系统重启）或者显示地删除一个消息队列时，该消息队列才被真正删除   
    + 读进程可以根据消息类型有选择地接收消息，而不像FIFO那样只能默认地接收   
不同计算机之间的进程通信，需要通过网络，并斗筲之才共同的协议，例如HTTP
    + 套接字：与其它通信机制不同的是，可用于不同机器间的互相通信   
  + 线程难相对简单，因为线程之间共享进程内的内存，一个例子是多个线程可以访问同一个共享变量   
  + java中的通信机制：volatile、等待/通知机制、join方式、InheritableThreadLocal、MapppedByteBuffer
  + 线程更轻量，线程上下文切换成本一般上要比进程上下文切换低
***

## 线程的创建
### Thread
Thread创建线程方式：创建线程类、匿名内部类
  + start()方法底层其实是给CPU注册当前线程，并且触发run()方法执行   
  + 线程的启动必须调用start方法，如果线程直接调用run()方法，相当于变成了普通类的执行，此时主线程将执行该线程   
  + 建议线程先创建子线程，主线程的任务放在之后，否则主线程（main）永远是先执行完     

Thread构造器
+ public Thread()   
+ public Thread(String name)
```
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
```
```
public class ThreadTest01 {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("0000");
        },"t1").start();
        System.out.println("11111");
    }
}
```
继承Thread优缺点：
+ 优点：编码简单
+ 缺点：线程类已经继承了Thread类就无法继承其他类了，功能不能通过继承拓展（单继承的局限性）
***
### Runnable
Runnable创建线程方式：创建线程类，匿名内部类方式
```
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
```
Thread类本身也实现了Runnable接口，Thread类中持有Runnable的属性，执行线程run方法底层是调用Runnable#run:
```
public class Thread implements Runnable {
    private Runnable target;
    
    public void run() {
        if (target != null) {
          	// 底层调用的是 Runnable 的 run 方法
            target.run();
        }
    }
}
```
实现Runnable方式的优缺点：
+ 缺点：代码复杂一点
+ 优点：
  + 线程任务类只实现了Runnable接口，可以继续继承其他类，避免了单继承的局限性
  + 同一个线程任务对象可以被包装成多个线程对象
  + 适合多个线程去共享同一个资源
  + 实现解耦操作，线程任务代码可以被多个线程共享，线程任务代码和线程独立
  + 线程池可以放入实现Runnable接口或Callale线程任务对象
***
###Callable
实现Callable接口   
  1. 定义一个线程任务类实现Callable接口，申明线程执行的结果类型
  2. 重写线程任务类的call方法，这个方法可以直接返回执行的结果
  3. 创建一个Callable的线程任务对象
  4. 把Callable的线程任务对象包装成一个未来任务对象
  5. 把未来任务对象 包装成线程对象
  6. 调用线程的start()方法启动线程







