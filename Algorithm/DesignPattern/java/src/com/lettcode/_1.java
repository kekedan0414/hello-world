package com.lettcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 你可以按任意顺序返回答案。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 */
public class _1 {
    public static void main(String[] args) {
        _1 ins = new  _1();
        int[] ret = ins.solution2(new int[]{1,2,3,4,5,6,7,8,9},2);
        for (int i: ret) {
            System.out.println(i);
        }
    }

    /**
     * j = i +1才正确
     * @param arr
     * @param target
     * @return
     */
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

    private int[] solution2(int[] arr, int target) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[] {i,j};
                }
            }

        }
        return new int[]{0};
    }

    private int[] solution3(int[] arr, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                return new int[] {map.get(target - arr[i]),i};
            }
            map.put(arr[i],i);
        }
        return new int[]{0};
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
