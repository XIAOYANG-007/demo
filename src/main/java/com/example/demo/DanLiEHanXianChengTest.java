package com.example.demo;


public class DanLiEHanXianChengTest {

    private  static volatile  DanLiEHanXianChengTest dlehT;


    public  DanLiEHanXianChengTest(){
    }


    public static DanLiEHanXianChengTest getDlehT(){
        if(dlehT==null)
        synchronized (DanLiEHanXianChengTest.class){
            if(dlehT==null){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dlehT=new DanLiEHanXianChengTest();
            }
        }
        return dlehT;
    }
}
