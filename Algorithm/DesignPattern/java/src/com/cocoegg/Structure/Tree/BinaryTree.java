package com.cocoegg.Structure.Tree;

/**
 * @author cocoegg
 * @date 2021/3/7 - 10:49
 */

/**
 * 树节点
 */
class TreeNode {
    private TreeNode mLeft;
    private TreeNode mRight;
    private int mValue;
    private boolean isDeleted;

    public TreeNode(int value) {
        this(null,null,value,false);
    }

    public TreeNode(TreeNode left, TreeNode right, int value, boolean isDeleted) {
        mLeft = left;
        mRight = right;
        mValue = value;
        this.isDeleted = isDeleted;
    }

    public TreeNode getLeft() {
        return mLeft;
    }

    public void setLeft(TreeNode left) {
        mLeft = left;
    }

    public TreeNode getRight() {
        return mRight;
    }

    public void setRight(TreeNode right) {
        mRight = right;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

public class BinaryTree {
    private static TreeNode mRoot;

    public static void insert(int value) {
        TreeNode newNode = new TreeNode(value);
        if (null == mRoot) {
            mRoot = newNode;
        } else {
            TreeNode currentNode = mRoot;
            TreeNode parentNode;

            while (null != currentNode) {
                parentNode = currentNode; // 记录当前节点为父节点
                if (value > currentNode.getValue()) { //在右边插入
                    currentNode = currentNode.getRight();
                    if (null == currentNode) {
                        parentNode.setRight(newNode);
                    }
                } else { //在左边插入
                    currentNode = currentNode.getLeft();
                    if (null == currentNode) {
                        parentNode.setLeft(newNode);
                    }
                }
            }
        }
    }


    //            10
    //       5          15
    //    3    7     13    17
    //   1 4  8 9  11 14  16 19

    /**
     * 相对于增查改，二叉树的删除是较为复杂的，必须保证删除后所有节点大于其左节点，小于其右节点。
     * 删除节点分为几种情况：
     *
     * 1.删除的节点为叶子节点：直接删除。
     *
     * 2.删除的节点只存在左子树或右子树：删除节点的父节点直接指向子树节点。
     *
     * 3.删除的节点同时存在左子树和右子树：将删除节点的左子树的最右节点或右子树的最左节点替换删除节点，
     * 同时删除替换节点，再将删除节点指向子树节点。
     * 原文链接：https://blog.csdn.net/qq_42320048/article/details/88171097
     * @param value
     * @return
     */
    public static boolean delete(int value) {
        TreeNode currentNode = find(value);
        if (currentNode == null) return false;

        if (currentNode.getValue() == value) {
            if( currentNode.getLeft() == null && currentNode.getRight() == null) {
               // parentNode.setLeft(null);
            }

        }
        return false;
    }

    public static TreeNode find(int key) {
        TreeNode currentNode = mRoot;
        if (null != currentNode) {
            while (key != currentNode.getValue()) {
                if (key > currentNode.getValue()) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode = currentNode.getLeft();
                }
                if (null == currentNode) {
                    return null;
                }
            }
            if (currentNode.isDeleted()) {
                return null;
            }
        }
        return currentNode;
    }

    /**
     * 先序遍历
     *
     * @param preNode
     */
    public static void preOrder(TreeNode preNode) {
        if (null != preNode) {
            System.out.println("midOrder"+ preNode.getValue() + "");
            preOrder(preNode.getLeft());
            preOrder(preNode.getRight());
        }
    }

    /**
     * 中序遍历
     *
     * @param midNode
     */
    public static void midOrder(TreeNode midNode) {
        if (null != midNode) {
            midOrder(midNode.getLeft());
            System.out.println("midOrder"+ midNode.getValue() + "");
            midOrder(midNode.getRight());
        }
    }

    /**
     * 后序遍历
     *
     * @param postNode
     */
    public static void postOrder(TreeNode postNode) {
        if (null != postNode) {
            postOrder(postNode.getLeft());
            postOrder(postNode.getRight());
            System.out.println("midOrder"+ postNode.getValue() + "");
        }
    }
}
