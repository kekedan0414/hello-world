package com.cocoegg.j2se;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cocoegg
 * @date 2021/1/21 - 21:29
 */
public class _ThreadLocal_DataFormat_unsafe {

    public static void main(String[] args) {

        //ThreadFactory threadFactory = r -> new Thread(r,"Thread Name %d");
        ExecutorService executor = new ThreadPoolExecutor(100,
                100,
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(100));

        for(int i=0;i<10;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Name="+Thread.currentThread().getName()+"->" + unSafe_Format.parse("2020-12-20 12:20:85"));

                }
            };
            Thread thread = new Thread(runnable);
            executor.execute(thread);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("it's done!");
        executor.shutdown();

    }
}

class unSafe_Format {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parse(String dateStr) {
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
