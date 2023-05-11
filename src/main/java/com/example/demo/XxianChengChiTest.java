package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

public class XxianChengChiTest {

    //单例线程池
    private static MyThreadPoolExecutor myThreadPoolExecutor;
    private static ThreadPoolExecutor poolExecutor;

    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        myThreadPoolExecutor=new MyThreadPoolExecutor();
        poolExecutor=myThreadPoolExecutor.poolExecutor;
        for(int i=0;i<100;i++){
            System.out.println("当前ID"+i);
            /*poolExecutor.execute(()->{
                System.out.println("当前时间"+System.currentTimeMillis());
            });*/
            int finalI = i;
            Future<String> str=poolExecutor.submit(()->{
                System.out.println("当前时间"+System.currentTimeMillis());
                return finalI + "";
            });
            try {
                String abcd= str.get();
                System.out.println(abcd);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Thread.sleep(2);
        }
        poolExecutor.shutdown();
    }
}
