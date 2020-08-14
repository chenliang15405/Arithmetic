package com.arithmetic.features.producerConsumer;

/**
 * @author alan.chen
 * @date 2020/7/25 12:54 PM
 */
public class PcTest {

    public static void main(String[] args) {

        //Pc2();

        Pc();
    }

    private static void Pc2() {
        PC2<Integer> pc = new PC2<>();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pc.produce();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pc.consume();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pc.produce();
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pc.consume();
            }
        }, "DD").start();
    }


    /**
     * 生产一个，消费一个，交替执行
     */
    private static void Pc1() {
        PC1<Integer> pc = new PC1<>();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pc.produce(i);
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pc.consume();
            }
        }, "BB").start();
    }

    /**
     * 生产一轮，消费一轮
     */
    private static void Pc() {
        PC<Integer> pc = new PC<>();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                pc.put(i);
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                pc.get();
            }
        }, "BB").start();
    }
}
