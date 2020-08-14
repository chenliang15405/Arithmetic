/**
 * @author alan.chen
 * @date 2020/7/18 3:07 PM
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(fiborNum(45));

        System.out.println(dp(45));

        String str = Integer.toBinaryString(2);
        System.out.println(str);

    }

    /**
     * 爬楼梯
     * @param n
     * @return
     */
    private static int dp(int n) {
        int x = 0;
        int y = 0;
        int z = 1;

        for (int i = 1; i <= n; i++) {
            x = y;
            y = z;
            z = x + y;
        }
        return z;
    }


    private static int fiborNum(int n) {
        if(n == 1) {
            return 1;
        }
        if(n == 2) {
            return 2;
        }
        return fiborNum(n - 1) + fiborNum(n - 2);
    }
}
