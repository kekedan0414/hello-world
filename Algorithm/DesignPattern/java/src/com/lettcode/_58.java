package com.lettcode;

/**
 * @author cocoegg
 * @date 2021/3/1 - 13:34
 * 58. 最后一个单词的长度
 * 给你一个字符串 s，由若干单词组成，单词之间用空格隔开。返回字符串中最后一个单词的长度。如果不存在最后一个单词，请返回 0 。
 *
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "Hello World"
 * 输出：5
 *
 * 示例 2：
 *
 * 输入：s = " "
 * 输出：0
 */
public class _58 {
    public static void main(String[] args) {
        System.out.println(lengthOfLastWord(("a ")));
    }

    public static int lengthOfLastWord(String s) {
        int count = 0;

        s = s.trim();
        for (int i = s.length() -1; i >=0; i--) {

            if (s.charAt(i) != ' ')
                count++;
            else
                break;
        }
        return count;
    }

}
