package com.github.bstation.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author tangsong
 * @date 2021/5/22 17:19
 */
public class MaxTreeWidth {

    /**
     *  计算二叉树的最大宽度，获取到节点最多的那一层的节点数
     *
     *   广度优先遍历
     *
     *   有两种方式可以获取到最大宽度，广度优先遍历时，需要如何确定一层已经遍历完全，所以有两种方式可以用来记录：
     *    1. 使用Map记录每个节点的层数，在遍历的时候记录每个节点的层数，当层数和当前的层数不一致，则表示当前层遍历完成
     *    2. 使用变量记录每一层的最右子节点，那么每次在遍历的时候都判断是否到达最右子节点，如果到达那么就表示需要下一层
     *    3. 使用队列收集节点时的长度来记录每一层的长度，队列每收集一层，那么队列的长度就是这一层的宽度
     */

    @Test
    public void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;

        System.out.println(maxWidthWithMap(node1));

        System.out.println(maxWidthWithNoMap(node1));

        System.out.println(maxWidthWithSize(node1));
    }


    /**
     *  使用Map获取最大宽度
     */
    public int maxWidthWithMap(Node head) {
        if(head == null) {
            return 0;
        }
        // key->Node value -> 当前节点的层数
        Map<Node, Integer> map = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        map.put(head, 1);

        int max = 0; // 记录最大宽度
        int curLevel = 1; // 当前遍历的层
        int curNodeLevel = 0; // 当前节点所在的层
        int nodeNum = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            curNodeLevel = map.get(node);
            if(node.left != null) {
                // 记录下一个节点的层数
                map.put(node.left, curNodeLevel + 1);
                queue.offer(node.left);
            }
            if(node.right != null) {
                map.put(node.right, curNodeLevel + 1);
                queue.offer(node.right);
            }
            // 判断当前遍历的层和节点的层是否一致，是否需要开始重新记录下一层
            if(curLevel != curNodeLevel) {
                curLevel++;
                max = Math.max(max, nodeNum);
                nodeNum = 1;
            } else {
                nodeNum++;
            }
        }
        // 只有到下一行才会结算上一行，所以少一次结算最后一行的操作
        max = Math.max(max, nodeNum);
        return max;
    }


    /**
     * 不适用Map计算最大宽度
     *  使用变量记录每层节点的最右节点
     */
    public int maxWidthWithNoMap(Node head) {
        if(head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        int max = 0; // 最大宽度
        Node nextEnd = head;  // 下一层的最右节点
        Node curRightEnd = head; // 当前层的最右节点
        int nodeNum = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if(node.left != null) {
                // 左节点和右节点都需要记录，因为可能没有右节点或者左节点
                nextEnd = node.left;
                queue.offer(node.left);
            }
            if(node.right != null) {
                nextEnd = node.right;
                queue.offer(node.right);
            }
            nodeNum++;
            // 判断当前是否到最后一个节点，如果到了，那么就计算当前层的最大宽度
            if(node == curRightEnd) {
                max = Math.max(max, nodeNum);
                nodeNum = 0;
                // 即将开始遍历下一层，那么将下一层的最右节点赋值给当前层节点
                curRightEnd = nextEnd;
            }
        }
        return max;
    }


    /**
     * 使用队列的收集的节点的长度记录每一层的宽度
     *
     */
    public int maxWidthWithSize(Node head) {
        if(head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        int max = 0; // 最大宽度
        int nodeNum = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 队列的长度就是节点最多的个数
            max = Math.max(max, size);

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if(node.left != null) {
                    // 左节点和右节点都需要记录，因为可能没有右节点或者左节点
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }

                // 无需计数器，queue的长度就是每一层的最多节点的个数
//                nodeNum++;
//                if(i == size - 1) {
//                    max = Math.max(max, nodeNum);
//                    nodeNum = 0;
//                }
            }
        }
        return max;
    }

}
