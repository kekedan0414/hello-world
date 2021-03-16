package com.lettcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class _131 {


  public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }



    List<List<Integer>> ret = new ArrayList<>();

    Deque<Integer> path = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {


        helper(root, targetSum);

        return ret;
    }

    private void helper(TreeNode root,int targetSum) {
        if (root == null) return;

        path.offer(root.val);
        if (root.left == null && root.right == null && targetSum == root.val) {
            List<Integer> list = new LinkedList<>();
            path.add(root.val);
            ret.add(new LinkedList<>(path));
            return;
        }
        helper(root.right,targetSum - root.val);
        helper(root.left,targetSum - root.val);
        path.poll();
    }

}