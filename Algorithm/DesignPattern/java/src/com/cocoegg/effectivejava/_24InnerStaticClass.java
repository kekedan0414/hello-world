package com.cocoegg.effectivejava;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/14 - 17:55
 */
public class _24InnerStaticClass {
    public static void main(String[] args) {
        List al = new ArrayList();
        int counter = 0;
        while (true) {
            al.add(new EnclosingClass(100000).getEnclosedClassObject());
            System.out.println(counter++);
        }
    }
}

class EnclosingClass {
    private int[] data;

    public EnclosingClass(int size) {
        data = new int[size];
    }

    public class EnclosedClass {}

    EnclosedClass getEnclosedClassObject() {
        return new EnclosedClass();
    }
}