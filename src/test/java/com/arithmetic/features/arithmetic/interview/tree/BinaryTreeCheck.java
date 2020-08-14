package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

import java.util.*;

/**
 *  判断二叉树是平衡二叉树
 *
 *  判断二叉树是排序二叉树
 *
 *  判断二叉树是完全二叉树
 *
 *  TODO 判断二叉树是满二叉树
 *
 *
 * @author alan.chen
 * @date 2020/6/15 3:01 PM
 */
public class BinaryTreeCheck {



    @Test
    public void isBST() {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);


        Node head1 = new Node(5);
        head1.left = new Node(1);
        head1.right = new Node(4);
        head1.right.left = new Node(3);
        head1.right.right = new Node(6);

        //head.left.right.left = new Node(11);

        boolean bst = isBST(head);
        System.out.println("是否是二叉排序树：" + bst);

        boolean bst2 = isBST2(head1);
        System.out.println("是否是二叉排序树：" + bst2);
    }


    @Test
    public void isCBT() {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        //head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        //head.right.right.left = new Node(9);
        //head.right.right.right = new Node(11);

        boolean cbt = isCBT(head);
        System.out.println("是否是完全二叉树：" + cbt);
    }


    @Test
    public void isAVL() {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        head.right.right.right = new Node(8);
        head.right.right.right.right = new Node(9);

        getHeight(head);

        System.out.println("是否是AVL树：" + isBanlanced);
        List<Integer> list = new ArrayList<>();

    }

    @Test
    public void treeHeight() {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        head.right.right.right = new Node(8);
        head.right.right.right.right = new Node(9);

        int hegiht = treeHeightWithCurson(head);
        System.out.println("递归树的高度： " + hegiht);

        int hegiht2 = treeHeightWithBFS(head);
        System.out.println("递归树的高度： " + hegiht2);

    }


    /**
     * BFS计算树的高度
     * @param head
     * @return
     */
    private int treeHeightWithBFS(Node head) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        int height = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            height++;
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return height;
    }

    /**
     * 递归获取树的高度
     * @param head
     * @return
     */
    private int treeHeightWithCurson(Node head) {
        if(head == null) {
            return 0;
        }
        int leftH = treeHeightWithCurson(head.left);
        int rightH = treeHeightWithCurson(head.right);

        return Math.max(leftH, rightH) + 1;
    }


    private boolean isBanlanced = true;
    /**
     *
     * @param head
     * @return
     */
    private int getHeight(Node head) {
        if(head == null) {
            isBanlanced = false;
            return 0;
        }

        // 递归获取左子树的高度
        int leftH = getHeight(head.left);
        // 递归获取右子树的高度
        int rightH = getHeight(head.right);

        // 判断是否是AVL
        if(Math.abs(leftH - rightH) > 1) {
            isBanlanced = false;
        } else {
            isBanlanced = true;
        }
        // 返回子树的最大深度
        //return Math.max(leftH, rightH) + 1;
        return (leftH > rightH ? leftH : rightH) + 1;
    }





    /**
     * 是否是完全二叉树
     *
     * 使用队列逐层判断
     *
     * @param head
     * @return
     */
    public boolean isCBT(Node head) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        boolean leaf = false;

        while (!queue.isEmpty()) {
            head = queue.poll();
            if(head != null) {
                Node left = head.left;
                Node right = head.right;

                // 判断是否没有左节点但有右节点
                if(left == null && right != null) {
                    return false;
                }
                // 判断是否到叶子节点
                if(leaf && (left != null || right != null)) {
                    return false;
                }
                // 先添加左节点，再添加右子树，因为判断左子树如果已经到叶子节点，则右子树的同一层不能再有子节点
                if(head.left != null) {
                    queue.offer(head.left);
                }
                if(head.right != null) {
                    queue.offer(head.right);
                }
                // 达到叶子节点
                if(head.left == null && head.right == null) {
                    leaf = true;
                }
            }
        }
        return true;
    }



    private Node pre = null;
    /**
     * 判断是否二叉排序树
     *
     * 不使用额外数组，使用变量表示
     *
     * @return
     */
    public boolean isBST2(Node head) {
        if(head != null) {

            if(!isBST2(head.left)) {
                return false;
            }
            if(pre != null && pre.value >= head.value) {
                return false;
            }
            pre = head;
            if(!isBST2(head.right)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 如果是二叉排序树，则中序遍历是递增的顺序，所以使用二叉排序树
     * 的中序遍历得到的数据，判断是否是升序的数据即可判断是否是BST
     *
     * @param head
     * @return
     */
    public boolean isBST(Node head) {
        List<Integer> list = new ArrayList<>();
        infixOrderInRecur(head, list);

        int j = 1;
        for (int i = 0; i < list.size() - 1; i++) {
            if(list.get(i) > list.get(j)) {
                return false;
            }
            j++;
        }
        return true;
    }


    private void infixOrderInRecur(Node head, List<Integer> list) {
        if(head == null) {
            return;
        }
        infixOrderInRecur(head.left, list);
        list.add(head.value);
        infixOrderInRecur(head.right, list);
    }


    @Test
    public void testSum() {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);


        int sum = getSum(head, 2, 6, 0);

        System.out.println(sum);
    }

    private int getSum(Node root, int L, int R, int sum) {
        if(root == null) {
            return sum;
        }
        if(root.value >= L && root.value <= R) {
            sum += root.value;
        }

        return getSum(root.left, L, R, sum) + getSum(root.right, L, R, sum);
    }


    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
}
