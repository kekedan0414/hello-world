package com.cocoegg.j2se;

import java.text.SimpleDateFormat;
import java.util.Random;

public class _Threadlocal_DateFormat implements Runnable{

    private static final ThreadLocal<SimpleDateFormat>formatter=ThreadLocal.withInitial(()->
            new SimpleDateFormat("yyyy-MM--dd :HH-mm"));

    public static void main(String[]args){
        _Threadlocal_DateFormat obj=new _Threadlocal_DateFormat();
        for(int i=0;i<10;i++){
            Thread t=new Thread(obj,""+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread Name="+Thread.currentThread().getName()+" "+"default formatter="+formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        formatter.set(new SimpleDateFormat());
        System.out.println("Thread Name="+Thread.currentThread().getName()+" "+"formatter="+formatter.get().toPattern());

    }
}