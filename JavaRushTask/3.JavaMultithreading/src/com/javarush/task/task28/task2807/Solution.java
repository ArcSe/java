package com.javarush.task.task28.task2807;

import java.util.concurrent.*;

/*

Знакомство с ThreadPoolExecutor
*/
public class Solution {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Runnable> list = new LinkedBlockingQueue<>();
        //service.
        for (int i = 1; i <11 ; i++) {
            final int num = i;
            list.add(new Runnable() {
                @Override
                public void run() {
                    doExpensiveOperation(num);
                }
            });
        }
        ThreadPoolExecutor  service = new ThreadPoolExecutor(3, 5, 1000, TimeUnit.MILLISECONDS,list);
        service.prestartAllCoreThreads();
        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);


        // Add your code here

        /* Example output
pool-1-thread-2, localId=2
pool-1-thread-3, localId=3
pool-1-thread-1, localId=1
pool-1-thread-3, localId=5
pool-1-thread-2, localId=4
pool-1-thread-3, localId=7
pool-1-thread-1, localId=6
pool-1-thread-3, localId=9
pool-1-thread-2, localId=8
pool-1-thread-1, localId=10
         */
    }

    private static void doExpensiveOperation(int localId) {
        System.out.println(Thread.currentThread().getName() + ", localId=" + localId);
    }
}
