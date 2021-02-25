package com.cocoegg.pattern.SingleTon.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 13:17
 */
public class Singleton3 {
    public static void main(String[] args) {

        Singleton3 singleton1 = Singleton3.getInstance();
        Singleton3 singleton2 = Singleton3.getInstance();
        System.out.println(singleton1.hashCode());
        System.out.println(singleton2.hashCode());
    }

    private Singleton3(){}

    private static class InnerSingleton {

        private static Singleton3 instance = new Singleton3();

    }

    public static Singleton3 getInstance() {

        return InnerSingleton.instance;
    }
}
