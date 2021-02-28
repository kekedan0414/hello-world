package com.lettcode;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 *
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *
 */
public class _14 {
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix1_0(new String[]{"123", "", "212"}));
    }

    //投机取巧
    public static String longestCommonPrefix(String[] strs) {

        String prefix = strs[0];
        for (String str : strs) {
            if (prefix == "") return "";
            while ( !str.startsWith(prefix)) {
                prefix = prefix.substring(0,prefix.length() - 1 );
            }
        }
        return prefix;
    }

    //纵向
    public static String longestCommonPrefix1(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        int length = strs[0].length();
        int count = strs.length;

        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < count; j++) {
                if (strs[j].length() < i + 1 ||  c != strs[j].charAt(i)) {
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }

    public static String longestCommonPrefix1_0(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].length() < i + 1 || strs[0].charAt(i) != strs[j].charAt(i)) {
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }
}
