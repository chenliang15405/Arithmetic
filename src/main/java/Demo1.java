import java.util.HashMap;
import java.util.Map;

/**
 * @author alan.chen
 * @date 2020/7/24 7:40 PM
 */
public class Demo1 {

    public static void main(String[] args) {
        String str = "abcbbbccccccafewr";
        System.out.println(getMaxLength(str));

        System.out.println(getMax(str));

        Map<Integer, String> map = new HashMap<>();
        Integer key = 1;
        String value = "123";

        map.put(key, value);

        System.out.println(map);

        key = null;

        System.out.println(map);

    }

    public static int getMax(String str) {
        if(str == null || str.length() == 0) {
            return -1;
        }
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;
        int max = 0;

        for (int i = 0; i < str.length(); i++) {
            if(map.containsKey(str.charAt(i))) {
                left = Math.max(map.get(str.charAt(i)) + 1, left);
            }
            map.put(str.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }


    public static int getMaxLength(String str) {
        if(str == null || str.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }
            map.put(c, i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

}
