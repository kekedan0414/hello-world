package com.lettcode;

/**
 * @author cocoegg
 * @date 2021/3/1 - 21:02
 * 171. Excel表列序号
 * 给定一个Excel表格中的列名称，返回其相应的列序号。
 *
 * 例如，
 *
 *     A -> 1
 *     B -> 2
 *     C -> 3
 *     ...
 *     Z -> 26
 *     AA -> 27
 *     AB -> 28
 *     ...
 *
 * 示例 1:
 *
 * 输入: "A"
 * 输出: 1
 *
 * 示例 2:
 *
 * 输入: "AB"
 * 输出: 28
 *
 * 示例 3:
 *
 * 输入: "ZY"
 * 输出: 701
 */
public class _170 {
    public static void main(String[] args) {


    }

    public int titleToNumber(String s) {
        int sum = 0;
        for(int i = 0; i < s.length(); i++) {
            int t = s.charAt(i) - 'A' + 1;
            sum = 26 * sum + t;
        }
        return sum;
    }
}
