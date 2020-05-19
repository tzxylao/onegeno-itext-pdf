package com.lll.learn.sync;

import java.util.concurrent.*;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/18 15:13
 **/
public class SyncPractice {

    public synchronized void sayHello() {
        sayHi();
        System.out.println("hello");
    }

    public synchronized void sayHi(){
        System.out.println("Hi");
    }

    public static synchronized void sayBye(){
        System.out.println("Bye");
        sayGood();
    }

    public static synchronized void sayGood(){
        System.out.println("Good");
    }

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS,new SynchronousQueue<>());

    public static void main(String[] args) throws InterruptedException {
        SyncPractice syncPractice = new SyncPractice();
        executor.execute(()->{
            syncPractice.sayHello();
            SyncPractice.sayBye();
        });
        Thread.sleep(10);
        executor.execute(()->{
            syncPractice.sayHi();
            SyncPractice.sayGood();
        });
        executor.shutdown();
    }
}
