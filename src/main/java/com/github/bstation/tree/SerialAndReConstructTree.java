package com.github.bstation.tree;


import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tangsong
 * @date 2021/5/22 17:33
 */
public class SerialAndReConstructTree {

    /**
     * 序列化和发序列化二叉树
     *  两种方式： 前序序列化、前序反序列化
     *           按层序列化、按层反序列化
     *
     */



    /**
     * 前序序列化
     *
     */
    public Queue<String> preSerial(Node head) {
        Queue<String> queue = new LinkedList<>();
        return process(head, queue);
    }

    /**
     * 前序反序列化
     */
    public Node buildByPreSerial(Queue<String> queue) {
        if(queue == null || queue.size() <= 0) {
            return null;
        }
        return preb(queue);
    }

    /**
     * 按层序列化
     *
     */
    public Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        if(head == null) {
            ans.add(null);
            return ans;
        }
        // 广度优先遍历时将当前的节点加入到队列中. 并且如果子节点如果是null，也需要加入到队列中，反序列化时需要占位
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        ans.offer(String.valueOf(head.value));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if(node.left != null) {
                ans.offer(String.valueOf(node.left.value));
                queue.offer(node.left);
            } else {
                ans.offer(null);
            }
            if(node.right != null) {
                ans.offer(String.valueOf(node.right.value));
                queue.offer(node.right);
            } else {
                ans.offer(null);
            }
        }
        return ans;
    }


    /**
     * 按层级反序列化
     */
    public Node buildLevelSerial(Queue<String> queue) {
        if(queue == null || queue.size() <= 0) {
            return null;
        }
        return levelPreb(queue);
    }

    private Node levelPreb(Queue<String> ans) {

        // 专门用来遍历，用于将节点构建好了之后，装入到队列中，可以在下次循环中取出来设置左子节点和右子节点
        Queue<Node> queue = new LinkedList<>();

        Node head = generatedNode(ans.poll());
        if(head != null) {
            queue.offer(head);
        }
        Node node = null;
        // 让装有节点的队列进行遍历
        while (!queue.isEmpty()) {
            // 按照层进行构建，不能递归，只能遍历每一层的节点并构建其左子节点和右子节点
            node = queue.poll();
            node.left = generatedNode(ans.poll());
            node.right = generatedNode(ans.poll());
            if(node.left != null) {
                // 将不为空的左子节点放入队列，下个循环可以取出来构建其左子节点和右子节点
                queue.offer(node.left);
            }
            if(node.right != null) {
                queue.offer(node.right);
            }
        }
        return head;
    }

    private Node generatedNode(String value) {
        if(value == null) {
            return null;
        }
        return new Node(Integer.parseInt(value));
    }


    private Queue<String> process(Node head, Queue<String> queue) {
        if(head == null) {
            queue.add(null);
            return queue;
        }
        // 先将当前节点放入到queue中
        queue.offer(String.valueOf(head.value));
        // 向左递归
        process(head.left, queue);
        process(head.right, queue);

        return queue;
    }


    /**
     * 前序反序列化
     *
     */
    private Node preb(Queue<String> queue) {
        String value = queue.poll();
        if(value == null) {
            return null;
        }
        // 前序序列化先创建当前节点
        Node node = new Node(Integer.parseInt(value));

        // 递归创建左子节点
        node.left = preb(queue);
        // 递归创建右子节点
        node.right = preb(queue);

        return node;
    }



}
