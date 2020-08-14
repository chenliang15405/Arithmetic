package com.arithmetic.features.datastructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 迷宫问题  回溯递归算法
 *
 * 1.使用递归计算是否可以找到出口
 *
 * 2.计算最短路径
 *
 *  @author alan.chen
 * @date 2020/5/26 11:34 PM
 */
public class Migong {

    @Test
    public void test1() {
        buildMigong();
    }

    @Test
    public void test2() {
        int[][] arr = new int[8][7];
        // 使用1 表示四周的墙，上下设置墙
        for (int i = 0; i < arr[0].length; i++) {
            arr[0][i] = 1;
            arr[7][i] = 1;
        }
        // 左右设置墙
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = 1;
            arr[i][arr[0].length - 1] = 1;
        }
        //设置挡板, 1 表示
        arr[3][1] = 1;
        arr[3][2] = 1;


        //使用递归回溯给小球找路径
        //setWay(arr, 1, 1);
        //setWay2(arr, 1, 1);

        System.out.println(setWay3(arr, 1, 1));
        print(arr);
    }


    private boolean setWay3(int[][] arr, int i, int j) {
        if(arr[6][5] == 2) {
            return true;
        } else {
            if(arr[i][j] == 0) {
                arr[i][j] = 2;

                if(setWay3(arr, i, j + 1)) {
                    return true;
                } else if(setWay3(arr, i + 1, j)) {
                    return true;
                } else if(setWay3(arr, i - 1, j)) {
                    return false;
                } else if(setWay3(arr, i, j - 1)) {
                    return true;
                } else {
                    arr[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    //使用递归回溯来给小球找路
    //说明
    //1. arr 表示地图
    //2. i,j 表示从地图的哪个位置开始出发 (1,1)
    //3. 如果小球能到 arr[6][5] 位置，则说明通路找到.
    //4. 约定： arr[i][j] 为 0 表示该点没有走过 当为 1 表示墙  ； 2 表示通路可以走 ； 3 表示该点已经走过，但是走不通
    //5. 在走迷宫时，需要确定一个策略(方法) 下->右->上->左 , 如果该点走不通，再回溯

    // 0 表示该点没有走过 当为 1 表示墙  ； 2 表示通路可以走 ； 3 表示该点已经走过，但是走不通
    private boolean setWay(int[][] arr, int i, int j) {
        // 已经找到路
        if(arr[6][5] == 2) {
            return true;
        } else {
            // 如果等于0表示还没有走
            if(arr[i][j] == 0) {
                arr[i][j] = 2;
                // 按照策略 下->右->上->左  走，这个策略是自定义的路径，就是判断的条件为false，下一个条件是判断哪个节点

                // 先向下
                if(setWay(arr, i+1, j)) {
                    return true;
                } else if(setWay(arr, i, j+1)) {
                    // 再向右
                    return true;
                } else if(setWay(arr, i-1, j)) {
                    // 再向上
                    return true;
                } else if(setWay(arr, i, j-1)) {
                    // 再向左
                    return true;
                } else {
                    // 如果上下左右都无法走通，将当前节点标记为3
                    arr[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    // 使用其他的策略路线找路：上->右->下->左
    private boolean setWay2(int[][] arr, int i, int j) {
        // 已经找到路
        if(arr[6][5] == 2) {
            return true;
        } else {
            // 如果等于0表示还没有走
            if(arr[i][j] == 0) {
                arr[i][j] = 2;

                // 先向上
                if(setWay2(arr, i-1, j)) {
                    return true;
                } else if(setWay2(arr, i, j+1)) {
                    // 再向右
                    return true;
                } else if(setWay2(arr, i+1, j)) {
                    // 再向下
                    return true;
                } else if(setWay(arr, i, j-1)) {
                    // 再向左
                    return true;
                } else {
                    // 如果上下左右都无法走通，将当前节点标记为3
                    arr[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }



    private void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    private int[][] buildMigong() {
        int[][] migong = new int[8][7]; // 8行7列的矩阵，二维数组就是一个数组中的每个元素都是一个数组，这个元素是竖着排列
        for (int i = 0; i < migong.length; i++) {
            for (int j = 0; j < migong[i].length; j++) {
                System.out.print(migong[i][j]);
            }
            System.out.println();
        }
        return migong;
    }




    @Test
    public void test3() {
        List<Integer> list = new ArrayList<>();
        System.out.println(test(20, list));

        System.out.println(test2(20, list));
    }

    /**
     * 回溯
     * @param i
     * @param list
     * @return
     */
    private boolean test(int i, List<Integer> list) {
        if(i % 3 ==0 && i % 9 ==0) {
            System.out.println("数据" + i);
            return true;
        } else {
            if(!list.contains(i) && i < 25 && i > 10) {
                list.add(i);
                if(test(i+1, list)) {
                    return true;
                } else if(test(i-1, list)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 递归
     * @param i
     * @param list
     * @return
     */
    private boolean test2(int i, List<Integer> list) {
        if(i % 3 ==0 && i % 9 ==0) {
            System.out.println("数据" + i);
            return true;
        } else {
            if(i < 30 && i > 10) {
                test2(++i, list);
            }
        }
        return false;
    }

}
