package com.ganesh;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {

    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private final Object lock = new Object();
    private final List<Integer> list = new ArrayList<>();
    private int value = 0;

    public static void main(String[] args) {
        ProducerConsumer processor = new ProducerConsumer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

    public void producer() throws InterruptedException {
        synchronized (lock) {

            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    lock.wait(); // In this case we wait on the lock object
                } else {
                    System.out.println("Adding new item: " + value + " Size: " + list.size());
                    list.add(value);
                    value++;
                    // We can call the notify - because the other thread will be notified
                    // Only when it is in a waiting state
                    lock.notify();
                }
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {

            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    value=0;
                    lock.wait(); // In this case we wait on the lock object
                } else {
                    System.out.println("Removing item: " + list.remove(list.size()-1));

                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }
}
