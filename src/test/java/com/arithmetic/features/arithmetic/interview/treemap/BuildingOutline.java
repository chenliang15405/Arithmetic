package com.arithmetic.features.arithmetic.interview.treemap;

import java.util.*;

/**
 * 大楼轮廓
 *
 * 给定一个N行3列的二维数组，每一行表示有一座大楼，一共有N座大楼，所有大楼的底部都坐落在X轴上，每一行的三个值（a,b,c）代表每座大楼从
 * （a,0）点开始，到(b,0)点结束，高度为c,输入的数据可以保证a<b，且a,b,c均为正数，大楼之间可以有重合，请输出整体的轮廓线
 *
 * 例子：
 * 给定一个二维数组 [ [1,3,3], [2,4,4], [5,6,1]]
 * 输入的轮廓线：[ [1,2,3], [2,4,4], [5,6,1]]
 *
 *
 * @author tangsong
 * @date 2021/2/27 20:11
 */
public class BuildingOutline {


    public static class Node {
        public boolean isUp;
        public int posi;
        public int h;

        public Node(boolean isUp, int posi, int h) {
            this.isUp = isUp;
            this.posi = posi;
            this.h = h;
        }
    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if(o1.posi != o2.posi) {
                return o1.posi - o2.posi;
            }
            if(o1.isUp != o2.isUp) {
                // 如果两个坐标点相同，那么向下的坐标排到前面
                return o1.isUp ? -1 : 1;
            }
            return 0;
        }
    }

    /**
     * 思路：
     *    1. 将每个x轴的坐标点以及对应的高度，是上升还是下降，记录下来，记录到数组中
     *    2. 对数组中的x轴（即坐标点）进行排序
     *    3. 遍历数组，获取到每个坐标点的最大高度，并记录
     *    4. 遍历记录每个坐标点的数组，并构建轮廓
     *
     */
    public static List<List<Integer>> buildingOutline(int[][] buildings) {
        // 记录每个坐标点的坐标、高度、上or下，所以需要2倍的长度
        Node[] nodes = new Node[buildings.length * 2];
        for (int i = 0; i < buildings.length; i++) {
            // 将每个坐标属性记录到数组中即可
            nodes[i * 2] = new Node(true, buildings[i][0], buildings[i][2]);
            nodes[i * 2 + 1] = new Node(false, buildings[i][1], buildings[i][2]);
        }
        // 对坐标进行排序
        Arrays.sort(nodes, new NodeComparator());
        // 遍历坐标属性数组，并记录坐标和高度的map
        TreeMap<Integer, Integer> ptMap = new TreeMap<>();  // key: height  value: times
        TreeMap<Integer, Integer> pmMap = new TreeMap<>(); // key: posi  value: height
        for (int i = 0; i < nodes.length; i++) {
            // 判断当前是上升还是下降，如果是上升，那么记录次数，如果是下降，那么就将该key的次数-1或移除
            // 因为每次都要记录一个当前坐标点的最大值，treeMap的最后一个key就是当前map中坐标的最大值，当某个大楼还没有下降的时候，
            // 那么就可以一直使用这个大楼的高度，如果一旦下降了，就不能使用了，所以需要将这个大楼的高度移除
            Node curNode = nodes[i];
            if(curNode.isUp) {
                ptMap.put(curNode.h, ptMap.getOrDefault(curNode.h, 0) + 1);
            } else {
                if(ptMap.containsKey(curNode.h)) {
                    if(ptMap.get(curNode.h) == 1) {
                        ptMap.remove(curNode.h);
                    } else {
                        ptMap.put(curNode.h, ptMap.get(curNode.h) - 1);
                    }
                }
            }
            // 判断当前是否有最大值，并加入到坐标：高度map中
            if(ptMap.isEmpty()) {
                pmMap.put(curNode.posi, 0);
            } else {
                // 如果高度数组中有数据，那么就获取最大的高度作为当前坐标的轮廓
                pmMap.put(curNode.posi, ptMap.lastKey());
            }
        }

        List<List<Integer>> res = new ArrayList<>();
        int start = 0;
        int height = 0;
        // 遍历坐标高度map，记录轮廓记录
        for (Map.Entry<Integer, Integer> entry : ptMap.entrySet()) {
            int curPosition = entry.getKey();
            int curHeight = entry.getValue();
            // 判断当前是否高度相等，如果相等则不记录坐标点
            if(height != curHeight) {
                // 高度不为0才记录，因为height=0，则表示现在的坐标点是上升的轮廓线，不需要记录坐标点，只需要记录当前坐标点为start
                // 当这个轮廓的高度变化的时候，可以获取到这个高度结束的坐标点，那么就可以记录为一条完整的轮廓线记录
                if(height != 0) {
                    List<Integer> newRecord = new ArrayList<>();
                    newRecord.add(start);
                    newRecord.add(curPosition);
                    newRecord.add(height);
                    res.add(newRecord);
                }
                start = curPosition;
                height = curHeight;
            }
        }

        return res;
    }


}
