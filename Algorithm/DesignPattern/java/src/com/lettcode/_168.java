package com.lettcode;

/**
 * @author cocoegg
 * @date 2021/3/1 - 19:38
 */
public class _168 {
    public static void main(String[] args) {
        String s = "";
        s = s + 'a';

    }
    public static String convertToTitle(int n) {
        String s = "";
        while(n != 0) {
            char t = (char)('A' - 1 + n % 26);
            n /= 26;
            s = t + s;

        }
        return s;
    }
}
