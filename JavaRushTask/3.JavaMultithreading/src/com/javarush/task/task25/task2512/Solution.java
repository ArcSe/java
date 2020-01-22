package com.javarush.task.task25.task2512;

/* 
Живем своим умом
*/
public class Solution implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (e != null) {
            t.interrupt();
            uncaughtException(t,e.getCause());
            System.out.println(e);

        }
       // System.out.println(stack[0].);
    }

    public static void main(String[] args) {
        new Solution().uncaughtException(Thread.currentThread(),new Exception("ABC", new RuntimeException("DEF", new IllegalAccessException("GHI"))));
    }
}
