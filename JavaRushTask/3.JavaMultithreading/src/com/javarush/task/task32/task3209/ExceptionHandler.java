package com.javarush.task.task32.task3209;

public class ExceptionHandler  {
    private Exception e;
    public ExceptionHandler(Exception e) {
        this.e = e;
    }

    public static void log(Exception e){
        System.out.println(e.toString());
    }
}
