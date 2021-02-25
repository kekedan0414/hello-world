package com.lettcode;

/**
 * 给你一个 32 位的有符号整数 x ，返回 x 中每位上的数字反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [−2^31,  2^31 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *  
 *
 * 示例 1：
 *
 * 输入：x = 123
 * 输出：321
 * 示例 2：
 *
 * 输入：x = -123
 * 输出：-321
 * 示例 3：
 *
 * 输入：x = 120
 * 输出：21
 * 示例 4：
 *
 * 输入：x = 0
 * 输出：0
 */
public class _7 {
    public static void main(String[] args) {
        //2,147,483,648 2^31
        //-2,147,483,648 -2^31
        System.out.println(reverse1(2147483647));
        System.out.println(reverse1(-2147483647));
        System.out.println(reverse1(321));
        System.out.println(reverse1(-321));
    }

    public static int reverse(int x) {
        long rev = 0;
        while (x != 0) {
            rev = 10 * rev + x % 10;
            x /= 10;
        }
        return (int )rev == rev ? (int)rev : 0;
    }

    public static int reverse1(int x) {
        int rev = 0;
        while (x != 0) {
            int tmp = 10 * rev + x % 10;
            if ((tmp - x%10) / 10 != rev) return 0;
            rev = tmp;
            x /= 10;
        }
        return rev;
    }
}

