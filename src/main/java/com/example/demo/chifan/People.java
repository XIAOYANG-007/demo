package com.example.demo.chifan;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

//人
public class People  extends  Thread{

    public int index;

    public String name;

    public Chopsticks left;

    public Chopsticks right;


    public People(){

    }

    public People(int id,String name,Chopsticks left,Chopsticks right){
        this.index=id;
        this.name=name;
        this.left=left;
        this.right=right;
    }


    public  void run(){
        if(index%2==1){
            synchronized (left){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (right){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(index+" "+name+" 吃完了");
                }
            }
        }
        if(index%2==0){
            synchronized (right){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (left){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(index+" "+name+" 吃完了");
                }
            }
        }
    }

}
