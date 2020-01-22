package com.javarush.task.task25.task2514;

/* 
Первый закон Финэйгла: если эксперимент удался, что-то здесь не так...
*/
public class Solution {
    public static class YieldRunnable implements Runnable {
        private int index;

        public YieldRunnable(int index) {
            this.index = index;
        }

        public void run() {
            System.out.println("begin-" + index);
            Thread.yield();
            System.out.println("end-" + index);
        }
    }

    public static void main(String[] args) {
        YieldRunnable yieldRunnable = new YieldRunnable(1);
        YieldRunnable yieldRunnable1 = new YieldRunnable(2);
        Thread thread1 = new Thread(yieldRunnable);
        Thread thread2 = new Thread(yieldRunnable1);
        thread1.start();
        thread2.start();

    }
}
