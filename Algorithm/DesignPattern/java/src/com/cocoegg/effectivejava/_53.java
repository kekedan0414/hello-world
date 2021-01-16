package com.cocoegg.effectivejava;

public class _53 {
    public static void main(String[] args) {

        System.out.println(sum(1,2,3));
        System.out.println(sum());


        System.out.println(min(1,2,5));
        System.out.println(min1(0));
    }

    static int sum(int... arg) {
        int sum = 0;
        if (arg.length > 0) {
            for (int i = 0; i < arg.length; i++) {
                sum += arg[i];
            }
        }
        return sum;
    }

    static int min(int... arg) {
        if (arg.length == 0) {
            throw new IllegalArgumentException("Too few arguments!");
        }
        int min = arg[0];
        for (int i = 0; i < arg.length; i++) {
            if (min > arg[i]) {
                min = arg[i];
            }
        }
        return min;
    }

    static int min1(int firstArg, int... arg) {

        int min = firstArg;
        if (arg.length > 0) {
            for (int i = 0; i < arg.length; i++) {
                if (min > arg[i]) {
                    min = arg[i];
                }
            }
        }
        return min;
    }
}
