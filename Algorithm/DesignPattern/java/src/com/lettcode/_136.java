package com.lettcode;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * @author cocoegg
 * @date 2021/3/1 - 13:47
 * 136. 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 说明：
 *
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 *
 * 输入: [2,2,1]
 * 输出: 1
 *
 * 示例 2:
 *
 * 输入: [4,1,2,1,2]
 * 输出: 4
 */
public class _136 {
    public static void main(String[] args) {
        int[] nums = new int[] {4,2,1,4,2};
        System.out.println(singleNumber(nums));
    }

    public static int singleNumber(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum = sum ^ nums[i];
        }
        return sum;
    }

    /**
     * 方法2: Hashset 出现2次就删除，只保留出现1次的。
     * @param nums
     * @return
     */
    public static int singleNumber_Hashset(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i]))
                set.remove(nums[i]);
            else
                set.add(nums[i]);
        }
        return set.iterator().next();
    }

    /**
     *  利用set的不可重复性
     *  target = 2*sets - nums
     * @param nums
     * @return
     */
    public static int singleNumber_Hashset_subtract(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
            sum +=  nums[i];
        }

        int sumSet = 0;
        for (int i : set) sumSet += i;

        return 2*sumSet - sum;
    }



}
