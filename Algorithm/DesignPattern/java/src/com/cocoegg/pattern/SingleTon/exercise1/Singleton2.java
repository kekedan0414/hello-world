package com.cocoegg.pattern.SingleTon.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 13:17
 */
public class Singleton2 {
    public static void main(String[] args) {

        Singleton2 singleton1 = Singleton2.getInstance();
        Singleton2 singleton2 = Singleton2.getInstance();
        System.out.println(singleton1.hashCode());
        System.out.println(singleton2.hashCode());
    }

    private volatile static Singleton2 instance;

    private Singleton2(){}

    public static Singleton2 getInstance() {
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null)
                    instance = new Singleton2();
            }
        }
        return instance;
    }
}
