package com.example.demo.AlternatingOutput;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.LockSupport;


/*
交替输出，本方式采用 LockSupport
LockSupport    线程阻塞工具类，所有方法都是静态方法，可以让线程在任意位置阻塞，阻塞也有其对应的唤醒方法
unpark(thread)  唤醒指定线程
park()  挂起

LockSupport的等待唤醒机制不需要持有锁不需要在同步代码块内，
他的park，unpark方法是wait/notify的升级版本

LockSupport的加锁与唤醒是使用的一个许可证(permit)的一个机制
线程阻塞需要消耗凭证(permit)，这个凭证最多只有1个。
当调用park方法时：
如果有凭证，则会直接消耗掉这个凭证然后正常退出
如果无凭证，就必须阻塞等待凭证可用
而unpark则相反，它会增加一个凭证，但凭证最多只能有1个，累加无放。


 */
public class OutPutTwo {

    public static List<String> outPutListN= Arrays.asList("1","2","3","4","5","6");
    public static List<String> outPutListD= Arrays.asList("A","B","C","D","E","F");
    public static List<String> outPutListC= Arrays.asList("a","b","c","d","e","f");

    public static Thread threadN,threadD,threadC;

    public static void main(String[] args) {
        threadN=new Thread(()->{
            for(String ln:outPutListN){
                System.out.println(ln);
                LockSupport.unpark(threadD);
                LockSupport.park();

            }
        });
        threadD=new Thread(()->{
            for(String ld:outPutListD){
                LockSupport.park();
                System.out.println(ld);
                LockSupport.unpark(threadC);
            }
        });
        threadC=new Thread(()->{
            for(String lc:outPutListC){
                LockSupport.park();
                System.out.println(lc);
                LockSupport.unpark(threadN);
            }
        });
        threadN.start();
        threadD.start();
        threadC.start();
    }
}
