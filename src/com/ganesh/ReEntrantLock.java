package com.ganesh;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntrantLock {
    /*
    ReentrantLock
    - It has the same behavior as the "Synchronized" approach
    - Of course it has some additional features
    new ReentrantLock(boolean fairness)
        if the fairness parameter is set to be TRUE then the longest w
        waiting thread will get the lock.

        else
        then there is no access order

        IMPORTANT: a good approach is to use try-catch-finally blocks when doing the critical section and call unlock() in the finally block.


     */

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();//Lock is interface
    private static void increment(){

        lock.lock();
        try{
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        }finally{
            lock.unlock();//Given thread releases the lock
        }

    }
    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
    }
}
