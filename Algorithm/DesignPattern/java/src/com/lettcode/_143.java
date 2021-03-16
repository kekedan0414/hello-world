package com.lettcode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class _143 {

    private static  class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ListNode p4 = new ListNode(4);
        ListNode p3 = new ListNode(3,p4);
        ListNode p2 = new ListNode(2,p3);
        ListNode p1 = new ListNode(1,p2);

        reorderList(p1);

    }

    public static void reorderList(ListNode head) {

        if (head == null || head.next == null || head.next.next == null) return;

        int n = 0;
        ListNode p1 = head;
        while(p1 != null) {
            n++;
            p1 = p1.next;
        }

        ListNode mid = head;
        ListNode pre = head;
        int m = n / 2;
        while(m > 0) {
            pre = mid;
            mid = mid.next;
            m--;
        }
        pre.next = null;

        ListNode rev = reverse(mid);

        // 1 2
        // 5 4 3
        ListNode p = head;
        ListNode q = rev;
        while(p != null && q != null) {
            p = p.next;
            head.next = rev;
            //p = p.next; 放在这个位置是错误的写法！！！！！！！！！！！！！！！
            head = p;

            q = q.next;
            rev.next = head == null ? rev.next : head;
            rev = q;
        }

    }

    static ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode rev = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return rev;
    }
}