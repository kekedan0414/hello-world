package com.lettcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author cocoegg
 * @date 2021/3/2 - 16:38
 */
public class _141 {
class ListNode {
    int val;
    ListNode next;
       ListNode(int x) {
     val = x;next = null;
    }
}

    public static void main(String[] args) {

    }

    public static boolean isCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode p = head;
        ListNode q = head.next;
        while (p != q) {
            if (q == null || q.next == null) return false;
            p = p.next;
            q = q.next.next;
        }
        return true;
    }

    public static boolean isCycle1(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }
}
