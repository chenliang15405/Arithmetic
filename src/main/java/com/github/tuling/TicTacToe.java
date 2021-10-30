package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/5 16:37
 */
public class TicTacToe {

    /**
     * 井字游戏，两个玩家，分别持有X 和 O 两种棋子，其中X棋子先走，当其中的一个棋子满足横向、纵向、对角线3个之后就表示赢了，
     * 那么给定一个棋盘，给定一个3X3的棋盘，计算当前的棋盘中是否有赢家
     *
     */

    @Test
    public void test() {
//        String[] strings = new String[]{"XXX", "OXO", " OO"};
        String[] strings = new String[]{"XXX", "OXO", "XOO"};
        System.out.println(validBoard(strings));
    }

    /**
     * 如果X棋子赢的话，那么X棋子先走，所以X棋子会比O棋子多一个，如果O棋子赢的话，那么X棋子和O棋子的数量一样多
     * 并且赢的条件只有横向、纵向、对侥幸
     *
     */
    private boolean validBoard(String[] board) {
        // 计算X和O棋子的数量
        int xCount = 0;
        int oCount = 0;
        for (String s : board) {
            for (char c : s.toCharArray()) {
                if(c == 'X') {
                    xCount++;
                } else if(c == 'O') {
                    oCount++;
                }
            }
        }

        // 判断当前是否有赢的棋子
        // 如果有3个X的棋子，那么X的棋子的数量必定比O的数量多1个，因为X先手
        if(win(board, "XXX") && xCount - oCount == 1) {
            return true;
        }
        // 如果有3个O的棋子，那么X的棋子和O的棋子相同数量，因为X先手
        if(win(board, "OOO") && xCount - oCount == 0) {
            return true;
        }
        return false;
    }


    private boolean win(String[] board, String chess) {
        for (int i = 0; i < board.length; i++) {
            // 判断是否有横向赢的棋子
            if(chess.equals(board[i])) {
                return true;
            }
            // 判断是否有纵向赢的棋子
            if(chess.equals("" + board[0].charAt(i) + board[1].charAt(i) + board[2].charAt(i))) {
                return true;
            }
        }
        // 判断是否有对角线的棋子
        if(chess.equals("" + board[0].charAt(0) + board[1].charAt(1) + board[2].charAt(2))) {
            return true;
        }
        if(chess.equals("" + board[0].charAt(2) + board[1].charAt(1) + board[2].charAt(0))) {
            return true;
        }
        return false;
    }


}
