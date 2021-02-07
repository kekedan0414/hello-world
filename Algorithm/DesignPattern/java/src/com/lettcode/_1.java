package com.lettcode;

import java.util.HashMap;
import java.util.Map;

// 两数之和
public class _1 {
    public static void main(String[] args) {
        _1 ins = new  _1();
        int[] ret = ins.solution1(new int[]{1,2,3,4,5,6,7,8,9},17);
        for (int i: ret) {
            System.out.println(i);
        }
    }

    private int[] solution(int[] arr, int target) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i; j < arr.length; j++) {
                if (target == arr[i] + arr[j]) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }

    private int[] solution1(int[] arr, int target) {
        Map<Integer,Integer> map = new HashMap();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                return new int[] {map.get(target - arr[i]),i};
            }
            map.put(arr[i],i);
        }
        return new int[0];
    }
}
