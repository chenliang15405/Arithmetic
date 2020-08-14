package com.arithmetic.features.datastructure.tree;

import org.junit.Test;

import java.util.*;

/**
 * 给定一个二叉树，填充一个数组来代表它的层级关系。把每个层级从左到右填充进一个独立的子数组，返回最后的数组
 *
 * @author alan.chen
 * @date 2020/6/15 2:26 PM
 */
public class BinaryTreeBST {


    @Test
    public void test() {
        Node root = new Node(1, "宋江");
        Node left = new Node(2, "吴用");
        Node leftRight = new Node(2, "吴用1111");
        Node right = new Node(3, "卢俊义");
        Node righ2 = new Node(4, "林冲");
        Node left2 = new Node(5, "玉麒麟");

        root.left = left;
        left.right = leftRight;
        root.right = right;
        right.right = righ2;
        right.left = left2;

        List<List<Integer>> bst = bfs(root);

        //bst.stream().flatMap(integers -> integers.stream()).forEach(System.out::println);
        bfs2(root);

        dfs(root);

        for (List<Integer> list : bst) {
            System.out.println(list);
        }

        //
        bfsNext(root);

        infixOrder(root);
    }


    public void bfs2(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node poll = queue.poll();
                System.out.println(poll);
                if(poll.left != null) {
                    queue.offer(poll.left);
                }
                if(poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
    }

    public void dfs(Node root) {
        Stack<Node> stack = new Stack<>();

        stack.push(root);

        while(!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur);
            if(cur.right != null) {
                stack.push(cur.right);
            }
            if(cur.left != null) {
                stack.push(cur.left);
            }
        }
    }



    /**
     * 将二叉树的每一层从左到右装入数组中
     * <p>
     * BFS
     *
     * @param root
     * @return
     */
    private List<List<Integer>> bfs(Node root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (root == null) {
            return result;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 获取每一层的数据
            List<Integer> list = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                list.add(node.id);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(list);
        }
        return result;
    }


    /**
     * 给定一个二叉树，把每个层级上的节点跟它在这个层级上的后面一个节点连接起来，每个层级的最后一个节点要指向null
     *
     * @param root
     */
    private void bfsNext(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 记录前一个节点
            Node pre = null;
            // 获取当前层级的个数
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                // 如果前一个节点不为null则将前一个节点的next指向当前节点
                if (pre != null) {
                    pre.next = node;
                }
                // 前一个节点移动
                pre = node;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }


    public void infixOrder(Node root) {
        if (root == null) {
            return;
        }
        infixOrder(root.left);
        System.out.print(root.id + "->");
        infixOrder(root.right);
    }


    class Node {
        public int id;
        public String name;
        public Node left;
        public Node right;
        public Node next;

        public Node(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
