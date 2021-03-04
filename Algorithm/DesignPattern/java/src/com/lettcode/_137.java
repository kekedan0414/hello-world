package com.lettcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author cocoegg
 * @date 2021/3/1 - 15:45
 * 137. 只出现一次的数字 II
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。
 *
 * 说明：
 *
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 *
 * 输入: [2,2,3,2]
 * 输出: 3
 *
 * 示例 2:
 *
 * 输入: [0,1,0,1,0,1,99]
 * 输出: 99
 */
public class _137 {
    public static void main(String[] args) {


    }

    public static int singleNumber(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if (map.get(nums[i]) == 2)
                    map.remove(nums[i]);
                else
                    map.put(nums[i], map.get(nums[i]) + 1);
            }
            else
                map.put(nums[i],1);
        }
        return map.entrySet().iterator().next().getKey();
    }

    /**
     *  利用set的不可重复性
     *  target = (3*sets - nums)/2
     *  注意*3后越界问题
     * @param nums
     * @return
     */
    public static int singleNumber_Hashset_subtract(int[] nums) {
        Set<Integer> set = new HashSet<>();
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
            sum +=  nums[i];
        }

        long sumSet = 0;
        for (long i : set) sumSet += i;

        return (int)((3*sumSet - sum) / 2);
    }
}
