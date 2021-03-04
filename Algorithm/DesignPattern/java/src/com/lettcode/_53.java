package com.lettcode;

/**
 * @author cocoegg
 * @date 2021/3/1 - 9:12
 * 53. 最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * 示例 2：
 *
 * 输入：nums = [1]
 * 输出：1
 *
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：0
 *
 * 示例 4：
 *
 * 输入：nums = [-1]
 * 输出：-1
 *
 * 示例 5：
 *
 * 输入：nums = [-100000]
 * 输出：-100000
 */
public class _53 {

    public static void main(String[] args) {
        int[] nums = new int[] {-1,1,5,-5,4};
        System.out.println(maxSubArray(nums));
    }

    /**
     * 动态规划
     * max = max(max(n-1) + n ,n)
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {

        int pre = 0, maxAns = nums[0];
        for (int i = 0; i < nums.length; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

}
