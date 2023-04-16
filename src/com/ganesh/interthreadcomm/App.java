package com.ganesh.interthreadcomm;

public class App {

    public static int counter1=0;
    public static int counter2=0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    // Because App object has a single lock: this is why the methods cannot be executed
    // simultaneously - Time slicing algorithm will not allow it
    // usually it is not a good practice to use synchronized keyword
    // because it is a global lock
//    public static synchronized void increment2(){
//        counter2++;
//    }
    public static void increment1(){
        // At the same time only one thread can execute this block of code
        // At the same time != Parallel - CPU time slicing
        synchronized (App.class) {
            counter1++;
        }
    }
    // we have to make sure that this method is executed only by a single thread
    // at a given time
    public static void increment2(){
        synchronized (App.class) {
            counter2++;
        }
    }
    public static void process(){


        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=0;i<100;i++){
                    increment1();
                }
            }
        });

        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=0;i<100;i++){
                    increment2();
                }
            }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("The counter is :"+counter1);
    }

    public static void main(String[] args) {
        process();
    }
}
