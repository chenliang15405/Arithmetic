package com.github.左神算法.morris;

import com.github.bstation.tree.Node;
import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/13 1:18
 */
public class MorrisTraversal {

    /**
     * Morris遍历
     *  时间复杂度：O(N)
     *  空间复杂度：O(1)
     *
     * 假设来到了当前节点cur，开始时cur等于头结点
     * 1. 如果cur没有左节点，则cur向右移动
     * 2. 如果cur有左子节点，则找到左子树最右的节点mostRight
     *    a. 如果mostRight的右指针为空，表示第一次访问该元素，让mostRight的右指针指向cur，然后cur向左移动
     *    b. 如果mostRight的右指针指向cur，表示是第二次访问该元素，则让其指向null，并将cur向右移动
     * 3. cur为空时遍历停止
     *
     *
     */
    @Test
    public void testMorrisIn() {
        String s = "abc";

        System.out.println(s);

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
        morris(head);

    }

    /**
     * morris中序遍历
     *
     *  前序遍历、后序遍历无非是调整打印节点的位置
     *
     */
    public void morris(Node root) {
        if(root == null) return;
        Node cur = root;
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
                } else {
                    mostRight.right = null;
                    // 第二次到达当前节点的时候打印，这个时候是从左子节点回来的时候
                    System.out.print(cur.value + "\t");
                    cur = cur.right;
                }
            } else {
                // 如果节点的左子节点为空，则第一次到达就打印
                System.out.print(cur.value + "\t");
                cur = cur.right;
            }
        }
    }


}
