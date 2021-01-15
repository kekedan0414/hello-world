package com.cocoegg.principles.Generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericDemo1 {
    public static void main(String[] args) {
        //https://blog.csdn.net/s10461/article/details/53941091

        List list = new ArrayList();

        list.add("F");
        list.add("J");
        list.add("Y");
        //list.add(5);

        List list2 = new ArrayList(list);

        new ArrayList<String>(Arrays.asList("a","b","s"));

        for (int i = 0; i < list.size(); i++) {
            System.out.println((String)list.get(i));
        }
        List list1 = new ArrayList<String>();

        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list.get(i).toString());
        }

        String [] a  = new String[2];

        Object [] b = new Object[2];

        System.out.println(a.getClass());

        System.out.println(b.getClass());

        System.out.println(String.class);

        if (Object[].class .equals(Object.class)) {
            System.out.println("good!");
        }

        ArrayList<String>  aa = new ArrayList<>();

        aa.add("a");

    }

    public void fooo (G5<? extends Integer> g) {
        return;
    }
}


//上下边界：
class G5 <T extends Number> {

    public <T extends Number> T foo(T t) {
        return t;
    }

    public T foo1(T t) {
        return t;
    }

}