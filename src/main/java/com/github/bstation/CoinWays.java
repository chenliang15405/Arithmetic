package com.github.bstation;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import javax.sound.midi.Soundbank;

/**
 * @author tangsong
 * @date 2021/5/17 23:53
 */
public class CoinWays {

    /**
     *  换钱的方法数
     *    给定数组arr，arr中所有的值都为正数并且不重复，每个值代表一种面值的货币，每种面值的货币都可以使用任意张，再给定一个正数aim代表要找的钱数
     *    求换钱有多少种方法
     */

    @Test
    public void test() {
        int[] arr = {5,10,50,100};
        int aim = 1000;

        System.out.println(way1(arr, aim));
        System.out.println(way2(arr, aim));
        System.out.println(way3(arr, aim));
        System.out.println(way4(arr, aim));
    }


    /**
     * 暴力递归
     *
     */
    public int way1(int[] arr, int aim) {
        if(arr == null || arr.length <= 0 || aim < 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    /**
     * index 表示当前使用的哪一种面额
     */
    private int process1(int[] arr, int index, int rest) {
        // base case
        if(index == arr.length) {
            // 当使用面额达到最后一张，则判断是否可以正好获取到
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        // i 表示当前面额使用的张数，整个循环解决的是每个面额使用的不同的张数的解法
        for (int i = 0; arr[index] * i <= rest; i++) {
            ways += process1(arr, index+1, rest - arr[index] * i);
        }
        return ways;
    }

    /**
     * 记忆化搜索
     *
     */
    public int way2(int[] arr, int aim) {
        if(arr == null || arr.length <= 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        // 2个可变参数，二维表
        int[][] dp = new int[N+1][aim+1];
        // 因为递归过程使用到了0，所以初始化为-1
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = -1;
            }
        }

        return process2(arr, 0, aim, dp);
    }

    /**
     * index 表示当前使用的面额是数组中的哪一个
     * dp: 记忆化搜索使用到的缓存数组，一个二维表，行是使用的面额，列是目标值
     *
     */
    private int process2(int[] arr, int index, int rest, int[][] dp) {
        // 查询缓存
        if(dp[index][rest] != -1) {
            return dp[index][rest];
        }
        // 运算
        if(index == arr.length) {
            // 获取到解法，则保存到缓存
            dp[index][rest] = rest == 0 ? 1 : 0;
            return dp[index][rest];
        }
        //计算
        int ways = 0;
        for (int i = 0; arr[index] * i <= rest; i++) {
            ways += process2(arr, index+1, rest - arr[index] * i, dp);
        }

        // 返回解法之前，保存当前可变参数可以确定的唯一解法
        dp[index][rest] = ways;
        return ways;
    }

    /**
     * 动态规划 -- 经典动态规划
     *
     */
    public int way3(int[] arr, int aim) {
        if(arr == null || arr.length <= 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        // 2个可变参数，二维表
        int[][] dp = new int[N+1][aim+1];
        // 根据条件，填充dp数组
        // 当index==arr.length时，rest==0的位置为1，其他都是0
        dp[N][0] = 1;
        // 从递归的条件中可以看到，并且得到了最后一行，那么每一行的值都依赖index+1，所以从下向上推（递归中是index+1），从左向右（比较符合逻辑）
        // 第N行已经填充了，从N-1开始
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // dp[i][j] = ? 直接将递归的过程拿过来，将其中的递归函数计算替换为从dp数组获取值即可
                //下面的整个循环在这里不是直接计算最终的目标值的解法，而是计算rest循环中，当前rest值为多少时，面额组成有多少种解法
                // 从下上上，从rest小向大的计算，最终填充dp数组，就得到了最终解，所以这里的<=应该是rest，不是aim，因为每次计算的解法
                // 是当前数组的每一个格子的解法
                int ways = 0;
                for (int zhang = 0; arr[index] * zhang <= rest; zhang++) {
                    ways += dp[index+1][rest - arr[index] * zhang];
                }
                dp[index][rest] = ways;
            }
        }
        // 递归的主函数中，参数就是0，aim，所以获取dp数组中的指定位置就是最终解
        return dp[0][aim];
    }

    /**
     * 动态规划 -- 精细（省略掉枚举过程）
     *   dp[index+1][rest - arr[index] * zhang] -> index + 1表示下一行，当zhang=0时，表示取dp[index+1][rest]的值
     *   就是当前位置的下一行，当zhang=1时，取dp[index+1][rest - arr[index]]，当zhang=2时，dp[index+1][rest - arr[index] * 2]
     *   但是当计算rest - arr[index]的位置时（同一行，前面的），当zhang=1时，取dp[index+1][rest - arr[index]]，
     *   当zhang=2时，dp[index+1][rest - arr[index] * 2]
     *   这些已经计算过了，所以当前的解法就是下一行当前位置的解法+当前行前一个rest - arr[index]的解法
     *
     *
     */
    public int way4(int[] arr, int aim) {
        if(arr == null || arr.length <= 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        // 2个可变参数，二维表
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        
        for(int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 等于当前列的下一行的位置的解法
                dp[index][rest] = dp[index+1][rest];
                // 判断前一个位置是否越界
                if(rest - arr[index] >= 0) {
                    // 当前位置的下一行的解法 + 当前行的前一个剩余空间的间隔的解法，因为是重复计算，所以加上同一行的前一个位置即可
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }

}
