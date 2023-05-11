package com.example.demo.AlternatingOutput;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




/*
交替输出，本方式采用ReentrantLock(rui en tring lok)
ReentrantLock与synchronized一样都是可重入锁

它的用法查看下面的例子
condition2.signal();//让此队列中休眠的线程唤醒
condition1.await();//让当前线程进入此队列中等待


与synchronized的区别
1可以多个队列，并设置某个等待队列的唤醒(lock.newCondition()，看以下代码)
2可以设置为公平锁(多线程运行时可能遇上线程饥饿问题，设置了优先级，或者锁，让某些线程一直霸占CPU资源，公平锁让)
3可以设置上锁超时对策 (lock.trylock(时间，单位))()
4锁可以被中断(lock.lockInterruptibly(),这样设置可以被打断，若在threadN是前面这样设置，线程外可以threadN.interrupt()打断其上锁以及运行)


*/
public class OutPutThree {

    public static List<String> outPutListN= Arrays.asList("1","2","3","4","5","6");
    public static List<String> outPutListD= Arrays.asList("A","B","C","D","E","F");
    public static List<String> outPutListC= Arrays.asList("a","b","c","d","e","f");

    public static Thread threadN,threadD,threadC;

    static CountDownLatch latch = new CountDownLatch(1);


    public static void main(String[] args) {

        Lock lock=new ReentrantLock();
        Condition condition1=lock.newCondition();//队列
        Condition condition2=lock.newCondition();
        Condition condition3=lock.newCondition();

        threadN=new Thread(()->{
            try {
                lock.lock();
                for(String ln:outPutListN){
                    System.out.println(ln);
                    latch.countDown();
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        threadD=new Thread(()->{
            /*try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            lock.lock();
            try {
                for(String ld:outPutListD){
                    System.out.println(ld);
                    condition3.signal();
                    condition2.await();
                }
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        });
        threadC=new Thread(()->{
/*            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            lock.lock();
            try {
                for(String lc:outPutListC){
                    System.out.println(lc);
                    condition1.signal();
                    condition3.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        });
        threadN.start();
        threadD.start();
        threadC.start();

    }
}
