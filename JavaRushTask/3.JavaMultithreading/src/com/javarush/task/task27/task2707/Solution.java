package com.javarush.task.task27.task2707;

/* 
Определяем порядок захвата монитора
*/
public class Solution {
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    public static boolean isLockOrderNormal(final Solution solution, final Object o1, final Object o2) throws Exception {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (o1) {
                        Thread.sleep(50);
                        synchronized (o2) {
                            solution.someMethodWithSynchronizedBlocks(o1, o2);
                        }
                    }
                }
                catch (InterruptedException e){
                    e.getStackTrace();
                }
            }
        };
        thread.start();
        Thread.sleep(10);
        new Thread(){
            @Override
            public void run() {
                solution.someMethodWithSynchronizedBlocks(o1, o2);
            }
        }.start();
        Thread.sleep(100);
        return thread.getState() != Thread.State.BLOCKED;
    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isLockOrderNormal(solution, o1, o2));
    }
}
