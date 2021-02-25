package com.cocoegg.pattern.SingleTon.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 13:17
 */
public class Singleton1 {
    public static void main(String[] args) {

        Singleton1 singleton1 = Singleton1.getInstance();
        Singleton1 singleton2 = Singleton1.getInstance();
        System.out.println(singleton1.hashCode());
        System.out.println(singleton2.hashCode());
    }

    private static Singleton1 instance = new Singleton1();

    private Singleton1(){}

    public static Singleton1 getInstance() {
        return instance;
    }
}
