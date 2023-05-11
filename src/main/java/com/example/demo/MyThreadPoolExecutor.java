package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MyThreadPoolExecutor {

    public ThreadPoolExecutor poolExecutor;

    public  MyThreadPoolExecutor(){
        if(poolExecutor==null)
            synchronized (MyThreadPoolExecutor.class){
                if(poolExecutor==null){
                    poolExecutor=new ThreadPoolExecutor(1,1,0l, TimeUnit.MINUTES,new ArrayBlockingQueue<>(10));
                }
             }
    }
}
