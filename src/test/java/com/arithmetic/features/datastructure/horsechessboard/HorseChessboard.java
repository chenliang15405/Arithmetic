package com.arithmetic.features.datastructure.horsechessboard;

import org.junit.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * 骑士周游（马踏棋盘）算法  回溯递归
 *
 * 找到当前点的所有可走的点，并假如到数组中，然后遍历该所有路线数组，走过的点设置为已访问，递归获取每一步对应的接下来
 * 可以走的所有点，如果不可走就开始回溯。
 *
 * 贪心算法优化：
 *    将每次获取到的下一步可走的所有点之后的下一步（相当于每走下一步那么下下步会有多个可走的点，那么有多个下一步，就会有根据下一步
 *    的不同，有多个下下不的数组），将下一步的后面的可能走的点递增排序，将可能的点少的放在前面（减少回溯次数）
 *
 *
 * @author alan.chen
 * @date 2020/6/6 6:41 PM
 */
public class HorseChessboard {


    private static int X; // 表示列数
    private static int Y; // 表示行数

    private static boolean[] visited; // 表示是否访问过
    private static boolean finished; // 如果为true 则表示成功


    @Test
    public void test() {
        X = 8;
        Y = 8;

        // 初始化棋盘
        int[][] chessboard = new int[X][Y];
        // 初始化访问数组
        visited = new boolean[X * Y];

        // 定义位置
        int row = 1; // 在传递时-1，因为是从0开始
        int col = 1;

        long start = System.currentTimeMillis();
        traversalChessboard(chessboard, visited, row - 1, col - 1, 1);

        long end = System.currentTimeMillis();

        System.out.println("耗时：" + (end-start) + " ms");

        // 打印数组
        for (int[] ints : chessboard) {
            System.out.println(Arrays.toString(ints));
        }
    }

    /**
     * 马踏棋盘算法计算
     *
     * 贪心算法优化：对计算的路线进行排序，找到少的路线进行选择
     *
     * @param chessboard 棋盘
     * @param visited 标示该点是否访问过数组
     * @param row 马儿当前的行，从0开始
     * @param column  马儿当前的列，从0开始
     * @param step 当前的步骤，是第几步，初始为第1步
     */
    private void traversalChessboard(int[][] chessboard, boolean[] visited, int row, int column, int step) {
        // 给当前的位置设置步数
        chessboard[row][column] = step;
        // 将当前位置设置为已访问,因为是一维数组标示，所以需要计算当前位置的坐标
        visited[row * X + column] = true;
        // 获取当前位置接下来的所有可走位置点
        List<Point> ps = next(new Point(column, row)); // 传递时,x表示column，y表示row

        // 对ps排序，就是对所有的下一步位置的数目进行非递减排序
        sort(ps);

        // 开始循环, 递归回溯
        while (!ps.isEmpty()) {
            // 从list中取一个可以走的位置
            Point point = ps.remove(0);
            if(!visited[point.y * X + point.x]) {
                // 递归查询路线，ponint的x表示列（横向），y表示列（纵向就是行）因为Point对象不是二维数组，只是记录x和y，传递的时候，x为column，y为row，x计算时表示横轴、y表示纵轴，则获取时x就是列，y就是行
                // setp要+1,不能++step, 因为还要回溯
                traversalChessboard(chessboard, visited, point.y, point.x, step + 1);
            }
        }

        // 等路线走不通的时候，就是递归到最后一个的时候，判断是完成了任务还是走不通了没有点可以走了
        if(step < X * Y && !finished) {
            // 表示没有点可以走了，走不通（棋盘原因导致走不通 2X2的棋盘就无法找到可走的点），则将当前点步数设置为0，并设置没有访问，退出
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            // 否则表示回溯递归完成，点都已经走完了
            finished = true;
        }

    }

    /**
     * 对下一步的计算位置数量排序
     * @param ps
     */
    private void sort(List<Point> ps) {
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                // 计算o1 和o2下一步的所有位置的数目，根据数目的大小升序排列
                List<Point> next1 = next(o1);
                List<Point> next2 = next(o2);
                if(next1.size() > next2.size()) {
                    // 返回1，则认为o1比o2大，则将o2排在前面
                    return 1;
                } else if (next1.size() < next2.size()) {
                    // 返回-1，则认为o1比o2小，会将o1排在o2前面
                    return -1;
                }
                // 相等则返回0
                return 0;
            }
        });
    }


    /**
     * 根据当前位置(Point对象)，获取接下来马儿可以走的所有位置，并放到一个集合中
     *
     * 最好的情况就是马儿接下来可以走8个位置，所以判断8个位置，将符合条件的加入集合
     *
     * @param curPoint
     * @return
     */
    private List<Point> next(Point curPoint) {
        List<Point> list = new ArrayList<>();

        //5的位置
        if((curPoint.x - 2) >= 0 && (curPoint.y - 1) >= 0) {
            list.add(new Point(curPoint.x - 2, curPoint.y - 1)); // point的x表示二维数组中的列，y表示行
        }
        //6的位置
        if((curPoint.x - 1) >= 0 && (curPoint.y - 2) >= 0) {
            list.add(new Point(curPoint.x - 1, curPoint.y - 2));
        }
        // 7的位置
        if((curPoint.x + 1) < X && (curPoint.y - 2) >= 0) {
            list.add(new Point(curPoint.x + 1, curPoint.y - 2));
        }
        // 0的位置
        if((curPoint.x + 2) < X && (curPoint.y - 1) >= 0) {
            list.add(new Point(curPoint.x + 2, curPoint.y - 1));
        }
        // 1的位置
        if((curPoint.x + 2) < X && (curPoint.y + 1) < Y) {  // Y表示行，X表示列
            list.add(new Point(curPoint.x + 2, curPoint.y + 1));
        }
        // 2的位置
        if((curPoint.x + 1) < X && (curPoint.y + 2) < Y) {
            list.add(new Point(curPoint.x + 1, curPoint.y + 2));
        }
        // 3的位置
        if((curPoint.x - 1) >= 0 && (curPoint.y + 2) < Y) {
            list.add(new Point(curPoint.x - 1, curPoint.y + 2));
        }
        // 4的位置
        if((curPoint.x - 2) >= 0 && (curPoint.y + 1) < Y) {
            list.add(new Point(curPoint.x - 2, curPoint.y + 1));
        }

        return list;
    }


}
