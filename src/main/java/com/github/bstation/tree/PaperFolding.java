package com.github.bstation.tree;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/22 14:51
 */
public class PaperFolding {

    /**
     * 请把一段纸竖着放在书桌上，然后从纸条下边向上对折一次，压出折痕后展开，此时折痕是凹下去的，即折痕突起的方向指向纸条的背面，
     * 如果从纸条的下边连续对折2次，演出折痕后展开，此时有3条折痕，从上到下依次是下折痕，下折痕和上折痕
     *
     * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次，请从上到下打印所有折痕的方向
     */

    @Test
    public void test() {
        int N = 3;
        printProcess(N);
    }



    /**
     * 打印所有折痕
     *  根据折痕的凹凸可以看到，如果有3次，那么折痕就是 凹凹凸凹凸凹凸，按照层序遍历，那么可以构建成一颗二叉树
     *       凹
     *   凹       凸
     * 凹  凸   凹   凸
     *
     *  规律就是左子树都是凹节点，右子树都是凸节点
     * 可以看到，如果按照中序遍历进行打印，就是从上到下的所有折痕，所以需要在脑子里构建一颗二叉树，按照中序遍历的方式进行打印
     *
     *
     */
    public void printProcess(int N) {
        if(N <= 0) {
            return;
        }
        // 从第一层开始，头节点是凹节点，所以是true
        process(1, N, true);
    }

    /**
     * 递归过程，表示到了某一个节点
     * i：表示当前节点的层数
     * N：表示当前一共的层数
     * down：true: 凹节点 false: 凸节点
     *
     */
    private void process(int i, int N, boolean down) {
        // 需要中序遍历，所以需要先递归到最左子节点
        if(i > N) {
            return;
        }
        // 因为二叉树的左子节点都是凹，所以是true
        process(i + 1, N, true);
        System.out.println(down ? "凹" : "凸");
        process(i + 1, N, false);
    }


}
