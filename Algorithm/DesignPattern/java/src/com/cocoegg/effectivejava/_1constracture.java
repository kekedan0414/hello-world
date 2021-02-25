package com.cocoegg.effectivejava;

import java.io.*;
import java.util.*;


/**
 * @author cocoegg
 * @date 2021/1/13 - 13:13
 */
public class _1constracture {
    public static void main(String[] args) throws Exception {

        // 2.
        List list = Collections.synchronizedList(new ArrayList());
        Integer.valueOf("1");
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("D:\\tmp\\Object.obj");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(NewObject.getInstance());
            //throw new Exception();
        } finally {
            if (fileOutputStream != null) {
                objectOutputStream.close();
            }
        }
        System.out.println("hello,world");
        try (
            InputStream inputStream = new FileInputStream("D:\\tmp\\Object.obj");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            InputStream inputStream1 = new FileInputStream("D:\\tmp\\Object.obj");
            //throw new Exception();
            ObjectInputStream objectInputStream1 = new ObjectInputStream(inputStream1)
        ) {

            NewObject object = (NewObject) objectInputStream.readObject();
            NewObject object1 = (NewObject) objectInputStream1.readObject();
            System.out.println(object.hashCode());
            System.out.println(object1.hashCode());
            objectInputStream.close();
            objectInputStream1.close();
            Object o = new Object();
            //throw new Exception();
        }

        String s ="111";
        String s1 ="111";
        System.out.println(s == s1);
        System.out.println(s.equals(s1));

        Integer b = new Integer(111);
        Integer b1 = new Integer(111);
        System.out.println(b == b1);
        System.out.println(b.equals(b1));


        Integer bb1 = Integer.valueOf(111);
        Integer bb2 = Integer.valueOf(111);
        System.out.println(bb1 == bb2);
        System.out.println(bb1.equals(bb2));


        System.out.println("-----------");
        ExeptionTest exeptionTest = new ExeptionTest();
        try {
            System.out.println("test1 = " + exeptionTest.test1());
        } catch (Exception e) {
            System.out.println("catch it!");
        }

        System.out.println("---------------");

        // 4. GC
        //        byte[] bytes = new byte[2 * 1024 * 1024];
        //        byte[] bytes2 = new byte[2 * 1024 * 1024];
        //        byte[] bytes3 = new byte[2 * 1024 * 1024];
        //        byte[] bytes4 = new byte[2 * 1024 * 1024];
        //        byte[] bytes5 = new byte[2 * 1024 * 1024];

        //

        StaticClassTest staticInnerClassTest = new StaticClassTest();
        staticInnerClassTest.test1();
        staticInnerClassTest.nsic.test();

        List list1 = new ArrayList();
        list1.add("1");
        list1.add("2");
        list1.add("3");



        Iterator iterator = list1.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Map<String,String> map = new HashMap<>();
        map.put("1","A");
        map.put("2","B");
        map.put("3","C");



        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry entry1 : entries) {

        }

        IA a = new A();





    }
}

class StaticClassTest {

    private int age;
    public NonStaticInnerClass nsic = new NonStaticInnerClass();

    public void test1() {
        NonStaticInnerClass a = new NonStaticInnerClass();
        NonStaticInnerClass a1 = new NonStaticInnerClass();
        NonStaticInnerClass a2 = new NonStaticInnerClass();
        System.out.println(this);
        StaticInnerClass b = new StaticInnerClass();
        StaticInnerClass b1 = new StaticInnerClass();
        StaticInnerClass b2 = new StaticInnerClass();
    }

    public class NonStaticInnerClass{

        public void test(){
            System.out.println(age);
            //System.out.println();
            System.out.println(this);
            StaticClassTest.this.nsic = null;//reference this
        }
    }

    private static class StaticInnerClass{
        public void test(){
            //System.out.println(age);
            //System.out.println();
            System.out.println(this);
            //StaticClassTest.this.nsic = null;//reference this
        }
    }
}

class AA implements IA.IAA {

    @Override
    public void aa() {

    }
}

class A implements IB {


    @Override
    public void a() {

    }

    @Override
    public void t2() {

    }

    static class AA implements IAA {

        @Override
        public void aa() {

        }
    }
}

interface IA {
    void a();
    interface IAA {
        void aa();
    }
}

interface IB extends IA {
    void t2();
}


class ExeptionTest {
    String test1() {
        String ret = "#0";
        try {
            int i = 1 / 0;
            System.out.println("#1");
        } catch (ArithmeticException e) {
            System.out.println("#2");
            e.printStackTrace();
        } finally {
            System.out.println("#3");
            //int i = 1 / 0 ;
            ret = "#4";
            System.out.println("#5");
        }

        return ret;
    }
}


//由于父类构造函数私有，所以不能被继承。
//class UtilA extends Util {
//
//}

class Util implements Cloneable {
    private Util(){}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class NewObject implements Serializable {
    static private NewObject INSTANCE = new NewObject();

    private NewObject(){}

    public static NewObject getInstance() {
        return INSTANCE;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}