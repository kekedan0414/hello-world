package com.cocoegg.effectivejava;

import java.util.ArrayList;
import java.util.List;

public class _54 {
    private static final List emptyList = new ArrayList<>(0);

    public static void main(String[] args) {

        List lit = getIntegers();
        List lit1 = getIntegers();

        List lit2 = getIntegers1();
        List lit22 = getIntegers1();
        System.out.println();
    }



    public static List getIntegers() {
        return new ArrayList<>(0);
    }

    public static List getIntegers1() {
        return emptyList;
    }
}
