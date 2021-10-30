package com.github.左神算法.KMP;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/8/7 22:10
 */
public class TreeEqual {

    /**
     * 给定两颗二叉树，node1为大的二叉树，node2是小的二叉树，判断node2是否为node1的子树，子树不是某一个分支，而是完整的子树
     *
     *
     *  二叉树序列化 + KMP
     */

    @Test
    public void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        node1.left = node2;
        node2.left = node4;
        node2.right = node5;
        node1.right = node3;
        node3.left = node6;
        node3.right = node7;

        Node small3 = new Node(3);
        Node small6 = new Node(6);
        Node small7 = new Node(7);

        small3.left = small6;
        small3.right = small7;

        System.out.println(containsTree1(node1, small3));
        System.out.println(containsTree2(node1, small3));

    }


    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }


    /**
     * 递归
     *
     */
    public boolean containsTree1(Node big, Node small) {
        if(small == null) {
            return true;
        }
        if(big == null) {
            return false;
        }
        // 当前节点是否包含small子树
        if(isSameValueStructure(big, small)) {
            return true;
        }
        // 查看左子树 或 右子树是否包含small子树
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    private boolean isSameValueStructure(Node head1, Node head2) {
        if(head1 == null && head2 != null) {
            return false;
        }
        if(head1 != null && head2 == null) {
            return false;
        }
        // 如果已经到达空节点，那么证明上面的每个节点都已经对比，并且相同，所以这里返回true
        if(head1 == null && head2 == null) {
            return true;
        }
        if(head1.value != head2.value) {
            return false;
        }
        // 如果到这里，那么head1.value == head2.value, 所以判断下面的所有节点是否相等
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }


    /**
     * 序列化 + KMP
     *
     *  1. 序列化两颗树，并且将null的位置也标记出来
     *  2. 对两个序列化的字符串使用KMP
     *
     */
    public boolean containsTree2(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        // 将两棵树序列化
        List<String> pre1 = new ArrayList<>();
        List<String> pre2 = new ArrayList<>();
        preSerial(big, pre1);
        preSerial(small, pre2);

        // 将列表转换为String[] 数组，方便进行KMP
        String[] str1 = new String[pre1.size()];
        for (int i = 0; i < pre1.size(); i++) {
            str1[i] = pre1.get(i);
        }
        String[] str2 = new String[pre2.size()];
        for (int i = 0; i < pre2.size(); i++) {
            str2[i] = pre2.get(i);
        }

        // 将两棵树包含问题转换为KMP查找字符串的问题
        return getIndexOf(str1, str2) != -1;
    }

    private int getIndexOf(String[] str1, String[] str2) {
        if(str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
            return -1;
        }
        // 构建nextArray
        int[] next = getNextArray(str2);
        int p1 = 0;
        int p2 = 0;

        // 双指针判断是否相等
        while (p1 < str1.length && p2 < str2.length) {
            if(isEqual(str1[p1], str2[p2])) {
                p1++;
                p2++;
            } else if(next[p2] != -1) {
                p2 = next[p2];
            } else {
                p1++;
            }
        }

        return p2 == str2.length ? p1 - p2 : -1;
    }

    // 构建需要匹配数组的nextArray
    private int[] getNextArray(String[] str2) {
        if (str2.length == 1) {
            return new int[] { -1 };
        }
        // next[i] 代表前一个字符的最长前缀的长度
        int[] next = new int[str2.length];
        // 规定
        next[0] = -1;
        next[1] = 0; // 因为next[i]表示的是前一个数的最长前缀，1的前面没有前缀，因为前缀不包含整个字符串
        int cn = 0; // 表示当前的最长的前缀的长度和需要开始匹配的字符的位置
        int index = 2;

        while (index < str2.length) {
            // 必须通过isEqual方法中逻辑判断是否相等，因为数组中会包含Null
            if(isEqual(str2[index - 1], str2[cn])) {
                next[index++] = ++cn;
            } else if(cn > 0) {
                cn = next[cn];
            } else {
                next[index++] = 0;
            }
        }
        return next;
    }

    private boolean isEqual(String s1, String s2) {
        if(s1 == null && s2 == null) {
            return true;
        }
        if(s1 == null || s2 == null) {
            return false;
        }
        if(s1.equals(s2)) {
            return true;
        }
        return false;
    }

    private void preSerial(Node head, List<String> list) {
        if(head == null) {
            list.add(null);
        } else {
            list.add(String.valueOf(head.value));
            preSerial(head.left, list);
            preSerial(head.right, list);
        }
    }

}
