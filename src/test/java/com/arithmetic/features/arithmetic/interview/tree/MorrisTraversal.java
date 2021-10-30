package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

import java.util.TreeMap;

/**
 * morris遍历：  遍历树的时间复杂度为O(N), 空间复杂度为O(1)
 *
 * 在一般对树的遍历上，除了先序、中序、后序等遍历，还可以使用morris遍历，并且morris更快
 *
 *
 * morris的前序、中序、后序遍历中，其实对于树遍历方式都是固定的，只不过是打印当前节点的值不同，导致出现不同的遍历顺序，因为有的节点会经过2次，所以打印值的时机不同，就会有不同的遍历
 * 对于树的递归、非递归遍历也是相同的，遍历的方式相同，打印值的时机不同
 *
 *
 * morris遍历的核心思想（步骤）：
 *   将当前节点记为cur，
 *   1）如果当前节点没有左子节点，那么直接将cur向右移动
 *   2）如果当前节点有左子节点，找到cur的左子树的最右子节点，记为mostRight
 *      1) 如果mostRight的右指针指向空，那么将mostRight的右指针指向cur，并将cur向左移动
 *      2）如果mostRight的右指针指向cur，那么将mostRight的右指针指向空，并将cur向右移动
 *
 *
 *
 * @author tangsong
 * @date 2021/2/7 19:38
 */
public class MorrisTraversal {



    @Test
    public void testMorrisIn() {
        Node head = new Node(1);
        Node head2 = new Node(2);
        Node head3 = new Node(3);
        Node head4 = new Node(4);
        Node head5 = new Node(5);
        Node head6 = new Node(6);
        Node head7 = new Node(7);

        head.left = head2;
        head.right = head3;
        head2.left = head4;
        head2.right = head5;
        head3.left = head6;
        head3.right = head7;

        // morris遍历
        morrisIn(head);

        // morris前序遍历
        morrisPre(head);

        System.out.println();

        // morris后序遍历
        morrisPos(head);
    }

    /**
     * morris遍历
     *
     * 中序遍历
     */
    public void morrisIn(Node head) {
        if(head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 判断当前节点的左子树是否为null
            if(mostRight != null) {
                // 如果不为空，则找到左子树的最右子节点
                // mostRight.right != null 表示向右边遍历，mostRight.right != cur表示不等于当前节点，因为如果最右节点的右指针指向空，就会将右指针指向cur节点,所以避免等于cur节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null) {
                    // 如果当前节点的左子树的最右节点的右指针为空，那么就指向cur节点
                    mostRight.right = cur;
                    // 将cur节点向左移动
                    cur = cur.left;
                    // 继续下一轮循环，不将cur节点向右移动
                    continue;
                } else {
                    // 如果最右节点的右指针不为空，那么一定是指向cur，所以需要将右指针指向空，并将当前节点向右移动，下面的cur=cur.right就是向右移动
                    mostRight.right = null;
                }

            }
            System.out.print(cur.value + "\t");
            // 如果为空则，直接移动到当前节点的右节点
            cur = cur.right;
        }
        System.out.println();
    }


    /**
     * morris 前序遍历
     *
     */
    public void morrisPre(Node head) {
        if(head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if(mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null) {
                    mostRight.right = cur;
                    // 对于会经过两次的节点，在第一次经过该节点的时候打印该值
                    System.out.print(cur.value + "\t");
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                // 如果一个节点的左子树为空，那么这个节点只会经过一次，所以直接打印该节点
                System.out.print(cur.value + "\t");
            }
            cur = cur.right;
        }
    }

    /**
     * morris后序遍历
     *
     */
    public void morrisPos(Node head) {
        if(head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while(cur != null) {
            mostRight = cur.left;
            if(mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    // 在这里，cur当前节点可以获取左子节点的右边界，并且每个节点
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        // cur==null，则表示遍历到整个树的最右边的边界，并且cur指向最右边的空节点,那么就遍历顶点的最右边界即可
        printEdge(head);
        System.out.println();
    }

    // 打印边界，这个的head参数表示该边界的定点
    private void printEdge(Node head) {
        // 将整个边的节点反转，并且返回最后一个节点（即最右子节点）
        Node tail = reverseEdge(head);

        while(tail != null) {
            System.out.print(tail.value + "\t");
            // 因为已经将该边界反转了，所以right指针指向的是上一个节点
            tail = tail.right;
        }
    }

    private Node reverseEdge(Node head) {
         Node pre = null;
         Node next = null;
         while (head != null) {
             next = head.right;
             head.right = pre;
             pre = head;
             head = next;
         }
         return pre;
    }


    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

}
