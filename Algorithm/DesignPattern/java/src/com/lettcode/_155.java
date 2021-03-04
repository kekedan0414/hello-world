package com.lettcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author cocoegg
 * @date 2021/3/1 - 16:41
 *
 */
public class _155 {
    /** initialize your data structure here. */
    Deque<Integer> minStack = new LinkedList<>();
    Deque<Integer> stack = new LinkedList<>();


    public _155() {
        minStack.push(Integer.MAX_VALUE);
    }


    public void push(int x) {
        stack.push(x);
        minStack.push(Math.min(x,minStack.peek()));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
