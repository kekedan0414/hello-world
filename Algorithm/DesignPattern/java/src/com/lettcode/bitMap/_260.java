package com.lettcode.bitMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class  _260 {
    public static void main(String[] args) {


    }

    //HashMap
    public int[] singleNumber(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]))
                map.remove(nums[i]);
            else
                map.put(nums[i],i);
        }

        int[] ret = new int[2];
        int i = 0;
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            ret[i++] = entry.getKey();
        }
        return ret;
    }

    //Set
    public int[] singleNumber1(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i]))
                set.remove(nums[i]);
            else
                set.add(nums[i]);
        }

        int[] ret = new int[2];
        int i = 0;
        for (int j : set) {
            ret[i++] = j;
        }
        return ret;
    }


    //bit

}
