package com.github.左神算法.并查集;

/**
 * @author tangsong
 * @date 2021/6/16 21:53
 */
public class FIndCircle {

    /**
     * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
     *
     * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
     *
     * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
     *
     * 返回矩阵中 省份 的数量。
     *
     *  并查集相关问题
     *
     */

    /**
     * 因为i和j直接相连，那么j也和i相连，所以这个正方形的矩阵是根据对角线堆成的，只需要计算一边的对角线就可以
     *
     * 并且因为如果i和j直接相连，j和x直接相连，那么i和x就间接相连，组成一个省份，所以问题计算多少个省份可以转换为
     *   先计算一共有多少个完全不相连的区域，不相连就是不同的省份。
     *
     *   使用并查集，将每个相连的城市连接到合并到同一个集合中，统计最后的集合个数就可以
     *
     */
    public int findCirCle(int[][] arr) {
        int N = arr.length;
        // 初始化每个集合，将每个集合初始化为单独的集合，最多只有N个集合
        UnionFind unionFind = new UnionFind(N);
        // 遍历当前的矩阵，只需要遍历一半就可以
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // 判断当前是否为1，如果是1则合并
                if(arr[i][j] == 1) {
                    // 将这两个位置的进行合并，合并为1个集合
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;
    }


    /**
     * 定义并查集
     *   使用数组代替之前的Map结构，数组结构更优，插入和查询比map更快
     */
    public static class UnionFind {
        public int[] parents;  // parents[i] 表示 i 元素对应的父节点是多少
        public int[] size;  // size[i] 表示当前集合代表节点对应的集合的大小
        public int sets;  // 一共有多少个集合
        public int[] help; // 辅助数组，为了优化find的过程

        public UnionFind(int N) {
            // 相当于将{0}, {1}, {2}, {3} .. 等元素都初始化为一个单独的集合
            parents = new int[N];
            size = new int[N];
            sets = N;
            // 初始化集合
            for (int i = 0; i < N; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int find(int i) {
            int hi = 0;
            // 找到集合的代表就节点
            while (i != parents[i]) {
                help[hi++] = i;
                i = parents[i]; // 当前节点的父节点
            }
            // 将所有节点直连到i节点下
            for (int j = 0; j < hi; j++) {
                parents[help[j]] = i;
            }
            return i;
        }

        public void union(int a, int b) {
            // 找到两个元素的集合代表节点
            int aHead = find(a);
            int bHead = find(b);
            // 判断是否同一个集合，如果不是同一个集合才进行合并
            if(aHead != bHead) {
                int aSize = size[aHead];
                int bSize = size[bHead];
                if(aSize >= bSize) {
                    size[aHead] += bSize;
                    parents[bHead] = aHead;
                } else {
                    size[bHead] += aSize;
                    parents[aHead] = bHead;
                }
                sets--;
            }
        }

        /**
         * 返回当前集合个数
         */
        public int sets() {
            return sets;
        }

    }


}
