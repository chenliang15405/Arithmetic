import java.util.ArrayList;
import java.util.List;

/**
 * @author alan.chen
 * @date 2020/8/7 1:43 PM
 */
public class RedPacket {

    public static void main(String[] args) {

        redPacket(100, 10);
    }


    /**
     * 二倍均值法 抢红包算法
     * @param totalMoney
     * @param totalPeple
     */
    public static void redPacket(int totalMoney, int totalPeple) {
        List<Integer> list = new ArrayList<>();

        int curPeople = totalPeple;

        // 只随机前n-1个人的，最后一个获取剩下的所有的数据
        for (int i = 0; i < totalPeple - 1; i++) {
            // 得到 [1, 平均金额的2倍]
            int acount = (int) (Math.random() * (totalMoney / curPeople * 2 - 1) + 1);
            totalMoney -= acount;
            curPeople--;
            list.add(acount);
        }
        // 最后一个人获取到剩下的所有金额
        list.add(totalMoney);


        System.out.println(list);

        System.out.println(list.stream().reduce((x, y) -> x + y).get());
    }

}
