package com.lettcode;

/**
 * @author cocoegg
 * @date 2021/2/27 - 10:57
 * 83. 删除排序链表中的重复元素
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 *
 * 示例 1:
 *
 * 输入: 1->1->2
 * 输出: 1->2
 *
 * 示例 2:
 *
 * 输入: 1->1->2->3->3
 * 输出: 1->2->3
 */
public class _83 {

    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}

      ListNode(int val) {
          this.val = val;
      }

      ListNode(int val, ListNode next) {
          this.val = val;
          this.next = next;
      }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1,new ListNode(1,new ListNode(2,new ListNode(3))));
        deleteDuplicates(head);
    }

    public static ListNode deleteDuplicates(ListNode head) {

        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }
}
