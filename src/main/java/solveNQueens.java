
/**
 * 渲染指定坐标为newColor，并将上下左右相同的数字也渲染为新坐标，如果不相同则不渲染
 *
 * @author alan.chen
 * @date 2020/7/31 3:42 PM
 */
public class solveNQueens {

    public static void main(String[] args) {
        int[][] image = {
                {1,1,1},{1,1,0},{1,0,1}
        };
        int sr = 1;
        int sc = 1;
        int newColor = 2;

        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[i].length; j++) {
                if(i == sr && j == sc && image[i][j] != newColor) {
                    infect(image, sr, sc, newColor, image[sr][sc]);
                }
            }
        }

        for (int[] ints : image) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

    }

    private static void infect(int[][] image, int sr, int sc, int newColor, int oldColor) {
        if(sr < 0 || sr >= image.length || sc < 0 || sc >= image[0].length || image[sr][sc] != oldColor) {
            return;
        }
        image[sr][sc] = newColor;
        infect(image, sr+1, sc, newColor, oldColor);
        infect(image, sr, sc+1, newColor, oldColor);
        infect(image, sr-1, sc, newColor, oldColor);
        infect(image, sr, sc-1, newColor, oldColor);
    }

}

