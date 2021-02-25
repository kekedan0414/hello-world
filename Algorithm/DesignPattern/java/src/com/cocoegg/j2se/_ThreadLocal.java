package com.cocoegg.j2se;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author cocoegg
 * @date 2021/1/20 - 16:41
 */
public class _ThreadLocal {


    public static void main(String[] args) throws InterruptedException {

////        System.out.println(Runtime.getRuntime().maxMemory());
//
//        WeakReference<Byte[]> sf = new WeakReference<>(new Byte[3 * 1024 * 1024]);
//
////        System.out.println(Runtime.getRuntime().freeMemory());
//
//
//        System.out.println(sf.get());
//
//        System.gc();
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(sf.get());
//
////        System.out.println(Runtime.getRuntime().freeMemory());
//
////        SoftReference<Byte[]> sf2 = new SoftReference<>(new Byte[3 * 1024 * 1024]);
////
////        System.out.println(sf.get());

        Byte[] bytes = new Byte[1 * 1024 * 1024];

        PhantomReference<Byte[]> pr = new PhantomReference<>(bytes, new ReferenceQueue<>());

        System.out.println(pr.get());

        bytes = null;

        while (true) {
            System.out.println("循环检查队列是否有入队" + "\t" + pr.isEnqueued());
            if (pr.isEnqueued()) {
                System.out.println("对象已被GC清理");
                break;
            }
            System.gc();
            TimeUnit.SECONDS.sleep(1);
        }

    }


}

