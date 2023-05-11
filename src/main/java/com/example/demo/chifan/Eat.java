package com.example.demo.chifan;


/*
哲学家进餐问题，5个人，5只筷子，5人围圈而坐，
5只筷子分布于5人空隙之中，每人拿到2只筷子即可进餐，
但是它们脑袋有点轴，每人都选择先拿右手再拿左手，于是出现了死锁
解决方式：请查看people类中的多线程处理方法
*/
public class Eat {

    public static void main(String[] args) {

        Chopsticks cs0=new Chopsticks();
        Chopsticks cs1=new Chopsticks();
        Chopsticks cs2=new Chopsticks();
        Chopsticks cs3=new Chopsticks();
        Chopsticks cs4=new Chopsticks();

        People p0=new People(1,"张三",cs0,cs1);
        People p1=new People(2,"李四",cs1,cs2);
        People p2=new People(3,"王五",cs2,cs3);
        People p3=new People(4,"赵六",cs3,cs4);
        People p4=new People(5,"刘七",cs4,cs0);

        p0.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();

    }
}
