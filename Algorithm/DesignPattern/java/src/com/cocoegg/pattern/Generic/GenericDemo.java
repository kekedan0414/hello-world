package com.cocoegg.pattern.Generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/9 - 16:16
 */
public class GenericDemo {
    public static void main(String[] args) {

        //一个栗子：https://blog.csdn.net/s10461/article/details/53941091
        /**
        List arrayList = new ArrayList();
        arrayList.add("a");
        arrayList.add(1);
        for (int i = 0; i < arrayList.size(); i++) {
            String item = (String)arrayList.get(i);
            System.out.println("item = " + item);
        }
         **/

        List<String> stringList = new ArrayList<String>();
        List<Integer> integerList = new ArrayList<Integer>();

        if (stringList.getClass() == integerList.getClass()) {
            System.out.println("gouzi" + stringList.getClass());
        }


        /**
         * 通过上面的例子可以证明，在编译之后程序会采取去泛型化的措施。
         * 也就是说Java中的泛型，只在编译阶段有效。
         * 在编译过程中，正确检验泛型结果后，会将泛型的相关信息擦出，
         * 并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。
         * 也就是说，泛型信息不会进入到运行时阶段。
         */

        //指定泛型
        Generic1<String, Integer> wifes = new Generic1<>("wifes", 5);
        wifes.setS(10);
        System.out.println(wifes.getS());
        System.out.println(wifes.getT());
        System.out.println(wifes);

        //不指定泛型
        Generic1 wifes1 = new Generic1(10,"wfs");
        System.out.println(wifes1);
        wifes1.setT(12);
        System.out.println(wifes1.getT());




    }
}

class Generic1<T,S> {
    private T t;
    private S s;

    public Generic1(T t, S s) {
        this.t = t;
        this.s = s;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public S getS() {
        return s;
    }

    public void setS(S s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "Generic1{" +
                "t=" + t +
                ", s=" + s +
                '}';
    }
}


