package com.cocoegg.pattern.SingleTon;

/**
 * @author cocoegg
 * @date 2021/1/6 - 10:53
 */
public class LazySingle {
    public static void main(String[] args) {

    }

    private static volatile LazySingle  lazySingle = null;

    private LazySingle(){}

    //一次校验
    public static LazySingle getInstance1(){
        if (lazySingle == null) {
            lazySingle = new LazySingle();
        }
        return lazySingle;
    }

    //加锁
    public static LazySingle getInstance2(){

        if (lazySingle == null) {
            //两个线程A，B都进到这一步，A先拿到锁，B在这里等着A释放后，又会new一个新对象，所以要加双重校验
            synchronized (LazySingle.class) {

                lazySingle = new LazySingle();
            }
        }
        return lazySingle;
    }

    //双重校验
    public static LazySingle getInstance3(){

        if (lazySingle == null) {
            //两个线程A，B都进到这一步，A先拿到锁，B在这里等着A释放后，又会new一个新对象，所以要加双重校验
            synchronized (LazySingle.class) {
                //再次判断，确保只有new一个
                if (lazySingle == null) {
                    lazySingle = new LazySingle();
                }
            }
        }
        return lazySingle;
    }

    //加lazySingle 前加上volitale
    public static LazySingle getInstance4(){

        if (lazySingle == null) {
            //两个线程A，B都进到这一步，A先拿到锁，B在这里等着A释放后，又会new一个新对象，所以要加双重校验
            synchronized (LazySingle.class) {
                //再次判断，确保只有new一个
                if (lazySingle == null) {
                    //这里可能指令重排，未初始化完成，另一个线程就拿到了未初始化的对象
                    lazySingle = new LazySingle();
                }
            }
        }
        return lazySingle;
    }

}
