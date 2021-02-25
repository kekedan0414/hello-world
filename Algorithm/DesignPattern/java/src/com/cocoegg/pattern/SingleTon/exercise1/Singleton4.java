package com.cocoegg.pattern.SingleTon.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 13:31
 */
public class Singleton4 {
    public static void main(String[] args) {
        Singleton44 singleton44 = Singleton44.getInstance();
        Singleton44 singleton444 = Singleton44.getInstance();
        System.out.println(singleton44.hashCode());
        System.out.println(singleton444.hashCode());
    }
}


enum Singleton44 {
    INSTANCE;
    public static Singleton44 getInstance(){
        return INSTANCE;
    }
}



