package com.arithmetic.features.arithmetic.interview.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 设计一种结构，在该结构中有功能：
 * 1. insert(key) 将key加入结构，key不重复
 * 2. delete(key) 删除key
 * 3. getRandom() 等概率随机的返回结构中的任意一个key
 *
 *  要求insert、delete、getRandom方法都是O(1)
 *
 * @author alan.chen
 * @date 2020/6/14 7:13 PM
 */
public class RandomPool {


    @Test
    public void test() {
        RandomPoolStructure<String> randomPool = new RandomPoolStructure<>();

        randomPool.insert("1");
        randomPool.insert("2");
        randomPool.insert("3");
        randomPool.insert("4");
        randomPool.insert("5");

        randomPool.delete("1");

        System.out.println(randomPool.getRandom());
        System.out.println(randomPool.getRandom());
        System.out.println(randomPool.getRandom());


    }


    @Test
    public void test3() {
        int[] arr = {4,5,6,7,0,1,2};
        System.out.println(search(arr, 0));
    }


    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;

        while(left <= right) {
            int mid = left + (right - left) / 2;

            if(nums[mid] == target) {
                return mid;
            }
            if(nums[left] < nums[mid]) {
                // 在左边递增
                // 二分查找
                if(nums[left] < target && nums[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if(nums[mid] < target && nums[right] > target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 设计一种结构，在该结构中有功能：
     * @param <K>
     */
    static class RandomPoolStructure<K> {

        /**
         * 要getRandom是O(1)时间复杂度，则需要2个map
         */
        private Map<K, Integer> keyIndexMap; // 记录数据map
        private Map<Integer, K> indexKeyMap; // 随机访问map
        private int size;  // 定义元素的数量

        public RandomPoolStructure() {
            keyIndexMap = new HashMap<>();
            indexKeyMap = new HashMap<>();
            size = 0;
        }


        /**
         * 插入元素
         * @param key
         */
        public void insert(K key) {
            if(!keyIndexMap.containsKey(key)) {
                keyIndexMap.put(key, size);
                indexKeyMap.put(size, key);
                size++;
            }
        }


        /**
         * 获取随机元素
         *
         * @return
         */
        public K getRandom() {
            // 根据添加元素的数量获取随机数
            int index = (int) (Math.random() * size); // 0 ~ size-1
            // 从维护位置的map中获取一个key
            K k = indexKeyMap.get(index);
            return k;
        }


        /**
         * 删除元素，注意删除后还是保证可以返回随机数据，中间不能有null
         * @param key
         */
        public void delete(K key) {
            // 删除元素时，因为删除就会出现中间有null，就不能保证Random返回随机数据，所以
            // 将最后一个数据填充到删除的数据中，删除元素的size保证不变，因为随机数取值，要保证连续性

            // 记录删除key的value（位置数据）
            Integer removeIndex = keyIndexMap.get(key);
            // 获取最后一个数
            int lastIndex = --size; // size每次insert会++，所以--就对应最后一个数
            K lastKey = indexKeyMap.get(lastIndex);

            // 删除该元素
            keyIndexMap.remove(key);
            indexKeyMap.remove(lastIndex);
            // 将最后一个元素和删除元素的位置重新加入map中
            keyIndexMap.put(lastKey, removeIndex);
            indexKeyMap.put(removeIndex, lastKey);

            /**
             * 先put 覆盖掉原来的数据，然后remove需要删除的数据
             */
            //keyIndexMap.put(lastKey, removeIndex);
            //indexKeyMap.put(removeIndex, lastKey);
            //keyIndexMap.remove(key);
            //indexKeyMap.remove(lastIndex);
        }

    }

}
