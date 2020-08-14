package com.arithmetic.features.datastructure.huffman;

import org.junit.Test;

/**
 * 赫夫曼树创建：根据数组的有序获取前2个节点，并构建出父节点，直到构建到的节点为根节点表示构建赫夫曼树
 *
 * 赫夫曼树编码：数据压缩
 *
 * 赫夫曼树解码：数据解压
 *
 * 赫夫曼树压缩文件
 * 赫夫曼树解压文件
 *
 * @author alan.chen
 * @date 2020/5/31 5:05 PM
 */
public class HuffmanCodes {


    /**
     * 使用赫夫曼树实现数据压缩
     */
    @Test
    public void testZip() {

    }



    class Node implements Comparable<HuffmanTreeDemo.Node> {
        int value; // 结点权值
        char c; //字符
        HuffmanTreeDemo.Node left; // 指向左子结点
        HuffmanTreeDemo.Node right; // 指向右子结点

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }

        @Override
        public int compareTo(HuffmanTreeDemo.Node o) {
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
