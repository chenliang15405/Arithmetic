package com.arithmetic.features.arithmetic.interview;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 猫狗队列，实现一个队列，三个方法 pollCat、pollCat、pollAll， 按照先后顺序依次输出
 *
 * @author alan.chen
 * @date 2020/6/7 4:30 PM
 */
public class CarDogQueue {

    public static class Pet {
        private String type;

        public Pet(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Dog extends Pet {
        public Dog() {
            super("dog");
        }
    }

    public static class Cat extends Pet {
        public Cat() {
            super("cat");
        }
    }

    public static class PetEnterQueue {
        private Pet pet;
        private long count;

        public PetEnterQueue(Pet pet, long count) {
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet() {
            return pet;
        }

        public void setPet(Pet pet) {
            this.pet = pet;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }

    public static class DogCatQueue {
        private Queue<PetEnterQueue> dogQueue;
        private Queue<PetEnterQueue> catQueue;
        private long count;

        public DogCatQueue() {
            dogQueue = new LinkedList<>();
            catQueue = new LinkedList<>();
            count = 0;
        }

        public void add(Pet pet) {
            if(pet.getType().equals("dog")) {
                this.dogQueue.add(new PetEnterQueue(pet, count++));
            } else if(pet.getType().equals("cat")) {
                this.catQueue.add(new PetEnterQueue(pet, count++));
            }
        }

        public Pet pollAll() {
            if(!dogQueue.isEmpty() && !catQueue.isEmpty()) {
                if(this.dogQueue.peek().getCount() < this.catQueue.peek().getCount()) {
                    return this.dogQueue.poll().getPet();
                } else {
                    return this.catQueue.poll().getPet();
                }
            } else if(!dogQueue.isEmpty()) {
                return this.dogQueue.poll().getPet();
            } else if(!catQueue.isEmpty()) {
                return this.catQueue.poll().getPet();
            }
            return null;
        }

        public Dog pollDog() {
            if(!dogQueue.isEmpty()) {
                return (Dog) dogQueue.poll().getPet();
            }
            return null;
        }

        public Cat pollCat() {
            if(!catQueue.isEmpty()) {
                return (Cat) catQueue.poll().getPet();
            }
            return null;
        }
    }



}
