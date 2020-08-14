package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

import java.util.*;

/**
 * 二叉树的前序、中序、后序遍历 包括递归和非递归的方式
 *
 * 判断二叉树是平衡二叉树
 *
 * 判断二叉树是排序二叉树
 *
 * 判断二叉树是完全二叉树
 *
 * @author alan.chen
 * @date 2020/6/8 10:49 PM
 */
public class BinaryTreeIterator {


    @Test
    public void treeOrder() {
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

        System.out.println("pre order : ");
        preOrder(head);

        System.out.println();
        preOrderCurstion(head);

        System.out.println("infix order : ");
        infixOrder(head);

        System.out.println("pos order : ");
        posOrder(head);


        System.out.println("bfs: ");
        bfs(head);

        System.out.println("dfs: ");
        dfs(head);

        System.out.println("树的高度");
        System.out.println(treeHeight(head));

        System.out.println("是否是二叉排序树");
        isBST(head);

        System.out.println("非递归后序 ");
        postOrder(head);
    }


    @Test
    public void treeIteror() {
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

        bfs1(head);
    }

    private void bfs1(Node head) {
        if(head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        while(!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                System.out.print(node.value);
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
            System.out.println();
        }
    }

    private void dfs(Node head) {
        if(head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);

        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            System.out.print(temp.value + "->");

            if(temp.right != null) {
                stack.push(temp.right);
            }
            if(temp.left != null) {
                stack.push(temp.left);
            }
        }
    }

    private void bfs(Node head) {
        if(head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        int count = 0;

        while(!queue.isEmpty()) {
            count++;
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                System.out.print(node.value + "->");
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        System.out.println("层数" + count);
    }


    public int treeHeight(Node head) {
        if(head == null) {
            return 0;
        }
        int left = treeHeight(head.left);
        int right = treeHeight(head.right);
        return Math.max(left, right) + 1;
    }


    public void isBST(Node head) {
        List<Integer> list = new ArrayList<>();
        infixOrder2(list, head);

        for (int i = 1; i < list.size(); i++) {
            if(list.get(i) < list.get(i - 1)) {
                System.out.println("is not BST");
            }
        }
        System.out.println("BST");
    }
    private void infixOrderWithCursion(List<Integer> list, Node head) {
        if(head == null) {
            return;
        }
        infixOrderWithCursion(list, head.left);
        list.add(head.value);
        infixOrderWithCursion(list, head.right);
    }

    private void infixOrder2(List<Integer> list, Node head) {
        Stack<Node> stack = new Stack<>();

        while(!stack.isEmpty() || head != null) {
            if(head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                list.add(head.value);
                head = head.right;
            }
        }
    }


    public void postOrder(Node head) {
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();

        stack1.push(head);

        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            stack2.push(node);
            if(node.left != null) {
                stack1.push(node.left);
            }
            if(node.right != null) {
                stack1.push(node.right);
            }
        }

        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().value + "->");
        }
    }


    /**
     * 非递归前序遍历
     * @param head
     */
    private void preOrder(Node head) {
        Stack<Node> stack = new Stack<>();
        stack.add(head);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.value + "->");
            if(node.right != null) {
                stack.add(node.right);
            }
            if(node.left != null) {
                stack.add(node.left);
            }
        }
    }


    public void preOrderCurstion(Node head) {
        if(head == null) {
            return;
        }
        System.out.print(head.value + "=>");
        preOrderCurstion(head.left);
        preOrderCurstion(head.right);
    }


    /**
     * 非递归中序遍历
     * @param head
     */
    public void infixOrder(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;

        while (!stack.isEmpty() || cur != null) {
            if(cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.print(cur.value + " ");
                cur = cur.right;
            }
        }
    }


    /**
     * 非递归后序遍历
     */
    public void posOrder(Node head) {
        if(head == null) {
            return;
        }
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();

        s1.push(head);

        while (!s1.isEmpty()) {
            Node node = s1.pop();
            s2.push(node);
            if(node.left != null) {
                s1.push(node.left);
            }
            if(node.right != null) {
                s1.push(node.right);
            }
        }
        // 遍历s2
        while (!s2.isEmpty()) {
            System.out.print(s2.pop().value + " ");
        }

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
