package com.example.demo.AlternatingOutput;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*
交替输出，本方法采用notify(),wait(),方法处理
obj.notify();唤醒在该对象上等待的某一个线程，控制不了是谁
obj.notifyAll();唤醒在该对象上等待的所有线程，其他线程随机抢这个锁
obj.wait();让出当前线程在这个对象上的锁，并阻塞当前线程

这样的方式最多支持2个线程交替，不是很推荐

CountDownLatch 同步工具类
countDown()  门栓值减1
await()    等待方法，直到门栓值为0则唤醒放开
await(时间数值，时间单位)，等待方法，直到时间结束唤醒

使用场景1：多个线程，插上门栓，必须等多个线程都执行完毕，
则设定门栓值为开的线程个数，主线程加上await()方法，
直到线程跑完执行countDown()方法，所有线程跑完门栓值变成0，主线程继续
使用场景2：(当前)多个线程，决定先后顺序时，可以给一个线程加上等待方法，
等待另一线程执行后去掉门栓


 */
public class OutputOne {

    public static List<String> outPutListN= Arrays.asList("1","2","3","4","5","6");
    public static List<String> outPutListD= Arrays.asList("A","B","C","D","E","F");
    public static List<String> outPutListC= Arrays.asList("a","b","c","d","e","f");

    private final static Object obj=new Object();

    static CountDownLatch latch = new CountDownLatch(1);
    public OutputOne(){

    }

    public static void main(String[] args) {


        new Thread(()->{
            synchronized (obj){
                for (String lc:outPutListC){
                    try {
                        System.out.println(lc);
                        latch.countDown();
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                obj.notify();
            }
        },"t1").start();

        new Thread(()->{
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj){
                for (String ld:outPutListD){
                    try {

                        System.out.println(ld);

                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                obj.notify();

            }
        },"t2").start();
    }
}
