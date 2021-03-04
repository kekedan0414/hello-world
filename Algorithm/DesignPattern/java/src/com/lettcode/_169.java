package com.lettcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cocoegg
 * @date 2021/3/1 - 19:51
 * 169. 多数元素
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 *
 *
 * 示例 1：
 *
 * 输入：[3,2,3]
 * 输出：3
 *
 * 示例 2：
 *
 * 输入：[2,2,1,1,1,2,2]
 * 输出：2
 *
 */
public class _169 {
    public static void main(String[] args) {

    }

    public int majorityElement(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();

        for(int i = 0;i < nums.length; i++) {
            if(map.containsKey(nums[i])) {
                map.put(nums[i],map.get(nums[i]) + 1);
                if (map.get(nums[i]) > nums.length / 2)
                    return nums[i];
            } else
                map.put(nums[i],1);
        }
        return 0;
    }

    public int majorityElement_sort(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

}
