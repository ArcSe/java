package com.javarush.task.task25.task2506;

public class LoggingStateThread extends Thread{
    Thread target;
    State copy = null;

    public LoggingStateThread(Thread target) {
        this.target = target;
    }

    @Override
    public void run() {
        while (true) {
            if(copy != target.getState()) {
                System.out.println(target.getState());
                copy = target.getState();
            }
            if(target.getState() == State.TERMINATED){
                System.out.println(target.getState());
                break;
            }
        }
    }
}
