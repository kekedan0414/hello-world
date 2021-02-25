package com.cocoegg.j2se;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cocoegg
 * @date 2021/1/22 - 9:23
 */


public class ThreadLocalTest {
    public static void main(String[] args) {

        SimpleDataFormatUnsafe simpleDataFormatUnsafe = new SimpleDataFormatUnsafe();
        //simpleDataFormatUnsafe.test();
        //simpleDataFormatUnsafe.test1();
        //simpleDataFormatUnsafe.test2();
        simpleDataFormatUnsafe.test3();
        //simpleDataFormatUnsafe.test31();
        //simpleDataFormatUnsafe.test32();

        // threadlocal定义为非static，则下面每个实例都持有自己的threadlocal属性，是浪费空间的。
        SimpleDataFormatUnsafe simpleDataFormatUnsafe1 = new SimpleDataFormatUnsafe();
        System.out.println(simpleDataFormatUnsafe.threadLocal);
        System.out.println(simpleDataFormatUnsafe1.threadLocal);

    }
}


class SimpleDataFormatUnsafe {

    // 问题：全局SimpleDateFormat，多线程不安全，SimpleDateFormat里的Calendar的Set/Get在多线程环境下引起赋值/取值混乱。
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 解决方案1： 局部变量 test1()
    public SimpleDateFormat simpleDateFormat_local = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    // 解决方案2： 加锁 test2()
    final static Object objectLock = new Object();

    // 解决方案3：ThreadLocal

    // 每个线程里面需要set(new T) test3()
    public ThreadLocal threadLocal = new ThreadLocal<SimpleDateFormat>();

    // 生产T，在get的时候会调用SuppliedThreadLocal的initialValue方法返回生产的T test31()
    public ThreadLocal threadLocalWithInit = ThreadLocal.withInitial(()->new SimpleDateFormat("yyy-MM-dd HH:mm:ss"));

    // static修饰 test32()
    public static ThreadLocal threadLocalWithInitWithStatic = ThreadLocal.withInitial(()->new SimpleDateFormat("yyy-MM-dd HH:mm:ss"));


    /**思考1：加锁影响效率，但是局部变量和ThreadLocal的区别在哪里*/
    // 关键描述：为线程提供局部变量(线程级别的局部变量，这个局部变量在线程内部都可见)。方法内部定义的变量，方法走完出栈。
    // 就是因为作用域不同，所以线程内部共享的某个变量可以定义为ThreadLocal，就不必在每个方法里面定义这个变量，反复传递了。

    /**思考2：ThreadLocal 为什么ThreadLocal要加static修饰？*/
    // 加上static表示这个引用属于类，只加载一次，全局只有一份。
    // 如果是非static的话，每个new出来的对象(SimpleDataFormatUnsafe)都会持有一个自己的ThreadLocal，浪费空间。

    /**思考3：ThreadLocal 为什么用弱引用，强引用有什么问题？*/
    // 强引用>软引用>弱引用>虚引用
    // 软引用：GC时不会回收，只有在内存不足的时候会回收。适合做缓存。
    // 弱引用：GC回收。
    // 在线程池中出现内存泄漏。线程池中的线程复用，因此
    // 每个线程持有一个ThreadLocalMap，key是ThreadLocal, value是ThreadLocal持有的值。如果key是非static的话，


    static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(10));

    public static void test() {
        for (int i = 0; i < 20; i++) {
            poolExecutor.execute(() -> {
                String dateString = simpleDateFormat.format(new Date());
                try {
                    Date parseDate = simpleDateFormat.parse(dateString);
                    String dateString2 = simpleDateFormat.format(parseDate);
                    System.out.println(dateString.equals(dateString2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void test1() {
        for (int i = 0; i < 20; i++) {
            final SimpleDataFormatUnsafe simpleDataFormatUnsafe = new SimpleDataFormatUnsafe();
            poolExecutor.execute(() -> {
                String dateString = simpleDataFormatUnsafe.simpleDateFormat_local.format(new Date());
                try {
                    Date parseDate = simpleDataFormatUnsafe.simpleDateFormat_local.parse(dateString);
                    String dateString2 = simpleDataFormatUnsafe.simpleDateFormat_local.format(parseDate);
                    System.out.println(dateString.equals(dateString2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void test2() {
        for (int i = 0; i < 20; i++) {
            poolExecutor.execute(() -> {
                synchronized (objectLock) {
                    String dateString = simpleDateFormat.format(new Date());
                    try {
                        Date parseDate = simpleDateFormat.parse(dateString);
                        String dateString2 = simpleDateFormat.format(parseDate);
                        System.out.println(dateString.equals(dateString2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void test3() {
        for (int i = 0; i < 1; i++) {
            // threadLocal.set赋值不能放在这里，这是属于主线程的，子线程在自己的ThreadLocalMap拿不到。
            // threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            poolExecutor.execute(() -> {
                threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                SimpleDateFormat sdf = (SimpleDateFormat) threadLocal.get();
                String dateString = sdf.format(new Date());
                try {
                    Date parseDate = sdf.parse(dateString);
                    String dateString2 = sdf.format(parseDate);
                    System.out.println(dateString.equals(dateString2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void test31() {
        for (int i = 0; i < 20; i++) {
            poolExecutor.execute(() -> {
                SimpleDateFormat sdf = (SimpleDateFormat) threadLocalWithInit.get();
                String dateString = sdf.format(new Date());
                try {
                    Date parseDate = sdf.parse(dateString);
                    String dateString2 = sdf.format(parseDate);
                    System.out.println(dateString.equals(dateString2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void test32() {
        for (int i = 0; i < 20; i++) {
            poolExecutor.execute(() -> {
                System.out.println("thread-:" + threadLocal);
                SimpleDateFormat sdf = (SimpleDateFormat) threadLocalWithInitWithStatic.get();
                String dateString = sdf.format(new Date());
                try {
                    Date parseDate = sdf.parse(dateString);
                    String dateString2 = sdf.format(parseDate);
                    System.out.println(dateString.equals(dateString2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}