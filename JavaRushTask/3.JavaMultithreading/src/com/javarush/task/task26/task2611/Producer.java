package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;

public class Producer implements Runnable{
    private ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        Integer i = 1;
        try {
            while (true) {
                map.put(i.toString(), "Some text for " + i);
                i++;
                Thread.sleep(500);
            }
        }
        catch (Exception e){
            System.out.println(Thread.currentThread().getName() + "thread was terminated");
        }

    }
}
