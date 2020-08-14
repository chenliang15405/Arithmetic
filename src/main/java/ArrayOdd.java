import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author alan.chen
 * @date 2020/7/24 2:34 PM
 */
public class ArrayOdd {

    public static void main(String[] args) {
        //ArrayOdd();
        List<Integer> list = new ArrayList<>();

        fillEle(list);

        long start = System.currentTimeMillis();

        //for (Integer i : list) {
        //    System.out.println(i);
        //}
        list.stream().forEach(System.out::println);


        System.out.println(System.currentTimeMillis() - start); // 6184
        // 5166
    }

    private static void fillEle(List<Integer> list) {
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
    }

    private static void ArrayOdd() {
        int[] arr = {1, 3, 2, 9, 5, 6, 6};

        Arrays.sort(arr);

        int[] sortArr = new int[arr.length * 2];

        int p1 = 0;
        int p2 = 1;

        for (int i = 0; i < arr.length; i++) {
            if(arr[i] % 2 == 0) {
                sortArr[p1] = arr[i];
                p1 += 2;
            } else {
                sortArr[p2] = arr[i];
                p2 += 2;
            }
        }

        Arrays.stream(sortArr).forEach(item -> System.out.print(item + " "));
    }

}
