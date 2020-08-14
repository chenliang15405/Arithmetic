package com.arithmetic.features.datastructure.tree;

/**
 * B-Tree和B+-Tree等都是以二叉排序树为前提
 *
 * B-Tree: 一个节点可以保存多个数据，所有键值分布在整个树中, 在关键字全集内做一次查找，性能逼近二分查找算法
 *
 * B+-Tree: 所有的数据都保存在叶子节点，非叶子节点只保存关键字信息和下一个节点的指向，叶子节点都是有序的，并且通过链指针相连
 *
 * B*-Tree: B*-Tree是B+-Tree的变种，在B+-Tree的非根和非叶子节点再增加指向兄弟的指针，B*-Tree的空间利用率更高
 *
 *
 * @author alan.chen
 * @date 2020/6/1 9:39 PM
 */
public class BTree {
}
