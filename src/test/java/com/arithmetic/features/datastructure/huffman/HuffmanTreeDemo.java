package com.arithmetic.features.datastructure.huffman;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 赫夫曼树：也叫做哈夫曼树，满足满二叉树定义
 *
 * 路径：从根节点到子节点或者孙子节点的通路
 * 路径长度：从根节点到叶子节点的路径的长度称为路径长度，树的层级-1
 *
 * 带权路径：叶子节点的权值乘以该叶子节点的路径长度就是带权路径
 *
 * 树的带权路径：所有`叶子节点`的带权路径之和就是树的带权路径(WPL)
 *
 * 树的带权路径越小，则被称为最优二叉树（赫夫曼树）
 *
 *
 * //因为需要将大的节点放在离根节点越近的位置，则就是最优二叉树，所以需要将该数组排序，每次取2个用来构建树，
 * //就是为了将大的节点放在离根节点越近的位置，保证树的带权路径越小，每次构建完成需要再次排序是为了保证所有节点的
 * //位置，保证不大不小的树也在路径根节点较近的位置，保证最优二叉树
 *
 * @author alan.chen
 * @date 2020/5/31 10:58 AM
 */
public class HuffmanTreeDemo {


    @Test
    public void test() {
        int[] arr = { 13, 7, 8, 3, 29, 6, 1 };
        Node root = createHuffmanTree(arr);

        // 前序遍历huffman树
        preOrder(root);
    }


    private Node createHuffmanTree(int[] arr) {
        // 为了操作方便，使用集合保存
        // 可以建立最小堆来实现，PriorityQueue 自定义比较器实现最小堆, 每次从堆中获取到2个最小数据
        // 将数据放入堆中，会自动调整堆
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(new Node(arr[i]));
        }

        // 构建赫夫曼树
        while (list.size() > 1) {
            // 先对节点进行排序
            Collections.sort(list);

            // 取出前两个元素
            Node node1 = list.get(0);
            Node node2 = list.get(1);
            // 创建当前两个元素的父节点
            Node parent = new Node(node1.value + node2.value);
            parent.left = node1;
            parent.right = node2;
            // 将父节点存储到集合中
            list.add(parent);
            // 删除两个子节点
            list.remove(node1);
            list.remove(node2);
        }
        // list集合中只有一个数据，则当前数据就是根节点
        return list.get(0);
    }

    public void preOrder(Node root) {
        if(root != null) {
            root.preOrder();
        } else {
            System.out.println("空树");
        }
    }

    // 创建结点类
    // 为了让Node 对象持续排序Collections集合排序
    // 让Node 实现Comparable接口
    class Node implements Comparable<Node> {
        int value; // 结点权值
        char c; //字符
        Node left; // 指向左子结点
        Node right; // 指向右子结点

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }

        @Override
        public int compareTo(Node o) {
            // 表示从小到大排序
            return this.value - o.value;
        }

        public void preOrder() {
            System.out.println(this);
            if(this.left != null) {
                this.left.preOrder();
            }
            if(this.right != null) {
                this.right.preOrder();
            }
        }

    }


}
