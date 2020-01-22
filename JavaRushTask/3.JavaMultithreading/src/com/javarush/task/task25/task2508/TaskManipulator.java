package com.javarush.task.task25.task2508;

public class TaskManipulator implements Runnable, CustomThreadManipulator{
    private Thread thread;
    @Override
    public void start(String threadName) {
        Thread newThread = new Thread(this);
        newThread.setName(threadName);
        thread = newThread;
        thread.start();

    }

    @Override
    public void stop() {
        thread.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println(thread.getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
