package com.cocoegg.algorithm.Recursion;

import java.util.Stack;


//https://blog.csdn.net/limin0983/article/details/70280484
public class ReverseStack {
    public static void main(String[] args) {
        Stack<Integer> A = new Stack<>();
        for (int i = 0; i < 20; i++) {
            A.push(i);
        }
        for(Integer i : A)
            System.out.print(i + " ");
        System.out.println();

        ReverseStackWithoutHelpStack.reverse(A);

        for(Integer i : A)
            System.out.print(i + " ");
    }
}


class ReverseStackWithHelpStack {
    public static <E> void reverse(Stack<E> A) {
        Stack<E> B = new Stack<>();
        helper(A,B,A.size());
    }

    private static <E> void helper(Stack<E> A, Stack<E> B, int n) {
        if(n == 1) return;
        else {
            E tmp = A.pop();
            popAll(A,B,n-1);
            A.push(tmp);
            popAll(B,A,n-1);
            helper(A,B,n-1);
        }
    }

    private static <E> void popAll(Stack<E> a, Stack<E> b, int n) {
        while (n>0) {
            b.push(a.pop());
            n--;
        }
    }
}

class ReverseStackWithoutHelpStack {
    public static <E> void reverse(Stack<E> A) {
        if (A.isEmpty()) return;

        //拿到栈底元素
        E e = getBottom(A);

        //反转栈
        reverse(A);

        //将栈底元素变为栈顶
        A.push(e);

    }

    public static <E> E getBottom(Stack<E> A) {

        //取出栈顶元素
        E num = A.pop();

        //中止条件
        if(A.isEmpty()) return num;

        //得到栈底元素
        E ret = getBottom(A);

        //回溯，将取出的元素重新入栈
        A.push(num);

        //返回栈底元素
        return ret;

    }

}
