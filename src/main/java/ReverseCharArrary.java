/**
 * @author alan.chen
 * @date 2020/7/20 6:22 PM
 */
public class ReverseCharArrary {

    private static char[] arr = {'t','h','i','s',' ','i','s',' ','a',' ','b','o','o','k'};

    public static void main(String[] args) {

        for (int i = 0; i < arr.length / 2; i++) {
            swap(arr, i, arr.length - i - 1);
        }

        System.out.println(arr);

        int k = 0;
        // 将整体反转
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == ' ') {
                // 反转每个单词v
                for (int j = i - 1; j >= k; j--) {
                    swap(arr, j, k++);
                }
                k = i + 1;
            }
        }
        // 将最后的一个单词反转
        if(k < arr.length) {
            for (int j = arr.length - 1; j >= k; j--) {
                swap(arr, j, k++);
            }
        }

        System.out.println(arr);
    }


    private static void swap(char[] arr, int x, int y) {
        char temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }


}
