/**
 * @author alan.chen
 * @date 2020/7/22 3:27 PM
 */
public class MartixSetZero {

    public static void main(String[] args) {

        int[][] matrix = new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };

        setZeroes(matrix);

    }


    public static void setZeroes(int[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return;
        }
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 0 && !visited[i][j]) {
                    visited[i][j] = true;
                    infect(matrix, i, j, visited);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }


    private static void infect(int[][] matrix, int i, int j, boolean[][] visited) {
        for (int k = 0; k < matrix[i].length; k++) {
            if(matrix[i][k] != 0) {
                visited[i][k] = true;
                matrix[i][k] = 0;
            }
        }
        for (int k = 0; k < matrix.length; k++) {
            if(matrix[k][j] != 0) {
                visited[k][j] = true;
                matrix[k][j] = 0;
            }
        }
    }
}
