package com.cocoegg.pattern.SingleTon;

/**
 * @author cocoegg
 * @date 2021/1/6 - 10:23
 */
public class HungrySingler {

    public static void main(String[] args) {
        HungrySingler.getHungrySingler().doSomethings();
    }

    private static HungrySingler hungrySingler = new HungrySingler();

    private HungrySingler() {
    }

    public static HungrySingler getHungrySingler() {
        return hungrySingler;
    }

    public void doSomethings(){
        System.out.println("hello,world!");
    }
}
